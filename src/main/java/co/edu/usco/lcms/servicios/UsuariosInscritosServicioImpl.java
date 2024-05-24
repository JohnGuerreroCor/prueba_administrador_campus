/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.UsuariosInscritosDao;
import co.edu.usco.lcms.model.Inscripcion;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author jankarlos
 *
 */
@Component
public class UsuariosInscritosServicioImpl implements UsuariosInscritosServicio {

	@Autowired
	UsuariosInscritosDao uiDao;
	
	public List<Inscripcion> InformacionPersonalInscrito(int ins_codigo, int persona, int tercero){
		return uiDao.InformacionPersonalInscrito(ins_codigo, persona, tercero);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.UaaServicio#listarTablaUaa(java.lang.String,
	 * int, int, int)
	 */
	
	public JSONRespuesta listarTablaUsuariosInscritos(String search, int start, int length, int draw, int posicion, String direccion, int codigoOferta) {
		// TODO Auto-generated method stub
		
		return uiDao.listarTablaUsuariosInscritos(search, start, length, draw, posicion, direccion, codigoOferta);
	
	}

}
