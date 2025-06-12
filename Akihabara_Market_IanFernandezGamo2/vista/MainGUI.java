package vista;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel contenedor;

    public MainGUI() {
        setTitle("Akihabara Market");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        // Paneles individuales
        PanelPrincipal panelPrincipal = new PanelPrincipal(this);
        PanelClientes panelClientes = new PanelClientes();
        PanelProductos panelProductos = new PanelProductos(); // Asumimos que ya existe

        // Agregar todos los paneles al contenedor
        contenedor.add(panelPrincipal, "principal");
        contenedor.add(panelClientes, "clientes");
        contenedor.add(panelProductos, "productos");

        add(contenedor);
        setVisible(true);
    }

    public void mostrarPanel(String nombrePanel) {
        cardLayout.show(contenedor, nombrePanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
