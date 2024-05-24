/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author jankarlos
 *
 */
public class UaaTipo {

	private int codigo;
	private String nombre;
	private int temporal;
	private String temporalNombre;

	/**
	 * 
	 */
	public UaaTipo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param nombre
	 * @param temporal
	 * @param temporalNombre
	 */
	public UaaTipo(int codigo, String nombre, int temporal, String temporalNombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.temporal = temporal;
		this.temporalNombre = temporalNombre;
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
	 * @return the temporal
	 */
	public int getTemporal() {
		return temporal;
	}

	/**
	 * @param temporal
	 *            the temporal to set
	 */
	public void setTemporal(int temporal) {
		this.temporal = temporal;
	}

	/**
	 * @return the temporalNombre
	 */
	public String getTemporalNombre() {
		return temporalNombre;
	}

	/**
	 * @param temporalNombre
	 *            the temporalNombre to set
	 */
	public void setTemporalNombre(String temporalNombre) {
		this.temporalNombre = temporalNombre;
	}

}
