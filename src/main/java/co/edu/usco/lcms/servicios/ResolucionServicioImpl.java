/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.ProgramaDao;
import co.edu.usco.lcms.dao.ResolucionDao;
import co.edu.usco.lcms.dao.WebParametroDao;
import co.edu.usco.lcms.librerias.CompararTipoUaa;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Programa;
import co.edu.usco.lcms.model.Resolucion;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@Component
public class ResolucionServicioImpl implements ResolucionServicio {

	@Autowired
	ResolucionDao resolucionDao;

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	ProgramaDao programaDao;

	@Autowired
	Constantes constantes;

	@Autowired
	CompararTipoUaa compararTipoUaa;

	@Override
	public String agregarResolucion(Resolucion resolucion) {
		// TODO Auto-generated method stub
		if (resolucion.getUaa().getCodigo() != 0 && resolucion.getNumero() != null
				&& resolucion.getDescripcion() != null && resolucion.getFecha() != null) {
			if (resolucion.getNumero().length() < 50) {
				if (resolucion.getDescripcion().length() < 200) {
					boolean returnInsercion = resolucionDao.agregarResolucion(resolucion);
					if (returnInsercion) {
						return "OK";
					} else {
						return "Ocurrio un error, vuelve a intentarlo.";
					}
				} else {
					return "Máximo 200 caracteres en el campo Descripción";
				}
			} else {
				return "Máximo 50 caracteres en el campo Número";
			}
		}
		return "Favor llenar todos los campos obligatorios del formulario";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.ResolucionServicio#modificarResolucion(int,
	 * co.edu.usco.lcms.model.Resolucion)
	 */
	@Override
	public String modificarResolucion(int id, Resolucion resolucion) throws ParseException {
		// TODO Auto-generated method stub
		Resolucion lcmsResolucion = resolucionDao.buscarResolucion(id);
		if (lcmsResolucion != null) {
			if (compararTipoUaa.tipoUaa(lcmsResolucion.getUaa().getUaaTipo().getCodigo())) {
				if (resolucion.getUaa().getCodigo() != 0 && resolucion.getNumero() != null
						&& resolucion.getDescripcion() != null && resolucion.getFecha() != null) {
					if (resolucion.getNumero().length() < 50) {
						if (resolucion.getDescripcion().length() < 200) {
							boolean returnModificar = resolucionDao.modificarResolucion(id, resolucion);
							if (returnModificar) {
								return "OK";
							} else {
								return "Ocurrio un error, vuelve a intentarlo.";
							}
						} else {
							return "Máximo 200 caracteres en el campo Descripción";
						}
					} else {
						return "Máximo 50 caracteres en el campo Número";
					}
				} else {
					return "Favor llenar todos los campos obligatorios del formulario";
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
	 * @see
	 * co.edu.usco.lcms.servicios.ResolucionServicio#eliminarResolucion(int)
	 */
	@Override
	public String eliminarResolucion(int id) {
		// TODO Auto-generated method stub
		//Programa programa = programaDao.buscarPrograma(0, id, 0, 0);

		//if (programa == null) {
			Resolucion lcmsResolucion = resolucionDao.buscarResolucion(id);
			if (lcmsResolucion != null) {
				if (compararTipoUaa.tipoUaa(lcmsResolucion.getUaa().getUaaTipo().getCodigo())) {
					boolean returnModificar = resolucionDao.eliminarResolucion(id);
					if (returnModificar) {
						return "OK";
					} else {
						return "Ocurrio un error, No se puede eliminar este registro.";
					}
				} else {
					return "No tiene permisos para eliminar este registro.";
				}
			} else {
				return "No tiene permisos para eliminar este registro.";
			}
		//} else {
			//return "No se puede eliminar esta resolución, ya se encuentra relacionada ha un programa.";
		//}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.ResolucionServicio#listarResolucion()
	 */
	@Override
	public List<Resolucion> listarResolucion(int codigo) {
		// TODO Auto-generated method stub
		return resolucionDao.listarResolucion(codigo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.ResolucionServicio#listarTablaResolucion(java.
	 * lang.String, int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaResolucion(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		return resolucionDao.listarTablaResolucion(search, start, length, draw, posicion, direccion);
	}

}
