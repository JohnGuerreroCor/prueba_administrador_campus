/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Programa;

/**
 * @author jankarlos
 *
 */
public interface ProgramaDao {
	/**
	 * Agrega una nueva Programa
	 * @param Programa Datos Programa a registrar
	 */
	public boolean agregarPrograma(Programa programa);
	
	/**
	 * Agrega una modificar Programa
	 * @param Programa Datos Programa a modificar
	 */
	public boolean modificarPrograma(int id, Programa programa);
	/**
	 * Agrega una eliminar Programa
	 * @param Programa Datos Programa a eliminar
	 */
	public boolean eliminarPrograma(int id);	
	
	/**
	 * Listar las Programas
	 * @return Lista de las Programas
	 */
	public List<Programa> listarPrograma(int id, int proMod);
	/**
	 * Lista las Programas paginados
	 * @return lista Programas
	 * */
	
	public JSONRespuesta listarTablaPrograma(String search, int start, int length, int draw, int posicion, String direccion);
	
	/**
	 * Buscar un programa por codigo
	 * @param codigo identificador del progrma que se quiere buscar
	 * @return retorna objeto programa
	 */
	public Programa buscarPrograma(int codigo, int codResolucion, int uaa, int codigoModificar);
}
