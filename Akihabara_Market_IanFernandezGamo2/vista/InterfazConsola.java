package vista;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import modelo.ClienteOtaku;
import modelo.ProductoOtaku;
public class InterfazConsola {

	Scanner scanner = new Scanner(System.in);
	
	public void menuPrincipal() {
		System.out.println("\n===== MENÚ PRINCIPAL =====");
	    System.out.println("1. Menú de CLIENTES");
	    System.out.println("2. Menú de PRODUCTOS");
	    System.out.println("3. Salir");
	    System.out.print("Selecciona una opción: ");
	}
	// creamos el menu de CLIENTES
	public void menuClientes() {
		System.out.println("\n===== MENÚ CLIENTES =====");
	    System.out.println("1. Añadir nuevo cliente");
	    System.out.println("2. Consultar un cliente específico por su ID");
	    System.out.println("3. Ver todos los cliente del sistema");
	    System.out.println("4. Actualizar la información de un cliente existente");
	    System.out.println("5. Eliminar un cliente del sistema");
	    System.out.println("6. Salir");
	    System.out.print("Selecciona una opción: ");
	}
	
	// creamos el menu de PRODUCTOS
	public void menuProductos() {
		System.out.println("\n===== MENÚ PRODUCTOS =====");
	    System.out.println("1. Añadir nuevo producto al inventario");
	    System.out.println("2. Consultar un producto específico por su ID");
	    System.out.println("3. Ver todos los productos del inventario");
	    System.out.println("4. Actualizar la información de un producto existente");
	    System.out.println("5. Eliminar un producto del inventario");
	    System.out.println("6. Asistente de IA");
	    System.out.println("7. Salir");
	    System.out.print("Selecciona una opción: ");
	}
	
	// pedimos los datos al usuario
	public ProductoOtaku pedirDatosProducto() {
		
		// no es necesario que validemos que el nombre y la categoria se encuentren vacias
		// aunque puede ser una buena implementación 
	    System.out.print("Nombre: ");
	    String nombre = scanner.nextLine();
	    System.out.print("Categoría: ");
	    String categoria = scanner.nextLine();
	    
	    // validamos que el precio sea un valor correcto que no acepte Strings ni numeros negativos
	    double precio = 0.0;
	    while (true) {
	        System.out.print("Precio: ");
	        String entrada = scanner.nextLine();
	        try {
	            precio = Double.parseDouble(entrada);
	            if (precio < 0) throw new NumberFormatException();
	            break;
	        } catch (NumberFormatException e) {
	            System.out.println("Error ingresa un número válido");
	        }
	    }
	    
	    // validamos que el STOCK tenga un valor correcto al ingresarlo que no acepte ni negativos, ni strings ni decimales
	    int stock = 0;
	    while (true) {
	        System.out.print("Stock: ");
	        String entrada = scanner.nextLine();
	        try {
	            stock = Integer.parseInt(entrada);
	            if (stock < 0) throw new NumberFormatException();
	            break;
	        } catch (NumberFormatException e) {
	            System.out.println("Error ingresa un número válido");
	        }
	    }
	    
	    //Creamos el objeto para agregar los datos
	    return new ProductoOtaku(nombre, categoria, precio, stock);
	}
	
	// pedimos el ID
	public int pedirIdProducto() {
		System.out.print("Introduce el ID del producto: ");
        while (!scanner.hasNextInt()) {
            System.out.println("ERROR introduce un número válido");
            scanner.next();
        }
        int id = scanner.nextInt();
        scanner.nextLine(); 
        return id;
	}
	
	// lista de productos
	
	public void mostrarListaProductos(List<ProductoOtaku> productos) {
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados");
        } else {
            System.out.println("\n==== LISTA DE PRODUCTOS ====");
            // imprimimos de forma odenada cn PrintF los atributos correctos de cada producto 
            for (ProductoOtaku p : productos) {
                System.out.printf("ID: %d | Nombre: %s | Categoría: %s | Precio: %.2f | Stock: %d\n",
                        p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock());
            }
        }
    }
	
	// mostrar solamente un producto 
	
	public void mostrarUnProducto(ProductoOtaku producto) {
        if (producto != null) {
            System.out.printf("ID: %d | Nombre: %s | Categoría: %s | Precio: %.2f | Stock: %d\n",
                    producto.getId(), producto.getNombre(), producto.getCategoria(),
                    producto.getPrecio(), producto.getStock());
        } else {
            System.out.println("Producto no encontrado.");
        }
    }
	
	// mostrar Mensajes de info, confirmacion o error
	
	public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
	
	// leer las opciones ingresadas por el usuario
	
    public int leerOpcion() {
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor, introduce un número válido: ");
            scanner.next();
        }
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }
	
    // lo utilizaremos para devoler lo que el usuario escribe, lo utilizamos para el asistente de IA
    public String leerTextoIA() {
        return scanner.nextLine();
    }
    
    
 // pedimos los datos al usuario
    public ClienteOtaku pedirDatosCliente() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        LocalDate fechaRegistro = null;
        while (true) {
            System.out.print("Fecha de Registro (año-mes-dia): ");
            String entrada = scanner.nextLine();
            try {
                fechaRegistro = LocalDate.parse(entrada);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Error: formato de fecha inválido. Usa (año-mes-dia)");
            }
        }

        System.out.print("Email: ");
        String email = scanner.nextLine();

        return new ClienteOtaku(nombre, telefono, email, fechaRegistro);
    }
 	
 	// pedimos el ID
 	public int pedirIdCliente() {
 		System.out.print("Introduce el ID del cliente: ");
         while (!scanner.hasNextInt()) {
             System.out.println("ERROR introduce un número válido");
             scanner.next();
         }
         int id = scanner.nextInt();
         scanner.nextLine(); 
         return id;
 	}
 	
 	// lista de productos
 	
 	public void mostrarListaClientes(List<ClienteOtaku> clientes) {
         if (clientes.isEmpty()) {
             System.out.println("No hay productos registrados");
         } else {
             System.out.println("\n==== LISTA DE PRODUCTOS ====");
             // imprimimos de forma odenada cn PrintF los atributos correctos de cada cliente 
             for (ClienteOtaku p : clientes) {
                 System.out.printf("ID: %d | Nombre: %s | Email: %s | Fecha Registro: %s | Telefono: %s\n",
                         p.getId(), p.getNombre(), p.getTelefono(), p.getFechaRegistro(), p.getEmail());
             }
         }
     }
 	
 	// mostrar solamente un producto 
 	
 	public void mostrarUnCliente(ClienteOtaku cliente) {
 	    if (cliente != null) {
 	        System.out.printf("ID: %d | Nombre: %s | Teléfono: %s | Fecha Registro: %s | Email: %s\n",
 	                cliente.getId(), cliente.getNombre(), cliente.getTelefono(),
 	                cliente.getFechaRegistro(), cliente.getEmail());
 	    } else {
 	        System.out.println("Cliente no encontrado.");
 	    }
 	}


 	
 	// mostrar Mensajes de info, confirmacion o error
 	
 	public void mostrarMensajeC(String mensaje) {
         System.out.println(mensaje);
     }
 	
 	// leer las opciones ingresadas por el usuario
 	
     public int leerOpcionC() {
         while (!scanner.hasNextInt()) {
             System.out.print("Por favor, introduce un número válido: ");
             scanner.next();
         }
         int opcion = scanner.nextInt();
         scanner.nextLine();
         return opcion;
     }
 	
     // lo utilizaremos para devoler lo que el usuario escribe, lo utilizamos para el asistente de IA
     public String leerTextoIAC() {
         return scanner.nextLine();
     }
}
