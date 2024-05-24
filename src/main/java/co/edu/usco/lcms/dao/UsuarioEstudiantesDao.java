/**
 * 
 */
package co.edu.usco.lcms.dao;

import co.edu.usco.lcms.model.Estudiante;
import co.edu.usco.lcms.model.UsuarioEstudiante;

/**
 * @author ANDRES-GPIE
 *
 */
public interface UsuarioEstudiantesDao {

	/**
	 * Consultar el ultimo uid
	 * @return
	 */
	public UsuarioEstudiante consultarUID();	
		
	/**
	 * Crear el usuario para el estudiante
	 * @param estudiante
	 * @return
	 */
	public boolean guardarUsuarioEstudiantes(final Estudiante estudiante);
	
	/**
	 * Guardar usuario grupo
	 * @param estudiante
	 * @return
	 */
	public boolean guardarUsuarioGrupo(final Estudiante estudiante);
	
	/**
	 * COnsultar si existe el usuario
	 * @param identificacion
	 * @return
	 */
	public UsuarioEstudiante consultarUsuarioEstudiantes(String identificacion);
	
	public UsuarioEstudiante consultarUsuarioGeneral(String identificacion);
	
	public UsuarioEstudiante consultarUsuario(int persona);

	boolean programarCambio(String usuario, String pin);
	
	boolean actualizarEstadoUsuarioVirtual(int usuario);

	int obligarCambio(int uid);
		
}
