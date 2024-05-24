/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import co.edu.usco.lcms.model.Inscripcion;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author jankarlos
 *
 */
public interface UsuariosInscritosServicio {
	
	public List<Inscripcion> InformacionPersonalInscrito(int ins_codigo, int persona, int tercero);
	
	/**
	 * Lista las Uaas paginados
	 * @return lista Uaas
	 * */
	
	public JSONRespuesta listarTablaUsuariosInscritos(String search, int start, int length, int draw, int posicion, String direccion, int codigoOferta);
}
