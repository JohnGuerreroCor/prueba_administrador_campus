/**
 * 
 */
package co.edu.usco.lcms.model.reservaespacios;

import java.sql.Date;

import co.edu.usco.lcms.model.Persona;
import co.edu.usco.lcms.model.Sede;
import co.edu.usco.lcms.model.Uaa;

/**
 * @author jankarlos
 *
 */
public class UaaPersonal {

	private int codigo;
	private Uaa uaa;
	private Persona persona;
	private Date fechaIncio;
	private Date fechaFin;
	private Cargo cargo;
	private int estado;
	private Sede sede;

	/**
	 * 
	 */
	public UaaPersonal() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param uaa
	 * @param persona
	 * @param fechaIncio
	 * @param fechaFin
	 * @param cargo
	 * @param estado
	 * @param sede
	 */
	public UaaPersonal(int codigo, Uaa uaa, Persona persona, Date fechaIncio, Date fechaFin, Cargo cargo, int estado,
			Sede sede) {
		super();
		this.codigo = codigo;
		this.uaa = uaa;
		this.persona = persona;
		this.fechaIncio = fechaIncio;
		this.fechaFin = fechaFin;
		this.cargo = cargo;
		this.estado = estado;
		this.sede = sede;
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

	/**
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * @param persona
	 *            the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * @return the fechaIncio
	 */
	public Date getFechaIncio() {
		return fechaIncio;
	}

	/**
	 * @param fechaIncio
	 *            the fechaIncio to set
	 */
	public void setFechaIncio(Date fechaIncio) {
		this.fechaIncio = fechaIncio;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin
	 *            the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the cargo
	 */
	public Cargo getCargo() {
		return cargo;
	}

	/**
	 * @param cargo
	 *            the cargo to set
	 */
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	/**
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}

	/**
	 * @return the sede
	 */
	public Sede getSede() {
		return sede;
	}

	/**
	 * @param sede
	 *            the sede to set
	 */
	public void setSede(Sede sede) {
		this.sede = sede;
	}

}
