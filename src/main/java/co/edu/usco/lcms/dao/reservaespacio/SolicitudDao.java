/**
 * 
 */
package co.edu.usco.lcms.dao.reservaespacio;

import java.util.Date;
import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.reservaespacios.Solicitud;

/**
 * @author jankarlos
 *
 */
public interface SolicitudDao {
	/**
	 * Agrega una nueva Solicitud
	 * 
	 * @param Solicitud
	 *            Datos Solicitud a registrar
	 */
	public boolean agregarSolicitud(Solicitud solicitud);

	/**
	 * Agrega una modificar Solicitud
	 * 
	 * @param Solicitud
	 *            Datos Solicitud a modificar
	 */
	public boolean modificarSolicitud(int id, Solicitud solicitud);

	/**
	 * Agrega una eliminar Solicitud
	 * 
	 * @param Solicitud
	 *            Datos Solicitud a eliminar
	 */
	public boolean eliminarSolicitud(int id);

	/**
	 * Listar las Solicituds
	 * 
	 * @return Lista de las Solicituds
	 */
	public List<Solicitud> listarSolicitud(long codigo);

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
	public JSONRespuesta listarTablaSolicitud(String search, int start, int length, int draw, int posicion,
			String direccion);

	/**
	 * Obtener la cantidad de solicitudes realizadas por un docente
	 * 
	 * @param dias
	 *            parametro para saber si se a de calcular la cantidad de
	 *            solicitudes por dia o por semanas
	 * @param fecha
	 *            consultar según esta fecha
	 * @param docente
	 *            codigo del docente
	 * @return cantidad de solicitudes
	 */
	public int cantidadSolicitudes(boolean dias, Date fecha, int docente, boolean estado, String hora);

	/**
	 * Agrega una modificar Solicitud
	 * 
	 * @param Solicitud
	 *            Datos Solicitud a modificar
	 */
	public boolean actualizarEstado(int id, Solicitud solicitud);

	/**
	 * Obtener cantidad de solicitudes por tipo de administrador y estado
	 * 
	 * @param estado
	 *            parametro para filtar las solicitudes
	 * @return retorna la cantidad de solicitudes
	 */
	public int cantidadSolicitudesAdmin(int estado, String mes, String anio, int uaaPersona);

	/**
	 * Obtener la cantidad de videoconferencias activas se ecnuentran en cierta
	 * hora y la cantidad de usuarios
	 * 
	 * @param estado
	 *            la solicitud en que estado se encuentra
	 * @param fecha
	 *            Fecha de la solicitud
	 * @param hora
	 *            Hora de la solicitud
	 * @return retorna el número de usuarios que hay activos para x cantidad de
	 *         videoconferncias en el lapso de cierta hora
	 */
	public int cantidadUsuarios(int estado, Date fecha, String hora);

	/**
	 * Cantidad de horas de solicitudes
	 * 
	 * @param estado
	 *            estado de la solicitud
	 * @param mes
	 *            parametro a filtrar las solicitudes
	 * @param anio
	 *            parametro a filtrar las solicitudes
	 * @param uaaPersona
	 *            parametro a filtrar las solicitudes
	 * @return retorna objeto de solicitudes
	 */
	public List<Solicitud> cantidadHorasSolicitudesAdmin(int estado, String mes, String anio, int uaaPersona);

}
