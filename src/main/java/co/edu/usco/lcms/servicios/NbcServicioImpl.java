/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.NbcDao;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Nbc;

/**
 * @author jankarlos
 *
 */
@Component
public class NbcServicioImpl implements NbcServicio {

	@Autowired
	NbcDao nbcDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.NbcServicio#agregarNbc(co.edu.usco.lcms.model.Nbc)
	 */
	@Override
	public String agregarNbc(Nbc nbc) {
		// TODO Auto-generated method stub
		if (nbc.getNombre() != null && nbc.getArea().getCodigo() > 0) {
			boolean returnInsercion = nbcDao.agregarNbc(nbc);

			if (returnInsercion) {
				return "OK";
			} else {
				return "Ocurrio un inconveniente, vuelve a intentarlo.";
			}
		} else {
			return "Todos los campos del formulario son obligatorios.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.NbcServicio#modificarNbc(int,
	 * co.edu.usco.lcms.model.Nbc)
	 */
	@Override
	public String modificarNbc(int id, Nbc nbc) {
		// TODO Auto-generated method stub
		if (nbc.getNombre() != null && nbc.getArea().getCodigo() > 0) {
			boolean returnModificar = nbcDao.modificarNbc(id, nbc);
			if (returnModificar) {
				return "OK";
			} else {
				return "Ocurrio un inconveniente, vuelve a intentarlo";
			}
		} else {
			return "Todos los campos del formulario son obligatorios.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.NbcServicio#eliminarNbc(int)
	 */
	@Override
	public String eliminarNbc(int id) {
		// TODO Auto-generated method stub
		boolean returnEliminar = nbcDao.eliminarNbc(id);
		if (returnEliminar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, No se puede eliminar este registro.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.NbcServicio#listarNbc()
	 */
	@Override
	public List<Nbc> listarNbc() {
		// TODO Auto-generated method stub
		return nbcDao.listarNbc();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.NbcServicio#listarTablaNbc(java.lang.String,
	 * int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarTablaNbc(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		return nbcDao.listarTablaNbc(search, start, length, draw, posicion, direccion);
	}

}
