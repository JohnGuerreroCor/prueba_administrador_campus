/**
 * Clase PerfilRestController para gestionar el servicio web de Perfil
 */
package co.edu.usco.lcms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.configuration.User;
import co.edu.usco.lcms.dao.PerfilDao;
import co.edu.usco.lcms.model.UsuarioGrupo;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class PerfilRestController {

	@Autowired
	PerfilDao perfilDao;

	/**
	 * Metodo GET para listar los datos del perfil del usuario
	 * 
	 * @return retornar los datos de perfil
	 */
	@RequestMapping(value = "/perfilSer", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms}) OR hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms}) OR hasRole(${role.docente}) OR hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public UsuarioGrupo getPersona() {
		UsuarioGrupo listaPersona = new UsuarioGrupo();
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		listaPersona = perfilDao.listarPerfil(user.getPersona().getCodigo()).get(0);
		return listaPersona;
	}
}
