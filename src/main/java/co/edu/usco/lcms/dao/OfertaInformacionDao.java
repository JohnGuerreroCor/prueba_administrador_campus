/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.OfertaInformacion;

/**
 * @author jankarlos
 *
 */
public interface OfertaInformacionDao {
	/**
	 * Agrega una nueva OfertaInformacion
	 * @param OfertaInformacion Datos OfertaInformacion a registrar
	 */
	public boolean agregarOfertaInformacion(OfertaInformacion ofertaInformacion);
	
	/**
	 * Modificar OfertaInformacion
	 * @param OfertaInformacion Datos OfertaInformacion a modificar
	 */
	public boolean modificarOfertaInformacion(int id, OfertaInformacion ofertaInformacion);
	/**
	 * Eliminar OfertaInformacion
	 * @param OfertaInformacion Datos OfertaInformacion a eliminar
	 */
	public boolean eliminarOfertaInformacion(int id);
	
	
	/**
	 * Listar las OfertaInformacion
	 * @return Lista de las OfertaInformacion
	 */
	public List<OfertaInformacion> listarOfertaInformacion(int id);
}
