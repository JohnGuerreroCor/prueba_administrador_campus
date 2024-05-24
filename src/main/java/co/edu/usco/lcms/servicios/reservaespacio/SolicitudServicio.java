/**
 * 
 */
package co.edu.usco.lcms.servicios.reservaespacio;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.reservaespacios.Solicitud;

/**
 * @author jankarlos
 *
 */
public interface SolicitudServicio {

	/**
	 * Agrega una nueva Solicitud
	 * 
	 * @param Solicitud
	 *            Datos Solicitud a registrar
	 * @throws ParseException
	 */
	public String agregarSolicitud(Solicitud solicitud);

	/**
	 * Agrega una modificar Solicitud
	 * 
	 * @param Solicitud
	 *            Datos Solicitud a modificar
	 */
	public String modificarSolicitud(int id, Solicitud solicitud);

	/**
	 * Agrega una eliminar Solicitud
	 * 
	 * @param Solicitud
	 *            Datos Solicitud a eliminar
	 */
	public String eliminarSolicitud(int id);

	/**
	 * Listar las Solicituds
	 * 
	 * @return Lista de las Solicituds
	 */
	public List<Solicitud> listarSolicitud();

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
	 * Metodo para adjudicar la solicitud
	 * 
	 * @param id
	 *            codigo de la solicitud a adjudicar
	 * @param solicitud
	 *            objeto con los datos de la solicitud
	 * @return mensaje de operación exitosa o no
	 */
	public String adjudicar(int id, Solicitud solicitud);

	/**
	 * Consultar cantidad de solicitudes
	 * 
	 * @param estado
	 *            parametro para filtrar las solicitudes
	 * @return retonar el numero de solicitudes
	 */
	public int cantidadSolicitudesAdmin(int estado, String mes, String anio, int uaaPersona);

	/**
	 * Retorna cantidad de horas usadas por un docente
	 * 
	 * @param estado
	 *            estado de la solicitud
	 * @param mes
	 *            parametro de mes a consultar
	 * @param anio
	 *            parametro de anio a consultar
	 * @param uaaPersona
	 *            parametro de docente a consultar
	 * @return cantidad de horas usadas
	 */
	public float cantidadHorasSolicitudesAdmin(int estado, String mes, String anio, int uaaPersona);

	/**
	 * Obtener cantidad de usuarios ocupados en adobe conect en un limite de tiempo
	 * @param estado estado de la solicitud
	 * @param fecha fecha a buscar
	 * @param hora en que hora
	 * @return retorna la cantidad
	 */
	public int cantidadUsuarios(int estado, Date fecha, String hora);
}
