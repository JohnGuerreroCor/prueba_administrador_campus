/**
 * 
 */
package co.edu.usco.lcms.servicios.reservaespacio;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.CalendarioDao;
import co.edu.usco.lcms.dao.OfertaAcademicaDao;
import co.edu.usco.lcms.dao.reservaespacio.AdobeConnectDao;
import co.edu.usco.lcms.dao.reservaespacio.ConfiguracionesDao;
import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.reservaespacios.Configuraciones;

/**
 * @author jankarlos
 *
 */
@Component
public class ConfiguracionesServicioImpl implements ConfiguracionesServicio {

	@Autowired
	AdobeConnectDao adobeConnectDao;

	@Autowired
	OfertaAcademicaDao ofertaAcademicaDao;

	@Autowired
	ConfiguracionesDao configuracionesDao;
	
	@Autowired
	CalendarioDao calendarioDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.ConfiguracionesServicio#
	 * agregarConfiguraciones(co.edu.usco.lcms.model.reservaespacios.
	 * Configuraciones)
	 */
	@Override
	public String agregarConfiguraciones(Configuraciones configuraciones) {
		// TODO Auto-generated method stub
		int numLicenciasAc = adobeConnectDao.listarAdobeConnect().get(0).getNumSesiones();

		java.util.Date date = new java.util.Date();
		Timestamp fechaActual = new Timestamp(date.getTime());

		int codCalendario = calendarioDao.listarCalendario(fechaActual).get(0).getCodigo();

		Calendario calendario = new Calendario();
		calendario.setCodigo(codCalendario);
		configuraciones.setCalendario(calendario);

		boolean returnInsercion = configuracionesDao.agregarConfiguraciones(configuraciones);

		if (returnInsercion) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.ConfiguracionesServicio#
	 * modificarConfiguraciones(int,
	 * co.edu.usco.lcms.model.reservaespacios.Configuraciones)
	 */
	@Override
	public String modificarConfiguraciones(int id, Configuraciones configuraciones) {
		// TODO Auto-generated method stub
		int numLicenciasAc = adobeConnectDao.listarAdobeConnect().get(0).getNumSesiones();

		java.util.Date date = new java.util.Date();
		Timestamp fechaActual = new Timestamp(date.getTime());

		int codCalendario = calendarioDao.listarCalendario(fechaActual).get(0).getCodigo();

		Calendario calendario = new Calendario();
		calendario.setCodigo(codCalendario);
		configuraciones.setCalendario(calendario);

		boolean returnModificar = configuracionesDao.modificarConfiguraciones(id, configuraciones);
		if (returnModificar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo";
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.ConfiguracionesServicio#
	 * eliminarConfiguraciones(int)
	 */
	@Override
	public String eliminarConfiguraciones(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.ConfiguracionesServicio#
	 * listarConfiguraciones(int)
	 */
	@Override
	public List<Configuraciones> listarConfiguraciones(int codigo) {
		// TODO Auto-generated method stub
		java.util.Date date = new java.util.Date();
		Timestamp fechaActual = new Timestamp(date.getTime());
		int codCalendario = calendarioDao.listarCalendario(fechaActual).get(0).getCodigo();

		return configuracionesDao.listarConfiguraciones(codigo, 0, codCalendario);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.ConfiguracionesServicio#
	 * listarTablaConfiguraciones(java.lang.String, int, int, int, int,
	 * java.lang.String)
	 */
	@Override
	public JSONRespuesta listarTablaConfiguraciones(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		return configuracionesDao.listarTablaConfiguraciones(search, start, length, draw, posicion, direccion);
	}

}
