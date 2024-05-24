/**
 * 
 */
package co.edu.usco.lcms.servicios.proacademica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.proacademica.EspacioOcupacionVirtualDao;
import co.edu.usco.lcms.model.Dia;
import co.edu.usco.lcms.model.reservaespacios.EspacioOcupacion;
import co.edu.usco.lcms.model.reservaespacios.Horas;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@Component
public class EspacioOcupacionVirtualServicioImpl implements EspacioOcupacionVirtualServicio {

	@Autowired
	EspacioOcupacionVirtualDao espacioOcupacionVirtualDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.proacademica.EspacioOcupacionVirtualServicio#
	 * agregarEspacioOcupacionVirtual(co.edu.usco.lcms.model.reservaespacios.
	 * EspacioOcupacion)
	 */
	@Override
	public String agregarEspacioOcupacionVirtual(EspacioOcupacion espacioOcupacion) {
		// TODO Auto-generated method stub
		if (espacioOcupacion.getCurso().getCodigo() > 0 && espacioOcupacion.getCurso().getUaaPersonal().getCodigo() > 0
				&& espacioOcupacion.getEspacio().getCodigo() > 0 && espacioOcupacion.getFecha() != null
				&& espacioOcupacion.getDiasHorasOcupadasDTO().size() > 0) {

			boolean returnInsercion = false;
			for (int j = 0; j < espacioOcupacion.getDiasHorasOcupadasDTO().size(); j++) {
				Dia dia = new Dia();
				dia.setCodigo(espacioOcupacion.getDiasHorasOcupadasDTO().get(j).getDia());
				espacioOcupacion.setDia(dia);

				int inicio = 0;
				int fin = 0;
				if (espacioOcupacion.getDiasHorasOcupadasDTO().get(j).getInicio().getHora() < espacioOcupacion
						.getDiasHorasOcupadasDTO().get(j).getFin().getHora()) {
					inicio = espacioOcupacion.getDiasHorasOcupadasDTO().get(j).getInicio().getHora();
					fin = espacioOcupacion.getDiasHorasOcupadasDTO().get(j).getFin().getHora();
				} else {
					fin = espacioOcupacion.getDiasHorasOcupadasDTO().get(j).getInicio().getHora();
					inicio = espacioOcupacion.getDiasHorasOcupadasDTO().get(j).getFin().getHora();
				}
				Horas horaInicio = new Horas();
				horaInicio.setCodigo(inicio);
				espacioOcupacion.setHoraInicio(horaInicio);

				Horas horaFin = new Horas();
				horaFin.setCodigo(fin + 1);
				espacioOcupacion.setHoraFin(horaFin);

				returnInsercion = espacioOcupacionVirtualDao.agregarEspacioOcupacionVirtual(espacioOcupacion);
			}

			if (returnInsercion) {
				return "OK";
			} else {
				return "Ocurrio un inconveniente, vuelve a intentarlo.";
			}
		} else {
			return "Favor llenar todos los campos obligatorios del formulario.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.proacademica.EspacioOcupacionVirtualServicio#
	 * consultarDisponibilidad(int, int, int, int, int)
	 */
	@Override
	public List<EspacioOcupacion> consultarDisponibilidad(int dia, int espacio, int espacioTipo, int docente,
			int actividad, String fecha, int semanas) {
		// TODO Auto-generated method stub
		return espacioOcupacionVirtualDao.consultarDisponibilidad(dia, espacio, espacioTipo, docente, actividad, fecha,
				semanas);
	}

	@Override
	public List<EspacioOcupacion> listadoEspacioOcupacion(int curso) {
		// TODO Auto-generated method stub
		return espacioOcupacionVirtualDao.listarEspacioOcupacion(curso);
	}

	@Override
	public String eliminarEspacioOcupacion(int id, int curso) {
		// TODO Auto-generated method stub
		boolean returnEliminar = espacioOcupacionVirtualDao.eliminarEspacioOcupacion(id, curso);

		if (returnEliminar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo";
		}
	}

}
