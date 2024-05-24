/***
 * Clase SedeRestController para gestionar los servicios web de sede
 */
package co.edu.usco.lcms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.model.Sede;
import co.edu.usco.lcms.servicios.SedeServicio;

/**
 * 
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class SedeRestController {

	@Autowired
	SedeServicio sedeServicio;
	
	/**
	 * Metodo GET para listar las sedes
	 * @param estado parametro de estado para listar las sedes que esten activas o inactivas
	 * @return listado de sedes
	 */
	@RequestMapping(value = "/sedesAll", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({"ROLE_ADMINISTRADOR_LCMS","ROLE_ADMINISTRADOR_FACULTAD_LCMS"})
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms}) OR hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public List<Sede> getSedesAll(@RequestParam("estado") int estado) {
		List<Sede> listOfSede = new ArrayList<Sede>();
		listOfSede = sedeServicio.listarSede(estado, 0);
		return listOfSede;

	}
	/*
	@RequestMapping(value = "/sedes", method = RequestMethod.POST, headers = "Accept=application/json")
	@Secured({"ROLE_ADMINISTRADOR_LCMS","ROLE_ADMINISTRADOR_FACULTAD_LCMS"})
	public ResponseEntity<Respuesta> adicionar(@RequestBody Sede sede) {
		String returnInsercion = sedeServicio.agregarSede(sede);

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

	@RequestMapping(value = "/sedes/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody Sede sede) throws ParseException {
		if (id > 0) {
			String returnModificar = sedeServicio.modificarSede(id, sede);
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

	@RequestMapping(value = "/sedes/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {

		String returnEliminar = sedeServicio.eliminarSede(id);
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

	@RequestMapping(value = "/sedes", method = RequestMethod.GET, headers = "Accept=application/json")
	public JSONRespuesta getSedes(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw) {

		JSONRespuesta listAsignaturas = new JSONRespuesta();
		listAsignaturas = sedeServicio.listarTablaSede(search, start, length, draw);
		return listAsignaturas;

	}
*/
}
