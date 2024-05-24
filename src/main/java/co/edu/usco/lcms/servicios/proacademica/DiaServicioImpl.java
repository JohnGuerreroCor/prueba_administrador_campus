/**
 * 
 */
package co.edu.usco.lcms.servicios.proacademica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.proacademica.DiaDao;
import co.edu.usco.lcms.model.Dia;
import co.edu.usco.lcms.servicios.reservaespacio.HoraServicio;

/**
 * @author jankarlos
 *
 */
@Component
public class DiaServicioImpl implements DiaServicio {

	@Autowired
	DiaDao diaDao;

	@Autowired
	HoraServicio horaServicio;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.proacademica.DiaServicio#listaDia()
	 */
	@Override
	public List<Dia> listaDia() {
		// TODO Auto-generated method stub
		return diaDao.listaDia();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.proacademica.DiaServicio#
	 * listarDiasHorasOcupados(int, int, int, int)
	 */
	@Override
	public List<Dia> listarDiasHorasOcupados(int dia, int espacio, int espacioTipo, int docente, int actividad,
			String fecha, int semanas) {
		// TODO Auto-generated method stub
		List<Dia> listaDia = diaDao.listaDia();
		listaDia.remove(listaDia.size() - 1);

		for (Dia d : listaDia) {
			d.setListaHoras(horaServicio.listarHorasDisponibles(d.getCodigo(), espacio, espacioTipo, docente, actividad,
					fecha, semanas));
		}
		return listaDia;
	}

}
