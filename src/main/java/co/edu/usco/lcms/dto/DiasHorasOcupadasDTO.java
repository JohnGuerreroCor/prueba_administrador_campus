/**
 * 
 */
package co.edu.usco.lcms.dto;

/**
 * @author jankarlos
 *
 */
public class DiasHorasOcupadasDTO {

	private int dia;
	private RangoHorasDiaDTO inicio;
	private RangoHorasDiaDTO fin;

	/**
	 * 
	 */
	public DiasHorasOcupadasDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param dia
	 * @param inicio
	 * @param fin
	 */
	public DiasHorasOcupadasDTO(int dia, RangoHorasDiaDTO inicio, RangoHorasDiaDTO fin) {
		super();
		this.dia = dia;
		this.inicio = inicio;
		this.fin = fin;
	}

	/**
	 * @return the dia
	 */
	public int getDia() {
		return dia;
	}

	/**
	 * @param dia
	 *            the dia to set
	 */
	public void setDia(int dia) {
		this.dia = dia;
	}

	/**
	 * @return the inicio
	 */
	public RangoHorasDiaDTO getInicio() {
		return inicio;
	}

	/**
	 * @param inicio
	 *            the inicio to set
	 */
	public void setInicio(RangoHorasDiaDTO inicio) {
		this.inicio = inicio;
	}

	/**
	 * @return the fin
	 */
	public RangoHorasDiaDTO getFin() {
		return fin;
	}

	/**
	 * @param fin
	 *            the fin to set
	 */
	public void setFin(RangoHorasDiaDTO fin) {
		this.fin = fin;
	}

}
