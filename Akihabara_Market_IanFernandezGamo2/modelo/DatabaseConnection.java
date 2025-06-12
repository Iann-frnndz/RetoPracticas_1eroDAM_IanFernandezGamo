package modelo;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
	// Conexion con la BDD
	public static Connection conectar() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/akihabara_db";
		String usuario = "root";
		String contraseña = "1234";
		return java.sql.DriverManager.getConnection(url, usuario, contraseña);
		}
		// Metodo que prueba la conexion y nos manda un mensaje para confirmarla 
		public static void main(String[] args) {
		try (Connection conn = conectar()) {
		System.out.println("Conexión exitosa.");
		} catch (SQLException e) {
		System.err.println("Error al conectar: " + e.getMessage());
		}
		}
}
