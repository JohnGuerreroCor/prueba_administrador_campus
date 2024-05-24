/**
 * 
 */
package co.edu.usco.lcms.servicios.reservaespacio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.reservaespacio.EspacioOcupacionDao;
import co.edu.usco.lcms.dao.reservaespacio.ServiciosAdobeConnectDao;
import co.edu.usco.lcms.dao.reservaespacio.SolicitudDao;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.reservaespacios.EspacioOcupacion;
import co.edu.usco.lcms.model.reservaespacios.Solicitud;

/**
 * @author Jankarlos Diaz Vieda
 *
 */
@Component
public class EspacioOcupacionServicioImpl implements EspacioOcupacionServicio {

	@Autowired
	EspacioOcupacionDao espacioOcupacionDao;

	@Autowired
	SolicitudDao solicitudDao;

	@Autowired
	ServiciosAdobeConnectDao serviciosAdobeConnectDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.EspacioOcupacionServicio#
	 * agregarEspacioOcupacion(co.edu.usco.lcms.model.reservaespacios.
	 * EspacioOcupacion)
	 */
	@Override
	public String agregarEspacioOcupacion(EspacioOcupacion espacioOcupacion) {
		// TODO Auto-generated method stub
		int returnInsercion = espacioOcupacionDao.agregarEspacioOcupacion(espacioOcupacion);

		if (returnInsercion > 0) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.EspacioOcupacionServicio#
	 * modificarEspacioOcupacion(int,
	 * co.edu.usco.lcms.model.reservaespacios.EspacioOcupacion)
	 */
	@Override
	public String modificarEspacioOcupacion(int id, EspacioOcupacion espacioOcupacion) {
		// TODO Auto-generated method stub
		boolean returnModificar = espacioOcupacionDao.modificarEspacioOcupacion(id, espacioOcupacion);

		if (returnModificar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.EspacioOcupacionServicio#
	 * eliminarEspacioOcupacion(int)
	 */
	@Override
	public String eliminarEspacioOcupacion(int id, Solicitud solicitud) {
		// TODO Auto-generated method stub
		boolean returnEliminarSolicitud = solicitudDao.eliminarSolicitud(id);
		if (returnEliminarSolicitud) {
			boolean returnEliminar = espacioOcupacionDao.eliminarEspacioOcupacion(id, solicitud);
			if (returnEliminar) {
				boolean returnEliminarVideo = serviciosAdobeConnectDao
						.eliminarReunion(solicitud.getCodigoVideoconferencia());
				if (returnEliminarVideo) {
					return "OK";
				} else {
					return "Ocurrio un inconveniente al eliminar la reunión en Adobe Connect, vuelve a intentarlo.";
				}
			} else {
				return "Ocurrio un inconveniente, vuelve a intentarlo.";
			}
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.EspacioOcupacionServicio#
	 * listarEspacioOcupacion(long)
	 */
	@Override
	public List<EspacioOcupacionDao> listarEspacioOcupacion(long codigo) {
		// TODO Auto-generated method stub
		return espacioOcupacionDao.listarEspacioOcupacion(codigo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.EspacioOcupacionServicio#
	 * listarTablaEspacioOcupacion(java.lang.String, int, int, int, int,
	 * java.lang.String)
	 */
	@Override
	public JSONRespuesta listarTablaEspacioOcupacion(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		return espacioOcupacionDao.listarTablaEspacioOcupacion(search, start, length, draw, posicion, direccion);
	}

	@Override
	public List<Solicitud> listarGrabaciones(long codigo) {
		// TODO Auto-generated method stub
		return serviciosAdobeConnectDao.grabacionesReunion(codigo);
	}
}
