/**
 * Clase UaaTipoRestController para gestionar los servicios web de UAA Tipo
 */
package co.edu.usco.lcms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.model.UaaTipo;
import co.edu.usco.lcms.servicios.UaaTipoServicio;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class UaaTipoRestController {

	@Autowired
	UaaTipoServicio uaaTipoServicio;

	/**
	 * Metodo GET para listar los tipos de uaa
	 * 
	 * @return retorna la lista de los registros de tipo UAA
	 */
	@RequestMapping(value = "/uaaTipoAll", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<UaaTipo> getUaaTipoAll() {
		List<UaaTipo> listOfTipoUaa = new ArrayList<UaaTipo>();
		listOfTipoUaa = uaaTipoServicio.listarUaaTipo(0);
		return listOfTipoUaa;
	}

	/**
	 * Metodo GET para listar los tipos de uaa según el codigo
	 * 
	 * @param id
	 *            codigo de uaa tipo
	 * @return retorna lista de tipoUAA segun codigo
	 */
	@RequestMapping(value = "/uaaTipoAll/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<UaaTipo> getUaaTipoId(@PathVariable int id) {
		List<UaaTipo> listOfTipoUaa = new ArrayList<UaaTipo>();
		listOfTipoUaa = uaaTipoServicio.listarUaaTipo(id);
		return listOfTipoUaa;
	}
}
