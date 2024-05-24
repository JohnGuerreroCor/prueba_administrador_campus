/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.Formalidad;

/**
 * @author jankarlos
 *
 */
public interface FormalidadDao {
	/**
	 * Listar las Formalidades
	 * @return Lista de las Formalidades
	 */
	public List<Formalidad> listarFormalidad();
	
}
