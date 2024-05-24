/**
 * 
 */
package co.edu.usco.lcms.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.UsuariosMatriculadosDao;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Matricula;

/**
 * @author jankarlos
 *
 */
@Component
public class UsuariosMatriculadosServicioImpl implements UsuariosMatriculadosServicio {

	@Autowired
	UsuariosMatriculadosDao umDao;
	
	public Matricula InformacionPersonalMatriculado(int ins_codigo){
		return umDao.InformacionPersonalMatriculado(ins_codigo);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.UaaServicio#listarTablaUaa(java.lang.String,
	 * int, int, int)
	 */
	
	public JSONRespuesta listarTablaUsuariosMatriculados(String search, int start, int length, int draw, int posicion, String direccion, int codigoOferta) {
		// TODO Auto-generated method stub
		
		return umDao.listarTablaUsuariosMatriculados(search, start, length, draw, posicion, direccion, codigoOferta);
	
	}

}
