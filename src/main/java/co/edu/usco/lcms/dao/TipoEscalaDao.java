/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.TipoEscala;

/**
 * @author jankarlos
 *
 */
public interface TipoEscalaDao {

	/**
	 * Lista de tipo escala
	 * @return lista tipo escala
	 */
	public List<TipoEscala> listarTipoEscala();
}
