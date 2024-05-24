/**
 * 
 */
package co.edu.usco.lcms.dao.reservaespacio;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.reservaespacios.Configuraciones;

/**
 * @author jankarlos
 *
 */
public interface ConfiguracionesDao {
	/**
	 * Agrega una nueva Configuraciones
	 * 
	 * @param Configuraciones
	 *            Datos Configuraciones a registrar
	 */
	public boolean agregarConfiguraciones(Configuraciones configuraciones);

	/**
	 * Agrega una modificar Configuraciones
	 * 
	 * @param Configuraciones
	 *            Datos Configuraciones a modificar
	 */
	public boolean modificarConfiguraciones(int id, Configuraciones configuraciones);

	/**
	 * Agrega una eliminar Configuraciones
	 * 
	 * @param Configuraciones
	 *            Datos Configuraciones a eliminar
	 */
	public boolean eliminarConfiguraciones(int id);

	/**
	 * Listar las Configuracioness
	 * 
	 * @return Lista de las Configuracioness
	 */
	public List<Configuraciones> listarConfiguraciones(int uaaCodigo, int codigoReg, int codCalendario);

	/**
	 * Lista las Reserva Horas paginados
	 * 
	 * @return lista las Reserva Horas
	 */

	public JSONRespuesta listarTablaConfiguraciones(String search, int start, int length, int draw, int posicion,
			String direccion);
}
