/**
 * 
 */
package co.edu.usco.lcms.servicios.reservaespacio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.reservaespacio.DocenteDao;
import co.edu.usco.lcms.model.reservaespacios.UaaPersonal;

/**
 * @author jankarlos
 *
 */
@Component
public class DocenteServicioImpl implements DocenteServicio {

	@Autowired
	DocenteDao docenteDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.reservaespacio.DocenteServicio#listarDocentes()
	 */
	@Override
	public List<UaaPersonal> listarDocentes(String criterio) {
		// TODO Auto-generated method stub
		return docenteDao.listarDocentes(criterio);
	}

}
