/**
 * Clase OfertaRequisitoRestController para gestionar los servicios web de oferta requisito 
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
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.model.OfertaRequisito;
import co.edu.usco.lcms.servicios.OfertaRequisitoServicio;
import co.edu.usco.lcms.utility.Respuesta;
import co.edu.usco.lcms.utility.ValidadorParametro;
import co.edu.usco.lcms.utility.ValidadorParametro.TipoValidador;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class OfertaRequisitoRestController {

	@Autowired
	OfertaRequisitoServicio ofertaRequisitoServicio;

	/**
	 * Metodo GET para listar los registro de oferta requisito según el id a
	 * consultar
	 * 
	 * @param id
	 *            codigo de oferta con el cual se quiere consultar los registros
	 *            de oferta requisito
	 * @return retorna una lista con los registro de oferta requisito
	 *         pertenecientes a una oferta según el parametro de busqueda
	 */
	@RequestMapping(value = "/ofertaRequisitosLista/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<OfertaRequisito> getOfertaRequisito(@PathVariable int id) {
		List<OfertaRequisito> listaProgramas = new ArrayList<OfertaRequisito>();
		listaProgramas = ofertaRequisitoServicio.listarOfertaRequisitos(id);
		return listaProgramas;
	}

	/**
	 * Metodo POST para agregar un registro de de oferta requisito.
	 * 
	 * @param ofertaRequisito
	 *            objeto con los datos de oferta requisito a agregar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/ofertaRequisito", method = RequestMethod.POST, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody OfertaRequisito ofertaRequisito) {

		boolean descripcion_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				ofertaRequisito.getDescripcion(), 255);

		if (!descripcion_valido) {
			return returnMensaje("Campo descripción no valido");
		}

		String returnInsercion = ofertaRequisitoServicio.agregarOfertaRequisitos(ofertaRequisito);
		if (returnInsercion.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnInsercion);
		}
	}

	/**
	 * Metodo DELETE para eliminar un registro de oferta requisito
	 * 
	 * @param id
	 *            codigo del registro que se quiere eliminar de oferta requisito
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/ofertaRequisito/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {

		String returnEliminar = ofertaRequisitoServicio.eliminarOfertaRequisitos(id);
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
