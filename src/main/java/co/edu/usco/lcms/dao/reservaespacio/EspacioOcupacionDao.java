/**
 * 
 */
package co.edu.usco.lcms.dao.reservaespacio;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.reservaespacios.EspacioOcupacion;
import co.edu.usco.lcms.model.reservaespacios.Solicitud;

/**
 * @author jankarlos
 *
 */
public interface EspacioOcupacionDao {

	/**
	 * Agrega una nueva EspacioOcupacion
	 * 
	 * @param EspacioOcupacion
	 *            Datos EspacioOcupacion a registrar
	 */
	public int agregarEspacioOcupacion(final EspacioOcupacion espacioOcupacion);

	/**
	 * Agrega una modificar EspacioOcupacion
	 * 
	 * @param EspacioOcupacion
	 *            Datos EspacioOcupacion a modificar
	 */
	public boolean modificarEspacioOcupacion(int id, EspacioOcupacion espacioOcupacion);

	/**
	 * eliminar EspacioOcupacion
	 * 
	 * @param EspacioOcupacionDao
	 *            Datos EspacioOcupacion a eliminar
	 */
	public boolean eliminarEspacioOcupacion(int id, Solicitud solicitud);

	/**
	 * Listar los registros de EspacioOcupacion
	 * 
	 * @return Lista de los registros de EspacioOcupacion
	 */
	public List<EspacioOcupacionDao> listarEspacioOcupacion(long codigo);

	/**
	 * Metodo listar paginado una lista de solicitudes
	 * 
	 * @param search
	 *            parametro de consulta para filtrar datos
	 * @param start
	 *            número de inicio de los registros
	 * @param length
	 *            número de fin de los registros
	 * @param draw
	 *            numero de pagina
	 * @param posicion
	 *            posición del campo con el cual se va a organizar
	 * @param direccion
	 *            parametro para ordenar descendente o ascendente
	 * @return lista paginada
	 */
	public JSONRespuesta listarTablaEspacioOcupacion(String search, int start, int length, int draw, int posicion,
			String direccion);

}
