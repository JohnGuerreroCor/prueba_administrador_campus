/**
 * Clase ConvenioRestController para gestionar los servicios web de convenios listar, agregar, modificar, eliminar
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

import co.edu.usco.lcms.model.Convenio;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.servicios.ConvenioServicio;
import co.edu.usco.lcms.utility.Respuesta;
import co.edu.usco.lcms.utility.ValidadorParametro;
import co.edu.usco.lcms.utility.ValidadorParametro.TipoValidador;

/**
 * 
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@RestController
public class ConvenioRestController {

	@Autowired
	ConvenioServicio convenioServicio;

	/**
	 * Listar datos de Convenio según la estructura que requiere DataTable de
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
	 * @return retorna la lista de Convenio segun los parametros enviados
	 */
	@RequestMapping(value = "/convenios/lista", method = RequestMethod.POST,consumes="application/json",headers = "content-type=application/x-www-form-urlencoded")
	//@Secured("ROLE_ADMINISTRADOR_LCMS")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public JSONRespuesta getConvenios(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {

		JSONRespuesta listConvenio = new JSONRespuesta();
		listConvenio = convenioServicio.listarTablaConvenio(search, start, length, draw, posicion, direccion);
		return listConvenio;
	}

	/**
	 * Metodo POST para agregar un registro de convenio
	 * 
	 * @param convenio
	 *            objeto con los datos de convenio que se quiere registrar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/convenios", method = RequestMethod.POST, headers = "Accept=application/json")
	//@Secured("ROLE_ADMINISTRADOR_LCMS")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody Convenio convenio) {

		boolean nombre_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				convenio.getDescripcion(), 200);

		if (!nombre_valido) {
			return returnMensaje("Campo nombre no valido");
		}

		String returnInsercion = convenioServicio.agregarConvenio(convenio);

		if (returnInsercion.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnInsercion);
		}
	}

	/**
	 * Metodo PUT de convenio para modificar los datos de un registro
	 * 
	 * @param id
	 *            codigo del registro que se quiere modificar
	 * @param convenio
	 *            objeto con los datos del convenio que se quiere modificar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/convenios/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	//@Secured("ROLE_ADMINISTRADOR_LCMS")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody Convenio convenio) {

		if (id > 0) {
			boolean nombre_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					convenio.getDescripcion(), 200);
			if (!nombre_valido) {
				return returnMensaje("Campo nombre no valido");
			}
			String returnModificar = convenioServicio.modificarConvenio(id, convenio);
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
	 * Metodo DELETE para eliminar un registro de convenio
	 * 
	 * @param id
	 *            codigo del convenio que se quiere eliminar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/convenios/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	//@Secured("ROLE_ADMINISTRADOR_LCMS")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {

		String returnEliminar = convenioServicio.eliminarConvenio(id);
		if (returnEliminar.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnEliminar);
		}
	}

	/**
	 * Metodo GET para listar los datos de convenios
	 * 
	 * @return retornar la lista de convenios
	 */
	@RequestMapping(value = "/convenioLista", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Convenio> getConvenioLista() {
		List<Convenio> listOfConvenio = new ArrayList<Convenio>();
		listOfConvenio = convenioServicio.listarConvenio();
		return listOfConvenio;
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