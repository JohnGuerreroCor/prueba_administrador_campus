/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.NivelAcademicoDao;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.NivelAcademico;

/**
 * @author jankarlos
 *
 */
@Component
public class NivelAcademicoServicioImpl implements NivelAcademicoServicio {

	@Autowired
	NivelAcademicoDao nivelAcademicoDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.NivelAcademicoServicio#agregarNivelAcademico(org.
	 * usco.lcms.model.NivelAcademico)
	 */
	@Override
	public String agregarNivelAcademico(NivelAcademico nivelAcademico) {
		// TODO Auto-generated method stub
		if (nivelAcademico.getNombre() != null && nivelAcademico.getFormalidad().getCodigo() > 0) {
			boolean returnInsercion = nivelAcademicoDao.agregarNivelAcademico(nivelAcademico);
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
	 * @see
	 * co.edu.usco.lcms.servicios.NivelAcademicoServicio#modificarNivelAcademico(
	 * int, co.edu.usco.lcms.model.NivelAcademico)
	 */
	@Override
	public String modificarNivelAcademico(int id, NivelAcademico nivelAcademico) {
		// TODO Auto-generated method stub
		if (nivelAcademico.getNombre() != null && nivelAcademico.getFormalidad().getCodigo() > 0) {
			boolean returnModificar = nivelAcademicoDao.modificarNivelAcademico(id, nivelAcademico);
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
	 * @see
	 * co.edu.usco.lcms.servicios.NivelAcademicoServicio#eliminarNivelAcademico(
	 * int)
	 */
	@Override
	public String eliminarNivelAcademico(int id) {
		// TODO Auto-generated method stub
		boolean returnEliminar = nivelAcademicoDao.eliminarNivelAcademico(id);
		if (returnEliminar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, No se puede eliminar este registro.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.NivelAcademicoServicio#listarNivelAcademico(int)
	 */
	@Override
	public List<NivelAcademico> listarNivelAcademico(int id) {
		// TODO Auto-generated method stub
		return nivelAcademicoDao.listarNivelAcademico(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.NivelAcademicoServicio#listarTablaNivelAcademico(
	 * java.lang.String, int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaNivelAcademico(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		return nivelAcademicoDao.listarTablaNivelAcademico(search, start, length, draw, posicion, direccion);
	}

}
