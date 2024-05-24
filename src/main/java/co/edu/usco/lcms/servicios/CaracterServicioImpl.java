/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.CaracterDao;
import co.edu.usco.lcms.model.Caracter;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author jankarlos
 *
 */
@Component
public class CaracterServicioImpl implements CaracterServicio {

	@Autowired
	CaracterDao caracterDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.CaracterServicio#agregarCaracter(org.usco.lcms.
	 * model.Caracter)
	 */
	@Override
	public String agregarCaracter(Caracter caracter) {
		// TODO Auto-generated method stub
		boolean returnInsercion = caracterDao.agregarCaracter(caracter);

		if (returnInsercion) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.CaracterServicio#modificarCaracter(int,
	 * co.edu.usco.lcms.model.Caracter)
	 */
	@Override
	public String modificarCaracter(String id, Caracter caracter) {
		// TODO Auto-generated method stub
		boolean returnModificar = caracterDao.modificarCaracter(id, caracter);
		if (returnModificar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.CaracterServicio#eliminarCaracter(int)
	 */
	@Override
	public String eliminarCaracter(String id) {
		// TODO Auto-generated method stub
		boolean returnEliminar = caracterDao.eliminarCaracter(id);
		if (returnEliminar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.CaracterServicio#listarCaracter(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Caracter> listarCaracter() {
		// TODO Auto-generated method stub
		return caracterDao.listarCaracter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.CaracterServicio#listarTablaCaracter(java.lang.
	 * String, int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaCaracter(String search, int start, int length, int draw) {
		// TODO Auto-generated method stub
		return caracterDao.listarTablaCaracter(search, start, length, draw);
	}

}
