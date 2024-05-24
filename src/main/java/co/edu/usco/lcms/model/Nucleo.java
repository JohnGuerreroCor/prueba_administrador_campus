package co.edu.usco.lcms.model;

public class Nucleo {
	private int codigo;
	private String acronimo;
	private String nombre;
	private String estado;
	
	/**
	 * 
	 */
	public Nucleo() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param codigo
	 * @param acronimo
	 * @param nombre
	 * @param estado
	 */
	public Nucleo(int codigo, String acronimo, String nombre, String estado) {
		super();
		this.codigo = codigo;
		this.acronimo = acronimo;
		this.nombre = nombre;
		this.estado = estado;
	}
	/**
	 * @return the codigo
	 */
	public int getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the acronimo
	 */
	public String getAcronimo() {
		return acronimo;
	}
	/**
	 * @param acronimo the acronimo to set
	 */
	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}

