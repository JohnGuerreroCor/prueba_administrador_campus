/**
 * 
 */
package co.edu.usco.lcms.servicios.proacademica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.proacademica.TipoCursoDao;
import co.edu.usco.lcms.model.TipoCurso;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@Component
public class TipoCursoServicioImpl implements TipoCursoServicio {

	@Autowired
	TipoCursoDao tipoCursoDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.proacademica.TipoCursoServicio#listarTipoCurso
	 * ()
	 */
	@Override
	public List<TipoCurso> listarTipoCurso() {
		// TODO Auto-generated method stub
		return tipoCursoDao.listarTipoCurso();
	}

}
