package co.edu.usco.lcms.controller;

/**
 * Clase ResolucionRestController para gestionar los servicios web de Resoluciones (Listar, Agregar, Modificar, Eliminar)
 */
import java.text.ParseException;
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
import co.edu.usco.lcms.model.Resolucion;
import co.edu.usco.lcms.servicios.ResolucionServicio;
import co.edu.usco.lcms.utility.Respuesta;
import co.edu.usco.lcms.utility.ValidadorParametro;
import co.edu.usco.lcms.utility.ValidadorParametro.TipoValidador;

/**
 * 
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */

@RestController
public class ResolucionRestController {

	@Autowired
	ResolucionServicio resolucionServicio;

	/**
	 * Metodo POST para agregar un registro a resoluciones
	 * 
	 * @param resolucion
	 *            objeto que contiene los datos de la resolucion a agregar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */

	@RequestMapping(value = "/resoluciones", method = RequestMethod.POST, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody Resolucion resolucion) {

		boolean descripcion_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				resolucion.getDescripcion(), 200);
		boolean numero_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				resolucion.getNumero(), 50);

		if (!descripcion_valido) {
			return returnMensaje("Campo descripcion no valido");
		}
		if (!numero_valido) {
			return returnMensaje("Campo número no valido");
		}
		String returnInsercion = resolucionServicio.agregarResolucion(resolucion);

		if (returnInsercion.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnInsercion);
		}

	}

	/**
	 * Metodo PUT para modificar los datos de un registro de resolucion
	 * 
	 * @param id
	 *            codigo de la resolucion que se quiere modificar
	 * @param resolucion
	 *            objeto con los datos de la resolucion que se quiere modificar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 * @throws ParseException
	 */
	@RequestMapping(value = "/resoluciones/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS"
	// })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody Resolucion resolucion)
			throws ParseException {

		if (id > 0) {
			boolean descripcion_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					resolucion.getDescripcion(), 200);
			boolean numero_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					resolucion.getNumero(), 50);

			if (!descripcion_valido) {
				return returnMensaje("Campo descripcion no valido");
			}
			if (!numero_valido) {
				return returnMensaje("Campo número no valido");
			}
			String returnModificar = resolucionServicio.modificarResolucion(id, resolucion);
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
	 * Metodo DELETE para eliminar un registro de resoluciones
	 * 
	 * @param id
	 *            codigo de la resolucion que se quiere eliminar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/resoluciones/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS"
	// })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {

		String returnEliminar = resolucionServicio.eliminarResolucion(id);
		if (returnEliminar.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnEliminar);
		}

	}

	/**
	 * Listar datos de Resoluciones según la estructura que requiere DataTable
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
	 * @return retorna la lista de Resolucioens segun los parametros enviados
	 */
	@RequestMapping(value = "/resoluciones/lista", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded")
	// @Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS"
	// })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public JSONRespuesta getResoluciones(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {

		JSONRespuesta listResolucion = new JSONRespuesta();
		listResolucion = resolucionServicio.listarTablaResolucion(search, start, length, draw, posicion, direccion);
		return listResolucion;
	}

	/**
	 * Metodo Get para listar las resoluciones
	 * 
	 * @return retorna en un listado las resoluciones
	 */
	@RequestMapping(value = "/resolucionLista/{proMod}", method = RequestMethod.GET, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS"
	// })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Resolucion> getResolucionLista(@PathVariable int proMod) {
		List<Resolucion> listOfResolucion = new ArrayList<Resolucion>();
		listOfResolucion = resolucionServicio.listarResolucion(proMod);
		return listOfResolucion;
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