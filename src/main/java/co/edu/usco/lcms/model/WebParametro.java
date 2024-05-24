/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author jankarlos
 *
 */
public class WebParametro {

	private int codigo;
	private String nombre;
	private String valor;
	private String descripcion;

	/**
	 * 
	 */
	public WebParametro() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param nombre
	 * @param valor
	 * @param descripcion
	 */
	public WebParametro(int codigo, String nombre, String valor, String descripcion) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.valor = valor;
		this.descripcion = descripcion;
	}

	/**
	 * @return the codigo
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(int codigo) {
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

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
