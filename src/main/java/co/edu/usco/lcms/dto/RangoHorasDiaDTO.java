/**
 * 
 */
package co.edu.usco.lcms.dto;

/**
 * @author Jankarlos Diaz Vieda
 *
 */
public class RangoHorasDiaDTO {

	private int columna;
	private int fila;
	private int hora;
	/**
	 * @param columna
	 * @param fila
	 * @param hora
	 */
	public RangoHorasDiaDTO(int columna, int fila, int hora) {
		super();
		this.columna = columna;
		this.fila = fila;
		this.hora = hora;
	}
	/**
	 * 
	 */
	public RangoHorasDiaDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the columna
	 */
	public int getColumna() {
		return columna;
	}
	/**
	 * @param columna the columna to set
	 */
	public void setColumna(int columna) {
		this.columna = columna;
	}
	/**
	 * @return the fila
	 */
	public int getFila() {
		return fila;
	}
	/**
	 * @param fila the fila to set
	 */
	public void setFila(int fila) {
		this.fila = fila;
	}
	/**
	 * @return the hora
	 */
	public int getHora() {
		return hora;
	}
	/**
	 * @param hora the hora to set
	 */
	public void setHora(int hora) {
		this.hora = hora;
	}

	
}
