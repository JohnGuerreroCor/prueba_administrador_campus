/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.sql.Timestamp;
import java.util.List;

import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.OfertaAcademica;

/**
 * @author jankarlos
 *
 */
public interface OfertaAcademicaDao {
	/**
	 * Agrega una nueva OfertaAcademica
	 * 
	 * @param OfertaAcademica
	 *            Datos OfertaAcademica a registrar
	 */
	public int agregarOfertaAcademica(final OfertaAcademica ofertaAcademica);

	/**
	 * Agrega una modificar OfertaAcademica
	 * 
	 * @param OfertaAcademica
	 *            Datos OfertaAcademica a modificar
	 */
	public boolean modificarOfertaAcademica(int id, OfertaAcademica ofertaAcademica);

	/**
	 * Guardar url de la imagen de la oferta
	 * 
	 * @param id
	 *            codigo de la oferta
	 * @param url
	 *            ruta de la imagen
	 * @return retorna true o false
	 */
	public boolean guardarUrl(int id, String url);

	/**
	 * Agrega una eliminar OfertaAcademica
	 * 
	 * @param OfertaAcademica
	 *            Datos OfertaAcademica a eliminar
	 */
	public boolean eliminarOfertaAcademica(int id);

	
	/**
	 * Lista las OfertaAcademicas paginados
	 * 
	 * @return lista OfertaAcademicas
	 */

	public JSONRespuesta listarTablaOfertaAcademica(String search, int start, int length, int draw, int posicion,
			String direccion, int facultad);

	/**
	 * Metodo para buscar una oferta por un codigo
	 * 
	 * @param codigo
	 *            valor para buscar la oferta
	 * @return objeto oferta academica
	 */
	public OfertaAcademica buscarOfertaAcademica(int codigo, int calendario, int programa, int codigoModificar);
}
