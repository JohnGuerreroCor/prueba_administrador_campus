/**
 * Clase PlanAcademicoRestController para gestionar los servicios web de plan académico listar, agregar, modificar, eliminar
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

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.PlanAcademico;
import co.edu.usco.lcms.model.PlanAcademicoAsignatura;
import co.edu.usco.lcms.servicios.PlanAcademicoServicio;
import co.edu.usco.lcms.utility.Respuesta;
import co.edu.usco.lcms.utility.ValidadorParametro;
import co.edu.usco.lcms.utility.ValidadorParametro.TipoValidador;

/**
 * 
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@RestController
public class PlanAcademicoRestController {

	@Autowired
	PlanAcademicoServicio planAcademicoServicio;

	/**
	 * Listar datos de Plan Académico según la estructura que requiere DataTable
	 * de JQuery
	 * 
	 * @param search
	 *            parametro a buscar en la consulta
	 * @param start
	 *            limite de inicio de los registros
	 * @param length
	 *            limite de fin hasta donde se consultaran los registros
	 * @param draw
	 *            número de veces que se realiza la consulta
	 * @param posicion
	 *            posición de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion
	 *            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de Plan Académico segun los parametros enviados
	 */
	
	@RequestMapping(value = "/plan-academico/lista", method = RequestMethod.POST,consumes="application/json",headers = "content-type=application/x-www-form-urlencoded")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public JSONRespuesta getPlanAcademica(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {

		JSONRespuesta listPlanAcademico = new JSONRespuesta();
		listPlanAcademico = planAcademicoServicio.listarTablaPlanAcademico(search, start, length, draw, posicion,
				direccion);
		return listPlanAcademico;
	}

	/**
	 * Metodo POST para agregar un registro de Plan Académico
	 * 
	 * @param planAcademico
	 *            objeto con los datos de plan académico a agregar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/plan-academico", method = RequestMethod.POST, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody final PlanAcademico planAcademico) {

		boolean nombre_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				planAcademico.getNombre(), 10);
		boolean total_horas_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
				String.valueOf(planAcademico.getTotalHoras()), 3);

		if (!nombre_valido) {
			return returnMensaje("Campo nombre plan académico no valido");
		}
		if (!total_horas_valido) {
			return returnMensaje("Campo total horas no valido");
		}

		for (PlanAcademicoAsignatura a : planAcademico.getAsignaturas()) {
			boolean intensidad_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
					String.valueOf(a.getIntensidad()), 3);
			boolean horas_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
					String.valueOf(a.getTrabajoIndependiente()), 3);
			boolean nota_minima_valido = ValidadorParametro.validarParametro(
					TipoValidador.VALIDADOR_LISTA_NUMEROS_PUNTO_Y_COMA, String.valueOf(a.getTrabajoIndependiente()), 3);
			boolean creditos_valido = ValidadorParametro.validarParametro(
					TipoValidador.VALIDADOR_LISTA_NUMEROS_PUNTO_Y_COMA, String.valueOf(a.getCreditos()), 3);

			if (!intensidad_valido) {
				return returnMensaje("Los datos ingresados en la asignatura " + a.getNombre()
						+ " no son validos, elimine y vuelva a ingresarla");
			}
			if (!horas_valido) {
				return returnMensaje("Los datos ingresados en la asignatura " + a.getNombre()
						+ " no son validos, elimine y vuelva a ingresarla");
			}
			if (!nota_minima_valido) {
				return returnMensaje("Los datos ingresados en la asignatura " + a.getNombre()
						+ " no son validos, elimine y vuelva a ingresarla");
			}
			if (!creditos_valido) {
				return returnMensaje("Los datos ingresados en la asignatura " + a.getNombre()
						+ " no son validos, elimine y vuelva a ingresarla");
			}
		}

		String returnInsercion = planAcademicoServicio.agregarPlanAcademico(planAcademico);

		if (returnInsercion.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnInsercion);
		}
	}

	/**
	 * Metodo DELETE para eliminar un registro de plan académico
	 * 
	 * @param id
	 *            codigo del registro que se quiere eliminar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/plan-academico/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {
		String returnEliminar = planAcademicoServicio.eliminarPlanAcademico(id);
		if (returnEliminar.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnEliminar);
		}
	}

	/**
	 * Metodo PUT para modificar un registro de plan académico
	 * 
	 * @param id
	 *            código del registro que se quiere modificar
	 * @param planAcademico
	 *            objeto con los datos del registro que se quiere modificar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/plan-academico/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody PlanAcademico planAcademico) {
		if (id > 0) {
			boolean nombre_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					planAcademico.getNombre(), 10);
			boolean total_horas_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
					String.valueOf(planAcademico.getTotalHoras()), 3);

			if (!nombre_valido) {
				return returnMensaje("Campo nombre plan académico no valido");
			}
			if (!total_horas_valido) {
				return returnMensaje("Campo total horas no valido");
			}

			String returnModificar = planAcademicoServicio.modificarPlanAcademico(id, planAcademico);
			if (returnModificar.equals("OK")) {
				Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
				return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
			} else {
				return returnMensaje(returnModificar);
			}
		}

		Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Metodo para listar los docentes
	 * @param criterio parametro de busqueda para filtrar los docentes
	 * @return retorna listado de los doncentes
	 */
	@RequestMapping(value = "/planAcademicoSer", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public List<PlanAcademico> getPlanAcademico(@RequestParam("criterio") String criterio) {
		List<PlanAcademico> listPlanAcademico = new ArrayList<PlanAcademico>();
		listPlanAcademico = planAcademicoServicio.listarPlanAcademico(criterio);
		return listPlanAcademico;
	}

	/**
	 * 
	 * @param mensaje
	 *            texto que se quiere retornar
	 * @return retorna de ResponseEntity un objeto JSON con el mensaje de
	 *         respuesta erroneo
	 */
	public ResponseEntity<Respuesta> returnMensaje(String mensaje) {
		Respuesta respuesta = new Respuesta();
		respuesta.setMensaje(mensaje);
		respuesta.setCodigo(0);
		respuesta.setExito(false);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}

}
