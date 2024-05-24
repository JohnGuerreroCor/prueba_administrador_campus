/**
 * Clase ConfiguracionesRestController para la asignacion de las configuraciones del módulo
 */
package co.edu.usco.lcms.controller.reservaespacios;

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
import co.edu.usco.lcms.model.reservaespacios.Configuraciones;
import co.edu.usco.lcms.servicios.reservaespacio.ConfiguracionesServicio;
import co.edu.usco.lcms.utility.Respuesta;
import co.edu.usco.lcms.utility.ValidadorParametro;
import co.edu.usco.lcms.utility.ValidadorParametro.TipoValidador;

/**
 * @author jankarlos
 * @version 1.0
 *
 */
@RestController
public class ConfiguracionesRestController {

	@Autowired
	ConfiguracionesServicio configuracionesServicio;

	/**
	 * Listar configuraciones facultad según la estructura que requiere
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
	 *            orden en que se quieren ordenar los registros ascendente o
	 *            descendente
	 * @return retorna la lista de asignaturas segun los parametros enviados
	 */

	@RequestMapping(value = "/configuraciones/lista", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded")
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms})")
	public JSONRespuesta getAsignaturas(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {

		JSONRespuesta listHoras = new JSONRespuesta();
		listHoras = configuracionesServicio.listarTablaConfiguraciones(search, start, length, draw, posicion,
				direccion);
		return listHoras;
	}

	/**
	 * Metodo para guardar datos reserva horas uaa
	 * 
	 * @param configuraciones
	 *            objeto con los datos parametros de reserva horas uaa
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/configuracionesSer", method = RequestMethod.POST, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody Configuraciones configuraciones) {

		boolean anticipacion = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
				String.valueOf(configuraciones.getDiasAnticipacion()), 2);
		boolean maxDia = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
				String.valueOf(configuraciones.getMaxSolicitudesDias()), 2);
		boolean maxSemana = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
				String.valueOf(configuraciones.getMaxSolicitudesSemana()), 2);

		if (!anticipacion) {
			return returnMensaje("Campo Número de días de anticipación no valido");
		}
		if (!maxDia) {
			return returnMensaje("Campo Número maximo de solicitudes por día no valido");
		}
		if (!maxSemana) {
			return returnMensaje("Campo Número maximo de solicitudes por semana no valido");
		}
		String returnInsercion = configuracionesServicio.agregarConfiguraciones(configuraciones);

		if (returnInsercion.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnInsercion);
		}
	}

	/**
	 * Metodo para actualizar los datos de reserva horas uaa
	 * 
	 * @param id
	 *            codigo del registro a modificar
	 * @param configuraciones
	 *            objeto con los datos parametros de reserva horas uaa
	 * @return
	 */
	@RequestMapping(value = "/configuracionesSer/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms})")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody Configuraciones configuraciones) {

		if (id > 0) {

			boolean anticipacion = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
					String.valueOf(configuraciones.getDiasAnticipacion()), 2);
			boolean maxDia = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
					String.valueOf(configuraciones.getMaxSolicitudesDias()), 2);
			boolean maxSemana = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
					String.valueOf(configuraciones.getMaxSolicitudesSemana()), 2);

			if (!anticipacion) {
				return returnMensaje("Campo Número de días de anticipación no valido");
			}
			if (!maxDia) {
				return returnMensaje("Campo Número maximo de solicitudes por día no valido");
			}
			if (!maxSemana) {
				return returnMensaje("Campo Número maximo de solicitudes por semana no valido");
			}

			String returnModificar = configuracionesServicio.modificarConfiguraciones(id, configuraciones);
			if (returnModificar.equals("OK")) {
				Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
				return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
			} else {
				return returnMensaje(returnModificar);
			}
		} else {
			return returnMensaje(
					"El súper administrador del sistema, Aún no ha asignado un número de horas a esta facultad.");
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
