/**
 * Clase NumeroLicenciasController para crear los servicios web de numero de licencias adobe connect
 */
package co.edu.usco.lcms.controller.reservaespacios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.dao.reservaespacio.AdobeConnectDao;
import co.edu.usco.lcms.model.reservaespacios.AdobeConnect;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@RestController
public class NumeroLicenciasController {

	@Autowired
	AdobeConnectDao adobeConnectDao;

	/**
	 * Metodo para listar los datos de Adobe Connect
	 * 
	 * @return retorna datos de Adobe Connect
	 */
	@RequestMapping(value = "/licenciasNum", method = RequestMethod.GET, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMIN_VIDEOCONFERENCIA_LCMS",
	// "ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms})")
	public AdobeConnect getNumLicencias() {
		AdobeConnect numLicencias = adobeConnectDao.listarAdobeConnect().get(0);
		return numLicencias;
	}

}
