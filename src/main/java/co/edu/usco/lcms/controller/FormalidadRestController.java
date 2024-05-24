/**
 * Clase FormalidadRestController para gestionar el servicio web de listar formalidad
 */
package co.edu.usco.lcms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.dao.FormalidadDao;
import co.edu.usco.lcms.model.Formalidad;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class FormalidadRestController {

	@Autowired
	FormalidadDao formalidadDao;

	/**
	 * Metodo GET para listar los datos de Formalidad
	 * 
	 * @return retorna la lista de Formalidad
	 */
	@RequestMapping(value = "/formalidadSer", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public List<Formalidad> getFormalidad() {
		List<Formalidad> listaDeFormalidad = new ArrayList<Formalidad>();
		listaDeFormalidad = formalidadDao.listarFormalidad();
		return listaDeFormalidad;
	}
}
