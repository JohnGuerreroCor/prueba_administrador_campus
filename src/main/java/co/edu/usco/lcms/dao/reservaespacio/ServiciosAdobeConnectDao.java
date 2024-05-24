/**
 * 
 */
package co.edu.usco.lcms.dao.reservaespacio;

import java.util.List;

import co.edu.usco.lcms.model.reservaespacios.Solicitud;

/**
 * @author jankarlos
 *
 */
public interface ServiciosAdobeConnectDao {

	public Solicitud crearReserva(Solicitud solicitud);

	public boolean eliminarReunion(long codigoVideoconferencia);
	
	public List<Solicitud> grabacionesReunion(long codigoVideoconferencia);

}
