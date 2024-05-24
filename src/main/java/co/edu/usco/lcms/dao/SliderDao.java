/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Slider;

/**
 * @author jankarlos
 *
 */
public interface SliderDao {
	/**
	 * Agrega una nueva Slider
	 * @param Slider Datos Slider a registrar
	 */
	public int agregarSlider(final Slider slider);
	
	/**
	 * Agrega una modificar Slider
	 * @param Slider Datos Slider a modificar
	 */
	public boolean modificarSlider(int id, Slider slider);
	/**
	 * Agrega una eliminar Slider
	 * @param Slider Datos Slider a eliminar
	 */
	public boolean eliminarSlider(int id);
	
	/**
	 * Guardar url de la imagen en el registro del slider
	 * @param id codigo del registro del slider
	 * @param url ruta de la imagen
	 * @return
	 */
	public boolean guardarUrl(int id, String url);
	/**
	 * Listar las Sliders
	 * @return Lista de las Sliders
	 */
	public List<Slider> listarSlider();
	/**
	 * Lista las Sliders paginados
	 * @return lista Sliders
	 * */
	
	public JSONRespuesta listarTablaSlider(String search, int start, int length, int draw, int posicion, String direccion);
}
