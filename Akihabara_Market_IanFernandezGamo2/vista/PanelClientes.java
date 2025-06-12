package vista;


import modelo.ClienteDAO;
import modelo.ClienteOtaku;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;


public class PanelClientes extends JPanel {

    // Declaración de variables
    private ClienteDAO clienteDAO;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    private JTextField tfNombre, tfEmail, tfTelefono, tfFechaRegistro;
    private JTextField tfIdBuscar, tfIdActualizar, tfIdEliminar;

    // Constructor
    public PanelClientes() {
        clienteDAO = new ClienteDAO(); 
        setLayout(new BorderLayout()); // Establece layout principal

        // Panel para el formulario de ingreso de datos
        JPanel panelFormulario = new JPanel(new GridLayout(2, 5, 10, 10));
        tfNombre = new JTextField();
        tfEmail = new JTextField();
        tfTelefono = new JTextField();
        tfFechaRegistro = new JTextField();

        JButton btnAgregar = new JButton("Agregar Cliente");

        // Añadir etiquetas al formulario
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(new JLabel("Email:"));
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(new JLabel("Fecha Registro (año-mes-dia):"));
        panelFormulario.add(new JLabel("")); // Espacio vacío para el botón

        // Añadir campos de texto y botón al formulario
        panelFormulario.add(tfNombre);
        panelFormulario.add(tfEmail);
        panelFormulario.add(tfTelefono);
        panelFormulario.add(tfFechaRegistro);
        panelFormulario.add(btnAgregar);

        // Añadir formulario al panel principal
        add(panelFormulario, BorderLayout.NORTH);

        // Crear el modelo de la tabla con encabezados
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Email", "Teléfono", "Fecha Registro"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que las celdas no sean editables dewsde el propio interfaz
            }
        };

        // Crear la tabla con el modelo y añadirla con scroll
        tablaClientes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        add(scrollPane, BorderLayout.CENTER);

        // Panel para acciones de buscar, actualizar y eliminar
        JPanel panelAcciones = new JPanel(new GridLayout(3, 3, 10, 10));
        tfIdBuscar = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        tfIdActualizar = new JTextField();
        JButton btnActualizar = new JButton("Actualizar");
        tfIdEliminar = new JTextField();
        JButton btnEliminar = new JButton("Eliminar");

        // Añadir componentes al panel de acciones
        panelAcciones.add(new JLabel("ID para buscar:"));
        panelAcciones.add(tfIdBuscar);
        panelAcciones.add(btnBuscar);

        panelAcciones.add(new JLabel("ID para actualizar:"));
        panelAcciones.add(tfIdActualizar);
        panelAcciones.add(btnActualizar);

        panelAcciones.add(new JLabel("ID para eliminar:"));
        panelAcciones.add(tfIdEliminar);
        panelAcciones.add(btnEliminar);

        
        add(panelAcciones, BorderLayout.SOUTH);

        // Cargar datos iniciales en la tabla
        cargarDatosTabla();

        // Agregar listeners a los botones
        btnAgregar.addActionListener(e -> agregarCliente());
        btnBuscar.addActionListener(e -> buscarClientePorId());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());

        // Establecer colores a los botones y tabla 
        btnAgregar.setBackground(new Color(46, 204, 113));
        btnBuscar.setBackground(new Color(0, 199, 255));
        btnEliminar.setBackground(new Color(255, 102, 102));
        btnActualizar.setBackground(new Color(204, 153, 255));

        tablaClientes.setBackground(new Color(255, 255, 204));
        tablaClientes.setSelectionBackground(new Color(255, 204, 153));
        tablaClientes.getTableHeader().setBackground(new Color(0, 199, 255));
    }

    // Método para cargar los datos en la tabla desde la base de datos
    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0); 
        List<ClienteOtaku> lista = clienteDAO.obtenerTodosLosclientes(); 
        for (ClienteOtaku c : lista) {
            modeloTabla.addRow(new Object[]{
                    c.getId(),
                    c.getNombre(),
                    c.getEmail(),
                    c.getTelefono(),
                    c.getFechaRegistro()
            });
        }
    }

    // Método para agregar un nuevo cliente
    private void agregarCliente() {
        try {
            String nombre = tfNombre.getText().trim();
            String email = tfEmail.getText().trim();
            String telefono = tfTelefono.getText().trim();
            String fechaStr = tfFechaRegistro.getText().trim();

            if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty() || fechaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate fechaRegistro;
            try {
                fechaRegistro = LocalDate.parse(fechaStr);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido (use yyyy-MM-dd)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ClienteOtaku cliente = new ClienteOtaku(nombre, email, telefono, fechaRegistro); // Crear nuevo cliente
            clienteDAO.agregarcliente(cliente);
            limpiarCamposAgregar(); 
            cargarDatosTabla(); 

            JOptionPane.showMessageDialog(this, "Cliente agregado correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para buscar cliente por ID
    private void buscarClientePorId() {
        try {
            int id = Integer.parseInt(tfIdBuscar.getText().trim()); 
            ClienteOtaku cliente = clienteDAO.obtenerclientePorId(id); 
            if (cliente != null) {
                modeloTabla.setRowCount(0); 
                modeloTabla.addRow(new Object[]{
                        cliente.getId(),
                        cliente.getNombre(),
                        cliente.getEmail(),
                        cliente.getTelefono(),
                        cliente.getFechaRegistro()
                });
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Introduce un ID válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void actualizarCliente() {
        try {
            int id = Integer.parseInt(tfIdActualizar.getText().trim());
            ClienteOtaku existente = clienteDAO.obtenerclientePorId(id);

            if (existente == null) {
                JOptionPane.showMessageDialog(this, "Cliente no existente", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear campos con datos actuales
            JTextField tfNombre = new JTextField(existente.getNombre());
            JTextField tfEmail = new JTextField(existente.getEmail());
            JTextField tfTelefono = new JTextField(existente.getTelefono());
            JTextField tfFecha = new JTextField(existente.getFechaRegistro().toString());

            Object[] mensaje = {
                    "Nombre:", tfNombre,
                    "Email:", tfEmail,
                    "Teléfono:", tfTelefono,
                    "Fecha Registro (yyyy-MM-dd):", tfFecha
            };

            // Mostrar diálogo para actualizar datos
            int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Actualizar Cliente", JOptionPane.OK_CANCEL_OPTION);

            if (opcion == JOptionPane.OK_OPTION) {
                String nombre = tfNombre.getText().trim();
                String email = tfEmail.getText().trim();
                String telefono = tfTelefono.getText().trim();
                String fechaStr = tfFecha.getText().trim();

                if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty() || fechaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LocalDate fechaRegistro;
                try {
                    fechaRegistro = LocalDate.parse(fechaStr); 
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(this, "Formato de fecha inválido (use yyyy-MM-dd)", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear cliente actualizado
                ClienteOtaku actualizado = new ClienteOtaku(id, nombre, email, telefono, fechaRegistro);
                boolean exito = clienteDAO.actualizarcliente(actualizado);

                if (exito) {
                    // Actualizar fila en la tabla visual
                    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                        if ((int) modeloTabla.getValueAt(i, 0) == id) {
                            modeloTabla.setValueAt(nombre, i, 1);
                            modeloTabla.setValueAt(email, i, 2);
                            modeloTabla.setValueAt(telefono, i, 3);
                            modeloTabla.setValueAt(fechaRegistro, i, 4);
                            break;
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar cliente", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Introduce un ID válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar un cliente por ID
    private void eliminarCliente() {
        try {
            int id = Integer.parseInt(tfIdEliminar.getText().trim()); 
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que quieres eliminar el cliente con ID " + id + "?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean exito = clienteDAO.eliminarcliente(id);
                if (exito) {
                    cargarDatosTabla(); 
                    JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo eliminar el cliente. Asegúrate de que el ID existe.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Introduce un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para limpiar campos del formulario de agregar cliente
    private void limpiarCamposAgregar() {
        tfNombre.setText("");
        tfEmail.setText("");
        tfTelefono.setText("");
        tfFechaRegistro.setText("");
    }

}
