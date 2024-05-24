package co.edu.usco.lcms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.dao.EstudioAnteriorDao;
import co.edu.usco.lcms.dao.PersonaDao;
import co.edu.usco.lcms.dao.UsuariosInscritosDao;
import co.edu.usco.lcms.model.EstudioAnterior;
import co.edu.usco.lcms.model.Inscripcion;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Nbc;
import co.edu.usco.lcms.model.Persona;
import co.edu.usco.lcms.model.Programa;
import co.edu.usco.lcms.servicios.UsuariosInscritosServicio;
import co.edu.usco.lcms.utility.Respuesta;
import co.edu.usco.lcms.utility.ValidadorParametro;
import co.edu.usco.lcms.utility.ValidadorParametro.TipoValidador;

/**
 * @author Jankarlos Diaz Vieda
 *
 */
@RestController
public class UsuariosInscritosRestController {

	@Autowired
	UsuariosInscritosServicio uiServicio;

	@Autowired
	UsuariosInscritosDao inscripcionDao;
	
	@Autowired
	EstudioAnteriorDao estudioAnteriorDao;
	
	@Autowired
	PersonaDao personaDao;

	@RequestMapping(value = "/ListaUsuariosInscritos", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public JSONRespuesta getUAA(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion,
			@RequestParam(value = "codigoOferta", defaultValue = "0") int codigoOferta) {

		JSONRespuesta listUaa = new JSONRespuesta();
		listUaa = uiServicio.listarTablaUsuariosInscritos(search, start, length, draw, posicion, direccion,
				codigoOferta);
		return listUaa;
	}

	@RequestMapping(value = "/ProgramasInscripciones", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Programa> getListadoProgramasPreInscripcion() {
		List<Programa> ListaProgramas = new ArrayList<Programa>();
		ListaProgramas = inscripcionDao.ListaProgramasInscripciones();
		return ListaProgramas;
	}
	

	@RequestMapping(value = "/consultarEstudiosAnterioresInscripcion", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<EstudioAnterior> getEstudiosAnteriores(@RequestParam("persona") int persona, @RequestParam("tercero") int tercero) {
		List<EstudioAnterior> estudiosAnteriores = new ArrayList<EstudioAnterior>();
		estudiosAnteriores= estudioAnteriorDao.consultarEstudioAnterior(persona, tercero);
		return estudiosAnteriores;
	}
	
	@RequestMapping(value = "/InformacionPersonalInscrito", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Inscripcion> getInformacion(@RequestParam("ins_codigo") int ins_codigo) {
		List<Inscripcion> inscripcion = uiServicio.InformacionPersonalInscrito(ins_codigo,0,0);
		return inscripcion;
	}

	public ResponseEntity<Respuesta> returnMensaje(String mensaje) {
		Respuesta respuesta = new Respuesta();
		respuesta.setMensaje(mensaje);
		respuesta.setCodigo(0);
		respuesta.setExito(false);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}
	

	@RequestMapping(value = "/consultarPersona", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public Persona getPersona(@RequestParam("codigo") String codigo) {
		Persona persona = personaDao.consultarPersona(codigo);
		return persona;
	}
	
	@RequestMapping(value = "/persona-correo", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestParam("codigo") int codigo, @RequestParam("correo") String correo) {

		boolean nombre_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_EMAIL, correo,
				200);

		if (!nombre_valido) {
			return returnMensaje("Campo Correo no valido");
		}

		boolean returnInsercion = personaDao.actualizarCorreo(codigo, correo);

		if (returnInsercion) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje("Ocurrio un inconveniente, vuelve a intentarlo.");
		}
	}
}
