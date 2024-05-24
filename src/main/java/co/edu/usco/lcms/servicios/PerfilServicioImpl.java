/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.PerfilDao;
import co.edu.usco.lcms.model.UsuarioGrupo;

/**
 * @author jankarlos
 *
 */
@Component
public class PerfilServicioImpl implements PerfilServicio {

	@Autowired
	PerfilDao perfilDao;
	/* (non-Javadoc)
	 * @see co.edu.usco.lcms.servicios.PerfilServicio#listarCalendario(int)
	 */
	@Override
	public List<UsuarioGrupo> listarPerfil(int codigo) {
		// TODO Auto-generated method stub
		return perfilDao.listarPerfil(codigo);
	}

}
