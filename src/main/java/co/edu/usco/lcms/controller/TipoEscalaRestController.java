/**
 * 
 */
package co.edu.usco.lcms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.dao.TipoEscalaDao;
import co.edu.usco.lcms.model.TipoEscala;

/**
 * @author jankarlos
 *
 */
@RestController
public class TipoEscalaRestController {

	@Autowired
	TipoEscalaDao tipoEscalaDao;

	/**
	 * 
	 * @return retorna el listado de areas
	 */
	@RequestMapping(value = "/tipoEscalaList", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<TipoEscala> getTipoEscala() {
		List<TipoEscala> listaTipoEscala = new ArrayList<TipoEscala>();
		listaTipoEscala = tipoEscalaDao.listarTipoEscala();
		return listaTipoEscala;
	}
}
