/**
 * 
 */
package co.edu.usco.lcms.servicios.proacademica;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.AsignaturaDao;
import co.edu.usco.lcms.dao.CalendarioDao;
import co.edu.usco.lcms.dao.proacademica.CursoDao;
import co.edu.usco.lcms.dao.proacademica.EspacioOcupacionVirtualDao;
import co.edu.usco.lcms.librerias.CompararTipoUaa;
import co.edu.usco.lcms.model.Asignatura;
import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author jankarlos
 *
 */
@Component
public class CursoProgServicioImpl implements CursoProgServicio {

	@Autowired
	CursoDao cursoDao;

	@Autowired
	CompararTipoUaa compararTipoUaa;

	@Autowired
	AsignaturaDao asignaturaDao;

	@Autowired
	CalendarioDao calendarioDao;

	@Autowired
	EspacioOcupacionVirtualDao espacioOcupacionVirtualDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.proacademica.CursoProgServicio#
	 * listarTablaCurso( java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarTablaCurso(String search, int start, int length, int draw, int posicion,
			String direccion, int calendario) {
		// TODO Auto-generated method stub
		return cursoDao.listarTablaCurso(search, start, length, draw, posicion, direccion, calendario);

	}

	@Override
	public String agregarCurso(Curso curso) {
		// TODO Auto-generated method stub
		if (curso.getAsignatura().getCodigo() > 0 && curso.getCupo() > 0 && curso.getSede().getCodigo() > 0
				&& curso.getPlanAcademico().getCodigo() > 0 && curso.getUaaPersonal().getCodigo() > 0
				&& curso.getTipoCurso().getCodigo() > 0 && curso.getFechaInicio() != null
				&& curso.getTipoEscala().getCodigo() > 0 && curso.getSemanas() >= 0) {
			Asignatura lcmsAsignatura = asignaturaDao.buscarAsignatura(curso.getAsignatura().getCodigo());
			if (lcmsAsignatura != null) {
				if (compararTipoUaa.tipoUaa(lcmsAsignatura.getUaa().getUaaTipo().getCodigo())) {

					Timestamp fechaCal = new Timestamp(curso.getFechaInicio().getTime());
					if (calendarioDao.listarCalendario(fechaCal).size() > 0) {
						int codCalendario = calendarioDao.listarCalendario(fechaCal).get(0).getCodigo();
						Calendario calendario = new Calendario();
						calendario.setCodigo(codCalendario);
						curso.setCalendario(calendario);

						String returValidarGrupo = this.validarGrupo(curso.getGrupo(),
								curso.getAsignatura().getCodigo(), codCalendario, 0);
						System.out.println("Num:::::::" + returValidarGrupo);
						/*
						 * if (returValidarGrupo) { return
						 * "Ya existe un curso para esta asignatura con el grupo número: "
						 * + curso.getGrupo(); } else {
						 */
						curso.setGrupo(returValidarGrupo);
						boolean returnInsercion = cursoDao.agregarCurso(curso);
						if (returnInsercion) {
							return "OK";
						} else {
							return "Ocurrio un inconveniente, vuelve a intentarlo.";
						}
						// }
					} else {
						return "No se encuentra establecido un periodo y calendario para estas fechas, por favor comunicarse con el area encargada.";
					}
				} else {
					return "No tiene permisos para crear el curso con esta asignatura.";
				}
			} else {
				return "No se puede crear el curso con esta asignatura.";
			}
		} else {
			return "Favor llenar todos los campos obligatorios del formulario.";
		}
	}

	@Override
	public String modificarCurso(int id, Curso curso) {
		// TODO Auto-generated method stub
		if (curso.getAsignatura().getCodigo() > 0 && curso.getCupo() > 0 && curso.getGrupo() != null
				&& curso.getSede().getCodigo() > 0 && curso.getPlanAcademico().getCodigo() > 0
				&& curso.getUaaPersonal().getCodigo() > 0 && curso.getTipoCurso().getCodigo() > 0
				&& curso.getFechaInicio() != null && curso.getTipoEscala().getCodigo() > 0 && curso.getSemanas() >= 0) {
			if (curso.getGrupo() != "00") {

				Asignatura lcmsAsignatura = asignaturaDao.buscarAsignatura(curso.getAsignatura().getCodigo());
				if (lcmsAsignatura != null) {
					if (compararTipoUaa.tipoUaa(lcmsAsignatura.getUaa().getUaaTipo().getCodigo())) {
						Timestamp fechaCal = new Timestamp(curso.getFechaInicio().getTime());

						if (calendarioDao.listarCalendario(fechaCal).size() > 0) {
							int codCalendario = calendarioDao.listarCalendario(fechaCal).get(0).getCodigo();
							Calendario calendario = new Calendario();
							calendario.setCodigo(codCalendario);
							curso.setCalendario(calendario);
							/*
							 * boolean returValidarGrupo =
							 * this.validarGrupo(curso.getGrupo(),
							 * curso.getAsignatura().getCodigo(), codCalendario,
							 * id); if (returValidarGrupo) { return
							 * "Ya existe un curso para esta asignatura con el grupo número: "
							 * + curso.getGrupo(); } else {
							 */
							boolean returnModificar = cursoDao.modificarCurso(id, curso);
							if (returnModificar) {
								return "OK";
							} else {
								return "Ocurrio un inconveniente, vuelve a intentarlo";
							}
							// }
						} else {
							return "No se encuentra establecido un periodo y calendario para estas fechas, por favor comunicarse con el area encargada.";
						}
					} else {
						return "No tiene permisos para modificar el curso con esta asignatura.";
					}
				} else {
					return "No se puede modificar el curso con esta asignatura.";
				}

			} else {
				return "El número de grupo no puede ser 00.";
			}
		} else {
			return "Por favor llenar todos los campos obligatorios.";
		}
	}

	@Override
	public String eliminarCurso(int id) {
		// TODO Auto-generated method stub
		boolean returnEliminar = espacioOcupacionVirtualDao.eliminarEspacioOcupacion(0, id);
		if (returnEliminar) {
			returnEliminar = cursoDao.eliminarCurso(id);
			if (returnEliminar) {
				return "OK";
			} else {
				return "Ocurrio un inconveniente, vuelve a intentarlo";
			}
		} else {
			return "Ocurrio un inconveniente al eliminar la programación, vuelve a intentarlo";
		}
	}

	public String validarGrupo(String grupo, int asignatura, int calendario, int codigoCurso) {
		List<Curso> curso = cursoDao.buscarCurso("", asignatura, "");

		if (curso.size() > 0) {
			if (curso.get(0).getCalendario().getCodigo() == calendario) {
				int num = Integer.parseInt(curso.get(0).getGrupo()) + 1;
				if (num < 10) {
					return "0" + String.valueOf(num);
				} else {
					return String.valueOf(num);
				}
			} else {
				return "01";
			}
		} else {
			return "01";
		}

		/*
		 * if (curso.size() > 0) { if (curso.get(0).getCalendario().getCodigo()
		 * == calendario) { if (codigoCurso > 0) { if (codigoCurso ==
		 * curso.get(0).getCodigo()) { return false; } else { return true; } }
		 * else { return true; } } else { return false; } } else { return false;
		 * }
		 **/
	}

	@Override
	public List<Curso> buscarCurso(String criterio, int asignatura, String grupo) {
		// TODO Auto-generated method stub
		return cursoDao.buscarCurso(criterio, asignatura, grupo);
	}

}
