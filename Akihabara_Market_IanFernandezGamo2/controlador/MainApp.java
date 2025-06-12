package controlador;

import modelo.LlmService;
import modelo.ProductoDAO;
import modelo.ProductoOtaku;
import vista.InterfazConsola;
import java.util.List;
import modelo.*;


public class MainApp {

	// Iniciamos los objetos para poder utilizarlos posteriormente
		InterfazConsola consola = new InterfazConsola();    
        ProductoDAO dao = new ProductoDAO();
        LlmService ia = new LlmService();
        
    // con este metodo vamos a inicializar todo nuestro programa
    public void ejecutar() {
        
    	// Primero debemos de añadir los productos de ejemplo en el caso de que no exitan ningunos en nuestro programa.
        SetupDatos.PrimerosProductos(dao);
        
        // declaramos la variable de salir para que mientras esta sea falsa, no salga del programa
        boolean salir = false;
        while (salir == false) {
        	
        	// Mandamos el menu inicial
        	consola.menuPrincipal();
        	int opcion = consola.leerOpcion();
        	
        	switch(opcion) {
        	
        	case 1:// Mostramos el menu de CLIENTES
        		ClientesController clientesController = new ClientesController();
        	    clientesController.ejecutarClientes();
        		break;
        	
        	case 2: // Mostramos el menu de PRODUCTOS
        		ProductosController productosController = new ProductosController();
        	    productosController.ejecutarProductos();  
        	    break;
        		
        	case 3:// SALIR la variable salir se vuelve true y el programa se cierra
        		consola.mostrarMensaje("SALIR");
        		salir = true;
        		break;
        	}
        }
	}

}
