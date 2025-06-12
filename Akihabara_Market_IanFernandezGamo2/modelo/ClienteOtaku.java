package modelo;

import java.time.LocalDate;

public class ClienteOtaku {

	
	protected int id;
	protected String nombre;
	protected String email;
	protected String telefono;
	protected LocalDate fecha_registro;
	
	public ClienteOtaku(int id, String nombre, String email, String telefono, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fecha_registro = fechaRegistro;
    }
	
	
	// construictor sin ID
	 public ClienteOtaku(String nombre, String email, String telefono, LocalDate fechaRegistro) {
	        this.nombre = nombre;
	        this.email = email;
	        this.telefono = telefono;
	        this.fecha_registro = fechaRegistro;
	    }

	 public int getId() {
	        return id;
	    }
	 public void setId(int id) {
	        this.id = id;
	    }
	 
	 public String getTelefono() {
	        return telefono;
	    }

	    public void setTelefono(String telefono) {
	        this.telefono = telefono;
	    }

	    public LocalDate getFechaRegistro() {
	        return fecha_registro;
	    }

	    public void setFechaRegistro(LocalDate fechaRegistro) {
	        this.fecha_registro = fechaRegistro;
	    }

	    

	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	   
}
