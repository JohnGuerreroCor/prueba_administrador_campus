/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.UaaTipoDao;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.UaaTipo;

/**
 * @author jankarlos
 *
 */
@Component
public class UaaTipoServicioImpl implements UaaTipoServicio {

	@Autowired
	UaaTipoDao uaaTipoDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.UaaTipoServicio#agregarUaaTipo(org.usco.lcms.
	 * model.UaaTipo)
	 */
	@Override
	public String agregarUaaTipo(UaaTipo uaaTipo) {
		// TODO Auto-generated method stub
		if (uaaTipo.getNombre() != null && uaaTipo.getTemporal() > -1) {
			if (uaaTipo.getNombre().length() < 99) {
				boolean returnInsercion = uaaTipoDao.agregarUaaTipo(uaaTipo);
				if (returnInsercion) {
					return "OK";
				} else {
					return "Ocurrio un inconveniente, vuelve a intentarlo.";
				}
			} else {
				return "Máximo 100 caracteres en el campo nombre.";
			}
		} else {
			return "Favor llenar todos los campos obligatorios.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.UaaTipoServicio#modificarUaaTipo(int,
	 * co.edu.usco.lcms.model.UaaTipo)
	 */
	@Override
	public String modificarUaaTipo(int id, UaaTipo uaaTipo) {
		// TODO Auto-generated method stub
		if (uaaTipo.getNombre() != null && uaaTipo.getTemporal() > -1) {
			if (uaaTipo.getNombre().length() < 99) {
				boolean returnModificar = uaaTipoDao.modificarUaaTipo(id, uaaTipo);
				if (returnModificar) {
					return "OK";
				} else {
					return "Ocurrio un inconveniente, vuelve a intentarlo.";
				}
			} else {
				return "Máximo 100 caracteres en el campo nombre.";
			}
		} else {
			return "Favor llenar todos los campos obligatorios.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.UaaTipoServicio#eliminarUaaTipo(int)
	 */
	@Override
	public String eliminarUaaTipo(int id) {
		// TODO Auto-generated method stub
		boolean returnModificar = uaaTipoDao.eliminarUaaTipo(id);
		if (returnModificar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.UaaTipoServicio#listarUaaTipo(int)
	 */
	@Override
	public List<UaaTipo> listarUaaTipo(int codigo) {
		// TODO Auto-generated method stub
		return uaaTipoDao.listarUaaTipo(codigo);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.UaaTipoServicio#listarTablaUaaTipo(java.lang.
	 * String, int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaUaaTipo(String search, int start, int length, int draw) {
		// TODO Auto-generated method stub
		return uaaTipoDao.listarTablaUaaTipo(search, start, length, draw);
	}

}
