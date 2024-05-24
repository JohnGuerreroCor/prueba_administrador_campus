/**
 * 
 */
package co.edu.usco.lcms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.dao.EscalaDao;
import co.edu.usco.lcms.model.Escala;

/**
 * @author jankarlos
 *
 */
@RestController
public class EscalaRestController {

	@Autowired
	EscalaDao escalaDao;

	/**
	 * 
	 * @return retorna el listado de areas
	 */
	@RequestMapping(value = "/escalaList/{tipo}", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Escala> getEscala(@PathVariable int tipo) {
		List<Escala> listaEscala = new ArrayList<Escala>();
		listaEscala = escalaDao.listaEscala(tipo);
		return listaEscala;
	}
}
