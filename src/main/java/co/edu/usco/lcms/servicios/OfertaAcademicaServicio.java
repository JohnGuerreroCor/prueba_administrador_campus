/**
 * 
 */
package co.edu.usco.lcms.servicios;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.OfertaAcademica;

/**
 * @author jankarlos
 *
 */
public interface OfertaAcademicaServicio {

	/**
	 * Agrega una nueva OfertaAcademica
	 * 
	 * @param OfertaAcademica
	 *            Datos OfertaAcademica a registrar
	 */
	public String agregarOfertaAcademica(OfertaAcademica ofertaAcademica);

	/**
	 * Agrega una modificar OfertaAcademica
	 * 
	 * @param OfertaAcademica
	 * Datos OfertaAcademica a modificar
	 */
	public String modificarOfertaAcademica(int id, OfertaAcademica ofertaAcademica);

	/**
	 * Guardar url de la imagen de la oferta
	 * @param id codigo de la oferta
	 * @param url ruta de la imagen
	 * @return retorna true o false
	 */
	public String guardarUrl(int id, String url);
	
	/**
	 * Agrega una eliminar OfertaAcademica
	 * 
	 * @param OfertaAcademica
	 * Datos OfertaAcademica a eliminar
	 */
	public String eliminarOfertaAcademica(int id);

	/**
	 * Lista las OfertaAcademicas paginados
	 * 
	 * @return lista OfertaAcademicas
	 */

	public JSONRespuesta listarTablaOfertaAcademica(String search, int start, int length, int draw, int posicion, String direccion, int facultad);
}
