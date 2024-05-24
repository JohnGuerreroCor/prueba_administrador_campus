/**
 * 
 */
package co.edu.usco.lcms.dao;

import co.edu.usco.lcms.model.ArchivosAdjuntos;

/**
 * @author jankarlos
 *
 */
public interface ArchivosAdjuntosDao {
	/**
	 * Listar las Asignaturas
	 * @return Lista de las Asignaturas
	 */
	public ArchivosAdjuntos consultarRegistro(String nombreEncriptado);
	

}
