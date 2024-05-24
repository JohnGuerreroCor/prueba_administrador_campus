/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import co.edu.usco.lcms.model.Caracter;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author jankarlos
 *
 */
public interface CaracterServicio {
	/**
	 * Agrega una nueva Caracter
	 * 
	 * @param Caracter
	 *            Datos Caracter a registrar
	 */
	public String agregarCaracter(Caracter caracter);

	/**
	 * Agrega una modificar Caracter
	 * 
	 * @param Caracter
	 *            Datos Caracter a modificar
	 */
	public String modificarCaracter(String id, Caracter caracter);

	/**
	 * Agrega una eliminar Caracter
	 * 
	 * @param Caracter
	 *            Datos Caracter a eliminar
	 */
	public String eliminarCaracter(String id);

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
