/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.PlanAcademico;

/**
 * @author jankarlos
 *
 */
public interface PlanAcademicoServicio {
	/**
	 * Agrega una nueva PlanAcademico
	 * @param PlanAcademico Datos PlanAcademico a registrar
	 */
	public String agregarPlanAcademico(final PlanAcademico planAcademico);
	
	/**
	 * Agrega una modificar PlanAcademico
	 * @param PlanAcademico Datos PlanAcademico a modificar
	 */
	public String modificarPlanAcademico(int id, PlanAcademico planAcademico);
	/**
	 * Agrega una eliminar PlanAcademico
	 * @param PlanAcademico Datos PlanAcademico a eliminar
	 */
	public String eliminarPlanAcademico(int id);
	
	/**
	 * Lista las PlanAcademicos paginados
	 * @return lista PlanAcademicos
	 * */
	
	public JSONRespuesta listarTablaPlanAcademico(String search, int start, int length, int draw, int posicion, String direccion);
	
	/**
	 * Lista plan academico
	 * @param criterio parametro de busqueda
	 * @return retorna lista de plan académico
	 */
	public List<PlanAcademico> listarPlanAcademico(String criterio);
}
