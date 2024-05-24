/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.Inscripcion;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Programa;

/**
 * @author ANDRES-GPIE
 *
 */
public interface InscripcionDao {
	
	public JSONRespuesta listaUsuariosInscritos(String search, long codigoOferta, int start, int length, int draw);
	
	
	/**
	 * Listado los programas que se han inscrito
	 * @param Buscar
	 * @return programas que tienen incripciones
	 */
	
	public List<Programa> ListaProgramasInscripciones();
	
	/**
	 * Consultar datos de la inscripcion
	 * @param ins_codigo
	 * @return
	 */
	public Inscripcion consultarInscripcion(int ins_codigo);
		
}
