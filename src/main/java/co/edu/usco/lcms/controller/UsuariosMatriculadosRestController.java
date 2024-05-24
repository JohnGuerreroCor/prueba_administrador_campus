
package co.edu.usco.lcms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.dao.UsuariosMatriculadosDao;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Matricula;
import co.edu.usco.lcms.model.Programa;
import co.edu.usco.lcms.servicios.UsuariosInscritosServicio;
import co.edu.usco.lcms.servicios.UsuariosMatriculadosServicio;
import co.edu.usco.lcms.utility.Respuesta;

/**
 * @author Jankarlos Diaz Vieda
 *
 */
@RestController
public class UsuariosMatriculadosRestController {

	@Autowired
	UsuariosMatriculadosServicio umServicio;
	
	@Autowired
	UsuariosMatriculadosDao matriculadosDao;
	
	@Autowired
	UsuariosInscritosServicio uiServicio;

	
	@RequestMapping(value = "/ListaUsuariosMatriculados", method = RequestMethod.POST,consumes="application/json",headers = "content-type=application/x-www-form-urlencoded")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public JSONRespuesta getUAA(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion,
			@RequestParam(value = "codigoOferta", defaultValue = "0") int codigoOferta) {
		
		JSONRespuesta listUaa = new JSONRespuesta();
		listUaa = umServicio.listarTablaUsuariosMatriculados(search, start, length, draw, posicion, direccion, codigoOferta);
		System.out.println("CANTIDAD TOTAL"+listUaa.getRecordsTotal());
		System.out.println("CANTIDAD FILTER"+listUaa.getRecordsFiltered());
		return listUaa;
	}
	
	@RequestMapping(value = "/ProgramasMatriculados", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Programa> getListadoProgramasPreMatricula() {
		List<Programa> ListaProgramas = new ArrayList<Programa>();
		ListaProgramas = matriculadosDao.ListaProgramasMatriculados();
		return ListaProgramas;
	}
		
	@RequestMapping(value = "/matriculadosOferta", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Matricula> getMatriculadosPorOferta(@RequestParam("oferta") int oferta) {
		List<Matricula> ListaMatricula = new ArrayList<Matricula>();
		ListaMatricula = matriculadosDao.ListaMatriculadosOferta(oferta);
		return ListaMatricula;
	}

	@RequestMapping(value = "/InformacionPersonalMatriculado", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public Matricula getInformacion(@RequestParam("matricula") int matricula) {
		Matricula matriculaDatos = umServicio.InformacionPersonalMatriculado(matricula);
		return matriculaDatos;
	}
	
	public ResponseEntity<Respuesta> returnMensaje(String mensaje) {
		Respuesta respuesta = new Respuesta();
		respuesta.setMensaje(mensaje);
		respuesta.setCodigo(0);
		respuesta.setExito(false);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}
}
