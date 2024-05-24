/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Resolucion;
import co.edu.usco.lcms.utility.Respuesta;

/**
 * @author jankarlos
 *
 */
public interface ResolucionServicio {
	/**
	 * Agrega una nueva Resolucion
	 * 
	 * @param Resolucion
	 *            Datos Resolucion a registrar
	 */
	public String agregarResolucion(Resolucion resolucion);

	/**
	 * Agrega una modificar Resolucion
	 * 
	 * @param Resolucion
	 *            Datos Resolucion a modificar
	 * @throws ParseException
	 */
	public String modificarResolucion(int id, Resolucion resolucion) throws ParseException;

	/**
	 * Agrega una eliminar Resolucion
	 * 
	 * @param Resolucion
	 **           Datos Resolucion a eliminar
	 */
	public String eliminarResolucion(int id);

	/**
	 * Listar las Resoluciones
	 * 
	 * @return Lista de las Resoluciones
	 */
	public List<Resolucion> listarResolucion(int codigo);

	public JSONRespuesta listarTablaResolucion(String search, int start, int length, int draw, int posicion, String direccion);
}
