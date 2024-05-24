/**
 * 
 */
package co.edu.usco.lcms.controller.proacademica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.dao.proacademica.EspacioDao;
import co.edu.usco.lcms.model.reservaespacios.Espacio;

/**
 * @author jankarlos
 *
 */
@RestController
public class EspacioRestController {

	@Autowired
	EspacioDao espacioDao;

	@RequestMapping(value = "/espacioList", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public List<Espacio> getEspacio(@RequestParam("criterio") String criterio) {
		List<Espacio> tipoEspacio = espacioDao.listarEspacios(0, criterio);
		return tipoEspacio;

	}
}
