/**
 * 
 */
package co.edu.usco.lcms.model;

import java.sql.Date;

/**
 * @author jankarlos
 *
 */
public class Resolucion {

	private long codigo;
	private Date fecha;
	private String descripcion;
	private String numero;
	private Uaa uaa;

	/**
	 * 
	 */
	public Resolucion() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param fecha
	 * @param descripcion
	 * @param numero
	 * @param uaa
	 */
	public Resolucion(long codigo, Date fecha, String descripcion, String numero, Uaa uaa) {
		super();
		this.codigo = codigo;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.numero = numero;
		this.uaa = uaa;
	}

	/**
	 * @return the codigo
	 */
	public long getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the uaa
	 */
	public Uaa getUaa() {
		return uaa;
	}

	/**
	 * @param uaa
	 *            the uaa to set
	 */
	public void setUaa(Uaa uaa) {
		this.uaa = uaa;
	}

}
