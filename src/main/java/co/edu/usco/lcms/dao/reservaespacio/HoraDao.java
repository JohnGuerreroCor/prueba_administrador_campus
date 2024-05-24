/**
 * 
 */
package co.edu.usco.lcms.dao.reservaespacio;

import java.util.List;

import co.edu.usco.lcms.model.reservaespacios.Horas;

/**
 * @author jankarlos
 *
 */
public interface HoraDao {
	/**
	 * Listar los Hora
	 * 
	 * @return Lista de los Hora
	 */
	public List<Horas> listarHoras();

	/**
	 * Consultar hora por codigo
	 * @param codigo
	 * @return retorna objeto horas
	 */
	public Horas buscarHora(int codigo);
}
