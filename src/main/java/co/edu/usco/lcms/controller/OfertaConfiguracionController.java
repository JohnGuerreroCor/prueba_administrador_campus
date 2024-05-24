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

import co.edu.usco.lcms.dto.OfertaConfiguracionDTO;
import co.edu.usco.lcms.model.OfertaConfiguracion;
import co.edu.usco.lcms.model.OfertaInformacion;
import co.edu.usco.lcms.servicios.OfertaConfiguracionServicio;
import co.edu.usco.lcms.utility.Respuesta;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class OfertaConfiguracionController {

	@Autowired
	OfertaConfiguracionServicio ofertaConfiguracionServicio;

	/**
	 * Metodo POST para agregar un registro de oferta configuracion
	 * 
	 * @param ofertaInformacion
	 *            objeto con los datos para el registro que se quiere agregar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/ofertaConfiguracionSer", method = RequestMethod.POST, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody OfertaConfiguracion ofertaConfiguracion) {

		String returnInsercion = ofertaConfiguracionServicio.agregarOfertaConfiguracion(ofertaConfiguracion);

		if (returnInsercion.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnInsercion);
		}
	}
	
	/**
	 * Metodo GET para listar oferta información
	 * 
	 * @return retornar oferta información en una lista
	 */
	@RequestMapping(value = "/ofertaConfiguracionSer/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<OfertaConfiguracionDTO> getOfertaConfiguracionLista(@PathVariable int id) {
		List<OfertaConfiguracionDTO> listaOfertaInformacion = new ArrayList<OfertaConfiguracionDTO>();
		listaOfertaInformacion = ofertaConfiguracionServicio.listarOfertaConfiguracion(id);
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
