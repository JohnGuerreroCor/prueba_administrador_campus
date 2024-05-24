/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.UaaDao;
import co.edu.usco.lcms.dao.WebParametroDao;
import co.edu.usco.lcms.librerias.CompararTipoUaa;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author jankarlos
 *
 */
@Component
public class UaaServicioImpl implements UaaServicio {

	@Autowired
	UaaDao uaaDao;

	@Autowired
	Constantes constantes;

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	CompararTipoUaa compararTipoUaa;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.UaaServicio#agregarUaa(co.edu.usco.lcms.model.
	 * Uaa)
	 */
	@Override
	public String agregarUaa(Uaa uaa) {
		// TODO Auto-generated method stub

		if (uaa.getUaaTipo().getCodigo() > 0 && uaa.getNombre() != null && uaa.getDependencia() > 0
				&& uaa.getNombreCorto() != null && uaa.getNombreImpresion() != null && uaa.getAcronimo() != null
				&& uaa.getSede().getCodigo() > 0 && uaa.getCentroCostos() != null) {

			if (uaa.getCentroCostos().length() > 0 && uaa.getCentroCostos().length() < 4) {
				if (uaa.getAcronimo().length() == 2) {
					if (uaa.getNombreCorto().length() <= 100) {
						boolean returnInsercion = uaaDao.agregarUaa(uaa);
						if (returnInsercion) {
							return "OK";
						} else {
							return "Ocurrio un inconveniente, vuelve a intentarlo.";
						}
					} else {
						return "El nombre corto debe tener máximo 100 caracteres";
					}
				} else {
					return "El acronimo debe tener dos caracteres";
				}
			} else {
				return "El centro de costos máximo puede tener 3 caracteres";
			}

		} else {
			return "Por favor llenar todos los campos obligatorios.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.UaaServicio#modificarUaa(int,
	 * co.edu.usco.lcms.model.Uaa)
	 */
	@Override
	public String modificarUaa(int id, Uaa uaa) {
		// TODO Auto-generated method stub
		if (uaa.getUaaTipo().getCodigo() > 0 && uaa.getNombre() != null && uaa.getDependencia() > 0
				&& uaa.getNombreCorto() != null && uaa.getNombreImpresion() != null && uaa.getAcronimo() != null
				&& uaa.getSede().getCodigo() > 0 && uaa.getCentroCostos() != null) {

			if (compararTipoUaa.tipoUaa(uaa.getUaaTipo().getCodigo())) {
				if (uaa.getCentroCostos().length() > 0 && uaa.getCentroCostos().length() < 4) {
					if (uaa.getAcronimo().length() == 2) {
						if (uaa.getNombreCorto().length() <= 100) {
							boolean returnModificar = uaaDao.modificarUaa(id, uaa);
							if (returnModificar) {
								return "OK";
							} else {
								return "Ocurrio un inconveniente, vuelve a intentarlo";
							}
						} else {
							return "El nombre corto debe tener máximo 100 caracteres";
						}
					} else {
						return "El acronimo debe tener dos caracteres";
					}
				} else {
					return "El centro de costos máximo puede tener 3 caracteres";
				}
			} else {
				return "No tiene permisos para modificar este registro.";
			}

		} else {
			return "Por favor llenar todos los campos obligatorios.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.UaaServicio#eliminarUaa(int)
	 */
	@Override
	public String eliminarUaa(int id) {
		// TODO Auto-generated method stub
		boolean returnEliminar = uaaDao.eliminarUaa(id);
		if (returnEliminar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, no se puede eliminar este registro.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.UaaServicio#listarUaa(int, int)
	 */
	@Override
	public List<Uaa> listarUaa(int uaaTipo, int codigo, boolean dependencia, int uaaMod, boolean uaaFormal) {
		// TODO Auto-generated method stub
		return uaaDao.listarUaa(uaaTipo, codigo, dependencia, uaaMod, uaaFormal);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.UaaServicio#listarTablaUaa(java.lang.String,
	 * int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaUaa(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		return uaaDao.listarTablaUaa(search, start, length, draw, posicion, direccion);
	}

}
