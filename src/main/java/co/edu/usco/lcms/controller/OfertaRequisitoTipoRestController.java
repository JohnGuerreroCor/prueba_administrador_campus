/**
 * Clase OfertaRequisitoTipoRestController para gestionar el servicio web de listar los tipos de oferta requisito
 */
package co.edu.usco.lcms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.dao.OfertaRequisitoTipoDao;
import co.edu.usco.lcms.model.OfertaRequisitoTipo;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@RestController
public class OfertaRequisitoTipoRestController {

	@Autowired
	OfertaRequisitoTipoDao ofertaRequisitoTipoDao;

	/**
	 * Metodo GET para listar los registros de tipo oferta requisito
	 * 
	 * @return
	 */
	@RequestMapping(value = "/programaRequisitoTipoLista", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<OfertaRequisitoTipo> getProgramaRequisitoTipoLista() {
		List<OfertaRequisitoTipo> listaProgramas = new ArrayList<OfertaRequisitoTipo>();
		listaProgramas = ofertaRequisitoTipoDao.listarProgramaRequisitoTipo(0);
		return listaProgramas;
	}
}
