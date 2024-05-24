/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.PlanAcademicoAsignaturaDao;
import co.edu.usco.lcms.model.PlanAcademicoAsignatura;

/**
 * @author jankarlos
 *
 */
@Component
public class PlanAcademicoAsignaturaServicioImpl implements PlanAcademicoAsignaturaServicio {

	@Autowired
	PlanAcademicoAsignaturaDao planAcademicoAsignaturaDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.PlanAcademicoAsignaturaServicio#
	 * listarPlanAcademicoAsignatura(int)
	 */
	@Override
	public List<PlanAcademicoAsignatura> listarPlanAcademicoAsignatura(int codigo) {
		// TODO Auto-generated method stub
		return planAcademicoAsignaturaDao.listarPlanAcademicoAsignatura(codigo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.PlanAcademicoAsignaturaServicio#
	 * modificarPlanAcademicoAsignatura(int,
	 * co.edu.usco.lcms.model.PlanAcademicoAsignatura)
	 */
	@Override
	public String modificarPlanAcademicoAsignatura(int id, PlanAcademicoAsignatura planAcademicoAsignatura) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.PlanAcademicoAsignaturaServicio#
	 * agregarPlanAcademicoAsignatura(co.edu.usco.lcms.model.
	 * PlanAcademicoAsignatura)
	 */
	@Override
	public String agregarPlanAcademicoAsignatura(PlanAcademicoAsignatura planAcademicoAsignatura) {
		// TODO Auto-generated method stub

		List<PlanAcademicoAsignatura> paa = planAcademicoAsignaturaDao.buscarPlanAcademicoAsignatura(
				planAcademicoAsignatura.getPlanAcademico().getCodigo(), planAcademicoAsignatura.getCodigo());

		if (paa.size() > 0) {

			boolean returnInsercion = planAcademicoAsignaturaDao
					.modificarPlanAcademicoAsignatura(paa.get(0).getCodigo(), planAcademicoAsignatura);

			if (returnInsercion) {
				return "OK";
			} else {
				return "Ocurrio un inconveniente, vuelve a intentarlo.";
			}
		} else {
			boolean returnInsercion = planAcademicoAsignaturaDao
					.agregarPlanAcademicoAsignatura(planAcademicoAsignatura);

			if (returnInsercion) {
				return "OK";
			} else {
				return "Ocurrio un inconveniente, vuelve a intentarlo.";
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.PlanAcademicoAsignaturaServicio#
	 * eliminarPlanAcademicoAsignatura(int)
	 */
	@Override
	public String eliminarPlanAcademicoAsignatura(int id) {
		// TODO Auto-generated method stub
		boolean returnEliminar = planAcademicoAsignaturaDao.eliminarPlanAcademicoAsignatura(id);
		if (returnEliminar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo.";
		}
	}

	@Override
	public List<PlanAcademicoAsignatura> buscarPlanAcademicoAsignatura(int plan, int asignatura) {
		// TODO Auto-generated method stub
		List<PlanAcademicoAsignatura> paa = planAcademicoAsignaturaDao.buscarPlanAcademicoAsignatura(plan, asignatura);
		return paa;

	}

}
