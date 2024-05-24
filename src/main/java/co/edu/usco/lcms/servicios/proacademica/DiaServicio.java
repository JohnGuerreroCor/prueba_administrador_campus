package co.edu.usco.lcms.servicios.proacademica;

import java.util.List;

import co.edu.usco.lcms.model.Dia;

public interface DiaServicio {

	/**
	 * Lista de día
	 * 
	 * @return lista de objeto día
	 */
	List<Dia> listaDia();

	List<Dia> listarDiasHorasOcupados(int dia, int espacio, int espacioTipo, int docente, int actividad, String fecha,
			int semanas);
}
