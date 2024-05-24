/**
 * 
 */
package co.edu.usco.lcms.dao.proacademica;

import java.util.List;

import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.Dia;

/**
 * @author jankarlos
 *
 */
@Component
public interface DiaDao {

	/**
	 * Lista de día
	 * 
	 * @return lista de objeto día
	 */
	List<Dia> listaDia();
}
