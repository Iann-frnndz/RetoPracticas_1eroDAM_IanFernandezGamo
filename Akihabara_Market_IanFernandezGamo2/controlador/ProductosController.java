package controlador;

import java.util.List;

import modelo.LlmService;
import modelo.ProductoDAO;
import modelo.ProductoOtaku;
import modelo.SetupDatos;
import vista.InterfazConsola;



public class ProductosController {
	// Primero debemos de añadir los productos de ejemplo en el caso de que no exitan ningunos en nuestro programa.
 
    
 // Iniciamos los objetos para poder utilizarlos posteriormente
 		InterfazConsola consola = new InterfazConsola();    
         ProductoDAO dao = new ProductoDAO();
         LlmService ia = new LlmService();
         
         public void ejecutarProductos() {
        	 
        	// Primero debemos de añadir los productos de ejemplo en el caso de que no exitan ningunos en nuestro programa.
        	 SetupDatos.PrimerosProductos(dao);
    // declaramos la variable de salir para que mientras esta sea falsa, no salga del programa
    boolean salir = false;
    while (salir == false) {
    	
    	// Mandamos el menu de productos
    	consola.menuProductos();
    	int opcion = consola.leerOpcion();
    	
    	switch(opcion) {
    	
    	case 1:// añadimos un producto llamando a InterfazConsola para pedirle los datos que ingresamos
    		consola.mostrarMensaje("AÑADIR PRODUCTO");
    		ProductoOtaku producto = consola.pedirDatosProducto();
    		dao.agregarProducto(producto);
    		consola.mostrarMensaje("producto añadido :)");
    		break;
    	
    	case 2: // vamos a buscar un producro especifico por un ID llamando a la consola
    		consola.mostrarMensaje("BUSCAR POR ID");
    		int consulta = consola.pedirIdProducto();
    		ProductoOtaku productoConsulta = dao.obtenerProductoPorId(consulta);
    		consola.mostrarUnProducto(productoConsulta);
    		break;
    		
    	case 3: // queremos mostrar todos los productos en un formato de lista
    		consola.mostrarMensaje("TODOS LOS PRODUCTOS");
    		List<ProductoOtaku> productos = dao.obtenerTodosLosProductos();
    		consola.mostrarListaProductos(productos);
    		break;
    		
    	case 4: // vamos a actualizar un producto dado un ID. Podemos cambiar todos sus campos excepto su ID
    		consola.mostrarMensaje("ACTUALIZAR PRODUCTO");
    		int actualizado = consola.pedirIdProducto();
    		ProductoOtaku productoActualizar = dao.obtenerProductoPorId(actualizado);
    		if(productoActualizar != null) {// en el caso de que el ID se encuentre se van a pedir los nuevos datos
    			consola.mostrarMensaje("Ingresa los datos para actualizar el producto deseado");
    			ProductoOtaku productoActualizado = consola.pedirDatosProducto();
    			productoActualizado.setId(actualizado);
    			
    			if(dao.actualizarProducto(productoActualizado)) {
    				consola.mostrarMensaje("Producto actualizado");
    				
    			}else {
    				consola.mostrarMensaje("ERROR intentalo de nuevo");
    				
    			}
    		
    		}else {// si no se intruduce el ID valido no aparece como correctio
    			consola.mostrarMensaje("El producto no se encuentra en el catalogo");
    			
    		}
    		break;
    		
    	case 5: // ELIMINAR PRODUCTO 
    		
    		consola.mostrarMensaje("ELIMINAR PRODUCTO");
    		int eliminar = consola.pedirIdProducto();// si el ID qeu se le pide al usuario existe 
    		if(dao.eliminarProducto(eliminar)) {// este se eliminará
    			consola.mostrarMensaje("Producto eliminado :)");
    			
    		}else {
    			consola.mostrarMensaje("ERROR vuelve a intentarlo");
    			
    		}
    		break;
    		
    	case 6: // FUNCIONES DE IA 
    	    consola.mostrarMensaje("FUNCIONES IA");
    	    
    	    // primero creamos un pequeño menu para elegir entre las 2 opciones de IA que tenemos 
    	    System.out.println("1. Generar descripción de producto con IA");
    	    System.out.println("2. Sugerir categoría para producto con IA");
    	    System.out.print("Selecciona una opción: ");
    	    int subOpcion = consola.leerOpcion();

    	    if (subOpcion == 1) {
    	        int idDesc = consola.pedirIdProducto();
    	        ProductoOtaku prodDesc = dao.obtenerProductoPorId(idDesc);
    	        if (prodDesc != null) {
    	        	// le mandamos un prompt a nuestra IA para que genere lo que nosotros queremois a partir del producto que le solicitamos 
    	            String promptDesc = "Genera una descripción de marketing breve y atractiva para el producto otaku (en español): "
    	                    + prodDesc.getNombre() + " de la categoría " + prodDesc.getCategoria() + ".";
    	            String respuestaDesc = ia.enviarPrompt(promptDesc);
    	            // mostramos la descripcion
    	            consola.mostrarMensaje("Descripción generada por IA:\n" + respuestaDesc);
    	        } else {
    	            consola.mostrarMensaje("Producto no encontrado.");
    	        }
    	    } else if (subOpcion == 2) {
    	        System.out.print("Introduce el nombre del nuevo producto: ");
    	        String nombreNuevo = consola.leerTextoIA();
    	        String promptCat = "Para un producto otaku llamado '" + nombreNuevo
    	                + "', sugiere una categoría adecuada de esta lista: Figura, Manga, Póster, Llavero, Ropa, Videojuego, Otro.";
    	        String respuestaCat = ia.enviarPrompt(promptCat);
    	        consola.mostrarMensaje("Categoría sugerida por IA:\n" + respuestaCat);
    	    } else {
    	        consola.mostrarMensaje("Opción inválida para funciones IA.");
    	    }
    	    break;
    		
    	case 7:// SALIR la variable salir se vuelve true y el programa se cierra
    		consola.mostrarMensaje("SALIR");
    		salir = true;
    		break;
    	}
    }
}
}
