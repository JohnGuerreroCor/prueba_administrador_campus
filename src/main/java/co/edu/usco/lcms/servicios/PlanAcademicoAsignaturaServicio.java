/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import co.edu.usco.lcms.model.PlanAcademicoAsignatura;

/**
 * @author jankarlos
 *
 */
public interface PlanAcademicoAsignaturaServicio {

	/**
	 * Listar las Asignaturas que pertenecen a un plan academico
	 * 
	 * @return Lista de las asignaturas pertenecen al plan academico
	 */
	public List<PlanAcademicoAsignatura> listarPlanAcademicoAsignatura(int codigo);

	/**
	 * Listar las Asignaturas que pertenecen a un plan academico
	 * 
	 * @return Lista de las asignaturas pertenecen al plan academico
	 */
	public List<PlanAcademicoAsignatura> buscarPlanAcademicoAsignatura(int plan, int asignatura);

	/**
	 * Modificar PlanAcademicoAsignatura
	 * 
	 * @param PlanAcademicoAsignatura
	 *            Datos plan Academico Asignatura a modificar
	 */
	public String modificarPlanAcademicoAsignatura(int id, PlanAcademicoAsignatura planAcademicoAsignatura);

	/**
	 * Agrega una nueva PlanAcademicoAsignatura
	 * 
	 * @param PlanAcademico
	 *            Datos PlanAcademico asignatura a registrar
	 */
	public String agregarPlanAcademicoAsignatura(PlanAcademicoAsignatura planAcademicoAsignatura);

	/**
	 * eliminar PlanAcademicoAsignatura
	 * 
	 * @param PlanAcademico
	 *            Datos PlanAcademico asignatura a eliminar
	 */
	public String eliminarPlanAcademicoAsignatura(int id);

}
