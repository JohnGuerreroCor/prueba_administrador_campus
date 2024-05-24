/**
 * Clase HoraRestController para gestioar el servicio web hora
 */
package co.edu.usco.lcms.controller.reservaespacios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.model.reservaespacios.Horas;
import co.edu.usco.lcms.servicios.reservaespacio.HoraServicio;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@RestController
public class HoraRestController {

	@Autowired
	HoraServicio horaServicio;

	/**
	 * Metodo para listar horas
	 * 
	 * @return lista horas
	 */
	@RequestMapping(value = "/horasSer", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms}) OR hasRole(${role.docente}) OR hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public List<Horas> getListadoHoras() {
		List<Horas> listHoras = new ArrayList<Horas>();
		listHoras = horaServicio.listarHoras();
		return listHoras;
	}
}
