package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.dto.OfertaConfiguracionDTO;
import co.edu.usco.lcms.model.OfertaConfiguracion;

public interface OfertaConfiguracionDao {
	
	/**
	 * Agrega una nueva OfertaConfiguracion
	 * @param OfertaConfiguracion Datos OfertaConfiguracion a registrar
	 */
	public String agregarOfertaConfiguracion(OfertaConfiguracion ofertaConfiguracion);
	
	/**
	 * Listar las OfertaConfiguracion
	 * @return Lista de las OfertaConfiguracion
	 */
	public List<OfertaConfiguracionDTO> listarOfertaConfiguracion(int oferta);

	List<String> listarUaa(String codigo);

	List<String> listarUsuarios(String codigo);
}
