/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usco.lcms.dao.AdmisionDao;
import co.edu.usco.lcms.dao.CalendarioDao;
import co.edu.usco.lcms.dao.CursoDao;
import co.edu.usco.lcms.dao.EstudianteDao;
import co.edu.usco.lcms.dao.InscripcionDao;
import co.edu.usco.lcms.dao.MatriculaDao;
import co.edu.usco.lcms.dao.OfertaDao;
import co.edu.usco.lcms.dao.PersonaDao;
import co.edu.usco.lcms.dao.PlanAcademicoAsignaturaDao;
import co.edu.usco.lcms.dao.PlanAcademicoDao;
import co.edu.usco.lcms.dao.PlanEstudianteDao;
import co.edu.usco.lcms.dao.UsuarioEstudiantesDao;
import co.edu.usco.lcms.model.Admision;
import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.model.Estudiante;
import co.edu.usco.lcms.model.Inscripcion;
import co.edu.usco.lcms.model.Matricula;
import co.edu.usco.lcms.model.Oferta;
import co.edu.usco.lcms.model.Persona;
import co.edu.usco.lcms.model.PlanAcademico;
import co.edu.usco.lcms.model.PlanAcademicoAsignatura;
import co.edu.usco.lcms.model.PlanEstudiante;
import co.edu.usco.lcms.model.UsuarioEstudiante;
import co.edu.usco.lcms.utility.Respuesta;

/**
 * @author jankarlos
 *
 */

@Component
@Transactional
public class AdmisionServicioImpl implements AdmisionServicio {

	@Autowired
	DataSource dataSourceAcademiaInvitado;

	@Autowired
	PersonaDao personaDao;

	@Autowired
	InscripcionDao inscripcionDao;

	@Autowired
	OfertaDao ofertaDao;

	@Autowired
	AdmisionDao admisionDao;

	@Autowired
	EstudianteDao estudianteDao;

	@Autowired
	PlanAcademicoDao planAcademicoDao;

	@Autowired
	PlanEstudianteDao planEstudianteDao;

	@Autowired
	MatriculaDao matriculaDao;

	@Autowired
	PlanAcademicoAsignaturaDao planAcaAsiDao;

	@Autowired
	CalendarioDao calendarioDao;

	@Autowired
	CursoDao cursoDao;

	@Autowired
	UsuarioEstudiantesDao usuarioEstudiantesDao;

