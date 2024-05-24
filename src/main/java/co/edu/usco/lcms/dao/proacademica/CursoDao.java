/**
 * 
 */
package co.edu.usco.lcms.dao.proacademica;

import java.util.List;

import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
public interface CursoDao {

	/**
	 * Obtener el listado de cursos
	 * 
	 * @param search
	 *            parametro de busqueda
	 * @param start
	 *            parametro de inicio de registros
	 * @param length
	 *            parametro final de numero de registros
	 * @param draw
	 * @param posicion
	 *            Parametro con el cual se van a ordenar los registros
	 * @param direccion
	 *            orden de los registros Desendente o Asendente
	 * @return retorna lista de cursos
	 */
	public JSONRespuesta listarTablaCurso(String search, int start, int length, int draw, int posicion,
			String direccion, int calendario);

	/**
	 * Agrega un nuevo curso
	 * 
	 * @param Curso
	 *            Datos Curso a registrar
	 */
	public boolean agregarCurso(Curso curso);

	/**
	 * Agrega una modificar curso
	 * 
	 * @param Curso
	 *            Datos Curso a modificar
	 */
	public boolean modificarCurso(int id, Curso curso);

	/**
	 * Agrega una eliminar curso
	 * 
	 * @param Curso
	 *            Datos Curso a eliminar
	 */
	public boolean eliminarCurso(int id);

	/**
	 * Metodo para consultar una asignatura según su codigo
	 * 
	 * @param codigo
	 *            parametro para filtrar la asignatura
	 * @return objeto asignatura
	 */
	public List<Curso> buscarCurso(String criterio, int asignatura, String grupo);
}
