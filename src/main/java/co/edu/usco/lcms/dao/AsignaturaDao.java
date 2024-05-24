/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.Asignatura;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author jankarlos
 *
 */
public interface AsignaturaDao {
	/**
	 * Agrega una nueva asignatura
	 * 
	 * @param Asignatura
	 *            Datos Asignatura a registrar
	 */
	public boolean agregarAsignatura(Asignatura asignatura);

	/**
	 * Agrega una modificar asignatura
	 * 
	 * @param Asignatura
	 *            Datos Asignatura a modificar
	 */
	public boolean modificarAsignatura(int id, Asignatura asignatura);

	/**
	 * Agrega una eliminar asignatura
	 * 
	 * @param Asignatura
	 *            Datos Asignatura a eliminar
	 */
	public boolean eliminarAsignatura(int id);

	/**
	 * Listar las Asignaturas
	 * 
	 * @return Lista de las Asignaturas
	 */
	public List<Asignatura> listarAsignatura(String criterio, String acronimo);

	/**
	 * Lista las Asignaturas paginados
	 * 
	 * @return lista asignaturas
	 */

	public JSONRespuesta listarTablaAsignatura(String search, int start, int length, int draw, int posicion,
			String direccion);

	/**
	 * Metodo para consultar una asignatura según su codigo
	 * 
	 * @param codigo
	 *            parametro para filtrar la asignatura
	 * @return objeto asignatura
	 */
	public Asignatura buscarAsignatura(int codigo);

}
