/**
 * 
 */
package co.edu.usco.lcms.model;

import java.util.Date;

/**
 * @author ANDRES-GPIE
 *
 */
public class Admision {
	private int id;
	
	private Date fecha;

	private Inscripcion inscripcion;

	/**
	 * 
	 */
	public Admision() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param fecha
	 * @param inscripcion
	 */
	public Admision(int id, Date fecha, Inscripcion inscripcion) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.inscripcion = inscripcion;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the inscripcion
	 */
	public Inscripcion getInscripcion() {
		return inscripcion;
	}

	/**
	 * @param inscripcion the inscripcion to set
	 */
	public void setInscripcion(Inscripcion inscripcion) {
		this.inscripcion = inscripcion;
	}


	
}