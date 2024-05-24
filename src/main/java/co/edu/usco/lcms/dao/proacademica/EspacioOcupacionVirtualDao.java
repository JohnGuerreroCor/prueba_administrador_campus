/**
 * 
 */
package co.edu.usco.lcms.dao.proacademica;

import java.util.List;

import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.reservaespacios.EspacioOcupacion;

/**
 * @author jankarlos
 *
 */
@Component
public interface EspacioOcupacionVirtualDao {

	/**
	 * Agrega una nueva EspacioOcupacion
	 * 
	 * @param EspacioOcupacion
	 *            Datos EspacioOcupacion a registrar
	 */
	public boolean agregarEspacioOcupacionVirtual(EspacioOcupacion espacioOcupacion);

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
	 * Listar horarios por curso
	 * 
	 * @param curso
	 *            parametro de consulta
	 * @return listado de espacio ocupacion
	 */
	public List<EspacioOcupacion> listarEspacioOcupacion(int curso);
	
	/**
	 * Metodo para elimiar espacio ocupacion
	 * @param id codigo para eliminar
	 * @return true o false
	 */
	public boolean eliminarEspacioOcupacion(int id, int curso);

}
