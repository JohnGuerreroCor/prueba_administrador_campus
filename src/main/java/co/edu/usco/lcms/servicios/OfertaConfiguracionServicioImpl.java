/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.OfertaConfiguracionDao;
import co.edu.usco.lcms.dto.OfertaConfiguracionDTO;
import co.edu.usco.lcms.model.OfertaConfiguracion;

/**
 * @author jankarlos
 *
 */
@Component
public class OfertaConfiguracionServicioImpl implements OfertaConfiguracionServicio {

	@Autowired
	OfertaConfiguracionDao ofertaConfiguracionDao;
	/* (non-Javadoc)
	 * @see co.edu.usco.lcms.servicios.OfertaConfiguracionServicio#agregarOfertaConfiguracion(co.edu.usco.lcms.model.OfertaConfiguracion)
	 */
	@Override
	public String agregarOfertaConfiguracion(OfertaConfiguracion ofertaConfiguracion) {
		// TODO Auto-generated method stub
		return ofertaConfiguracionDao.agregarOfertaConfiguracion(ofertaConfiguracion);
	}
	@Override
	public List<OfertaConfiguracionDTO> listarOfertaConfiguracion(int oferta) {
		// TODO Auto-generated method stub
		return ofertaConfiguracionDao.listarOfertaConfiguracion(oferta);
	}

}
