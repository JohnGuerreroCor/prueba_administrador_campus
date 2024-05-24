/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.FormalidadDao;
import co.edu.usco.lcms.model.Formalidad;

/**
 * @author jankarlos
 *
 */
@Component
public class FormalidadServicioImple implements FormalidadServicio {

	@Autowired
	FormalidadDao formalidadDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.FormalidadServicio#listarFormalidad()
	 */
	@Override
	public List<Formalidad> listarFormalidad() {
		// TODO Auto-generated method stub
		return formalidadDao.listarFormalidad();
	}

}
