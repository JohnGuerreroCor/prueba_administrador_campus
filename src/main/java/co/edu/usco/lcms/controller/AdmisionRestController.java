package co.edu.usco.lcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.dao.CursoDao;
import co.edu.usco.lcms.servicios.AdmisionServicio;
import co.edu.usco.lcms.utility.Respuesta;

@RestController
public class AdmisionRestController {

	@Autowired
	AdmisionServicio admisionServicio;
	
	@Autowired
	CursoDao cursoDao;

	@RequestMapping(value = "/admisionUsuario/{codigo}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Respuesta> agregar(@PathVariable int codigo) {
		
		String mensaje = admisionServicio.agregarAdmision(codigo);		
		Respuesta respuesta = new Respuesta();
		respuesta.setMensaje(mensaje);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		
	}

	@RequestMapping(value = "/cursoUp", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public String curso() {
		cursoDao.updateCurso();
		return "ok1";
	}

	@RequestMapping(value = "/crearusuario", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public String cursousu() {
		admisionServicio.agregarAdmisionPrueba();
		return "ok";
	}
	
	
}
