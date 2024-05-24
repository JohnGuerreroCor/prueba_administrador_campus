/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import co.edu.usco.lcms.model.OfertaRequisito;

/**
 * @author jankarlos
 *
 */
public interface OfertaRequisitoServicio {
	/**
	 * Agrega una nueva ProgramaRequisitos
	 * 
	 * @param ProgramaRequisitosDao
	 *            Datos ProgramaRequisitos a registrar
	 */
	public String agregarOfertaRequisitos(OfertaRequisito ofertaRequisito);

	/**
	 * Agrega una eliminar ProgramaRequisitos
	 * 
	 * @param ProgramaRequisitosDao
	 *            Datos ProgramaRequisitos a eliminar
	 */
	public String eliminarOfertaRequisitos(int id);

	/**
	 * Listar las ProgramaRequisitoss
	 * 
	 * @return Lista de las ProgramaRequisitoss
	 */
	public List<OfertaRequisito> listarOfertaRequisitos(int id);
}
