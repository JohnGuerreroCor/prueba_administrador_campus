/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Sede;

/**
 * @author jankarlos
 *
 */
public interface SedeDao {
	/**
	 * Agrega una nueva Sede
	 * @param Sede Datos Sede a registrar
	 */
	public boolean agregarSede(Sede sede);
	
	/**
	 * Agrega una modificar Sede
	 * @param Sede Datos Sede a modificar
	 */
	public boolean modificarSede(int id, Sede sede);
	/**
	 * Agrega una eliminar Sede
	 * @param Sede Datos Sede a eliminar
	 */
	public boolean eliminarSede(int id);
	
	
	/**
	 * Listar las Sedes
	 * @return Lista de las Sedes
	 */
	public List<Sede> listarSede(int estado, int id);
	/**
	 * Lista las Sedes paginados
	 * @return lista Sedes
	 * */
	
	public JSONRespuesta listarTablaSede(String search, int start, int length, int draw);

}
