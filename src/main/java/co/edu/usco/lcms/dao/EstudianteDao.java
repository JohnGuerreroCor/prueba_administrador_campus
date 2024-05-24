/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.Estudiante;

/**
 * @author ANDRES-GPIE
 *
 */
public interface EstudianteDao {

	/**
	 * Agrega una nueva inscripcion
	 * 
	 * @param persona
	 *            Datos persona a inscribirse
	 * @return
	 */
	public boolean guardarEstudiante(Estudiante estudiante);

	/**
	 * Consultar si existe la persona
	 * 
	 * @param persona
	 * @return per_codigo
	 */
	public Estudiante consultarEstudiante(long perCodigo);

	/**
	 * Buscar los estudiantes de un curso
	 * 
	 * @param curCodigo
	 *            codigo del curso a consultar
	 * @return lista de estudiantes del curso
	 */
	public List<Estudiante> buscarEstudiantesCurso(int curCodigo);

}
