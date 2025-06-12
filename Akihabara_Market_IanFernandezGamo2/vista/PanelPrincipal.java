package vista;

import javax.swing.*;
import java.awt.*;

public class PanelPrincipal extends JPanel {

    private JButton btnClientes;
    private JButton btnProductos;
    

    public PanelPrincipal(MainGUI mainGUI) {
        setLayout(new GridLayout(2, 1, 20, 20));

        btnClientes = new JButton("Clientes");
        btnProductos = new JButton("Productos");

        //paneles principales de clientes y productos
        btnClientes.addActionListener(e -> mainGUI.mostrarPanel("clientes"));
        btnProductos.addActionListener(e -> mainGUI.mostrarPanel("productos"));

        add(btnClientes);
        add(btnProductos);
    }
}
