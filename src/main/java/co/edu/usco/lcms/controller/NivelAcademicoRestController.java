/**
 * Clase NivelAcademicoRestController para gestionar los servicios web de Nivel Acádemico listar, agregar, modificar, eliminar
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
import co.edu.usco.lcms.model.NivelAcademico;
import co.edu.usco.lcms.servicios.NivelAcademicoServicio;
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
public class NivelAcademicoRestController {

	@Autowired
	NivelAcademicoServicio nivelAcademicoServicio;

	/**
	 * Listar datos de Nivel Acádemico según la estructura que requiere
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
	 * @return retorna la lista de Nivel Acádemico segun los parametros enviados
	 */
	
	@RequestMapping(value = "/niveles/lista", method = RequestMethod.POST,consumes="application/json",headers = "content-type=application/x-www-form-urlencoded")
	//@Secured("ROLE_ADMINISTRADOR_LCMS")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public JSONRespuesta getNiveles(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {

		JSONRespuesta listNiveles = new JSONRespuesta();
		listNiveles = nivelAcademicoServicio.listarTablaNivelAcademico(search, start, length, draw, posicion,
				direccion);
		return listNiveles;
	}

	/**
	 * Metodo POST para agregar un registro de Nivel Acádemico
	 * 
	 * @param nivelAcademico
	 *            objeto con los datos de nivel acádemico que se queire
	 *            registrar
	 * @return retorna un listado de las asignaturas encontradas
	 */
	@RequestMapping(value = "/niveles", method = RequestMethod.POST, headers = "Accept=application/json")
	//@Secured("ROLE_ADMINISTRADOR_LCMS")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody NivelAcademico nivelAcademico) {
		boolean nombre_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				nivelAcademico.getNombre(), 50);

		if (!nombre_valido) {
			return returnMensaje("Campo nombre no valido");
		}

		String returnInsercion = nivelAcademicoServicio.agregarNivelAcademico(nivelAcademico);

		if (returnInsercion.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnInsercion);
		}

	}

	/**
	 * Metodo PUT para modificar un registro de nivel acádemico
	 * 
	 * @param id
	 *            codigo del registro que se quiere modificar
	 * @param nivelAcademico
	 *            objeto con los datos del nivel academico que se quiere
	 *            modificar
	 * @return retorna un listado de las asignaturas encontradas
	 */
	@RequestMapping(value = "/niveles/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	//@Secured("ROLE_ADMINISTRADOR_LCMS")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody NivelAcademico nivelAcademico) {

		if (id > 0) {
			boolean nombre_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					nivelAcademico.getNombre(), 50);

			if (!nombre_valido) {
				return returnMensaje("Campo nombre no valido");
			}

			String returnModificar = nivelAcademicoServicio.modificarNivelAcademico(id, nivelAcademico);
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
	 * Metodo DELETE para eliminar un registro de nivel acádemico
	 * 
	 * @param id
	 *            codigo del registro a eliminar
	 * @return retorna un listado de las asignaturas encontradas
	 */
	@RequestMapping(value = "/niveles/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	//@Secured("ROLE_ADMINISTRADOR_LCMS")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {
		String returnEliminar = nivelAcademicoServicio.eliminarNivelAcademico(id);
		if (returnEliminar.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnEliminar);
		}

	}

	/**
	 * Metodo GET para listar los registros de nivel academico
	 * 
	 * @return retorna la lista de nivel academico
	 */
	@RequestMapping(value = "/nivelAcademicoLista", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<NivelAcademico> getNivelAcademicoLista() {
		List<NivelAcademico> listOfNivelAcademico = new ArrayList<NivelAcademico>();
		listOfNivelAcademico = nivelAcademicoServicio.listarNivelAcademico(0);
		return listOfNivelAcademico;
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