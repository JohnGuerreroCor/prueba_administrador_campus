package co.edu.usco.lcms.model;

/**
 * 
 * @author dacru-lcms
 *
 */
public class Caracter {
	private String codigo;
	private String nombre;

	/**
	 * 
	 */
	public Caracter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param nombre
	 */
	public Caracter(String codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}	

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
