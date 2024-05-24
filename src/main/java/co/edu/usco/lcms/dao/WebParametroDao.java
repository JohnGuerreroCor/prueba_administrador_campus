/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.WebParametro;

/**
 * @author jankarlos
 *
 */
public interface WebParametroDao {
	/**
	 * Listar las WebParametros
	 * 
	 * @return Lista de las WebParametros
	 */
	public List<WebParametro> listarWebParametro(String nombre);
}
