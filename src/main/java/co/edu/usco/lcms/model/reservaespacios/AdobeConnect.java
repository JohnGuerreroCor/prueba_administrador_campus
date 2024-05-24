/**
 * 
 */
package co.edu.usco.lcms.model.reservaespacios;

/**
 * @author jankarlos
 *
 */
public class AdobeConnect {

	private int codigo;
	private String usuario;
	private String clave;
	private int numSesiones;
	private int numTotalHoras;
	private int numTotalDisponible;
	private String url;

	/**
	 * 
	 */
	public AdobeConnect() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param usuario
	 * @param clave
	 * @param numSesiones
	 * @param numTotalHoras
	 * @param numTotalDisponible
	 * @param url
	 */
	public AdobeConnect(int codigo, String usuario, String clave, int numSesiones, int numTotalHoras,
			int numTotalDisponible, String url) {
		super();
		this.codigo = codigo;
		this.usuario = usuario;
		this.clave = clave;
		this.numSesiones = numSesiones;
		this.numTotalHoras = numTotalHoras;
		this.numTotalDisponible = numTotalDisponible;
		this.url = url;
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
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * @return the numSesiones
	 */
	public int getNumSesiones() {
		return numSesiones;
	}

	/**
	 * @param numSesiones the numSesiones to set
	 */
	public void setNumSesiones(int numSesiones) {
		this.numSesiones = numSesiones;
	}

	/**
	 * @return the numTotalHoras
	 */
	public int getNumTotalHoras() {
		return numTotalHoras;
	}

	/**
	 * @param numTotalHoras the numTotalHoras to set
	 */
	public void setNumTotalHoras(int numTotalHoras) {
		this.numTotalHoras = numTotalHoras;
	}

	/**
	 * @return the numTotalDisponible
	 */
	public int getNumTotalDisponible() {
		return numTotalDisponible;
	}

	/**
	 * @param numTotalDisponible the numTotalDisponible to set
	 */
	public void setNumTotalDisponible(int numTotalDisponible) {
		this.numTotalDisponible = numTotalDisponible;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	
}
