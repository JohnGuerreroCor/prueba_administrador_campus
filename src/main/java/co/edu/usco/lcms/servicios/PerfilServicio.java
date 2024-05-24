/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import co.edu.usco.lcms.model.UsuarioGrupo;

/**
 * @author jankarlos
 *
 */
public interface PerfilServicio {


	/**
	 * Listar los datos de la persona
	 */
	public List<UsuarioGrupo> listarPerfil(int codigo);
}
