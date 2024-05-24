/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.ProgramaDao;
import co.edu.usco.lcms.dao.WebParametroDao;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Programa;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@Component
public class ProgramaServicioImpl implements ProgramaServicio {

	@Autowired
	ProgramaDao programaDao;

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	Constantes constantes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.ProgramaServicio#agregarPrograma(org.usco.
	 * lcms. model.Programa)
	 */
	@Override
	public String agregarPrograma(Programa programa) {
		// TODO Auto-generated method stub
		if (programa.getTitulo_otorgado() != null && programa.getHorario() != null && programa.getCalendario() != null
				&& programa.getCreacion() != null && programa.getResolucion().getCodigo() > 0
				&& programa.getNbc().getCodigo() > 0 && programa.getSede().getCodigo() > 0
				&& programa.getNivelAcademico().getCodigo() > 0 && programa.getJornada().getCodigo() > 0
				&& programa.getConvenio().getCodigo() > 0 && programa.getProgramaEstado().getCodigo() != null
				&& programa.getPropio() != null && programa.getUaa().getCodigo() > 0) {

			if (programa.getTitulo_otorgado().length() < 100) {
				Programa uaaPrograma = programaDao.buscarPrograma(0, 0, programa.getUaa().getCodigo(), 0);
				if (uaaPrograma == null) {
					boolean returnInsercion = programaDao.agregarPrograma(programa);
					if (returnInsercion) {
						return "OK";
					} else {
						return "Ocurrio un inconveniente, vuelve a intentarlo.";
					}
				} else {
					return "La UAA que selecciono ya se encuentra asociada a otro programa.";
				}
			} else {
				return "Máximo 100 caracteres para el campo titulo otorgado";
			}
		} else {
			return "Favor llenar todos los campos obligatorios del formulario.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.ProgramaServicio#modificarPrograma(int,
	 * co.edu.usco.lcms.model.Programa)
	 */
	@Override
	public String modificarPrograma(int id, Programa programa) {
		// TODO Auto-generated method stub
		String codigoFormalidad = webParametroDao.listarWebParametro(constantes.WEP_FORMALIDAD).get(0).getValor();
		Programa formalidadPrograma = programaDao.buscarPrograma(id, 0, 0, 0);
		if (formalidadPrograma != null) {
			// int codigoFormalidadPrograma =
			// formalidadPrograma.getFormalidad().getCodigo();
			// if
			// (codigoFormalidad.equals(String.valueOf(codigoFormalidadPrograma)))
			// {
			if (1 == 1) {
				if (programa.getTitulo_otorgado() != null && programa.getHorario() != null
						&& programa.getCalendario() != null && programa.getCreacion() != null
						&& programa.getResolucion().getCodigo() > 0 && programa.getNbc().getCodigo() > 0
						&& programa.getSede().getCodigo() > 0 && programa.getNivelAcademico().getCodigo() > 0
						&& programa.getJornada().getCodigo() > 0 && programa.getConvenio().getCodigo() > 0
						&& programa.getProgramaEstado().getCodigo() != null && programa.getPropio() != null
						&& programa.getUaa().getCodigo() > 0) {
					if (programa.getTitulo_otorgado().length() < 100) {

						Programa uaaPrograma = programaDao.buscarPrograma(0, 0, programa.getUaa().getCodigo(),
								programa.getCodigo());
						if (uaaPrograma == null) {

							boolean returnModificar = programaDao.modificarPrograma(id, programa);
							if (returnModificar) {
								return "OK";
							} else {
								return "Ocurrio un inconveniente, vuelve a intentarlo.";
							}
						} else {
							return "La UAA que selecciono ya se encuentra asociada a otro programa.";
						}
					} else {
						return "Máximo 100 caracteres para el campo titulo otorgado";
					}
				} else {
					return "Favor llenar todos los campos obligatorios del formulario.";
				}
			} else {
				return "No tiene permisos para modificar este registro.";
			}
		} else {
			return "No se puede modificar este registro.";
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.ProgramaServicio#eliminarPrograma(int)
	 */
	@Override
	public String eliminarPrograma(int id) {
		// TODO Auto-generated method stub
		String codigoFormalidad = webParametroDao.listarWebParametro(constantes.WEP_FORMALIDAD).get(0).getValor();
		Programa formalidadPrograma = programaDao.buscarPrograma(id, 0, 0, 0);
		if (formalidadPrograma != null) {
			// int codigoFormalidadPrograma =
			// formalidadPrograma.getFormalidad().getCodigo();
			// if
			// (codigoFormalidad.equals(String.valueOf(codigoFormalidadPrograma)))
			// {
			if (1 == 1) {
				boolean returnEliminar = programaDao.eliminarPrograma(id);
				if (returnEliminar) {
					return "OK";
				} else {
					return "Ocurrio un error, No se puede eliminar este registro.";
				}
			} else {
				return "No tiene permisos para eliminar este registro.";
			}
		} else {
			return "No se puede eliminar este registro.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.ProgramaServicio#listarPrograma(int)
	 */
	@Override
	public List<Programa> listarPrograma(int id, int proMod) {
		// TODO Auto-generated method stub
		return programaDao.listarPrograma(id, proMod);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.ProgramaServicio#listarTablaPrograma(java.
	 * lang. String, int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaPrograma(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		return programaDao.listarTablaPrograma(search, start, length, draw, posicion, direccion);
	}

}
