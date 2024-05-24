/**
 * 
 */
package co.edu.usco.lcms.servicios.proacademica;

import java.util.List;

import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */

public interface CursoProgServicio {

	/**
	 * Metodo listar paginado una lista de solicitudes
	 * 
	 * @param search
	 *            parametro de consulta para filtrar datos
	 * @param start
	 *            número de inicio de los registros
	 * @param length
	 *            número de fin de los registros
	 * @param draw
	 *            numero de pagina
	 * @param posicion
	 *            posición del campo con el cual se va a organizar
	 * @param direccion
	 *            parametro para ordenar descendente o ascendente
	 * @return lista paginada
	 */
	public JSONRespuesta listarTablaCurso(String search, int start, int length, int draw, int posicion,
			String direccion, int calendario);

	/**
	 * Agrega una nueva curso
	 * 
	 * @param Curso
	 *            Datos Curso a registrar
	 */
	public String agregarCurso(Curso curso);

	/**
	 * Agrega una modificar curso
	 * 
	 * @param Curso
	 *            Datos Curso a modificar
	 */
	public String modificarCurso(int id, Curso curso);

	/**
	 * Agrega una eliminar curso
	 * 
	 * @param Curso
	 *            Datos Curso a eliminar
	 */
	public String eliminarCurso(int id);

	/**
	 * 
	 * @param criterio
	 * @param asignatura
	 * @param grupo
	 * @return
	 */
	public List<Curso> buscarCurso(String criterio, int asignatura, String grupo);
}
