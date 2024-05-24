/**
 * Clase PlanAcademicoAsignaturaRestController para gestionar los servicios web de Plan Académico Asignatura
 */
package co.edu.usco.lcms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.model.PlanAcademicoAsignatura;
import co.edu.usco.lcms.servicios.PlanAcademicoAsignaturaServicio;
import co.edu.usco.lcms.utility.Respuesta;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class PlanAcademicoAsignaturaRestController {

	@Autowired
	PlanAcademicoAsignaturaServicio planAcademicoAsignaturaServicio;

	/**
	 * Metodo GET para listar los registros de plan académico asignatura
	 * 
	 * @param codigo
	 *            parametro codigo del plan academico con el cual se quiere
	 *            filtar el listado
	 * @return retorna lista de plan académico asignatura según el código a
	 *         consultar
	 */
	@RequestMapping(value = "/planAcademicoAsignaturasLista", method = RequestMethod.GET, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS"
	// })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<PlanAcademicoAsignatura> getPlanAcademicoAsignaturas(@RequestParam("codigo") int codigo) {
		List<PlanAcademicoAsignatura> listaPlanAcademicAsignatura = new ArrayList<PlanAcademicoAsignatura>();
		listaPlanAcademicAsignatura = planAcademicoAsignaturaServicio.listarPlanAcademicoAsignatura(codigo);
		return listaPlanAcademicAsignatura;
	}

	@RequestMapping(value = "/datosPlanAcademicoAsignatura", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public PlanAcademicoAsignatura getDatosPlanAcademicoAsignatura(@RequestParam("plan") int plan,
			@RequestParam("asignatura") int asignatura) {

		List<PlanAcademicoAsignatura> listaPlanAcademicAsignatura = planAcademicoAsignaturaServicio
				.buscarPlanAcademicoAsignatura(plan, asignatura);

		return listaPlanAcademicAsignatura.size() > 0 ? listaPlanAcademicAsignatura.get(0) : null;
	}

	/**
	 * Metodo POST para agregar un registro de plan académico asignatura
	 * 
	 * @param planAcademicoAsignatura
	 *            objeto con los datos de plan académico asignatura
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/planAcademicoAsignaturaSer", method = RequestMethod.POST, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS"
	// })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody PlanAcademicoAsignatura planAcademicoAsignatura) {
		String returnInsercion = planAcademicoAsignaturaServicio
				.agregarPlanAcademicoAsignatura(planAcademicoAsignatura);

		if (returnInsercion.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			Respuesta respuesta = new Respuesta();
			respuesta.setMensaje(returnInsercion);
			respuesta.setCodigo(0);
			respuesta.setExito(false);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo DELETE para eliminar un registro de plan académico asignatura
	 * 
	 * @param id
	 *            codigo del registro que se quiere eliminar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/planAcademicoAsignaturaSer/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS"
	// })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {

		String returnEliminar = planAcademicoAsignaturaServicio.eliminarPlanAcademicoAsignatura(id);
		if (returnEliminar.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			Respuesta respuesta = new Respuesta();
			respuesta.setMensaje(returnEliminar);
			respuesta.setCodigo(0);
			respuesta.setExito(false);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
		}
	}
}
