/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.OfertaRequisito;
/**
 * @author jankarlos
 *
 */
public interface OfertaRequisitoDao {
	/**
	 * Agrega una nueva ProgramaRequisitos
	 * 
	 * @param ProgramaRequisitosDao
	 *            Datos ProgramaRequisitos a registrar
	 */
	public boolean agregarOfertaRequisitos(OfertaRequisito ofertaRequisito);

	/**
	 * Agrega una eliminar ProgramaRequisitos
	 * 
	 * @param ProgramaRequisitosDao
	 *            Datos ProgramaRequisitos a eliminar
	 */
	public boolean eliminarOfertaRequisitos(int id);

	/**
	 * Listar las ProgramaRequisitoss
	 * 
	 * @return Lista de las ProgramaRequisitoss
	 */
	public List<OfertaRequisito> listarOfertaRequisitos(int id);

}
