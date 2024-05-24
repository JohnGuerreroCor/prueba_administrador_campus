/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.ConvenioDao;
import co.edu.usco.lcms.model.Convenio;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author jankarlos
 *
 */
@Component
public class CovenioServicioImpl implements ConvenioServicio {

	@Autowired
	ConvenioDao convenioDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.ConvenioServicio#agregarConvenio(org.usco.lcms.
	 * model.Convenio)
	 */
	@Override
	public String agregarConvenio(Convenio convenio) {
		// TODO Auto-generated method stub
		if (convenio.getDescripcion() != null && convenio.getResolucion().getCodigo() != 0
				&& convenio.getUaa().getCodigo() != 0) {
			boolean returnInsercion = convenioDao.agregarConvenio(convenio);

			if (returnInsercion) {
				return "OK";
			} else {
				return "Ocurrio un inconveniente, vuelve a intentarlo.";
			}
		} else {
			return "Favor llenar todos los campos obligatorios del formulario.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.ConvenioServicio#modificarConvenio(int,
	 * co.edu.usco.lcms.model.Convenio)
	 */
	@Override
	public String modificarConvenio(int id, Convenio convenio) {
		// TODO Auto-generated method stub
		if (convenio.getDescripcion() != null && convenio.getResolucion().getCodigo() != 0
				&& convenio.getUaa().getCodigo() != 0) {
			boolean returnModificar = convenioDao.modificarConvenio(id, convenio);
			if (returnModificar) {
				return "OK";
			} else {
				return "Ocurrio un inconveniente, vuelve a intentarlo";
			}
		} else {
			return "Favor llenar todos los campos obligatorios del formulario.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.ConvenioServicio#eliminarConvenio(int)
	 */
	@Override
	public String eliminarConvenio(int id) {
		// TODO Auto-generated method stub
		boolean returnEliminar = convenioDao.eliminarConvenio(id);
		if (returnEliminar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.ConvenioServicio#listarConvenio(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Convenio> listarConvenio() {
		// TODO Auto-generated method stub
		return convenioDao.listarConvenio();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.ConvenioServicio#listarTablaConvenio(java.lang.
	 * String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarTablaConvenio(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		return convenioDao.listarTablaConvenio(search, start, length, draw, posicion, direccion);
	}

}
