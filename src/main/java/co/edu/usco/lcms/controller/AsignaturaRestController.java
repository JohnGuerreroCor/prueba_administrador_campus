/** 
 * Clase AsignaturaRestController para consumir los servicios web de asignaturas, listar, agregar, modificar y eliminar
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

import co.edu.usco.lcms.model.Asignatura;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.servicios.AsignaturaServicio;
import co.edu.usco.lcms.utility.Respuesta;
import co.edu.usco.lcms.utility.ValidadorParametro;
import co.edu.usco.lcms.utility.ValidadorParametro.TipoValidador;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@RestController
public class AsignaturaRestController {

	@Autowired
	AsignaturaServicio asignaturaServicio;

	/**
	 * Listar datos de asignatura según la estructura que requiere DataTable de JQuery 
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
	 * @return retorna la lista de asignaturas segun los parametros enviados
	 */
	@RequestMapping(value = "/asignaturas/lista", method = RequestMethod.POST,consumes="application/json",headers = "content-type=application/x-www-form-urlencoded")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public JSONRespuesta getAsignaturas(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {

		JSONRespuesta listAsignaturas = new JSONRespuesta();
		listAsignaturas = asignaturaServicio.listarTablaAsignatura(search, start, length, draw, posicion, direccion);
		return listAsignaturas;
	}

	/**
	 * 
	 * @param criterio
	 *            criterio de búsqueda para listar las asignaturas
	 * @param acronimo
	 *            parametro de búsqueda para consultar las asignaturas según el
	 *            campo acronimo
	 * @return retorna un listado de las asignaturas encontradas
	 */
	@RequestMapping(value = "/asignaturasLista", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Asignatura> getAListadoAsignaturas(@RequestParam(required = false, value = "criterio") String criterio,
			@RequestParam(required = false, value = "acronimo") String acronimo) {
		List<Asignatura> listAsignaturas = new ArrayList<Asignatura>();
		listAsignaturas = asignaturaServicio.listarAsignatura(criterio == null ? "" : criterio,
				acronimo == null ? "" : acronimo);
		return listAsignaturas;
	}

	@RequestMapping(value = "/asignaturaCodAcronimo", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public Asignatura getAsignaturaAcronimo(@RequestParam(required = false, value = "criterio") String criterio,
			@RequestParam(required = false, value = "acronimo") String acronimo) {

		List<Asignatura> asignatura = asignaturaServicio.listarAsignatura(criterio == null ? "" : criterio,
				acronimo == null ? "" : acronimo);
		return asignatura.size() > 0 ? asignatura.get(0) : new Asignatura();

	}

	/**
	 * Metodo POST asignaturas para agregarlas
	 * 
	 * @param asignatura
	 *            objeto que contiene los parametros de las asignaturas
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/asignaturas", method = RequestMethod.POST, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody Asignatura asignatura) {

		boolean nombre_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				asignatura.getNombre(), 100);
		boolean nombre_impresion_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				asignatura.getNombreImpresion(), 12);
		boolean trabajo_presencial_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
				String.valueOf(asignatura.getTrabajoPresencial()), 3);
		boolean trabajo_independiente_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
				String.valueOf(asignatura.getTrabajoIndependiente()), 3);
		boolean creditos_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
				String.valueOf(asignatura.getCreditos()), 3);
		asignatura.setPublicar(1);
		if (!nombre_valido) {
			return returnMensaje("Campo nombre no valido");
		}
		if (!nombre_impresion_valido) {
			return returnMensaje("Campo nombre impresión no valido");
		}
		if (!trabajo_presencial_valido) {
			return returnMensaje("Campo Trabajo Presencial no valido");
		}
		if (!trabajo_independiente_valido) {
			return returnMensaje("Campo Trabajo Independiente no valido");
		}
		if (!creditos_valido) {
			return returnMensaje("Campo Creditos no valido");
		}

		String returnInsercion = asignaturaServicio.agregarAsignatura(asignatura);

		if (returnInsercion.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnInsercion);
		}
	}

	/**
	 * Metodo PUT asignatura para poder modificar la asignatura
	 * 
	 * @param id
	 *            codigo de la asignatura a modificar
	 * @param asignatura
	 *            objeto que contiene los parametros de las asignaturas
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/asignaturas/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody Asignatura asignatura) {

		if (id > 0) {
			boolean nombre_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					asignatura.getNombre(), 100);
			boolean nombre_impresion_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					asignatura.getNombreImpresion(), 12);
			boolean trabajo_presencial_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
					String.valueOf(asignatura.getTrabajoPresencial()), 3);
			boolean trabajo_independiente_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
					String.valueOf(asignatura.getTrabajoIndependiente()), 3);
			boolean creditos_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
					String.valueOf(asignatura.getCreditos()), 3);
			
			asignatura.setPublicar(1);
			if (!nombre_valido) {
				return returnMensaje("Campo nombre no valido");
			}
			if (!nombre_impresion_valido) {
				return returnMensaje("Campo nombre impresión no valido");
			}
			if (!trabajo_presencial_valido) {
				return returnMensaje("Campo Trabajo Presencial no valido");
			}
			if (!trabajo_independiente_valido) {
				return returnMensaje("Campo Trabajo Independiente no valido");
			}
			if (!creditos_valido) {
				return returnMensaje("Campo Creditos no valido");
			}
			String returnModificar = asignaturaServicio.modificarAsignatura(id, asignatura);
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
	 * Motodo DELETE asignatura para eliminar una asignatura
	 * 
	 * @param id
	 *            codigo de la asignatura que se quiere eliminar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/asignaturas/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {

		String returnEliminar = asignaturaServicio.eliminarAsignatura(id);
		if (returnEliminar.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnEliminar);
		}
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
