/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Programa;

/**
 * @author jankarlos
 *
 */
public interface ProgramaServicio {
	/**
	 * Agrega una nueva Programa
	 * @param Programa Datos Programa a registrar
	 */
	public String agregarPrograma(Programa programa);
	
	/**
	 * Agrega una modificar Programa
	 * @param Programa Datos Programa a modificar
	 */
	public String modificarPrograma(int id, Programa programa);
	/**
	 * Agrega una eliminar Programa
	 * @param Programa Datos Programa a eliminar
	 */
	public String eliminarPrograma(int id);
	
	
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

}
