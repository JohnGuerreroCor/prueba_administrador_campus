/**
 * 
 */
package co.edu.usco.lcms.dao.reservaespacio;

import java.util.List;

import co.edu.usco.lcms.model.Curso;

/**
 * @author jankarlos
 *
 */
public interface CursoDao {

	/**
	 * Listar los cursos
	 * @return Lista de los cursos
	 */
	public List<Curso> listarCurso(int docente, String criterio);
	
}
