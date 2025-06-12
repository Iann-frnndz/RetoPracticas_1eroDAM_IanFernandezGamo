package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
	// AGREGAR clienteS
		public void agregarcliente(ClienteOtaku cliente) {
			// Ingresamos la query que se ha de realizar en nuestra BDD
	        String sql = "INSERT INTO clientes (nombre, email, telefono, fecha_registro) VALUES (?, ?, ?, ?)";
	        
	        // probamos la conexión y si esta es correcta ejecuta la query
	        try (Connection conn = DatabaseConnection.conectar();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setString(1, cliente.getNombre());
	            stmt.setString(2, cliente.getEmail());
	            stmt.setString(3, cliente.getTelefono());
	            stmt.setDate(4, Date.valueOf(cliente.getFechaRegistro()));

	            stmt.executeUpdate();
	        } catch (SQLException e) { // en el caso de que la ejecución de la query no sea disponible mandará un mensaje de error
	            System.err.println("Error al agregar cliente: " + e.getMessage());
	        }
	    }
		
		// OBTENER EL cliente por id
		 public ClienteOtaku obtenerclientePorId(int id) {
		        String sql = "SELECT * FROM clientes WHERE id = ?";
		        try (Connection conn = DatabaseConnection.conectar();
		             PreparedStatement stmt = conn.prepareStatement(sql)) {

		            stmt.setInt(1, id);
		            ResultSet rs = stmt.executeQuery();

		            if (rs.next()) {
		                return new ClienteOtaku(
		                		rs.getInt("id"),
		                        rs.getString("nombre"),
		                        rs.getString("email"),
		                        rs.getString("telefono"),
		                        rs.getDate("fecha_registro").toLocalDate()
		                );
		            }

		        } catch (SQLException e) {
		            System.err.println("Error al obtener cliente: " + e.getMessage());
		        }
		        
		        // en el caso de que no se encuentre el id a buscar retornamos null 
		        return null;
		    }
		 
		 // OBTENER TODOS LOS clienteS
		 public List<ClienteOtaku> obtenerTodosLosclientes() {
			 // creamos la lista para almacenar de manera temporal los clientes que buscamos
		        List<ClienteOtaku> listaC = new ArrayList<>();
		        String sql = "SELECT * FROM clientes";

		        try (Connection conn = DatabaseConnection.conectar();
		             PreparedStatement stmt = conn.prepareStatement(sql);
		             ResultSet rs = stmt.executeQuery()) {

		            while (rs.next()) {
		                listaC.add(new ClienteOtaku(
		                		rs.getInt("id"),
		                        rs.getString("nombre"),
		                        rs.getString("email"),
		                        rs.getString("telefono"),
		                        rs.getDate("fecha_registro").toLocalDate()
		                ));
		            }

		        } catch (SQLException e) {
		            System.err.println("Error al obtener todos los clientes: " + e.getMessage());
		        }

		        return listaC;
		    }
		 
		 // ACTUALIZAR cliente 
		 
		 public boolean actualizarcliente(ClienteOtaku cliente) {
			 String sql = "UPDATE clientes SET nombre = ?, email = ?, telefono = ?, fecha_registro = ? WHERE id = ?";
		        try (Connection conn = DatabaseConnection.conectar();
		             PreparedStatement stmt = conn.prepareStatement(sql)) {

		        	 stmt.setString(1, cliente.getNombre());
		             stmt.setString(2, cliente.getEmail());
		             stmt.setString(3, cliente.getTelefono());
		             stmt.setDate(4, Date.valueOf(cliente.getFechaRegistro()));
		             stmt.setInt(5, cliente.getId());
		            
		            // si se modifica al menos una fila va a retornar TRUE
		            return stmt.executeUpdate() > 0;

		        } catch (SQLException e) {
		            System.err.println("Error al actualizar cliente: " + e.getMessage());
		            
		            // si no se modifica ninguna linea y o da un error, esto retornará FALSE
		            return false;
		        }
		 }
		 
		 // ELIMINAR cliente
		 
		 public boolean eliminarcliente(int id) {
		        String sql = "DELETE FROM clientes WHERE id = ?";
		        try (Connection conn = DatabaseConnection.conectar();
		             PreparedStatement stmt = conn.prepareStatement(sql)) {

		            stmt.setInt(1, id);
		            return stmt.executeUpdate() > 0;

		        } catch (SQLException e) {
		            System.err.println("Error al eliminar cliente: " + e.getMessage());
		            return false;
		        }
		    }
		 
		 
		 


}
