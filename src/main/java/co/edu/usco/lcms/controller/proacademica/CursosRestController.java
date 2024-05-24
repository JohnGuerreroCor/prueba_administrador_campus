/**
 * 
 */
package co.edu.usco.lcms.controller.proacademica;

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

import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.servicios.proacademica.CursoProgServicio;
import co.edu.usco.lcms.utility.Respuesta;
import co.edu.usco.lcms.utility.ValidadorParametro;
import co.edu.usco.lcms.utility.ValidadorParametro.TipoValidador;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class CursosRestController {

	@Autowired
	CursoProgServicio cursoServicio;

	/**
	 * Listar datos de curso según la estructura que requiere DataTable de
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
	 * @return retorna la lista de cursos segun los parametros enviados
	 */
	@RequestMapping(value = "/cursos/lista", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded")
	@PreAuthorize("hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public JSONRespuesta getCursos(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion,
			@RequestParam(value = "calendario", defaultValue = "0") int calendario) {
		JSONRespuesta listCursos = new JSONRespuesta();
		listCursos = cursoServicio.listarTablaCurso(search, start, length, draw, posicion, direccion, calendario);
		return listCursos;
	}

	/**
	 * Metodo POST cursos para agregarlas
	 * 
	 * @param curso
	 *            objeto que contiene los parametros de las cursos
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/cursoSer", method = RequestMethod.POST, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody Curso curso) {

		boolean cupo_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
				String.valueOf(curso.getCupo()), 3);
		boolean semanas_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
				String.valueOf(curso.getSemanas()), 2);

		if (!cupo_valido) {
			return returnMensaje("Campo Cupo no valido");
		}
		if (!semanas_valido) {
			return returnMensaje("Campo Semanas no valido");
		}

		String returnInsercion = cursoServicio.agregarCurso(curso);
		if (returnInsercion.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnInsercion);
		}
	}

	/**
	 * Metodo PUT curso para poder modificar la curso
	 * 
	 * @param id
	 *            codigo de la curso a modificar
	 * @param curso
	 *            objeto que contiene los parametros de las cursos
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/cursoSer/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody Curso curso) {

		if (id > 0) {
			boolean grupo_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
					String.valueOf(curso.getGrupo()), 2);
			boolean cupo_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
					String.valueOf(curso.getCupo()), 3);

			if (!grupo_valido) {
				return returnMensaje("Campo Grupo no valido");
			}
			if (!cupo_valido) {
				return returnMensaje("Campo Cupo no valido");
			}

			String returnModificar = cursoServicio.modificarCurso(id, curso);
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
	 * Motodo DELETE curso para eliminar una curso
	 * 
	 * @param id
	 *            codigo de la curso que se quiere eliminar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/cursoSer/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {
		String returnEliminar = cursoServicio.eliminarCurso(id);
		if (returnEliminar.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnEliminar);
		}
	}

	/**
	 * Metodo para listar los docentes
	 * 
	 * @param criterio
	 *            parametro de busqueda para filtrar los docentes
	 * @return retorna listado de los doncentes
	 */
	@RequestMapping(value = "/buscarCurso", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public List<Curso> getCursos(@RequestParam("criterio") String criterio) {
		List<Curso> listCurso = new ArrayList<Curso>();
		listCurso = cursoServicio.buscarCurso(criterio, 0, "");
		return listCurso;
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
