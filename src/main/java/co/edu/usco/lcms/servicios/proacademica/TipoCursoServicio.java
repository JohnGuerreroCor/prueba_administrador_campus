/**
 * 
 */
package co.edu.usco.lcms.servicios.proacademica;

import java.util.List;

import co.edu.usco.lcms.model.TipoCurso;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
public interface TipoCursoServicio {

	/**
	 * Lista de tipo curso
	 * @return lista de tipos de cursos
	 */
	public List<TipoCurso> listarTipoCurso();

}
