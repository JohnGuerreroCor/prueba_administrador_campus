/**
 * 
 */
package co.edu.usco.lcms.dao;

import co.edu.usco.lcms.model.Admision;

/**
 * @author ANDRES-GPIE
 *
 */
public interface AdmisionDao {
	/**
	 * Agrega una nueva admision
	 * @param Datos de la incripcion
	 * @return guardado o no
	 */
	public boolean agregarAdmision(Admision admision);
	
	/**
	 * Consultar datos de la admision
	 * @param ins_codigo
	 * @return
	 */
	public Admision consultarAdmision(int ins_codigo);
		
}
