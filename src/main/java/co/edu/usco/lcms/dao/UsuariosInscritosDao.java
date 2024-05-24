package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.Inscripcion;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Programa;
/**
 * @author jankarlos
 *
 */
public interface UsuariosInscritosDao {
	
	public List<Inscripcion> InformacionPersonalInscrito (int ins_codigo, int persona, int tercero);
	
	/**
	 * Lista las Uaas paginados
	 * @return lista Uaas
	 * */
	
	public JSONRespuesta listarTablaUsuariosInscritos(String search, int start, int length, int draw, int posicion, 
			String direccion, int codigoOferta);
	
	/**
	 * Listado los programas que se han inscrito
	 * @param Buscar
	 * @return programas que tienen incripciones
	 */
	
	public List<Programa> ListaProgramasInscripciones();
		
}
