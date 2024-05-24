/**
 * 
 */
package co.edu.usco.lcms.controller.proacademica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.model.TipoCurso;
import co.edu.usco.lcms.servicios.proacademica.TipoCursoServicio;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class TipoCursoRestController {

	@Autowired
	TipoCursoServicio tipoCursoServicio;

	@RequestMapping(value = "/tipoCursoList", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public List<TipoCurso> getTipoCurso() {
		List<TipoCurso> tipoCurso = tipoCursoServicio.listarTipoCurso();
		return tipoCurso;

	}
}
