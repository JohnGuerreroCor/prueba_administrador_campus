/**
 * 
 */
package co.edu.usco.lcms.model.reservaespacios;

import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.Uaa;

/**
 * @author jankarlos
 *
 */
public class Configuraciones {

	private int codigo;
	private int diasAnticipacion;
	private int maxSolicitudesDias;
	private int maxSolicitudesSemana;
	private Uaa uaa;
	private Calendario calendario;

	/**
	 * 
	 */
	public Configuraciones() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param diasAnticipacion
	 * @param maxSolicitudesDias
	 * @param maxSolicitudesSemana
	 * @param uaa
	 * @param calendario
	 */
	public Configuraciones(int codigo, int diasAnticipacion, int maxSolicitudesDias, int maxSolicitudesSemana, Uaa uaa,
			Calendario calendario) {
		super();
		this.codigo = codigo;
		this.diasAnticipacion = diasAnticipacion;
		this.maxSolicitudesDias = maxSolicitudesDias;
		this.maxSolicitudesSemana = maxSolicitudesSemana;
		this.uaa = uaa;
		this.calendario = calendario;
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
	 * @return the diasAnticipacion
	 */
	public int getDiasAnticipacion() {
		return diasAnticipacion;
	}

	/**
	 * @param diasAnticipacion
	 *            the diasAnticipacion to set
	 */
	public void setDiasAnticipacion(int diasAnticipacion) {
		this.diasAnticipacion = diasAnticipacion;
	}

	/**
	 * @return the maxSolicitudesDias
	 */
	public int getMaxSolicitudesDias() {
		return maxSolicitudesDias;
	}

	/**
	 * @param maxSolicitudesDias
	 *            the maxSolicitudesDias to set
	 */
	public void setMaxSolicitudesDias(int maxSolicitudesDias) {
		this.maxSolicitudesDias = maxSolicitudesDias;
	}

	/**
	 * @return the maxSolicitudesSemana
	 */
	public int getMaxSolicitudesSemana() {
		return maxSolicitudesSemana;
	}

	/**
	 * @param maxSolicitudesSemana
	 *            the maxSolicitudesSemana to set
	 */
	public void setMaxSolicitudesSemana(int maxSolicitudesSemana) {
		this.maxSolicitudesSemana = maxSolicitudesSemana;
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
	 * @return the calendario
	 */
	public Calendario getCalendario() {
		return calendario;
	}

	/**
	 * @param calendario
	 *            the calendario to set
	 */
	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}

}