/**
 * 
 */
package co.edu.usco.lcms.controller.proacademica;

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

import co.edu.usco.lcms.model.reservaespacios.EspacioOcupacion;
import co.edu.usco.lcms.servicios.proacademica.EspacioOcupacionVirtualServicio;
import co.edu.usco.lcms.utility.Respuesta;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class EspacioOcupacionVirtualRestController {

	@Autowired
	EspacioOcupacionVirtualServicio espacioOcupacionVirtualServicio;

	@RequestMapping(value = "/espacioOcupacionVirtual", method = RequestMethod.POST, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody EspacioOcupacion espacioOcupacion) {

		String returnInsercion = espacioOcupacionVirtualServicio.agregarEspacioOcupacionVirtual(espacioOcupacion);

		if (returnInsercion.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnInsercion);
		}
	}

	@RequestMapping(value = "/espacioOcupacionVirtualList", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public List<EspacioOcupacion> getEspacioOcupacionVirtual(@RequestParam("curso") int curso) {
		List<EspacioOcupacion> espacioOcupacion = espacioOcupacionVirtualServicio.listadoEspacioOcupacion(curso);
		return espacioOcupacion;

	}

	/**
	 * Motodo DELETE espacio ocupacion para eliminar un registro de espacio
	 * ocupacion
	 * 
	 * @param id
	 *            codigo del registro espacio ocupacion que se quiere eliminar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/espacioOcupacionVirtual/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {

		String returnEliminar = espacioOcupacionVirtualServicio.eliminarEspacioOcupacion(id, 0);
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
