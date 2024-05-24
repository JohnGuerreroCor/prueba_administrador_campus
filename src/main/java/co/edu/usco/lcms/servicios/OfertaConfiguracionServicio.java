package co.edu.usco.lcms.servicios;

import java.util.List;

import co.edu.usco.lcms.dto.OfertaConfiguracionDTO;
import co.edu.usco.lcms.model.OfertaConfiguracion;

public interface OfertaConfiguracionServicio {

	/**
	 * Agrega una nueva OfertaConfiguracion
	 * 
	 * @param OfertaConfiguracion
	 *            Datos OfertaConfiguracion a registrar
	 */
	public String agregarOfertaConfiguracion(OfertaConfiguracion ofertaConfiguracion);
	
	/**
	 * Listar las OfertaInformacion
	 * 
	 * @return Lista de las OfertaInformacion
	 */
	public List<OfertaConfiguracionDTO> listarOfertaConfiguracion(int oferta);
}
