/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.AsignaturaDao;
import co.edu.usco.lcms.dao.ComponenteDao;
import co.edu.usco.lcms.dao.NucleoDao;
import co.edu.usco.lcms.dao.UaaDao;
import co.edu.usco.lcms.dao.WebParametroDao;
import co.edu.usco.lcms.librerias.CompararTipoUaa;
import co.edu.usco.lcms.model.Asignatura;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@Component
public class AsignaturaServicioImpl implements AsignaturaServicio {

	@Autowired
	AsignaturaDao asignaturaDao;

	@Autowired
	UaaDao uaaDao;

	@Autowired
	ComponenteDao componenteDao;

	@Autowired
	NucleoDao nucleoDao;

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	Constantes constantes;

	@Autowired
	CompararTipoUaa compararTipoUaa;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.AsignaturaServicio#agregarAsignatura(org.usco.
	 * lcms.model.Asignatura)
	 */
	@Override
	public String agregarAsignatura(Asignatura asignatura) {
		// TODO Auto-generated method stub

		if (asignatura.getCaracter().getCodigo() != null && asignatura.getUaa().getCodigo() > 0
				&& asignatura.getNucleo().getCodigo() > 0 && asignatura.getNbc().getCodigo() > 0
				&& asignatura.getTrabajoIndependiente() >= 0 && asignatura.getTrabajoPresencial() >= 0
				&& asignatura.getNombre() != null && asignatura.getNombreImpresion() != null
				&& asignatura.getCreditos() >= 0) {

			if (asignatura.getNombre().length() <= 100) {
				if (asignatura.getNombreImpresion().length() <= 12) {
					if (asignatura.getTrabajoIndependiente() >= 0 && asignatura.getTrabajoIndependiente() < 999) {
						if (asignatura.getTrabajoPresencial() >= 0 && asignatura.getTrabajoPresencial() < 999) {

							String Acronimo = "";

							Acronimo += componenteDao.listarComponente(asignatura.getComponente().getCodigoComponente())
									.get(0).getAcronimoComponente();

							Acronimo += nucleoDao.listarNucleo(asignatura.getNucleo().getCodigo()).get(0).getAcronimo();
							Uaa returnUaa = uaaDao.listarUaa(0, asignatura.getUaa().getCodigo(), false, 0, false)
									.get(0);

							Acronimo += returnUaa.getAcronimo();

							Uaa returnUaaDep = uaaDao.listarUaa(0, returnUaa.getDependencia(), false, 0, false).get(0);
							if (returnUaaDep.getAcronimo() != null && returnUaaDep.getAcronimo() != "") {
								Acronimo += returnUaaDep.getAcronimo();
							} else {
								Acronimo += returnUaa.getAcronimo();
							}

							List<Asignatura> asignaturaDatos = asignaturaDao.listarAsignatura("", Acronimo);

							String CodigoAcronimo = "";
							if (asignaturaDatos.size() > 0) {
								CodigoAcronimo = asignaturaDatos.get(0).getNombreCorto();
							} else {
								CodigoAcronimo = new Asignatura().getNombreCorto();
							}

							String NumeroAcronimo;

							if (CodigoAcronimo != null) {
								int NumAcronimo = Integer.parseInt(CodigoAcronimo.substring(6));
								if (NumAcronimo < 9) {
									NumAcronimo = NumAcronimo + 1;
									NumeroAcronimo = "0" + NumAcronimo;
								} else {
									NumAcronimo = NumAcronimo + 1;
									NumeroAcronimo = String.valueOf(NumAcronimo);
								}
							} else {
								NumeroAcronimo = "01";
							}

							asignatura.setNombreCorto(Acronimo + NumeroAcronimo);

							boolean returnInsercion = asignaturaDao.agregarAsignatura(asignatura);

							if (returnInsercion) {
								return "OK";
							} else {
								return "Ocurrio un inconveniente, vuelve a intentarlo.";
							}
						} else {
							return "Número de trabajo presencial por horas no valido";
						}
					} else {
						return "Número de trabajo independiente por horas no valido";
					}
				} else {
					return "Máximo 12 caracteres en el campo nombre impresión.";
				}
			} else {
				return "Máximo 100 caracteres en el campo nombre.";
			}
		} else {
			return "Favor llenar todos los campos obligatorios del formulario.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.AsignaturaServicio#modificarAsignatura(int,
	 * co.edu.usco.lcms.model.Asignatura)
	 */
	@Override
	public String modificarAsignatura(int id, Asignatura asignatura) {
		// TODO Auto-generated method stub
		Asignatura lcmsAsignatura = asignaturaDao.buscarAsignatura(id);
		if (lcmsAsignatura != null) {
			if (compararTipoUaa.tipoUaa(lcmsAsignatura.getUaa().getUaaTipo().getCodigo())) {
				if (asignatura.getCaracter().getCodigo().length() > 0 && asignatura.getUaa().getCodigo() != 0
						&& asignatura.getNucleo().getCodigo() != 0 && asignatura.getNbc().getCodigo() != 0
						&& asignatura.getTrabajoIndependiente() >= 0 && asignatura.getTrabajoPresencial() >= 0
						&& asignatura.getNombre().length() > 0 && asignatura.getNombreImpresion().length() > 0
						&& asignatura.getCreditos() >= 0) {
					
					boolean returnModificar = asignaturaDao.modificarAsignatura(id, asignatura);
					if (returnModificar) {
						return "OK";
					} else {
						return "Ocurrio un inconveniente, vuelve a intentarlo";
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
	 * @see
	 * co.edu.usco.lcms.servicios.AsignaturaServicio#eliminarAsignatura(int)
	 */
	@Override
	public String eliminarAsignatura(int id) {
		// TODO Auto-generated method stub
		Asignatura lcmsAsignatura = asignaturaDao.buscarAsignatura(id);
		if (lcmsAsignatura != null) {
			if (compararTipoUaa.tipoUaa(lcmsAsignatura.getUaa().getUaaTipo().getCodigo())) {
				boolean returnEliminar = asignaturaDao.eliminarAsignatura(id);
				if (returnEliminar) {
					return "OK";
				} else {
					return "Ocurrio un inconveniente, vuelve a intentarlo";
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
	 * @see
	 * co.edu.usco.lcms.servicios.AsignaturaServicio#listarAsignatura(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public List<Asignatura> listarAsignatura(String criterio, String acronimo) {
		// TODO Auto-generated method stub
		return asignaturaDao.listarAsignatura(criterio, acronimo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.AsignaturaServicio#listarTablaAsignatura(java.
	 * lang.String, int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaAsignatura(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		return asignaturaDao.listarTablaAsignatura(search, start, length, draw, posicion, direccion);
	}

}
