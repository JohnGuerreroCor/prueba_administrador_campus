/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import co.edu.usco.lcms.model.Asignatura;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author jankarlos
 *
 */
public interface AsignaturaServicio {
	/**
	 * Agrega una nueva asignatura
	 * @param Asignatura Datos Asignatura a registrar
	 */
	public String agregarAsignatura(Asignatura asignatura);
	
	/**
	 * Agrega una modificar asignatura
	 * @param Asignatura Datos Asignatura a modificar
	 */
	public String  modificarAsignatura(int id, Asignatura asignatura);
	/**
	 * Agrega una eliminar asignatura
	 * @param Asignatura Datos Asignatura a eliminar
	 */
	public String  eliminarAsignatura(int id);
	
	
	/**
	 * Listar las Asignaturas
	 * @return Lista de las Asignaturas
	 */
	public List<Asignatura> listarAsignatura(String criterio, String acronimo);
	/**
	 * Lista las Asignaturas paginados
	 * @return lista asignaturas
	 * */
	
	public JSONRespuesta listarTablaAsignatura(String search, int start, int length, int draw, int posicion, String direccion);
}
