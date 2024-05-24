package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Uaa;

/**
 * @author jankarlos
 *
 */
public interface UaaDao {
	/**
	 * Agrega una nueva Uaa
	 * 
	 * @param Uaa
	 *            Datos Uaa a registrar
	 */
	public boolean agregarUaa(Uaa uaa);

	/**
	 * Agrega una modificar Uaa
	 * 
	 * @param Uaa
	 *            Datos Uaa a modificar
	 */
	public boolean modificarUaa(int id, Uaa uaa);

	/**
	 * Agrega una eliminar Uaa
	 * 
	 * @param Uaa
	 *            Datos Uaa a eliminar
	 */
	public boolean eliminarUaa(int id);

	/**
	 * Listar las Uaas
	 * 
	 * @return Lista de las Uaas
	 */
	public List<Uaa> listarUaa(int uaaTipo, int codigo, boolean dependencia, int uaaMod, boolean uaaFormal);

	/**
	 * Lista las Uaas paginados
	 * 
	 * @return lista Uaas
	 */

	public JSONRespuesta listarTablaUaa(String search, int start, int length, int draw, int posicion, String direccion);
}
