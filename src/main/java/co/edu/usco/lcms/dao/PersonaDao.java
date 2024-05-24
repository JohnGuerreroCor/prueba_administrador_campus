/**
 * 
 */
package co.edu.usco.lcms.dao;

import co.edu.usco.lcms.model.Persona;

/**
 * @author ANDRES-GPIE
 *
 */
public interface PersonaDao {
	
	/**
	 * Agregar nueva persona
	 * @param persona Datos persona
	 * @return 
	 */
	public boolean guardarPersona(Persona persona);
	
	/**
	 * Agregar nuevo tercero
	 * @param persona Datos persona
	 * @return
	 */
	//public boolean guardarTercero(final Persona persona);
	
	/**
	 * Actualizar informacion tercero
	 * @param persona
	 * @return
	 */
	public boolean actualizarTercero(final Persona persona);
	
	/**
	 * Actualizar datos persona por medio del procedimiento almacenado
	 * @param persona
	 * @return
	 */
	public boolean ActualizarPersonaPA(final Persona persona);
	
	/**
	 * Agregar nuevo tercero Pre Inscripcion
	 * @param persona Datos persona
	 * @return
	 */
	
	public boolean guardarTerceroPreInscripcion(final Persona persona);
	
	/**
	 * Cnsultar si existe la persona
	 * @param persona
	 * @return per_codigo
	 */
	public Persona consultarPersona(String codigo);	
	
	public boolean actualizarCorreo(int codigo, String correo);
	
}
