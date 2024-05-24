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

import co.edu.usco.lcms.dao.OfertaAcademicaEstadoDao;
import co.edu.usco.lcms.model.OfertaAcademicaEstado;

/**
 * @author han
 *
 */
@RestController
public class OfertaAcademicaEstadoRestController {

	@Autowired
	OfertaAcademicaEstadoDao ofertaAcademicaEstadoDao;

	/**
	 * Metodo GET para listar los registros de oferta academica
	 * 
	 * @return retorna la lista de oferta academica
	 */
	@RequestMapping(value = "/ofertaAcademicaEstadoLista", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<OfertaAcademicaEstado> getOfertaEstado() {
		List<OfertaAcademicaEstado> listaDeEstadoOferta = new ArrayList<OfertaAcademicaEstado>();
		listaDeEstadoOferta = ofertaAcademicaEstadoDao.listarOfertaAcademicaEstado();
		return listaDeEstadoOferta;
	}

}
