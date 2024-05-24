/**
 * 
 */
package co.edu.usco.lcms.dao.reservaespacio;

import java.util.List;

import co.edu.usco.lcms.model.reservaespacios.AdobeConnect;

/**
 * @author jankarlos
 *
 */
public interface AdobeConnectDao {

	/**
	 * Listar los AdobeConnect
	 * 
	 * @return Lista de los datos AdobeConnect
	 */
	public List<AdobeConnect> listarAdobeConnect();

}
