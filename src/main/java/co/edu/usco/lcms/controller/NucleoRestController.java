/**
 * Clase NucleoRestController para gestionar los servicios web de Núcleo
 */
package co.edu.usco.lcms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.dao.NucleoDao;
import co.edu.usco.lcms.model.Nucleo;

/**
 * 
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@RestController
public class NucleoRestController {

	@Autowired
	NucleoDao nucleoDao;

	/**
	 * Metodo GET para listar los nucleos segun un parametro
	 * @param id parametro a consultar los niveles
	 * @return retorna listado de niveles segun el parametro de busqueda
	 */
	@RequestMapping(value = "/nucleoLista/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Nucleo> getNucleoLista(@PathVariable int id) {
		List<Nucleo> listOfNbc = new ArrayList<Nucleo>();
		listOfNbc = nucleoDao.listarNucleo(id);
		return listOfNbc;
	}

	/**
	 * Metodo GET para listar todos los nucleos
	 * @return retorna lista de nucleos
	 */
	@RequestMapping(value = "/nucleoLista", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Nucleo> getNucleoLista() {
		List<Nucleo> listOfNbc = new ArrayList<Nucleo>();
		listOfNbc = nucleoDao.listarNucleo(0);
		return listOfNbc;
	}

	/*
	@RequestMapping(value = "/nucleos", method = RequestMethod.GET, headers = "Accept=application/json")
	public JSONRespuesta getNucleos(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw) {

		JSONRespuesta listNucleo = new JSONRespuesta();
		listNucleo = nucleoDao.listarTablaNucleo(search, start, length, draw);
		return listNucleo;

	}

	// ------------ POST - INSERT -----------
	@RequestMapping(value = "/nucleos", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Respuesta> adicionar(@RequestBody Nucleo nucleo) {

		boolean returnInsercion = nucleoDao.agregarNucleo(nucleo);
		if (returnInsercion) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
		}
	}

	// ---------------- PUT - UPDATE -----------
	@RequestMapping(value = "/nucleos/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody Nucleo nucleo) {

		if (id > 0) {
			boolean returnModificar = nucleoDao.modificarNucleo(id, nucleo);
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

	// ------------------- DELETE - ELIMINAR ----------
	@RequestMapping(value = "/nucleos/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {

		boolean returnEliminar = nucleoDao.eliminarNucleo(id);
		if (returnEliminar) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
		}
	}
*/
}