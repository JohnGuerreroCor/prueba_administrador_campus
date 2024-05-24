/**
 * Clase DocenteController para gestionar los servicios web de loistar docente
 */
package co.edu.usco.lcms.controller.reservaespacios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.model.reservaespacios.UaaPersonal;
import co.edu.usco.lcms.servicios.reservaespacio.DocenteServicio;

/**
 * @author jankarlos
 * @version 1.0
 */
@RestController
public class DocenteController {
	
	@Autowired
	DocenteServicio docenteServicio;
	
	/**
	 * Metodo para listar los docentes
	 * @param criterio parametro de busqueda para filtrar los docentes
	 * @return retorna listado de los doncentes
	 */
	@RequestMapping(value = "/docentesServ", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMIN_VIDEOCONFERENCIA_LCMS", "ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS", "ROLE_DOCENTE" })
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms}) OR hasRole(${role.docente}) OR hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public List<UaaPersonal> getDocentes(@RequestParam("criterio") String criterio) {
		List<UaaPersonal> listCurso = new ArrayList<UaaPersonal>();
		listCurso = docenteServicio.listarDocentes(criterio);
		return listCurso;
	}
	
}
