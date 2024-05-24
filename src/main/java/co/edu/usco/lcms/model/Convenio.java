package co.edu.usco.lcms.model;

import java.sql.Date;

/**
 * 
 * @author dacru-lcms
 *
 */
public class Convenio {
	private int codigo;
	private String descripcion;
	private Date fecha_creacion;
	private Date fecha_terminacion;
	private Resolucion resolucion;// Documento
	private Uaa uaa; // Entidad
	
	/**
	 * 
	 */
	public Convenio() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param descripcion
	 * @param fecha_creacion
	 * @param fecha_terminacion
	 * @param resolucion
	 * @param uaa
	 */
	public Convenio(int codigo, String descripcion, Date fecha_creacion, Date fecha_terminacion, Resolucion resolucion,
			Uaa uaa) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.fecha_creacion = fecha_creacion;
		this.fecha_terminacion = fecha_terminacion;
		this.resolucion = resolucion;
		this.uaa = uaa;
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
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the fecha_creacion
	 */
	public Date getFecha_creacion() {
		return fecha_creacion;
	}

	/**
	 * @param fecha_creacion the fecha_creacion to set
	 */
	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}

	/**
	 * @return the fecha_terminacion
	 */
	public Date getFecha_terminacion() {
		return fecha_terminacion;
	}

	/**
	 * @param fecha_terminacion the fecha_terminacion to set
	 */
	public void setFecha_terminacion(Date fecha_terminacion) {
		this.fecha_terminacion = fecha_terminacion;
	}

	/**
	 * @return the resolucion
	 */
	public Resolucion getResolucion() {
		return resolucion;
	}

	/**
	 * @param resolucion the resolucion to set
	 */
	public void setResolucion(Resolucion resolucion) {
		this.resolucion = resolucion;
	}

	/**
	 * @return the uaa
	 */
	public Uaa getUaa() {
		return uaa;
	}

	/**
	 * @param uaa the uaa to set
	 */
	public void setUaa(Uaa uaa) {
		this.uaa = uaa;
	}

	

}
