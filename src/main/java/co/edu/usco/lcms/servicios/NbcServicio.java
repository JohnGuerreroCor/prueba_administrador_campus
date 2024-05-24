/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Nbc;

/**
 * @author jankarlos
 *
 */
public interface NbcServicio {
	/**
	 * Agrega una nueva Nbc
	 * 
	 * @param Nbc
	 *            Datos Nbc a registrar
	 */
	public String agregarNbc(Nbc nbc);

	/**
	 * Agrega una modificar Nbc
	 * 
	 * @param Nbc
	 *            Datos Nbc a modificar
	 */
	public String modificarNbc(int id, Nbc nbc);

	/**
	 * Agrega una eliminar Nbc
	 * 
	 * @param Nbc
	 *            Datos Nbc a eliminar
	 */
	public String eliminarNbc(int id);

	/**
	 * Listar las Nbcs
	 * 
	 * @return Lista de las Nbcs
	 */
	public List<Nbc> listarNbc();

	/**
	 * Lista las Nbcs paginados
	 * 
	 * @return lista Nbcs
	 */

	public JSONRespuesta listarTablaNbc(String search, int start, int length, int draw, int posicion, String direccion);
}
