/**
 * Clase ProgramaRestController para gestionar los servicios web de Programa listar, agregar, modificar, eliminar
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

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Programa;
import co.edu.usco.lcms.servicios.ProgramaServicio;
import co.edu.usco.lcms.utility.Respuesta;
import co.edu.usco.lcms.utility.ValidadorParametro;
import co.edu.usco.lcms.utility.ValidadorParametro.TipoValidador;

/**
 * 
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class ProgramaRestController {

	@Autowired
	ProgramaServicio programaServicio;

	/**
	 * Listar datos de Programa según la estructura que requiere DataTable de
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
	 * @return retorna la lista de Programa segun los parametros enviados
	 */
	@RequestMapping(value = "/programas/lista", method = RequestMethod.POST,consumes="application/json",headers = "content-type=application/x-www-form-urlencoded")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public JSONRespuesta getProgramas(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {

		JSONRespuesta listPrograma = new JSONRespuesta();
		listPrograma = programaServicio.listarTablaPrograma(search, start, length, draw, posicion, direccion);
		return listPrograma;
	}

	/**
	 * Metodo GET para listar los registros de programa
	 * 
	 * @return retorna lista de programa
	 */
	@RequestMapping(value = "/programasLista", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Programa> getProgramasLista() {
		List<Programa> listaProgramas = new ArrayList<Programa>();
		listaProgramas = programaServicio.listarPrograma(0, 0);
		return listaProgramas;
	}

	/**
	 * Metodo GET para listar los programas según criterios
	 * 
	 * @param id
	 *            codigo de sede para filtrar los prohgramas según la sede a que
	 *            pertenezcan
	 * @param proMod
	 *            codigo del programa a modificar parametro para listar el
	 *            programa que se quiere modificar
	 * @return retorna la lista de programas segun los parametros de búsqueda
	 */
	@RequestMapping(value = "/programasLista/{id}/{proMod}", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Programa> getProgramasLista(@PathVariable int id, @PathVariable int proMod) {
		List<Programa> listaProgramas = new ArrayList<Programa>();
		listaProgramas = programaServicio.listarPrograma(id, proMod);
		return listaProgramas;
	}

	/**
	 * Metodo POST para agregar un registro a programas
	 * 
	 * @param programa
	 *            objeto con los datos de programa a registrar
	 * @return retorna mensaje de operación exitosa o ocurrio un errror
	 */
	@RequestMapping(value = "/programas", method = RequestMethod.POST, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody Programa programa) {
		boolean titulo_otorgado_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				programa.getTitulo_otorgado(), 100);
		boolean horario_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				programa.getHorario(), 100);

		if (!titulo_otorgado_valido) {
			return returnMensaje("Campo titulo otorgado no valido");
		}
		if (!horario_valido) {
			return returnMensaje("Campo horario no valido");
		}
		String returnInsercion = programaServicio.agregarPrograma(programa);
		if (returnInsercion.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnInsercion);
		}
	}

	/**
	 * Metodo PUT para modificar un registro de Programa
	 * 
	 * @param id
	 *            Código del registro que se quiere modificar
	 * @param programa
	 *            objeto con los datos del registro que se quiere modificar
	 * @return retorna mensaje de operación exitosa o ocurrio un errror
	 */
	@RequestMapping(value = "/programas/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody Programa programa) {

		if (id > 0) {
			boolean titulo_otorgado_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					programa.getTitulo_otorgado(), 100);
			boolean horario_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					programa.getHorario(), 100);

			if (!titulo_otorgado_valido) {
				return returnMensaje("Campo titulo otorgado no valido");
			}
			if (!horario_valido) {
				return returnMensaje("Campo horario no valido");
			}
			String returnModificar = programaServicio.modificarPrograma(id, programa);
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
	 * Metodo DELETE para eliminar un registro
	 * 
	 * @param id
	 *            código del registro que se quiere eliminar
	 * @return retorna mensaje de operación exitosa o ocurrio un errror
	 */
	@RequestMapping(value = "/programas/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {

		String returnEliminar = programaServicio.eliminarPrograma(id);
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