/**
 * 
 */
package co.edu.usco.lcms.controller.reservaespacios;

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
import co.edu.usco.lcms.model.reservaespacios.Solicitud;
import co.edu.usco.lcms.servicios.reservaespacio.EspacioOcupacionServicio;
import co.edu.usco.lcms.utility.Respuesta;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@RestController
public class EspacioOcupacionController {

	@Autowired
	EspacioOcupacionServicio espacioOcupacionServicio;

	/**
	 * Listar datos de espacio ocupacion según la estructura que requiere
	 * DataTable de JQuery
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
	 * @return retorna la lista de asignaturas segun los parametros enviados
	 */
	@RequestMapping(value = "/espacioOcupacionSer/listar", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded")
	// @Secured({ "ROLE_ADMIN_VIDEOCONFERENCIA_LCMS",
	// "ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS", "ROLE_DOCENTE" })
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms}) OR hasRole(${role.docente})")
	public JSONRespuesta getEspacioOCupacionista(
			@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {

		JSONRespuesta listEspacioOcupacion = new JSONRespuesta();
		listEspacioOcupacion = espacioOcupacionServicio.listarTablaEspacioOcupacion(search, start, length, draw,
				posicion, direccion);
		return listEspacioOcupacion;
	}

	/**
	 * Metodo para eliminar
	 * 
	 * @param id
	 *            codigo del registro que se quiere eliminar
	 * @return retorna un mensaje de operación exitosa o que ocurrio un error.
	 */
	@RequestMapping(value = "/espacioOcupacionSer/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMIN_VIDEOCONFERENCIA_LCMS",
	// "ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms}) OR hasRole(${role.docente})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id, @RequestBody Solicitud solicitud) {
		String returnEliminar = espacioOcupacionServicio.eliminarEspacioOcupacion(id, solicitud);
		if (returnEliminar.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnEliminar);
		}
	}

	@RequestMapping(value = "/listaGrabaciones", method = RequestMethod.GET, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMIN_VIDEOCONFERENCIA_LCMS" })
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms}) OR hasRole(${role.docente})")
	public List<Solicitud> getListadoGrabaciones(@RequestParam(required = false, value = "codigo") long codigo) {
		List<Solicitud> listSolicitudes = espacioOcupacionServicio.listarGrabaciones(codigo);
		return listSolicitudes;
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
