/**
 * 
 */
package co.edu.usco.lcms.servicios;


import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Matricula;

/**
 * @author jankarlos
 *
 */
public interface UsuariosMatriculadosServicio {
	
	public Matricula InformacionPersonalMatriculado(int ins_codigo);
	
	
	
	/**
	 * Lista las Uaas paginados
	 * @return lista Uaas
	 * */
	
	public JSONRespuesta listarTablaUsuariosMatriculados(String search, int start, int length, int draw, int posicion, String direccion, int codigoOferta);
}
