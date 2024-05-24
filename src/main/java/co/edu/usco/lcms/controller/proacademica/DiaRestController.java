/**
 * 
 */
package co.edu.usco.lcms.controller.proacademica;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.model.Dia;
import co.edu.usco.lcms.servicios.proacademica.DiaServicio;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class DiaRestController {

	@Autowired
	DiaServicio diaServicio;

	@RequestMapping(value = "/diaList", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public List<Dia> getDia() {
		List<Dia> tipoDia = diaServicio.listaDia();
		return tipoDia;

	}

	@RequestMapping(value = "/diaHorasOcupadasSer", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.prog.academica.lcms}) OR hasRole(${role.admin.prog.academica.facultad.lcms})")
	public List<Dia> getOcupadasHoras(@RequestParam("dia") int dia, @RequestParam("espacio") int espacio,
			@RequestParam("espacioTipo") int espacioTipo, @RequestParam("docente") int docente,
			@RequestParam("actividad") int actividad, @RequestParam("fecha") String fecha,
			@RequestParam("semanas") int semanas) {
		List<Dia> listHoras = new ArrayList<Dia>();
		listHoras = diaServicio.listarDiasHorasOcupados(dia, espacio, espacioTipo, docente, actividad, fecha, semanas);
		return listHoras;
	}
}
