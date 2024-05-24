/**
 * 
 */
package co.edu.usco.lcms.dao.proacademica;

import java.util.List;

import co.edu.usco.lcms.model.reservaespacios.Espacio;

/**
 * @author jankarlos
 *
 */
public interface EspacioDao {

	List<Espacio> listarEspacios(int codigo, String criterio);
}
