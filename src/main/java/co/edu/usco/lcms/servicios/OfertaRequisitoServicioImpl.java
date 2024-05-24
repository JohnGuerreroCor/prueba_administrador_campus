/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.OfertaRequisitoDao;
import co.edu.usco.lcms.model.OfertaRequisito;

/**
 * @author jankarlos
 *
 */
@Component
public class OfertaRequisitoServicioImpl implements OfertaRequisitoServicio {

	@Autowired
	OfertaRequisitoDao ofertaRequisitoDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.ProgramaRequisitoServicio#
	 * agregarProgramaRequisitos(co.edu.usco.lcms.model.ProgramaRequisito)
	 */
	@Override
	public String agregarOfertaRequisitos(OfertaRequisito ofertaRequisito) {
		// TODO Auto-generated method stub
		if (ofertaRequisito.getDescripcion() != null && ofertaRequisito.getOfertaRequisitoTipo().getCodigo() > 0
				&& ofertaRequisito.getOfertaAcademica().getCodigo() > 0) {
			boolean returnInsercion = ofertaRequisitoDao.agregarOfertaRequisitos(ofertaRequisito);
			if (returnInsercion) {
				return "OK";
			} else {
				return "Ocurrio un inconveniente, vuelve a intentarlo.";
			}
		} else {
			return "Formulario invalido, por favor verifique los campos.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.ProgramaRequisitoServicio#
	 * eliminarProgramaRequisitos(int)
	 */
	@Override
	public String eliminarOfertaRequisitos(int id) {
		// TODO Auto-generated method stub
		boolean returnEliminar = ofertaRequisitoDao.eliminarOfertaRequisitos(id);
		if (returnEliminar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.ProgramaRequisitoServicio#
	 * listarProgramaRequisitos(int)
	 */
	@Override
	public List<OfertaRequisito> listarOfertaRequisitos(int id) {
		// TODO Auto-generated method stub
		return ofertaRequisitoDao.listarOfertaRequisitos(id);
	}

}
