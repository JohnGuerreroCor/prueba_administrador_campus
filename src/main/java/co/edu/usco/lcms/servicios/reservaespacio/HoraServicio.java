/**
 * 
 */
package co.edu.usco.lcms.servicios.reservaespacio;

import java.util.List;

import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.reservaespacios.EspacioOcupacion;
import co.edu.usco.lcms.model.reservaespacios.Horas;

/**
 * @author jankarlos
 *
 */
@Component
public interface HoraServicio {

	/**
	 * Listado de horas
	 * 
	 * @return horas lista
	 */
	public List<Horas> listarHoras();

	/**
	 * Lsitar horas disponibles
	 * 
	 * @return lista horas
	 */
	public List<Horas> listarHorasDisponibles(int dia, int espacio, int espacioTipo, int docente, int actividad,
			String fecha, int semanas);

}
