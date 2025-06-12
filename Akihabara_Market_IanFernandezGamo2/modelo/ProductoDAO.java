package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

	// AGREGAR PRODUCTOS
	public void agregarProducto(ProductoOtaku producto) {
		// Ingresamos la query que se ha de realizar en nuestra BDD
        String sql = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
        
        // probamos la conexión y si esta es correcta ejecuta la query
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());

            stmt.executeUpdate();
        } catch (SQLException e) { // en el caso de que la ejecución de la query no sea disponible mandará un mensaje de error
            System.err.println("Error al agregar producto: " + e.getMessage());
        }
    }
	
	// OBTENER EL PRODUCTO por id
	 public ProductoOtaku obtenerProductoPorId(int id) {
	        String sql = "SELECT * FROM productos WHERE id = ?";
	        try (Connection conn = DatabaseConnection.conectar();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setInt(1, id);
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                return new ProductoOtaku(
	                    rs.getInt("id"),
	                    rs.getString("nombre"),
	                    rs.getString("categoria"),
	                    rs.getDouble("precio"),
	                    rs.getInt("stock")
	                );
	            }

	        } catch (SQLException e) {
	            System.err.println("Error al obtener producto: " + e.getMessage());
	        }
	        
	        // en el caso de que no se encuentre el id a buscar retornamos null 
	        return null;
	    }
	 
	 // OBTENER TODOS LOS PRODUCTOS
	 public List<ProductoOtaku> obtenerTodosLosProductos() {
		 // creamos la lista para almacenar de manera temporal los productos que buscamos
	        List<ProductoOtaku> lista = new ArrayList<>();
	        String sql = "SELECT * FROM productos";

	        try (Connection conn = DatabaseConnection.conectar();
	             PreparedStatement stmt = conn.prepareStatement(sql);
	             ResultSet rs = stmt.executeQuery()) {

	            while (rs.next()) {
	                lista.add(new ProductoOtaku(
	                    rs.getInt("id"),
	                    rs.getString("nombre"),
	                    rs.getString("categoria"),
	                    rs.getDouble("precio"),
	                    rs.getInt("stock")
	                ));
	            }

	        } catch (SQLException e) {
	            System.err.println("Error al obtener todos los productos: " + e.getMessage());
	        }

	        return lista;
	    }
	 
	 // ACTUALIZAR PRODUCTO 
	 
	 public boolean actualizarProducto(ProductoOtaku producto) {
		 String sql = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, stock = ? WHERE id = ?";
	        try (Connection conn = DatabaseConnection.conectar();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setString(1, producto.getNombre());
	            stmt.setString(2, producto.getCategoria());
	            stmt.setDouble(3, producto.getPrecio());
	            stmt.setInt(4, producto.getStock());
	            stmt.setInt(5, producto.getId());
	            
	            // si se modifica al menos una fila va a retornar TRUE
	            return stmt.executeUpdate() > 0;

	        } catch (SQLException e) {
	            System.err.println("Error al actualizar producto: " + e.getMessage());
	            
	            // si no se modifica ninguna linea y o da un error, esto retornará FALSE
	            return false;
	        }
	 }
	 
	 // ELIMINAR PRODUCTO
	 
	 public boolean eliminarProducto(int id) {
	        String sql = "DELETE FROM productos WHERE id = ?";
	        try (Connection conn = DatabaseConnection.conectar();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setInt(1, id);
	            return stmt.executeUpdate() > 0;

	        } catch (SQLException e) {
	            System.err.println("Error al eliminar producto: " + e.getMessage());
	            return false;
	        }
	    }
	 
	 
	 // BUSCAR LOS PRODUCTOS POR EL NOMBRE
	 public List<ProductoOtaku> buscarProductosPorNombre(String nombre) {
	        List<ProductoOtaku> lista = new ArrayList<>();
	        String sql = "SELECT * FROM productos WHERE nombre LIKE ?";

	        try (Connection conn = DatabaseConnection.conectar();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setString(1, "%" + nombre + "%");
	            ResultSet rs = stmt.executeQuery();

	            while (rs.next()) {
	                lista.add(new ProductoOtaku(
	                    rs.getInt("id"),
	                    rs.getString("nombre"),
	                    rs.getString("categoria"),
	                    rs.getDouble("precio"),
	                    rs.getInt("stock")
	                ));
	            }

	        } catch (SQLException e) {
	            System.err.println("Error al buscar productos por nombre: " + e.getMessage());
	        }

	        return lista;
	    }
}
