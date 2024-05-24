/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.OfertaInformacionDao;
import co.edu.usco.lcms.model.OfertaInformacion;

/**
 * @author jankarlos
 *
 */
@Component
public class OfertaInformacionServicioImpl implements OfertaInformacionServicio {

	@Autowired
	OfertaInformacionDao ofertaInformacionDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.OfertaInformacionServicio#
	 * agregarOfertaInformacion(co.edu.usco.lcms.model.OfertaInformacion)
	 */
	@Override
	public String agregarOfertaInformacion(OfertaInformacion ofertaInformacion) {
		// TODO Auto-generated method stub
		if (ofertaInformacion.getTitulo() != null && ofertaInformacion.getContenido() != null) {
			boolean returnInsercion = ofertaInformacionDao.agregarOfertaInformacion(ofertaInformacion);

			if (returnInsercion) {
				return "OK";
			} else {
				return "Ocurrio un inconveniente, vuelve a intentarlo.";
			}
		} else {
			return "Favor llenar todos los campos obligatorios del formulario.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.OfertaInformacionServicio#
	 * modificarOfertaInformacion(int, co.edu.usco.lcms.model.OfertaInformacion)
	 */
	@Override
	public String modificarOfertaInformacion(int id, OfertaInformacion ofertaInformacion) {
		// TODO Auto-generated method stub
		if (ofertaInformacion.getTitulo() != null && ofertaInformacion.getContenido() != null) {
			boolean returnModificar = ofertaInformacionDao.modificarOfertaInformacion(id, ofertaInformacion);
			if (returnModificar) {
				return "OK";
			} else {
				return "Ocurrio un inconveniente, vuelve a intentarlo";
			}
		} else {
			return "Favor llenar todos los campos obligatorios del formulario.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.OfertaInformacionServicio#
	 * eliminarOfertaInformacion(int)
	 */
	@Override
	public String eliminarOfertaInformacion(int id) {
		// TODO Auto-generated method stub
		boolean returnEliminar = ofertaInformacionDao.eliminarOfertaInformacion(id);
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
	 * co.edu.usco.lcms.servicios.OfertaInformacionServicio#listarOfertaInformacion
	 * (int)
	 */
	@Override
	public List<OfertaInformacion> listarOfertaInformacion(int id) {
		// TODO Auto-generated method stub
		return ofertaInformacionDao.listarOfertaInformacion(id);
	}

}
