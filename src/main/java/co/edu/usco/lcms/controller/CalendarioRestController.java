/**
 * Clase CalendarioRestController para gestionar los servicios web de calendario, listar, agregar, modificar, eliminar
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

import co.edu.usco.lcms.dao.CalendarioDao;
import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.utility.Respuesta;

/**
 * 
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class CalendarioRestController {

	@Autowired
	CalendarioDao calendarioDao;

	/**
	 * Listar datos de Calendario según la estructura que requiere DataTable de
	 * JQuery
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
	 * @return retorna la lista de Calendario segun los parametros enviados
	 */
	@RequestMapping(value = "/calendarios", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded")
	// @Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS"
	// })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public JSONRespuesta getCalendarios(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw) {
		JSONRespuesta ListaCalendario = new JSONRespuesta();
		ListaCalendario = calendarioDao.listarTablaCalendario(search, start, length, draw);
		return ListaCalendario;
	}

	/**
	 * Metodo POST para agregar calendario
	 * 
	 * @param calendario
	 *            objeto calendario con los parametros requeridos para agregar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/calendarios", method = RequestMethod.POST, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS"
	// })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody Calendario calendario) {
		boolean returnInsercion = calendarioDao.agregarCalendario(calendario);
		if (returnInsercion) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo PUT para modificar un registro de calendario
	 * 
	 * @param id
	 *            codigo del calendario que se quiere modificar
	 * @param calendario
	 *            objeto calendario con los parametros requeridos para modificar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/calendarios/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS"
	// })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody Calendario calendario) {
		if (id > 0) {
			boolean returnModificar = calendarioDao.modificarCalendario(id, calendario);
			if (returnModificar) {
				Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
				return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
			} else {
				Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR);
				return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
			}
		}

		Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Metodo DELETE para eliminar un registro de calendario
	 * 
	 * @param id
	 *            codigo del calendario que se quiere eliminar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/calendarios/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS"
	// })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {
		boolean returnEliminar = calendarioDao.eliminarCalendario(id);
		if (returnEliminar) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/listarCalendarios", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public List<Calendario> getCalendarios() {
		List<Calendario> listaDeCalendario = new ArrayList<Calendario>();
		listaDeCalendario = calendarioDao.listadoCalendarios();
		return listaDeCalendario;
	}
}