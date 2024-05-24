/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import co.edu.usco.lcms.model.OfertaInformacion;

/**
 * @author jankarlos
 *
 */
public interface OfertaInformacionServicio {
	/**
	 * Agrega una nueva OfertaInformacion
	 * 
	 * @param OfertaInformacion
	 *            Datos OfertaInformacion a registrar
	 */
	public String agregarOfertaInformacion(OfertaInformacion ofertaInformacion);

	/**
	 * Modificar OfertaInformacion
	 * 
	 * @param OfertaInformacion
	 *            Datos OfertaInformacion a modificar
	 */
	public String modificarOfertaInformacion(int id, OfertaInformacion ofertaInformacion);

	/**
	 * Eliminar OfertaInformacion
	 * 
	 * @param OfertaInformacion
	 *            Datos OfertaInformacion a eliminar
	 */
	public String eliminarOfertaInformacion(int id);

	/**
	 * Listar las OfertaInformacion
	 * 
	 * @return Lista de las OfertaInformacion
	 */
	public List<OfertaInformacion> listarOfertaInformacion(int id);
}
