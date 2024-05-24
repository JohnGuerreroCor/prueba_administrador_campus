/**
 * Clase OfertaInformacionController para gestionar la información de la oferta
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

import co.edu.usco.lcms.model.OfertaInformacion;
import co.edu.usco.lcms.servicios.OfertaInformacionServicio;
import co.edu.usco.lcms.utility.Respuesta;
import co.edu.usco.lcms.utility.ValidadorParametro;
import co.edu.usco.lcms.utility.ValidadorParametro.TipoValidador;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class OfertaInformacionController {

	@Autowired
	OfertaInformacionServicio ofertaInformacionServicio;

	/**
	 * Metodo POST para agregar un registro de oferta información
	 * 
	 * @param ofertaInformacion
	 *            objeto con los datos para el registro que se quiere agregar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/ofertaInformacionSer", method = RequestMethod.POST, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody OfertaInformacion ofertaInformacion) {

		boolean titulo_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				ofertaInformacion.getTitulo(), 255);
		boolean orden_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
				String.valueOf(ofertaInformacion.getOrden()), 2);

		if (!titulo_valido) {
			return returnMensaje("Campo titulo no valido");
		}
		if (!orden_valido) {
			return returnMensaje("Campo titulo no valido");
		}

		String returnInsercion = ofertaInformacionServicio.agregarOfertaInformacion(ofertaInformacion);

		if (returnInsercion.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnInsercion);
		}
	}

	/**
	 * Metodo PUT para modificar un registro
	 * 
	 * @param id
	 *            codigo del registro que se quiere modificar
	 * @param ofertaInformacion
	 *            obejto con los datos del registro que se quiere modificar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/ofertaInformacionSer/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody OfertaInformacion ofertaInformacion) {
		if (id > 0) {
			boolean titulo_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					ofertaInformacion.getTitulo(), 255);
			boolean orden_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
					String.valueOf(ofertaInformacion.getOrden()), 2);

			if (!titulo_valido) {
				return returnMensaje("Campo titulo no valido");
			}
			if (!orden_valido) {
				return returnMensaje("Campo titulo no valido");
			}
			String returnModificar = ofertaInformacionServicio.modificarOfertaInformacion(id, ofertaInformacion);
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
	 * Metodo DELETE para eliminar un registro de oferta información
	 * 
	 * @param id
	 *            codigo del registro que se quiere eliminar de oferta
	 *            información
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/ofertaInformacionSer/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {
		String returnEliminar = ofertaInformacionServicio.eliminarOfertaInformacion(id);
		
		if (returnEliminar.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnEliminar);
		}
		
	}

	/**
	 * Metodo GET para listar los datos de oferta información según parametro de
	 * bu8squeda
	 * 
	 * @param id
	 *            codigo para consultar los datos de oferta información
	 * @return retorna lista de oferta información
	 */
	@RequestMapping(value = "/ofertaInformacionSer/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<OfertaInformacion> getOfertaInformacionLista(@PathVariable int id) {
		List<OfertaInformacion> listaOfertaInformacion = new ArrayList<OfertaInformacion>();
		listaOfertaInformacion = ofertaInformacionServicio.listarOfertaInformacion(id);
		return listaOfertaInformacion;
	}

	/**
	 * Metodo GET para listar oferta información
	 * 
	 * @return retornar oferta información en una lista
	 */
	@RequestMapping(value = "/ofertaInformacionSer", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<OfertaInformacion> getOfertaInformacionLista() {
		List<OfertaInformacion> listaOfertaInformacion = new ArrayList<OfertaInformacion>();
		listaOfertaInformacion = ofertaInformacionServicio.listarOfertaInformacion(0);
		return listaOfertaInformacion;
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
