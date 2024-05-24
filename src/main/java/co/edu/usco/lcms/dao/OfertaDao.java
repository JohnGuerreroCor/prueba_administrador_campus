/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.Oferta;
import co.edu.usco.lcms.model.OfertaRequisitos;
import co.edu.usco.lcms.model.PreInscripcion;
import co.edu.usco.lcms.model.RequisitosAdjuntos;

/**
 * @author ANDRES-GPIE
 *
 */
public interface OfertaDao {
	/**
	 * Crear Lista de las ofertas
	 * @return Lista de todas las ofertas
	 */
	public List<Oferta> crearListaOferta(String uaa, String requierePago);
	
	/**
	 * Crear lista ofertas educacion formal
	 * @return
	 */
	public List<Oferta> crearListaOfertaEducacionFormal(String calendario);
	
	/**
	 * Crear Lista de las facultades de la ofertas
	 * @return Lista de todas las ofertas
	 */
	public List<Oferta> crearListaFacultadesOferta(int requierePago);
	
	/**
	 * Crear Lista de las ofertas inactivas
	 * @return Lista de todas las ofertas
	 */
	public List<Oferta> crearListaOfertaInactiva();
	
	/**
	 * Cosnultamos los datos del programa
	 * @param codigo
	 * @return datos del programa
	 */
	public Oferta consultarDatosOferta(int codigoOferta);

	/**
	 * COnsultamos la informacion ingresada por el dueño del curso
	 * @param codigoOferta
	 * @return
	 */
	public List<Oferta> consultarInformacionOferta(int codigoOferta);
		
	/**
	 * Consultar requisitos de la oferta de tipo adjunto
	 * @param codigo
	 * @return listado de los requisitos de la oferta
	 */
	public List<OfertaRequisitos> consultarRequisitosOfertaAdjuntos(String codigoOferta);
	
	/**
	 * Consultar estado y si es admision automatica del programa
	 * @param codigoOferta
	 * @return estado y admision automaticas
	 */
	public Oferta consultarEstadoOferta(long codigoOferta, String codigoEncriptado);
	
	/**
	 * Consultar la cantidad de inscritos a la oferta
	 * @param codigoOferta
	 * @param codigoEncriptado
	 * @return
	 */
	public Oferta consultarCantidadInscritos(long codigoOferta);
	
	/**
	 * Consultar requisitos de la oferta de tipo Adjunto
	 * @param codigoInscripcion
	 * @return listado de los requisitos de la oferta
	 */
	public List<Oferta> consultarRequisitosAdjuntosOferta(int codigoInscripcion, int oferta);
	
	/**
	 * Consultar los archivos adjuntos en la isncripcion
	 * @param codigoInscripcion
	 * @return
	 */
	public List<RequisitosAdjuntos> consultarRequisitosAdjuntosInscripcion(int codigoInscripcion);
	
	/**
	 * Buscar listad preInscritos
	 * @param codigoOferta
	 * @return
	 */
	public List<PreInscripcion> buscarPreInscritos(int codigoOferta);
}
