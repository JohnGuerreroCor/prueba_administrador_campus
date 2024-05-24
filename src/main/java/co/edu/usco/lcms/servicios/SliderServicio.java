/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Slider;

/**
 * @author jankarlos
 *
 */
public interface SliderServicio {
	/**
	 * Agrega una nueva Slider
	 * 
	 * @param Slider
	 *            Datos Slider a registrar
	 */
	public String agregarSlider(Slider slider);

	/**
	 * Agrega una modificar Slider
	 * 
	 * @param Slider
	 *            Datos Slider a modificar
	 */
	public String modificarSlider(int id, Slider slider);

	/**
	 * Agrega una eliminar Slider
	 * 
	 * @param Slider
	 *            Datos Slider a eliminar
	 */
	public String eliminarSlider(int id);

	/**
	 * Listar las Sliders
	 * 
	 * @return Lista de las Sliders
	 */
	public List<Slider> listarSlider();

	/**
	 * Agregar url de la imagen
	 * @param id codigo del registro de slider
	 * @param ruta url de la imagen
	 * @return Slider dato de registro de imagen
	 */
	public String guardarUrl(int id, String ruta);

	/**
	 * Lista las Sliders paginados
	 * 
	 * @return lista Sliders
	 */

	public JSONRespuesta listarTablaSlider(String search, int start, int length, int draw, int posicion,
			String direccion);
}
