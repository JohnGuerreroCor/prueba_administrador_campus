/**
 * 
 */
package co.edu.usco.lcms.dao;

import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author ANDRES-GPIE
 *
 */
public interface CursoDao {

	/**
	 * Buscar el plan academico para el plan academico seleccionado
	 * 
	 * @param pla_codigo
	 * @return
	 */
	public Curso buscarCurso(int asi_codigo, int pla_codigo, int cal_codigo);
	
	public String updateCurso();

}
