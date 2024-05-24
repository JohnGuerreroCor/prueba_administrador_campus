/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.OfertaRequisitoTipo;

/**
 * @author jankarlos
 *
 */
public interface OfertaRequisitoTipoDao {
	/**
	 * Listar las ProgramaRequisitosTipo
	 * 
	 * @return Lista de las ProgramaRequisitosTipo
	 */
	public List<OfertaRequisitoTipo> listarProgramaRequisitoTipo(int id);

}
