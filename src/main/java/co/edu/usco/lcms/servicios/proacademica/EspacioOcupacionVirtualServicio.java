/**
 * 
 */
package co.edu.usco.lcms.servicios.proacademica;

import java.util.List;

import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.reservaespacios.EspacioOcupacion;

/**
 * @author Jankarlos Diazn0'
 *
 */
@Component
public interface EspacioOcupacionVirtualServicio {

	/**
	 * Agrega una nueva EspacioOcupacion
	 * 
	 * @param EspacioOcupacion
	 *            Datos EspacioOcupacion a registrar
	 */
	public String agregarEspacioOcupacionVirtual(final EspacioOcupacion espacioOcupacion);

	/**
	 * Consultar la disponibilidad de un espacio y de un docente segun el dia y
	 * la hora
	 * 
	 * @param dia
	 *            codigo de dia
	 * @param horaInicio
	 *            codigo de hora inicio
	 * @param horaFin
	 *            codigo de hora de fin
	 * @param espacio
	 *            codigo del espacio
	 * @param docente
	 *            codigo del docente uaaPersonal
	 * @return retorna objeto espacio ocupacion si se encuentra algún resultado
	 */
	public List<EspacioOcupacion> consultarDisponibilidad(int dia, int espacio, int espacioTipo, int docente,
			int actividad, String fecha, int semanas);

	/**
	 * Listado de espacio ocupacion por curso
	 * 
	 * @param curso
	 *            parametro de filtro
	 * @return retona lista de espacio ocupacion
	 */
	public List<EspacioOcupacion> listadoEspacioOcupacion(int curso);

	/**
	 * Agrega una eliminar espacio ocupacion
	 * 
	 * @param Asignatura
	 *            Datos espacio ocupacion a eliminar
	 */
	public String eliminarEspacioOcupacion(int id, int curso);

}
