package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Matricula;
import co.edu.usco.lcms.model.Programa;
/**
 * @author jankarlos
 *
 */
public interface UsuariosMatriculadosDao {
	
	public Matricula InformacionPersonalMatriculado (int ins_codigo);
	
	/**
	 * Lista las Uaas paginados
	 * @return lista Uaas
	 * */
	
	public JSONRespuesta listarTablaUsuariosMatriculados(String search, int start, int length, int draw, int posicion, 
			String direccion, int codigoOferta);
	
	/**
	 * Listado los programas que se han inscrito
	 * @param Buscar
	 * @return programas que tienen incripciones
	 */
	
	public List<Programa> ListaProgramasMatriculados();
	
	public List<Matricula> ListaMatriculadosOferta(int oferta);
		
}
