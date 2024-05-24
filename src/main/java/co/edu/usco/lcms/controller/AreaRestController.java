/**
 * Clase AreaRestController para consumir el servicio web de listar las areas
 */
package co.edu.usco.lcms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.dao.AreaDao;
import co.edu.usco.lcms.model.Area;

/**
 * 
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */

@RestController
public class AreaRestController {
	
	@Autowired
	AreaDao areaDao;

	/**
	 * 
	 * @return retorna el listado de areas
	 */
	@RequestMapping(value = "/snies-area", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Area> getAreas() {
		List<Area> listaDeArea = new ArrayList<Area>();
		listaDeArea = areaDao.listarArea();
		return listaDeArea;
	}

}
