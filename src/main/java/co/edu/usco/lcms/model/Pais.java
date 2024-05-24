package co.edu.usco.lcms.model;

public class Pais {
	private int codigo;
	private String acronimo;
	private String nombre;
	
	/**
	 * 
	 */
	public Pais() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param codigo
	 * @param acronimo
	 * @param nombre
	 */
	public Pais(int codigo, String acronimo, String nombre) {
		super();
		this.codigo = codigo;
		this.acronimo = acronimo;
		this.nombre = nombre;
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
	
}

