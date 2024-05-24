/**
 * 
 */
package co.edu.usco.lcms.dao.proacademica;

import java.util.List;

import co.edu.usco.lcms.model.TipoCurso;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
public interface TipoCursoDao {
	
	/**
	 * Lista de tipo curso
	 * @return
	 */
	public List<TipoCurso> listarTipoCurso();

}