	@Override
	@Transactional
	public String agregarAdmision(int codigo) {

		Inscripcion inscripcion = inscripcionDao.consultarInscripcion(codigo);
		Oferta estadoOferta = ofertaDao.consultarEstadoOferta(inscripcion.getOferta().getCodigo(), "");
		Persona personaExiste = personaDao.consultarPersona(inscripcion.getPersona().getIdentificacion());
		inscripcion.setPersona(personaExiste);

		if (estadoOferta.getAdmisionAutomatica() == 1) {
			Admision consultarAdmision = admisionDao.consultarAdmision(inscripcion.getInscripcionPrograma().getId());

			if (consultarAdmision == null) {
				Admision admision = new Admision();
				admision.setInscripcion(inscripcion);
				admisionDao.agregarAdmision(admision);

				inscripcion.setOferta(estadoOferta);
				Estudiante estudiante = new Estudiante();
				estudiante.setInscripcion(inscripcion);
				estudiante.setAdmision(admision);
				estudiante.setPersona(personaExiste);
				estudianteDao.guardarEstudiante(estudiante);

				inscripcion.setEstudiante(estudiante);
				inscripcion.getEstudiante().setCodigo(estudiante.getCodigo());
				PlanAcademico planAcademicoExiste = planAcademicoDao
						.consultarPlanAcademico(inscripcion.getOferta().getPrograma().getCodigo());
				if (planAcademicoExiste == null) {
					Respuesta respuesta = new Respuesta(Respuesta.PLAN_ACADEMICO);
					return respuesta.getMensaje();
				}
				PlanEstudiante planEstudiante = new PlanEstudiante();
				planEstudiante.setInscripcion(inscripcion);
				planEstudiante.setPlanAcademico(planAcademicoExiste);
				if (!planEstudianteDao.agregarPlanEstudiante(planEstudiante)) {
					Respuesta respuesta = new Respuesta(Respuesta.PLAN_ESTUDIANTE);
					return respuesta.getMensaje();
				}

				Calendario calendario = calendarioDao.consultarCalendario(estadoOferta.getCalendario().getCodigo());

				Matricula matricula = new Matricula();
				matricula.setInscripcion(inscripcion);
				matricula.setCodigoUsuario(inscripcion.getCodigoUsuario());
				Oferta oferta = new Oferta();
				oferta.setCalendario(calendario);
				matricula.setOferta(oferta);

				if (!matriculaDao.agregarMatricula(matricula)) {
					Respuesta respuesta = new Respuesta(Respuesta.MATRICULA_ERROR);
					return respuesta.getMensaje();
				}
				List<PlanAcademicoAsignatura> paa = planAcaAsiDao
						.buscarPlanAcademicoAsignatura(planAcademicoExiste.getCodigo(), 0);
				if (paa == null) {
					Respuesta respuesta = new Respuesta(Respuesta.PLAN_ACADEMICO_ASIGNATURA);
					return respuesta.getMensaje();
				}
				for (PlanAcademicoAsignatura paa1 : paa) {
					Curso curso = cursoDao.buscarCurso(paa1.getAsignatura().getCodigo(),
							planAcademicoExiste.getCodigo(), estadoOferta.getCalendario().getCodigo());

					System.out.println("El curso para matricula es:" + curso.getCodigo());

					if (curso != null) {
						if (!matriculaDao.agregarMatriculaCursoActual(matricula.getId(), curso.getCodigo(),
								paa1.getCodigo())) {
							Respuesta respuesta = new Respuesta(Respuesta.MATRICULA_ERROR);
							return respuesta.getMensaje();
						} else {
							System.out.println("Se agrego la matricula curso actual");
						}
					}
				}
				if (estadoOferta.getCrearUsuario() == 1) {

					if (estadoOferta.getInterna().equals("0")) {
						System.out.println("NO ES INTERNA");

						// consultar si ya existe el usuario
						UsuarioEstudiante usuario = usuarioEstudiantesDao
								.consultarUsuarioGeneral(estudiante.getPersona().getIdentificacion());
						if (usuario == null) {
							System.out.println("SI");
							// estudiante.setPersona(personaExiste);
							UsuarioEstudiante usuarioEstudiante = usuarioEstudiantesDao.consultarUID();
							estudiante.setUsuarioestudiante(usuarioEstudiante);
							usuarioEstudiantesDao.guardarUsuarioEstudiantes(estudiante);

							Oferta uaa = ofertaDao.consultarDatosOferta(inscripcion.getOferta().getCodigo());
							inscripcion.setOferta(uaa);
							estudiante.setInscripcion(inscripcion);
							usuarioEstudiantesDao.guardarUsuarioGrupo(estudiante);
						} else {
							System.out.println("NO");
							if (usuario.getIsTipo() == 5) {
								usuarioEstudiantesDao.actualizarEstadoUsuarioVirtual(usuario.getUid());
							}
							estudiante.setUsuarioestudiante(usuario);

							Oferta uaa = ofertaDao.consultarDatosOferta(inscripcion.getOferta().getCodigo());
							inscripcion.setOferta(uaa);
							estudiante.setInscripcion(inscripcion);
							usuarioEstudiantesDao.guardarUsuarioGrupo(estudiante);
						}
					} else {
						UsuarioEstudiante usuario = usuarioEstudiantesDao.consultarUsuario(personaExiste.getId());
						UsuarioEstudiante usuarioEstudiante = new UsuarioEstudiante();
						usuarioEstudiante.setUid(usuario.getUid());
						estudiante.setUsuarioestudiante(usuarioEstudiante);
						Oferta uaa = ofertaDao.consultarDatosOferta(inscripcion.getOferta().getCodigo());
						inscripcion.setOferta(uaa);
						estudiante.setInscripcion(inscripcion);
						usuarioEstudiantesDao.guardarUsuarioGrupo(estudiante);
					}
				}
				Respuesta respuesta = new Respuesta(Respuesta.ADMISION_OK);
				return respuesta.getMensaje();
			} else {
				Respuesta respuesta = new Respuesta(Respuesta.ADMISION_EXISTE);
				return respuesta.getMensaje();
			}
		} else {
			Respuesta respuesta = new Respuesta(Respuesta.PROGRAMA_NO_ADMISION_AUTOMATICA);
			return respuesta.getMensaje();
		}
	}

	@Override
	public String agregarAdmisionPrueba() {
		// TODO Auto-generated method stub

		Curso curso = cursoDao.buscarCurso(62597, 1139, 202);

		System.out.println("El curso para matricula es:" + curso.getCodigo());

		if (curso != null) {
			if (!matriculaDao.agregarMatriculaCursoActual(671325, curso.getCodigo(), 54802)) {
				Respuesta respuesta = new Respuesta(Respuesta.MATRICULA_ERROR);
				return respuesta.getMensaje();
			} else {
				System.out.println("Se agrego la matricula curso actual");
			}
		}
		
		Estudiante estudiante = new Estudiante();
		Persona persona = new Persona();
		persona.setIdentificacion("1013596511");
		persona.setId(153084);
		
		persona.setTercero(false);
		
		persona.setNombre("ANDREW");
		persona.setApellido("TABLARICO");
		persona.setEmail("andrestablarico@gmail.com");
		persona.setEmailPreInscripcion("andrestablarico@gmail.com");
		estudiante.setPersona(persona);

		UsuarioEstudiante usuarioEstudiante = usuarioEstudiantesDao.consultarUID();
		estudiante.setUsuarioestudiante(usuarioEstudiante);
		usuarioEstudiantesDao.guardarUsuarioEstudiantes(estudiante);

		Oferta uaa = ofertaDao.consultarDatosOferta(2411);
		Inscripcion inscripcion = new Inscripcion();
		inscripcion.setOferta(uaa);
		estudiante.setInscripcion(inscripcion);
		usuarioEstudiantesDao.guardarUsuarioGrupo(estudiante);
		return "OK";
	}

}
