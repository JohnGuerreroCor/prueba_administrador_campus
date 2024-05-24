/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.UaaTipo;

/**
 * @author jankarlos
 *
 */
public interface UaaTipoServicio {
	/**
	 * Agrega una nueva UaaTipo
	 * 
	 * @param UaaTipo
	 *            Datos UaaTipo a registrar
	 */
	public String agregarUaaTipo(UaaTipo uaaTipo);

	/**
	 * Agrega una modificar UaaTipo
	 * 
	 * @param UaaTipo
	 *            Datos UaaTipo a modificar
	 */
	public String modificarUaaTipo(int id, UaaTipo uaaTipo);

	/**
	 * Agrega una eliminar UaaTipo
	 * 
	 * @param UaaTipo
	 *            Datos UaaTipo a eliminar
	 */
	public String eliminarUaaTipo(int id);

	/**
	 * Listar las UaaTipos
	 * 
	 * @return Lista de las UaaTipos
	 */
	public List<UaaTipo> listarUaaTipo(int codigo);

	/**
	 * Lista las UaaTipos paginados
	 * 
	 * @return lista UaaTipos
	 */

	public JSONRespuesta listarTablaUaaTipo(String search, int start, int length, int draw);
}
