/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.UsuarioGrupo;


/**
 * @author jankarlos
 *
 */
public interface PerfilDao {

	/**
	 * Listar los datos de la persona
	 */
	public List<UsuarioGrupo> listarPerfil(int codigo);

}
