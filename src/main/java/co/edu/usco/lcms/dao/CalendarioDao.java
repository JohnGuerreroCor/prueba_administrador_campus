/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.sql.Timestamp;
import java.util.List;

import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author jankarlos
 *
 */
public interface CalendarioDao {

	/**
	 * Cosnulatar calendario
	 * 
	 * @param codigoCalendario
	 * @return
	 */
	public Calendario consultarCalendario(int codigoCalendario);

	/**
	 * Listar del calendario al que pertenece la oferta academica según la fecha
	 * 
	 * @return Lista de las calendario
	 */
	public List<Calendario> listarCalendario(Timestamp timestamp);

	/**
	 * Agrega una nuevo Calendario
	 * 
	 * @param Calendario
	 *            Datos calendario a registrar
	 */
	public boolean agregarCalendario(Calendario calendario);

	/**
	 * Agrega una modificar Calendario
	 * 
	 * @param Calendario
	 *            Datos Calendario a modificar
	 */
	public boolean modificarCalendario(int id, Calendario calendario);

	/**
	 * Agrega una eliminar Calendario
	 * 
	 * @param Calendario
	 *            Datos Calendario a eliminar
	 */
	public boolean eliminarCalendario(int id);

	/**
	 * Lista las Calendarios paginados
	 * 
	 * @return lista Calendarios
	 */

	public JSONRespuesta listarTablaCalendario(String search, int start, int length, int draw);

	/**
	 * 
	 * @return listado calendario
	 */
	public List<Calendario> listadoCalendarios();
}
