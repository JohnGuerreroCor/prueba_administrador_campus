/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Nucleo;

/**
 * @author jankarlos
 *
 */
public interface NucleoDao {
	/**
	 * Agrega una nueva Nucleo
	 * @param Nucleo Datos Nucleo a registrar
	 */
	public boolean agregarNucleo(Nucleo nucleo);
	
	/**
	 * Agrega una modificar Nucleo
	 * @param Nucleo Datos Nucleo a modificar
	 */
	public boolean modificarNucleo(int id, Nucleo nucleo);
	/**
	 * Agrega una eliminar Nucleo
	 * @param Nucleo Datos Nucleo a eliminar
	 */
	public boolean eliminarNucleo(int id);
	
	
	/**
	 * Listar las Nucleos
	 * @return Lista de las Nucleos
	 */
	public List<Nucleo> listarNucleo(int id);
	/**
	 * Lista las Nucleos paginados
	 * @return lista Nucleos
	 * */
	
	public JSONRespuesta listarTablaNucleo(String search, int start, int length, int draw);

}
