/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.PlanAcademicoDao;
import co.edu.usco.lcms.dao.WebParametroDao;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.PlanAcademico;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author jankarlos
 *
 */
@Component
public class PlanAcademicoServicioImpl implements PlanAcademicoServicio {

	@Autowired
	PlanAcademicoDao planAcademicoDao;

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	Constantes constantes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.PlanAcademicoServicio#agregarPlanAcademico(
	 * org. usco.lcms.model.PlanAcademico)
	 */
	@Override
	public String agregarPlanAcademico(PlanAcademico planAcademico) {
		// TODO Auto-generated method stub
		if (planAcademico.getNombre() != null && planAcademico.getPrograma().getCodigo() > 0
				&& planAcademico.getResolucion().getCodigo() > 0 && planAcademico.getAsignaturas().size() > 0) {

			boolean returnInsercion = planAcademicoDao.agregarPlanAcademico(planAcademico);

			if (returnInsercion) {
				return "OK";
			} else {
				return "Ocurrio un inconveniente, vuelve a intentarlo.";
			}
		} else {
			return "Todos los campos obligatorios del formulario son requeridos.";
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.PlanAcademicoServicio#modificarPlanAcademico(
	 * int, co.edu.usco.lcms.model.PlanAcademico)
	 */
	@Override
	public String modificarPlanAcademico(int id, PlanAcademico planAcademico) {
		// TODO Auto-generated method stub
		PlanAcademico formalidadPlanAcademico = planAcademicoDao.buscarPlanAcademico(id);
		if (formalidadPlanAcademico != null) {
			if (planAcademico.getNombre() != null && planAcademico.getPrograma().getCodigo() > 0
					&& planAcademico.getResolucion().getCodigo() > 0) {
				boolean returnModificar = planAcademicoDao.modificarPlanAcademico(id, planAcademico);
				if (returnModificar) {
					return "OK";
				} else {
					return "Ocurrio un inconveniente, vuelve a intentarlo";
				}
			} else {
				return "Todos los campos obligatorios del formulario son requeridos.";
			}
		} else {
			return "No tiene permisos para modificar este registro.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.PlanAcademicoServicio#eliminarPlanAcademico(
	 * int)
	 */
	@Override
	public String eliminarPlanAcademico(int id) {
		// TODO Auto-generated method stub
		PlanAcademico uaaTipoPlanAcademico = planAcademicoDao.buscarPlanAcademico(id);
		if (uaaTipoPlanAcademico != null) {
			boolean returnEliminar = planAcademicoDao.eliminarPlanAcademico(id);
			if (returnEliminar) {
				return "OK";
			} else {
				return "Ocurrio un inconveniente, vuelve a intentarlo.";
			}
		} else {
			return "No tiene permisos para eliminar este registro.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.PlanAcademicoServicio#
	 * listarTablaPlanAcademico( java.lang.String, int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaPlanAcademico(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		return planAcademicoDao.listarTablaPlanAcademico(search, start, length, draw, posicion, direccion);
	}

	@Override
	public List<PlanAcademico> listarPlanAcademico(String criterio) {
		// TODO Auto-generated method stub
		return planAcademicoDao.listarPlanAcademico(criterio);
	}

}
