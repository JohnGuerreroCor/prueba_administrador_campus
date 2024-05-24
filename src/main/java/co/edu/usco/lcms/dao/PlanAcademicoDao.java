/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.PlanAcademico;

/**
 * @author jankarlos
 *
 */
public interface PlanAcademicoDao {

	/**
	 * Consultar plan academico
	 * 
	 * @param estudiante
	 * @return
	 */
	public PlanAcademico consultarPlanAcademico(int codigoPrograma);

	/**
	 * Agrega una nueva PlanAcademico
	 * 
	 * @param PlanAcademico
	 *            Datos PlanAcademico a registrar
	 */
	public boolean agregarPlanAcademico(final PlanAcademico planAcademico);

	/**
	 * Agrega una modificar PlanAcademico
	 * 
	 * @param PlanAcademico
	 *            Datos PlanAcademico a modificar
	 */
	public boolean modificarPlanAcademico(int id, PlanAcademico planAcademico);

	/**
	 * eliminar PlanAcademico
	 * 
	 * @param PlanAcademico
	 *            Datos PlanAcademico a eliminar
	 */
	public boolean eliminarPlanAcademico(int id);

	/**
	 * Lista las PlanAcademicos paginados
	 * 
	 * @return lista PlanAcademicos
	 */

	public JSONRespuesta listarTablaPlanAcademico(String search, int start, int length, int draw, int posicion,
			String direccion);

	/**
	 * Metodo para consultar datos de un plan academico según un codigo
	 * 
	 * @param codigo
	 *            parametro para buscar
	 * @return objeto plan Academico
	 */
	public PlanAcademico buscarPlanAcademico(int codigo);

	/**
	 * Listar plan academico
	 * 
	 * @param criterio
	 *            parametro de busqueda para consultar el plan academico
	 * @return lista de plan academico
	 */
	public List<PlanAcademico> listarPlanAcademico(String criterio);
}
