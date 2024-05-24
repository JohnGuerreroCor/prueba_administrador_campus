/**
 * Clase ComponenteRestController para gestionar los servicios web de componente listar, agregar, modificar, eliminar
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

import co.edu.usco.lcms.dao.ComponenteDao;
import co.edu.usco.lcms.model.Componente;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.utility.Respuesta;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@RestController
public class ComponenteRestController {

	@Autowired
	ComponenteDao componenteDao;

	/**
	 * Listar datos de Componente según la estructura que requiere DataTable de
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
	 * @return retorna la lista de componente segun los parametros enviados
	 */
	@RequestMapping(value = "/componentes", method = RequestMethod.POST,consumes="application/json",headers = "content-type=application/x-www-form-urlencoded")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public JSONRespuesta getComponente(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw) {

		JSONRespuesta listComponente = new JSONRespuesta();
		listComponente = componenteDao.listarTablaComponente(search, start, length, draw);
		return listComponente;
	}

	/**
	 * Metodo GET para listar los componente en una lista
	 * 
	 * @return lista de componentes
	 */
	@RequestMapping(value = "/componenteLista", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Componente> getComponenteLista() {
		List<Componente> listOfComponente = new ArrayList<Componente>();
		listOfComponente = componenteDao.listarComponente(0);
		return listOfComponente;
	}

	/**
	 * Metodo GET para listar los componentes según un parametro de busqueda
	 * 
	 * @param id
	 *            codigo de componente para listar
	 * @return lista de componente segun el parametro de busqueda
	 */
	@RequestMapping(value = "/componenteLista/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Componente> getComponenteLista(@PathVariable int id) {
		List<Componente> listOfComponente = new ArrayList<Componente>();
		listOfComponente = componenteDao.listarComponente(id);
		return listOfComponente;
	}

	/**
	 * Metodo POST de componentes para agregar un registro de componente
	 * 
	 * @param componente
	 *            objeto con los datos de componente
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/componentes", method = RequestMethod.POST, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody Componente componente) {

		boolean returnInsercion = componenteDao.agregarComponente(componente);
		if (returnInsercion) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Metodo PUT de componentes para modificar los datos de un registro
	 * 
	 * @param id
	 *            codigo de un registro de componente que se quiere modificar
	 * @param componente
	 *            objeto de con los datos de componente
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/componentes/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody Componente componente) {

		if (id > 0) {
			boolean returnModificar = componenteDao.modificarComponente(id, componente);
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
	 * Metodo DELETE de Componente para eliminar un registro
	 * 
	 * @param id
	 *            codigo del registro de componente que se queire eliminar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/componentes/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {

		boolean returnEliminar = componenteDao.eliminarComponente(id);
		if (returnEliminar) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
		}
	}
}
