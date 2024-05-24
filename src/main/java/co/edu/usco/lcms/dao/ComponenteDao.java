/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.Componente;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author jankarlos
 *
 */
public interface ComponenteDao {
	/**
	 * Agrega una nueva Componente
	 * @param Componente Datos Componente a registrar
	 */
	public boolean agregarComponente(Componente componente);
	
	/**
	 * Agrega una modificar Componente
	 * @param Componente Datos Componente a modificar
	 */
	public boolean modificarComponente(int id, Componente componente);
	/**
	 * Agrega una eliminar Componente
	 * @param Componente Datos Componente a eliminar
	 */
	public boolean eliminarComponente(int id);
	
	
	/**
	 * Listar las Componentes
	 * @return Lista de las Componentes
	 */
	public List<Componente> listarComponente(int id);
	/**
	 * Lista las Componentes paginados
	 * @return lista Componentes
	 * */
	
	public JSONRespuesta listarTablaComponente(String search, int start, int length, int draw);

}
