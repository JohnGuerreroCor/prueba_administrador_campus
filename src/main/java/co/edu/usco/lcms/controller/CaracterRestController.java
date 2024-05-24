/**
 * Clase CaracterRestController para gestionar los servicios web de Caracter listar, agregar, modificar, eliminar
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

import co.edu.usco.lcms.model.Caracter;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.servicios.CaracterServicio;
import co.edu.usco.lcms.utility.Respuesta;

/**
 * 
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class CaracterRestController {

	@Autowired
	CaracterServicio caracterServicio;

	/**
	 * Listar datos de Caracter según la estructura que requiere DataTable de
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
	 * 
	 * @return retorna la lista de caracter segun los parametros enviados
	 */
	@RequestMapping(value = "/caracteres", method = RequestMethod.POST,consumes="application/json",headers = "content-type=application/x-www-form-urlencoded")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public JSONRespuesta getCaracteres(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw) {

		JSONRespuesta listAsignaturas = new JSONRespuesta();
		listAsignaturas = caracterServicio.listarTablaCaracter(search, start, length, draw);
		return listAsignaturas;
	}

	/**
	 * Metodo GET de caracter para listar los datos de caracter
	 * 
	 * @return retorna una lista de caracter
	 */
	@RequestMapping(value = "/caracteresLista", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Caracter> getCaracteresLista() {
		List<Caracter> listOfCaracter = new ArrayList<Caracter>();
		listOfCaracter = caracterServicio.listarCaracter();
		return listOfCaracter;
	}

	/**
	 * Metodo POST de Caracter para listar los caracteres
	 * 
	 * @param caracter
	 *            objeto caracter que contiene los datos de caracter a agregar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/caracteres", method = RequestMethod.POST, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody Caracter caracter) {

		String returnInsercion = caracterServicio.agregarCaracter(caracter);

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
	 * Metodo PUT de caracter para modificar los datos de un registro de
	 * caracter
	 * 
	 * @param id
	 *            codigo del registro de caracter que se quiere modificar
	 * @param caracter
	 *            objeto caracter con los datos que se queiren modificar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/caracteres/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> modificar(@PathVariable String id, @RequestBody Caracter caracter) {

		if (id.length() > 0) {
			String returnModificar = caracterServicio.modificarCaracter(id, caracter);
			if (returnModificar.equals("OK")) {
				Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
				return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
			} else {
				Respuesta respuesta = new Respuesta();
				respuesta.setMensaje(returnModificar);
				respuesta.setCodigo(0);
				respuesta.setExito(false);
				return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
			}
		}

		Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Metodo DELETE de caracter para eliminar los datos de un registro de
	 * caracter
	 * 
	 * @param id
	 *            codigo del registro caracter que se quiere eliminar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/caracteres/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable String id) {

		String returnEliminar = caracterServicio.eliminarCaracter(id);
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