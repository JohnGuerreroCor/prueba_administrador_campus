/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.NivelAcademico;

/**
 * @author jankarlos
 *
 */
public interface NivelAcademicoServicio {
	/**
	 * Agrega una nueva NivelAcademico
	 * @param NivelAcademico Datos NivelAcademico a registrar
	 */
	public String agregarNivelAcademico(NivelAcademico nivelAcademico);
	
	/**
	 * Agrega una modificar NivelAcademico
	 * @param NivelAcademico Datos NivelAcademico a modificar
	 */
	public String modificarNivelAcademico(int id, NivelAcademico nivelAcademico);
	/**
	 * Agrega una eliminar NivelAcademico
	 * @param NivelAcademico Datos NivelAcademico a eliminar
	 */
	public String eliminarNivelAcademico(int id);
	
	
	/**
	 * Listar las NivelAcademicos
	 * @return Lista de las NivelAcademicos
	 */
	public List<NivelAcademico> listarNivelAcademico(int id);
	/**
	 * Lista las NivelAcademicos paginados
	 * @return lista NivelAcademicos
	 * */
	
	public JSONRespuesta listarTablaNivelAcademico(String search, int start, int length, int draw, int posicion, String direccion);
}
