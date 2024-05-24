/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.SedeDao;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Sede;

/**
 * @author jankarlos
 *
 */
@Component
public class SedeServicioImpl implements SedeServicio {

	@Autowired
	SedeDao sedeDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.SedeServicio#agregarSede(co.edu.usco.lcms.model.
	 * Sede)
	 */
	@Override
	public String agregarSede(Sede sede) {
		// TODO Auto-generated method stub
		boolean returnInsercion = sedeDao.agregarSede(sede);
		if (returnInsercion) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.SedeServicio#modificarSede(int,
	 * co.edu.usco.lcms.model.Sede)
	 */
	@Override
	public String modificarSede(int id, Sede sede) {
		// TODO Auto-generated method stub
		boolean returnModificar = sedeDao.modificarSede(id, sede);
		if (returnModificar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.SedeServicio#eliminarSede(int)
	 */
	@Override
	public String eliminarSede(int id) {
		// TODO Auto-generated method stub
		boolean returnEliminar = sedeDao.eliminarSede(id);
		if (returnEliminar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.SedeServicio#listarSede(int, int)
	 */
	@Override
	public List<Sede> listarSede(int estado, int id) {
		// TODO Auto-generated method stub
		return sedeDao.listarSede(estado, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.SedeServicio#listarTablaSede(java.lang.String,
	 * int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaSede(String search, int start, int length, int draw) {
		// TODO Auto-generated method stub
		return sedeDao.listarTablaSede(search, start, length, draw);
	}

}
