package modelo;

public class ProductoOtaku {
	// ATRIBUTOS DE LOS PRODUCTOS
	protected int id;
	protected String nombre;
	protected String categoria;
	protected double precio;
	protected int stock;
	
	// CONSTRUCTOR
	
	public ProductoOtaku(int id, String nombre, String categoria, double precio, int stock) {
		this.id = id;
		this.nombre= nombre;
		this.categoria = categoria;
		this.precio = precio;
		this.stock = stock;
		
	}
	
	// constructor sin id para agregar (PK)
	public ProductoOtaku( String nombre, String categoria, double precio, int stock) {
		this.nombre= nombre;
		this.categoria = categoria;
		this.precio = precio;
		this.stock = stock;
		
	}
	
	// GETTERS 
	
	public int getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getCategoria() {
		return categoria;
		
	}
	
	public double getPrecio() {
		return precio;
		
	}
	
	public int getStock() {
		return stock;
	}
	
	// SETTERS
	
	public void setId(int id) {
		this.id = id;
		
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
		
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
		
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
		
	}
	
	public void setStock(int stock) {
		this.stock = stock;
		
	}
}
