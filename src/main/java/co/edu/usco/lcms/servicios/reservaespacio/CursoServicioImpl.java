/**
 * 
 */
package co.edu.usco.lcms.servicios.reservaespacio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.reservaespacio.CursoDao;
import co.edu.usco.lcms.model.Curso;

/**
 * @author Jankarlos Diaz Vieda
 *
 */
@Component
public class CursoServicioImpl implements CursoServicio {

	@Autowired
	CursoDao cursoDao;
	/* (non-Javadoc)
	 * @see co.edu.usco.lcms.servicios.reservaespacio.CursoServicio#listarCurso(java.lang.String)
	 */
	@Override
	public List<Curso> listarCurso(int docente, String criterio) {
		// TODO Auto-generated method stub
		return cursoDao.listarCurso(docente,criterio);
	}

}
