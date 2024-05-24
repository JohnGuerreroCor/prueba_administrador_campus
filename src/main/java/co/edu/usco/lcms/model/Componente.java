/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author jankarlos
 *
 */
public class Componente {

	private int codigoComponente;
	private String nombreComponente;
	private String acronimoComponente;
	/**
	 * 
	 */
	public Componente() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param codigoComponente
	 * @param nombreComponente
	 * @param acronimoComponente
	 */
	public Componente(int codigoComponente, String nombreComponente, String acronimoComponente) {
		super();
		this.codigoComponente = codigoComponente;
		this.nombreComponente = nombreComponente;
		this.acronimoComponente = acronimoComponente;
	}
	/**
	 * @return the codigoComponente
	 */
	public int getCodigoComponente() {
		return codigoComponente;
	}
	/**
	 * @param codigoComponente the codigoComponente to set
	 */
	public void setCodigoComponente(int codigoComponente) {
		this.codigoComponente = codigoComponente;
	}
	/**
	 * @return the nombreComponente
	 */
	public String getNombreComponente() {
		return nombreComponente;
	}
	/**
	 * @param nombreComponente the nombreComponente to set
	 */
	public void setNombreComponente(String nombreComponente) {
		this.nombreComponente = nombreComponente;
	}
	/**
	 * @return the acronimoComponente
	 */
	public String getAcronimoComponente() {
		return acronimoComponente;
	}
	/**
	 * @param acronimoComponente the acronimoComponente to set
	 */
	public void setAcronimoComponente(String acronimoComponente) {
		this.acronimoComponente = acronimoComponente;
	}

	
}
