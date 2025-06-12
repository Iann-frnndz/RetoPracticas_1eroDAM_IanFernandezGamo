package vista;

import modelo.ProductoDAO;
import modelo.ProductoOtaku;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PanelProductos extends JPanel {

    private ProductoDAO productoDAO;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    
    private JTextField tfNombre, tfCategoria, tfPrecio, tfStock, tfIdBuscar, tfIdActualizar, tfIdEliminar;

    public PanelProductos() {
        productoDAO = new ProductoDAO();
        setLayout(new BorderLayout());

        // Panel para agregar un producto nuevo 
        JPanel panelFormulario = new JPanel(new GridLayout(2, 5, 10, 10));
        tfNombre = new JTextField();
        tfCategoria = new JTextField();
        tfPrecio = new JTextField();
        tfStock = new JTextField();

        // boton de agregar producto 
        JButton btnAgregar = new JButton("Agregar Producto");
        
        // añadimos los campos para señalar que debemos ingresar en cada campo
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(new JLabel("") ); // añadimos el espacio para que nos quede mas centrado
        
        // añadimos los campos de nuestra BDD en text field
        panelFormulario.add(tfNombre);
        panelFormulario.add(tfCategoria);
        panelFormulario.add(tfPrecio);
        panelFormulario.add(tfStock);
        panelFormulario.add(btnAgregar);
        
        // centramos el panel del formulario de agregar al norte
        add(panelFormulario, BorderLayout.NORTH);

       // generamos el modelo de nuestra tabla ingresando los datos que queemos mostrar en ella
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Categoría", "Precio", "Stock"}, 0) {
            @Override
         // hacemos que la tabla no sea editable a menos de que seleccionamos actualizar
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        // mostramos los datos de la tabla y los centramos en el centro
        tablaProductos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        add(scrollPane, BorderLayout.CENTER);

        // creamos el panel inferior en el quie vamos a incluir buscar, actualizar y eliminar
        JPanel panelAcciones = new JPanel(new GridLayout(3, 3, 10, 10));

        tfIdBuscar = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        tfIdActualizar = new JTextField();
        JButton btnActualizar = new JButton("Actualizar");
        tfIdEliminar = new JTextField();
        JButton btnEliminar = new JButton("Eliminar");
        
        // añadimos labels para saber que ingresar 
        panelAcciones.add(new JLabel("ID para buscar:"));
        // este solo va a admitir campos numericos validos
        panelAcciones.add(tfIdBuscar);
        panelAcciones.add(btnBuscar);

        panelAcciones.add(new JLabel("ID para actualizar:"));
        panelAcciones.add(tfIdActualizar);
        panelAcciones.add(btnActualizar);

        panelAcciones.add(new JLabel("ID para eliminar:"));
        panelAcciones.add(tfIdEliminar);
        panelAcciones.add(btnEliminar);
        // este se debe ingresar en el sur de nuestra pantalla
        add(panelAcciones, BorderLayout.SOUTH);

        // cargamos los datos iniciales de nuestra BDD
        cargarDatosTabla();

        // agregamos los listeners para que cuando rellenemos los datos llame a los metodos de nuestro programa 
        btnAgregar.addActionListener(e -> agregarProducto());
        btnBuscar.addActionListener(e -> buscarProductoPorId());
        btnActualizar.addActionListener(e -> actualizarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        
        
        // colores de botones
        btnAgregar.setBackground(new Color(46, 204, 113)); 
        btnBuscar.setBackground(new Color(0, 199, 255));
        btnEliminar.setBackground(new Color (255, 102, 102));
        btnActualizar.setBackground(new Color(204, 153, 255));

        // colores de la tabla
        tablaProductos.setBackground(new Color(255, 255, 204));
        tablaProductos.setSelectionBackground(new Color(255, 204, 153));
        tablaProductos.getTableHeader().setBackground(new Color(0, 199, 255));
    }
    // hacemos el metodo para cargar los datps de la BDD
    private void cargarDatosTabla() {
        List<ProductoOtaku> lista = productoDAO.obtenerTodosLosProductos();
        for (ProductoOtaku p : lista) {
        	// por cada producto que se encuentre en la lista de productos se añade 
            modeloTabla.addRow(new Object[]{p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()});
        }
    }
    
    // agregamos el metodo para agregar a nuestra bdd
    private void agregarProducto() {
        try {
        	// añadimos las variables necesarisas, usamos trim para ignorar espacios antes y despues del texto ingresado
            String nombre = tfNombre.getText().trim();
            String categoria = tfCategoria.getText().trim();
            double precio = Double.parseDouble(tfPrecio.getText().trim());
            int stock = Integer.parseInt(tfStock.getText().trim());
            
            // añadimos las excepciones en el caso de que haya un error al escribir en los campos
            if(nombre.isEmpty() || categoria.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Los campos no pueden estar vacíos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(precio < 0 || stock < 0) {
                JOptionPane.showMessageDialog(this, "Precio y stock no pueden ser negativos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // creamos  un objeto del productoOtaku para agregarlo a la base de datos y posteriorimente cargarlo en la tabla
            ProductoOtaku p = new ProductoOtaku(nombre, categoria, precio, stock);
            productoDAO.agregarProducto(p);
            cargarDatosTabla();
            limpiarCamposAgregar();
            // esto muestra un panel con mensaje 
            JOptionPane.showMessageDialog(this, "Producto agregado correctamente");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio y stock deben ser números válidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo para buscar un producto por un ID
    // busca el id que manda el usuario, en el caso de que este no exista nos da un error 
    private void buscarProductoPorId() {
        try {
            int id = Integer.parseInt(tfIdBuscar.getText().trim());
            ProductoOtaku p = productoDAO.obtenerProductoPorId(id);
            if (p != null) {
                modeloTabla.setRowCount(0);
                modeloTabla.addRow(new Object[]{p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()});
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Introduce un ID válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // metodo para actuaizar el producto
    // debe pruimero comprobar que el ID que se esta ingresando existe y en caso de que no nos manadara un error 
    private void actualizarProducto() {
        try {
            int id = Integer.parseInt(tfIdActualizar.getText().trim());
            ProductoOtaku existente = productoDAO.obtenerProductoPorId(id);

            if (existente == null) {
                JOptionPane.showMessageDialog(this, "Producto no existente", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

         // Cuando verificamos la id nos sale un tabla para que se ingresen los nuevos datos
            JTextField tfNombre = new JTextField(existente.getNombre());
            JTextField tfCategoria = new JTextField(existente.getCategoria());
            JTextField tfPrecio = new JTextField(String.valueOf(existente.getPrecio()));
            JTextField tfStock = new JTextField(String.valueOf(existente.getStock()));
         // poniendo como placeholder el texto ya existente
            Object[] mensaje = {
                "Nombre:", tfNombre,
                "Categoría:", tfCategoria,
                "Precio:", tfPrecio,
                "Stock:", tfStock
            };
         // mensaje de que el producto se ha actualizado correctamente
            int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Producto", JOptionPane.OK_CANCEL_OPTION);
            
         // si los nuevos datos se han ingresado no son correctos vamos a mandar las excepciones
            if (opcion == JOptionPane.OK_OPTION) {
                String nombre = tfNombre.getText().trim();
                String categoria = tfCategoria.getText().trim();
                double precio = Double.parseDouble(tfPrecio.getText().trim());
                int stock = Integer.parseInt(tfStock.getText().trim());

                if (nombre.isEmpty() || categoria.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nombre y categoría no pueden estar vacíos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (precio < 0 || stock < 0) {
                    JOptionPane.showMessageDialog(this, "Precio y stock no pueden ser negativos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
             // pero si estos son correctos nos mandará un mensaje de que los datos actualizados son correctos
                ProductoOtaku actualizado = new ProductoOtaku(id, nombre, categoria, precio, stock);
                boolean exito = productoDAO.actualizarProducto(actualizado);

                if (exito) {
                    // Actualiza la tabla sin duplicar
                    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                        if ((int) modeloTabla.getValueAt(i, 0) == id) {
                            modeloTabla.setValueAt(nombre, i, 1);
                            modeloTabla.setValueAt(categoria, i, 2);
                            modeloTabla.setValueAt(precio, i, 3);
                            modeloTabla.setValueAt(stock, i, 4);
                            break;
                        }
                    }

                    JOptionPane.showMessageDialog(this, "Producto actualizado correctamente");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar producto", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
         // en el caso de que los datos ingresados no sean los correctos mandamos la excepcion
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Introduce datos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // metodo para eliminar el producto
    private void eliminarProducto() {
        try {
        	// primero mandamos un mensaje para confirmar que la eliminacion del producto sea correcta
            int id = Integer.parseInt(tfIdEliminar.getText().trim());
            int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Seguro que quieres eliminar el producto con ID " + id + "?", 
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            
         // se divide en 2 opciones, si la respuesta del usuario es SI y si es NO
            if (confirm == JOptionPane.YES_OPTION) {
                boolean exito = productoDAO.eliminarProducto(id);
                if (exito) {
                    modeloTabla.setRowCount(0);
                    cargarDatosTabla();
                    JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "No se pudo eliminar el producto. Asegúrate de que el ID existe.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Introduce un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

// este metodo limpia los cambios que acabamos de rellenar despues de la agregacion de un nuevo producto 
    private void limpiarCamposAgregar() {
        tfNombre.setText("");
        tfCategoria.setText("");
        tfPrecio.setText("");
        tfStock.setText("");
    }
    

}