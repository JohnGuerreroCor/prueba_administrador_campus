/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.UaaTipo;

/**
 * @author jankarlos
 *
 */
public interface UaaTipoDao {
	/**
	 * Agrega una nueva UaaTipo
	 * @param UaaTipo Datos UaaTipo a registrar
	 */
	public boolean agregarUaaTipo(UaaTipo uaaTipo);
	
	/**
	 * Agrega una modificar UaaTipo
	 * @param UaaTipo Datos UaaTipo a modificar
	 */
	public boolean modificarUaaTipo(int id, UaaTipo uaaTipo);
	/**
	 * Agrega una eliminar UaaTipo
	 * @param UaaTipo Datos UaaTipo a eliminar
	 */
	public boolean eliminarUaaTipo(int id);
	
	
	/**
	 * Listar las UaaTipos
	 * @return Lista de las UaaTipos
	 */
	public List<UaaTipo> listarUaaTipo(int codigo);
	/**
	 * Lista las UaaTipos paginados
	 * @return lista UaaTipos
	 * */
	
	public JSONRespuesta listarTablaUaaTipo(String search, int start, int length, int draw);

}
