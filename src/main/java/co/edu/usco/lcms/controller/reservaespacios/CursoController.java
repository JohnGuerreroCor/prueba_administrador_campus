/**
 * Clase CursoController servicio web de curso
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

import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.servicios.reservaespacio.CursoServicio;

/**
 * @author jankarlos
 * @version 1.0
 */
@RestController
public class CursoController {

	@Autowired
	CursoServicio cursoServicio;
	
	/**
	 * Metodo para listar los cursos
	 * @param docente codigo del docente del cual se quiere filtrar los cursos
	 * @param criterio parametro para filtrar busqueda de los cursos
	 * @return retorna los cursos de un docente
	 */
	@RequestMapping(value = "/cursosServ", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMIN_VIDEOCONFERENCIA_LCMS", "ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS", "ROLE_DOCENTE" })
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms}) OR hasRole(${role.docente})")
	public List<Curso> getCursos(@RequestParam("codigo") int docente, @RequestParam("criterio") String criterio) {
		List<Curso> listCurso = new ArrayList<Curso>();
		listCurso = cursoServicio.listarCurso(docente,criterio);
		return listCurso;
	}
}
