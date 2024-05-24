/**
 * 
 */
package co.edu.usco.lcms.servicios.reservaespacio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.proacademica.DiaDao;
import co.edu.usco.lcms.dao.proacademica.EspacioOcupacionVirtualDao;
import co.edu.usco.lcms.dao.reservaespacio.HoraDao;
import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.model.Dia;
import co.edu.usco.lcms.model.reservaespacios.Espacio;
import co.edu.usco.lcms.model.reservaespacios.EspacioOcupacion;
import co.edu.usco.lcms.model.reservaespacios.Horas;

/**
 * @author Jankarlos Diaz Vieda
 *
 */
@Component
public class HoraServicioImpl implements HoraServicio {

	@Autowired
	HoraDao horaDao;

	@Autowired
	DiaDao diaDao;

	@Autowired
	EspacioOcupacionVirtualDao espacioOcupacionVirtualDoa;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.HoraServicio#listarHoras()
	 */
	@Override
	public List<Horas> listarHoras() {
		// TODO Auto-generated method stub
		return horaDao.listarHoras();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.HoraServicio#
	 * listarHorasDisponibles()
	 */
	@Override
	public List<Horas> listarHorasDisponibles(int codigoDia, int codigoEspacio, int espacioTipo, int docente, int actividad, String fecha, int semanas) {
		// TODO Auto-generated method stub
		if (espacioTipo == 20) {
			List<Horas> listadoHoras = horaDao.listarHoras();
			return listadoHoras;
		} else {
			List<EspacioOcupacion> espacioOcupacionList = espacioOcupacionVirtualDoa.consultarDisponibilidad(codigoDia,
					codigoEspacio, espacioTipo, docente, actividad, fecha, semanas);

			List<EspacioOcupacion> ArrayCodHorasOcupadas = new ArrayList<EspacioOcupacion>();
			for (EspacioOcupacion a : espacioOcupacionList) {
				for (int i = a.getHoraInicio().getCodigo(); i <= a.getHoraFin().getCodigo(); i++) {
					EspacioOcupacion espacioOcupacion = new EspacioOcupacion();
					Curso curso = new Curso();
					curso.setCodigo(a.getCurso().getCodigo());
					espacioOcupacion.setCurso(curso);

					Espacio espacio = new Espacio();
					espacio.setCodigo(a.getEspacio().getCodigo());
					espacioOcupacion.setEspacio(espacio);

					Dia dia = new Dia();
					dia.setCodigo(a.getDia().getCodigo());
					espacioOcupacion.setDia(dia);

					Horas hora = new Horas();
					hora.setCodigo(Integer.valueOf(i));
					espacioOcupacion.setHora(hora);

					ArrayCodHorasOcupadas.add(espacioOcupacion);
				}
			}

			List<Horas> listadoHoras = horaDao.listarHoras();
			for (Horas h : listadoHoras) {
				for (EspacioOcupacion e : ArrayCodHorasOcupadas) {
					if (h.getCodigo() == e.getHora().getCodigo()) {
						h.setOcupado(true);
						h.setHora(String.valueOf(e.getCurso().getCodigo()));
					}
				}
			}

			return listadoHoras;
		}
	}

}
