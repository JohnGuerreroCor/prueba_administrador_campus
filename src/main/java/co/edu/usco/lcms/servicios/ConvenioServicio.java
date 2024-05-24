/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import co.edu.usco.lcms.model.Convenio;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author jankarlos
 *
 */
public interface ConvenioServicio {
	/**
	 * Agrega una nueva Convenio
	 * @param Convenio Datos Convenio a registrar
	 */
	public String agregarConvenio(Convenio convenio);
	
	/**
	 * Agrega una modificar Convenio
	 * @param Convenio Datos Convenio a modificar
	 */
	public String modificarConvenio(int id, Convenio convenio);
	/**
	 * Agrega una eliminar Convenio
	 * @param Convenio Datos Convenio a eliminar
	 */
	public String eliminarConvenio(int id);
	
	
	/**
	 * Listar los Convenios
	 * @return Lista de los Convenios
	 */
	public List<Convenio> listarConvenio();
	/**
	 * Lista los Convenios paginados
	 * @return lista Convenios
	 * */
	
	public JSONRespuesta listarTablaConvenio(String search, int start, int length, int draw, int posicion, String direccion);
}
