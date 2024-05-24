/**
 * 
 */
package co.edu.usco.lcms.servicios.reservaespacio;

import java.util.List;

import co.edu.usco.lcms.model.reservaespacios.UaaPersonal;

/**
 * @author jankarlos
 *
 */
public interface DocenteServicio {

	/**
	 * Listar los Docente
	 * @return Lista de los Docente
	 */
	public List<UaaPersonal> listarDocentes(String criterio);
}
