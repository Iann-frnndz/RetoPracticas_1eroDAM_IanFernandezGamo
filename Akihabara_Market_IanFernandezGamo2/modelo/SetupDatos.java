package modelo;

// vamos a ingresar los datos de ejemplo que se nos proporcionan en el enunciado 
public class SetupDatos {
    public static void PrimerosProductos(ProductoDAO dao) {
    	// estos solo se cargaran al sistema en el caso de que nuestra BDD se encuentre vacia , esto nos servirá para realizar pruebas en el CRUD
        if (dao.obtenerTodosLosProductos().isEmpty()) {
            dao.agregarProducto(new ProductoOtaku("Figura de Anya Forger", "Figura", 59.95, 8));
            dao.agregarProducto(new ProductoOtaku("Manga Chainsaw Man Vol.1", "Manga", 9.99, 20));
            dao.agregarProducto(new ProductoOtaku("Póster Studio Ghibli Colección", "Póster", 15.50, 15));
            System.out.println("Productos iniciales cargados.");
        }
    }
}