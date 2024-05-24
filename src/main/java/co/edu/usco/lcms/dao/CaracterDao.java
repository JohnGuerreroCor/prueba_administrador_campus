/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.Caracter;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author jankarlos
 *
 */
public interface CaracterDao {
	/**
	 * Agrega una nueva Caracter
	 * 
	 * @param Caracter
	 *            Datos Caracter a registrar
	 */
	public boolean agregarCaracter(Caracter caracter);

	/**
	 * Agrega una modificar Caracter
	 * 
	 * @param Caracter
	 *            Datos Caracter a modificar
	 */
	public boolean modificarCaracter(String id, Caracter caracter);

	/**
	 * Agrega una eliminar Caracter
	 * 
	 * @param Caracter
	 *            Datos Caracter a eliminar
	 */
	public boolean eliminarCaracter(String id);

	/**
	 * Listar las Caracters
	 * 
	 * @return Lista de las Caracters
	 */
	public List<Caracter> listarCaracter();

	/**
	 * Lista las Caracters paginados
	 * 
	 * @return lista Caracters
	 */

	public JSONRespuesta listarTablaCaracter(String search, int start, int length, int draw);
}
