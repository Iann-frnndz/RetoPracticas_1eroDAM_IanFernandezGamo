package controlador;

import java.util.List;

import modelo.LlmService;
import modelo.ClienteDAO;
import modelo.ClienteOtaku;
import modelo.SetupDatos;
import vista.InterfazConsola;

public class ClientesController {
	// Iniciamos los objetos para poder utilizarlos posteriormente
	 		InterfazConsola consola = new InterfazConsola();    
	         ClienteDAO dao = new ClienteDAO();
	         LlmService ia = new LlmService();
	         
	    public void ejecutarClientes() {
	        	
	    // declaramos la variable de salir para que mientras esta sea falsa, no salga del programa
	    boolean salir = false;
	    while (salir == false) {
	    	
	    	// Mandamos el menu de clientes
	    	consola.menuClientes();
	    	int opcion = consola.leerOpcion();
	    	
	    	switch(opcion) {
	    	
	    	case 1:// añadimos un cliente llamando a InterfazConsola para pedirle los datos que ingresamos
	    		consola.mostrarMensaje("AÑADIR cliente");
	    		ClienteOtaku cliente = consola.pedirDatosCliente();
	    		dao.agregarcliente(cliente);
	    		consola.mostrarMensaje("cliente añadido :)");
	    		break;
	    	
	    	case 2: // vamos a buscar un producro especifico por un ID llamando a la consola
	    		consola.mostrarMensaje("BUSCAR POR ID");
	    		int consulta = consola.pedirIdCliente();
	    		ClienteOtaku clienteConsulta = dao.obtenerclientePorId(consulta);
	    		consola.mostrarUnCliente(clienteConsulta);
	    		break;
	    		
	    	case 3: // queremos mostrar todos los clientes en un formato de lista
	    		consola.mostrarMensaje("TODOS LOS clienteS");
	    		List<ClienteOtaku> clientes = dao.obtenerTodosLosclientes();
	    		consola.mostrarListaClientes(clientes);
	    		break;
	    		
	    	case 4: // vamos a actualizar un cliente dado un ID. Podemos cambiar todos sus campos excepto su ID
	    		consola.mostrarMensaje("ACTUALIZAR cliente");
	    		int actualizado = consola.pedirIdCliente();
	    		ClienteOtaku clienteActualizar = dao.obtenerclientePorId(actualizado);
	    		if(clienteActualizar != null) {// en el caso de que el ID se encuentre se van a pedir los nuevos datos
	    			consola.mostrarMensaje("Ingresa los datos para actualizar el cliente deseado");
	    			ClienteOtaku clienteActualizado = consola.pedirDatosCliente();
	    			clienteActualizado.setId(actualizado);
	    			
	    			if(dao.actualizarcliente(clienteActualizado)) {
	    				consola.mostrarMensaje("cliente actualizado");
	    				
	    			}else {
	    				consola.mostrarMensaje("ERROR intentalo de nuevo");
	    				
	    			}
	    		
	    		}else {// si no se intruduce el ID valido no aparece como correctio
	    			consola.mostrarMensaje("El cliente no se encuentra en el catalogo");
	    			
	    		}
	    		break;
	    		
	    	case 5: // ELIMINAR cliente 
	    		
	    		consola.mostrarMensaje("ELIMINAR cliente");
	    		int eliminar = consola.pedirIdCliente();// si el ID qeu se le pide al usuario existe 
	    		if(dao.eliminarcliente(eliminar)) {// este se eliminará
	    			consola.mostrarMensaje("cliente eliminado :)");
	    			
	    		}else {
	    			consola.mostrarMensaje("ERROR vuelve a intentarlo");
	    			
	    		}
	    		break;
	    		
    		
	    		
	    	case 6:// SALIR la variable salir se vuelve true y el programa se cierra
	    		consola.mostrarMensaje("SALIR");
	    		salir = true;
	    		break;
	    	}
	    }
	}
}
