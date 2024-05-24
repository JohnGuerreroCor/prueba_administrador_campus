package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.PlanAcademicoAsignatura;

/**
 * @author Jankarlos Diaz
 *
 */
public interface PlanAcademicoAsignaturaDao {
	
	/**
	 * Buscar el plan academico para el plan academico seleccionado
	 * @param pla_codigo
	 * @return
	 */
	public List<PlanAcademicoAsignatura> buscarPlanAcademicoAsignatura(int pla_codigo, int asignatura);
	
	/**
	 * Listar las Asignaturas que pertenecen  a un plan academico
	 * @return Lista de las asignaturas pertenecen al plan academico
	 */
	public List<PlanAcademicoAsignatura> listarPlanAcademicoAsignatura(int codigo);
	
	/**
	 * Modificar PlanAcademicoAsignatura
	 * 
	 * @param PlanAcademicoAsignatura
	 *            Datos plan Academico Asignatura a modificar
	 */
	public boolean modificarPlanAcademicoAsignatura(int id, PlanAcademicoAsignatura planAcademicoAsignatura);

	/**
	 * Agrega una nueva PlanAcademicoAsignatura
	 * 
	 * @param PlanAcademico
	 *            Datos PlanAcademico asignatura a registrar
	 */
	public boolean agregarPlanAcademicoAsignatura(PlanAcademicoAsignatura planAcademicoAsignatura);

	/**
	 * eliminar PlanAcademicoAsignatura
	 * 
	 * @param PlanAcademico
	 *            Datos PlanAcademico asignatura a eliminar
	 */
	public boolean eliminarPlanAcademicoAsignatura(int id);
	}
