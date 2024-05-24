/**
 * 
 */
package co.edu.usco.lcms.servicios.reservaespacio;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.configuration.User;
import co.edu.usco.lcms.dao.CalendarioDao;
import co.edu.usco.lcms.dao.EstudianteDao;
import co.edu.usco.lcms.dao.OfertaAcademicaDao;
import co.edu.usco.lcms.dao.WebParametroDao;
import co.edu.usco.lcms.dao.reservaespacio.AdobeConnectDao;
import co.edu.usco.lcms.dao.reservaespacio.ConfiguracionesDao;
import co.edu.usco.lcms.dao.reservaespacio.EspacioOcupacionDao;
import co.edu.usco.lcms.dao.reservaespacio.HoraDao;
import co.edu.usco.lcms.dao.reservaespacio.ServiciosAdobeConnectDao;
import co.edu.usco.lcms.dao.reservaespacio.SolicitudDao;
import co.edu.usco.lcms.librerias.TheadNotificacionCorreo;
import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.model.Usuario;
import co.edu.usco.lcms.model.reservaespacios.AdobeConnect;
import co.edu.usco.lcms.model.reservaespacios.Configuraciones;
import co.edu.usco.lcms.model.reservaespacios.Espacio;
import co.edu.usco.lcms.model.reservaespacios.EspacioOcupacion;
import co.edu.usco.lcms.model.reservaespacios.Horario;
import co.edu.usco.lcms.model.reservaespacios.Horas;
import co.edu.usco.lcms.model.reservaespacios.OcupacionTipo;
import co.edu.usco.lcms.model.reservaespacios.Solicitud;
import co.edu.usco.lcms.model.reservaespacios.UaaPersonal;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@Component
public class SolicitudServicioImpl implements SolicitudServicio {

	@Autowired
	SolicitudDao solicitudDao;

	@Autowired
	HoraDao horaDao;

	@Autowired
	ConfiguracionesDao configuracionesDao;

	@Autowired
	AdobeConnectDao adobeConnectDao;

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	EspacioOcupacionDao espacioOcupacionDao;

	@Autowired
	OfertaAcademicaDao ofertaAcademicaDao;

	@Autowired
	ServiciosAdobeConnectDao serviciosAdobeConnectDao;

	@Autowired
	EstudianteDao estudianteDao;

	@Autowired
	Constantes constantes;

	@Autowired
	CalendarioDao calendarioDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.SolicitudServicio#
	 * agregarSolicitud (co.edu.usco.lcms.model.reservaespacios.Solicitud)
	 */
	@Override
	public String agregarSolicitud(Solicitud solicitud) {
		// TODO Auto-generated method stub

		int facultad = solicitud.getUaaPersonal().getUaa().getDependencia();

		java.util.Date dateCal = new java.util.Date();
		Timestamp fechaActualCal = new Timestamp(dateCal.getTime());
		Calendario calActual = calendarioDao.listarCalendario(fechaActualCal).get(0);
		int codCalendario = calActual.getCodigo();

		List<Configuraciones> configuracionesList = configuracionesDao.listarConfiguraciones(facultad, 0,
				codCalendario);

		if (configuracionesList.size() > 0) {

			Configuraciones configuraciones = configuracionesList.get(0);

			int numDiasAnticipacion = configuraciones.getDiasAnticipacion();
			int maxSolicitudesDia = configuraciones.getMaxSolicitudesDias();
			int maxSolicitudesSemana = configuraciones.getMaxSolicitudesSemana();

			SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");

			DateFormat formatter;
			formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

			Date fechaSolicitud = solicitud.getFecha();
			Date fechaActual = new Date();

			String fa = dt1.format(fechaActual) + " 00:00:00";
			Date perFechaFin = calActual.getPeriodo().getFechaFin();

			Date fechaActualComparar = null;

			try {
				fechaActualComparar = (Date) formatter.parse(fa);
			} catch (ParseException ex) {
				ex.printStackTrace();
				return "Ocurrio un inconveniente, vuelve a intentarlo más tarde.";
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaActualComparar);

			if (numDiasAnticipacion == 0) {
				calendar.add(Calendar.DAY_OF_YEAR, -1);
			}

			if (calendar.getTime().before(fechaSolicitud) || calendar.getTime().equals(fechaSolicitud)) {

				if (fechaSolicitud.before(perFechaFin)) {

					long numDiasDif = fechaSolicitud.getTime() - fechaActualComparar.getTime();
					int anticipacion = 0;
					if (numDiasDif > 0) {
						calendar.setTimeInMillis(numDiasDif);
						anticipacion = calendar.get(Calendar.DAY_OF_YEAR);
					}
					if (anticipacion >= numDiasAnticipacion) {

						int cantSolicitudesDia = solicitudDao.cantidadSolicitudes(true, fechaSolicitud,
								solicitud.getUaaPersonal().getCodigo(), false, "");
						int cantSolicitudesSemana = solicitudDao.cantidadSolicitudes(false, fechaSolicitud,
								solicitud.getUaaPersonal().getCodigo(), false, "");

						if (cantSolicitudesSemana < maxSolicitudesSemana) {
							if (cantSolicitudesDia < maxSolicitudesDia) {

								String horaInicio = horaDao.buscarHora(solicitud.getHoraInicio().getCodigo())
										.getHora24h();
								String horaFin = horaDao.buscarHora(solicitud.getHoraFin().getCodigo()).getHora24h();

								String fechaInicioSolicitud = dt1.format(solicitud.getFecha()) + " " + horaInicio;
								String fechaFinSolicitud = dt1.format(solicitud.getFecha()) + " " + horaFin;

								Date fechaInicio = null;
								Date fechaFin = null;

								try {
									fechaInicio = (Date) formatter.parse(fechaInicioSolicitud);
									fechaFin = (Date) formatter.parse(fechaFinSolicitud);
								} catch (ParseException ex) {
									return "Ocurrio un inconveniente, vuelve a intentarlo mas tarde.";
								}

								if (solicitud.getCurso().getCodigo() > 0 && solicitud.getUaaPersonal().getCodigo() > 0
										&& solicitud.getHoraInicio().getCodigo() > 0
										&& solicitud.getHoraFin().getCodigo() > 0 && solicitud.getTema() != null
										&& solicitud.getTipoAcceso() != null && solicitud.getDescripcion() != null) {
									if (fechaInicio.before(fechaFin)) {

										boolean returnInsercion = solicitudDao.agregarSolicitud(solicitud);
										if (returnInsercion) {
											return "OK";
										} else {
											return "Ocurrio un inconveniente, vuelve a intentarlo.";
										}

									} else {
										return "La hora de inicio no puede ser inferior a la hora fin.";
									}
								} else {
									return "Por favor llenar todos los campos del formulario.";
								}
							} else {
								return "Excedio el número máximo de solicitudes por dia. Intente en otra fecha.";
							}
						} else {
							return "Excedio el número máximo de solicitudes por semana. Intente en otra fecha.";
						}
					} else {
						return "Para realizar una solicitud debe ser con " + numDiasAnticipacion
								+ " días de anticipación.";
					}
				} else {
					return "La fecha de solicitud no puede posterior a la fecha final del periodo actual.";
				}
			} else {
				return "La fecha de solicitud no puede ser inferior a la fecha actual.";
			}
		} else {
			return "El administrador aún no ha configurado el sistema.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.SolicitudServicio#
	 * modificarSolicitud(int, co.edu.usco.lcms.model.reservaespacios.Solicitud)
	 */
	@Override
	public String modificarSolicitud(int id, Solicitud solicitud) {
		// TODO Auto-generated method stub
		if (solicitud.getEstado().equals("2")) {

			int facultad = solicitud.getUaaPersonal().getUaa().getDependencia();

			java.util.Date dateCal = new java.util.Date();
			Timestamp fechaActualCal = new Timestamp(dateCal.getTime());
			Calendario calActual = calendarioDao.listarCalendario(fechaActualCal).get(0);
			int codCalendario = calActual.getCodigo();

			Configuraciones configuraciones = configuracionesDao.listarConfiguraciones(facultad, 0, codCalendario)
					.get(0);

			int numDiasAnticipacion = configuraciones.getDiasAnticipacion();

			SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");

			DateFormat formatter;
			formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

			Date fechaSolicitud = solicitud.getFecha();
			Date fechaActual = new Date();
			Date perFechaFin = calActual.getPeriodo().getFechaFin();

			String fa = dt1.format(fechaActual) + " 00:00:00";
			Date fechaActualComparar = null;
			try {
				fechaActualComparar = (Date) formatter.parse(fa);
			} catch (ParseException ex) {
				return "Ocurrio un inconveniente, vuelve a intentarlo más tarde.";
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaActualComparar);
			if (numDiasAnticipacion == 0) {
				calendar.add(Calendar.DAY_OF_YEAR, -1);
			}
			if (calendar.getTime().before(fechaSolicitud)) {
				if (fechaSolicitud.before(perFechaFin)) {
					calendar.setTimeInMillis(fechaSolicitud.getTime() - fechaActual.getTime());

					String horaInicio = horaDao.buscarHora(solicitud.getHoraInicio().getCodigo()).getHora24h();
					String horaFin = horaDao.buscarHora(solicitud.getHoraFin().getCodigo()).getHora24h();
					String horaInicioReg = solicitudDao.listarSolicitud(solicitud.getCodigo()).get(0).getHoraInicio()
							.getHora24h();
					String horaFinReg = solicitudDao.listarSolicitud(solicitud.getCodigo()).get(0).getHoraFin()
							.getHora24h();

					String fechaInicioSolicitud = dt1.format(solicitud.getFecha()) + " " + horaInicio;
					String fechaFinSolicitud = dt1.format(solicitud.getFecha()) + " " + horaFin;
					String fechaInicioSolicitudReg = dt1.format(solicitud.getFecha()) + " " + horaInicioReg;
					String fechaFinSolicitudReg = dt1.format(solicitud.getFecha()) + " " + horaFinReg;

					Date fechaInicio = null;
					Date fechaFin = null;
					Date fechaInicioReg = null;
					Date fechaFinReg = null;

					try {
						fechaInicio = (Date) formatter.parse(fechaInicioSolicitud);
						fechaFin = (Date) formatter.parse(fechaFinSolicitud);
						fechaInicioReg = (Date) formatter.parse(fechaInicioSolicitudReg);
						fechaFinReg = (Date) formatter.parse(fechaFinSolicitudReg);
					} catch (ParseException ex) {
						return "Ocurrio un inconveniente, vuelve a intentarlo mas tarde.";
					}

					float horaToMinuto = ((fechaFin.getTime() - fechaInicio.getTime()) / 60000);
					float horaToMinutoReg = ((fechaFinReg.getTime() - fechaInicioReg.getTime()) / 60000);

					if (solicitud.getCurso().getCodigo() > 0 && solicitud.getUaaPersonal().getCodigo() > 0
							&& solicitud.getHoraInicio().getCodigo() > 0 && solicitud.getHoraFin().getCodigo() > 0
							&& solicitud.getTema() != null && solicitud.getTipoAcceso() != null
							&& solicitud.getDescripcion() != null) {
						if (fechaInicio.before(fechaFin)) {

							boolean returnModificar = solicitudDao.modificarSolicitud(id, solicitud);
							if (returnModificar) {
								return "OK";
							} else {
								return "Ocurrio un inconveniente, vuelve a intentarlo";
							}

						} else {
							return "La hora de inicio no puede ser inferior a la hora fin.";
						}
					} else {
						return "Por favor llenar todos los campos del formulario.";
					}
				} else {
					return "La fecha de solicitud no puede posterior a la fecha final del periodo actual.";
				}
			} else {
				return "No se puede modificar esta solicitud, la fecha se encuentra vencida.";
			}
		} else {
			return "No se puede modificar esta solicitud, pues ya se encuentra adjudicada.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.SolicitudServicio#
	 * eliminarSolicitud(int)
	 */
	@Override
	public String eliminarSolicitud(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.SolicitudServicio#
	 * listarSolicitud( )
	 */
	@Override
	public List<Solicitud> listarSolicitud() {
		// TODO Auto-generated method stub
		return solicitudDao.listarSolicitud(0);
	}

	/*
	 * (non-Javadoc),
	 * 
	 * @see co.edu.usco.lcms.servicios.reservaespacio.SolicitudServicio#
	 * listarTablaSolicitud(java.lang.String, int, int, int, int,
	 * java.lang.String)
	 */
	@Override
	public JSONRespuesta listarTablaSolicitud(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		return solicitudDao.listarTablaSolicitud(search, start, length, draw, posicion, direccion);
	}

	@Override
	public String adjudicar(int id, Solicitud solicitud) {
		// TODO Auto-generated method stub
		if (solicitud.getEstado().equals("0")) {
			EspacioOcupacion espacioOcupacion = new EspacioOcupacion();
			espacioOcupacion.setCodigo(0);
			solicitud.setEspacioOcupacion(espacioOcupacion);
			envioCorreo(solicitud, "", "Rechazada");
			return actualizarEstado(id, solicitud);
		} else {
			int facultad = solicitud.getUaaPersonal().getUaa().getDependencia();

			java.util.Date dateCal = new java.util.Date();
			Timestamp fechaActualCal = new Timestamp(dateCal.getTime());
			Calendario calenActual = calendarioDao.listarCalendario(fechaActualCal).get(0);
			int codCalendario = calenActual.getCodigo();

			Configuraciones configuraciones = configuracionesDao.listarConfiguraciones(facultad, 0, codCalendario)
					.get(0);

			int maxSolicitudesDia = configuraciones.getMaxSolicitudesDias();
			int maxSolicitudesSemana = configuraciones.getMaxSolicitudesSemana();

			SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			SimpleDateFormat dt2 = new SimpleDateFormat("dd-MM-yyyy");
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

			Date fechaSolicitud = solicitud.getFecha();
			String factual = dt1.format(new Date());
			String fsolicitud = dt1.format(solicitud.getFecha());
			Date perFechaFin = calenActual.getPeriodo().getFechaFin();

			Date fechaActualComparar = null;
			Date fechaSolicitudComparar = null;

			try {
				fechaActualComparar = (Date) formatter.parse(factual);
				fechaSolicitudComparar = (Date) formatter.parse(fsolicitud);
			} catch (ParseException ex) {
				return "Ocurrio un inconveniente, vuelve a intentarlo más tarde.";
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaActualComparar);
			calendar.add(Calendar.DAY_OF_YEAR, -1);

			if (calendar.getTime().before(fechaSolicitudComparar)) {
				if (fechaSolicitud.before(perFechaFin)) {
					String horaInicio = horaDao.buscarHora(solicitud.getHoraInicio().getCodigo()).getHora24h();
					String horaFin = horaDao.buscarHora(solicitud.getHoraFin().getCodigo()).getHora24h();

					String fechaInicioSolicitud = dt2.format(solicitud.getFecha()) + " " + horaInicio;
					String fechaFinSolicitud = dt2.format(solicitud.getFecha()) + " " + horaFin;

					Date fechaInicio = null;
					Date fechaFin = null;

					try {
						fechaInicio = (Date) formatter.parse(fechaInicioSolicitud);
						fechaFin = (Date) formatter.parse(fechaFinSolicitud);
					} catch (ParseException ex) {
						return "Ocurrio un inconveniente, vuelve a intentarlo mas tarde.";
					}

					int cantSolicitudesDia = solicitudDao.cantidadSolicitudes(true, solicitud.getFecha(),
							solicitud.getUaaPersonal().getCodigo(), true, "");
					int cantSolicitudesSemana = solicitudDao.cantidadSolicitudes(false, solicitud.getFecha(),
							solicitud.getUaaPersonal().getCodigo(), true, "");

					int cantUsuariosVideoconferencia = solicitudDao.cantidadUsuarios(1, solicitud.getFecha(),
							solicitud.getHoraInicio().getHora24h().split(":")[0]);

					int numSesionesAdobe = adobeConnectDao.listarAdobeConnect().get(0).getNumSesiones();

					int listaEstudiantes = estudianteDao.buscarEstudiantesCurso(solicitud.getCurso().getCodigo())
							.size();

					int numParticipantes = listaEstudiantes + 1;

					int numUsuariosDisponibles = numSesionesAdobe - cantUsuariosVideoconferencia;

					if (numParticipantes <= numUsuariosDisponibles) {
						if (cantSolicitudesSemana < maxSolicitudesSemana) {
							if (cantSolicitudesDia < maxSolicitudesDia) {
								Solicitud soli = serviciosAdobeConnectDao.crearReserva(solicitud);
								String urlReserva = soli.getEspacioOcupacion().getUrl();
								solicitud.setCodigoVideoconferencia(soli.getCodigoVideoconferencia());
								if (!urlReserva.isEmpty()) {
									int codigoEspacioOcupacion = adjudicarEspacioOcupacion(solicitud, urlReserva);

									if (codigoEspacioOcupacion > 0) {
										EspacioOcupacion espacioOcupacion = new EspacioOcupacion();
										espacioOcupacion.setCodigo(codigoEspacioOcupacion);
										solicitud.setEspacioOcupacion(espacioOcupacion);

										String resultEstado = actualizarEstado(id, solicitud);
										if (resultEstado.equals("OK")) {

											envioCorreo(solicitud, urlReserva, "Adjudicada");
											return "OK";

										} else {
											return "Ocurrio un inconveniente, al tratar de actualizar el estado de la solicitud. Vuelve a intentarlo.";
										}
									} else {
										return "Ocurrio un inconveniente, al tratar de adjudicar esta solicitud. Vuelve a intentarlo.";
									}
								} else {
									return "Ocurrio un inconveniente, al tratar de adjudicar esta solicitud en adobe connect. Vuelve a intentarlo.";
								}
							} else {
								return "Excedio el número máximo de solicitudes por dia según la facultad a la cual pertenece el docente. Intente en otra fecha.";
							}
						} else {
							return "Excedio el número máximo de solicitudes por semana según la facultad a la cual pertenece el docente. Intente en otra fecha.";
						}
					} else {
						return "No se puede adjudicar esta solicitud, ya se encuentran adjudicadas el número máximo de usuarios para el uso de videoconferencia en esta fecha y hora.";
					}
				} else {
					return "La fecha de solicitud no puede posterior a la fecha final del periodo actual.";
				}
			} else {
				return "No se puede adjudicar esta solicitud, la fecha y hora de la solicitud ya paso.";
			}
		}
	}

	public String actualizarEstado(int id, Solicitud solicitud) {
		boolean returnModificar = solicitudDao.actualizarEstado(id, solicitud);
		if (returnModificar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo";
		}
	}

	public int adjudicarEspacioOcupacion(Solicitud solicitud, String urlReserva) {

		EspacioOcupacion espacioOcupacion = new EspacioOcupacion();
		espacioOcupacion.setDescripcion(solicitud.getDescripcion());
		espacioOcupacion.setUrl(urlReserva);

		java.util.Date utilStartDate = solicitud.getFecha();
		java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
		espacioOcupacion.setFecha(sqlStartDate);
		espacioOcupacion.setEstado("1");

		Curso curso = new Curso();
		curso.setCodigo(solicitud.getCurso().getCodigo());
		espacioOcupacion.setCurso(curso);

		UaaPersonal uaaPersonal = new UaaPersonal();
		uaaPersonal.setCodigo(solicitud.getUaaPersonal().getCodigo());
		espacioOcupacion.setUaaPersonal(uaaPersonal);

		Uaa uaa = new Uaa();
		uaa.setCodigo(solicitud.getUaaPersonal().getUaa().getCodigo());
		espacioOcupacion.setUaa(uaa);

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Usuario usuario = new Usuario();

		usuario.setCodigo(user.getId());
		espacioOcupacion.setUsuario(usuario);

		String valorOcupacionTipo = webParametroDao.listarWebParametro(constantes.WEP_TIPO_OCUPACION).get(0).getValor();
		OcupacionTipo ocupacionTipo = new OcupacionTipo();
		ocupacionTipo.setCodigo(Integer.parseInt(valorOcupacionTipo));
		espacioOcupacion.setOcupacionTipo(ocupacionTipo);

		String valorHora = webParametroDao.listarWebParametro(constantes.WEP_HORAS).get(0).getValor();
		Horas hora = new Horas();
		hora.setCodigo(Integer.parseInt(valorHora));
		espacioOcupacion.setHora(hora);

		String valorHorario = webParametroDao.listarWebParametro(constantes.WEP_HORARIO).get(0).getValor();
		Horario horario = new Horario();
		horario.setCodigo(Integer.parseInt(valorHorario));
		espacioOcupacion.setHorario(horario);

		String valorSalas = webParametroDao.listarWebParametro(constantes.WEP_SALAS).get(0).getValor();
		String[] codigoSalas = valorSalas.split(",");

		double aleatorio = Math.floor(Math.random() * (codigoSalas.length));
		int salaCod = Integer.parseInt(codigoSalas[(int) aleatorio]);

		Espacio espacio = new Espacio();
		espacio.setCodigo(salaCod);
		espacioOcupacion.setEspacio(espacio);

		int returnRegistrar = espacioOcupacionDao.agregarEspacioOcupacion(espacioOcupacion);
		return returnRegistrar;

	}

	public void envioCorreo(Solicitud solicitud, String url, String estado) {

		String destinatario = solicitud.getUaaPersonal().getPersona().getEmail();

		SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
		String fsolicitud = dt1.format(solicitud.getFecha());

		String footer = "NOTA CONFIDENCIAL:<br>"
				+ "Este mensaje es exclusivamente para el uso de la persona o entidad a quien está dirigido; La información contenida en este e-mail y en todos sus archivos anexos es completamente confidencial, de igual manera las opiniones expresadas son netamente personales, y no necesariamente transmiten el pensamiento de la Universidad Surcolombiana. Si usted no es el destinatario por favor háganoslo saber respondiendo a este correo y por favor destruya cualquier copia del mismo y sus adjuntos, cualquier almacenamiento, distribución, divulgación o copia de este mensaje está estrictamente prohibida y sancionada por la ley."
				+ "Si por error recibe este mensaje, ofrecemos disculpas.<br><br>" + "CONFIDENTIAL NOTE:<br>"
				+ "This message is exclusively for  use of the individual or entity to whom it is forwarded.  The information of this e-mail and all its attachments is completely confidential; likewise, the opinions expressed are purely personal and they do not necessarily convey the thought of  Surcolombiana University.  If you are not the addressee, please let us know it by replying to this e-mail and please delete any copy and its attachments.  Any storage, distribution, dissemination or copy of this information is strictly prohibited and punished  by law.";
		String asunto = "Adjudicación reserva de videoconferencia - " + fsolicitud + " "
				+ solicitud.getHoraInicio().getHora();
		String mensaje = "Cordial saludo " + solicitud.getUaaPersonal().getPersona().getNombre().toUpperCase() + " "
				+ solicitud.getUaaPersonal().getPersona().getApellido().toUpperCase()
				+ ",<br><br>Su solicitud de reserva para videoconferencia fue <b>" + estado + "</b>"
				+ "<br><br>Datos:<br>" + "<br><b>Tema: </b>" + solicitud.getTema() + "<br><b>Descripción: </b>"
				+ solicitud.getDescripcion() + "<br><b>Fecha: </b>" + fsolicitud + "<br><b>Hora Inicio: </b>"
				+ solicitud.getHoraInicio().getHora() + "<br><b>Hora Fin: </b>" + solicitud.getHoraFin().getHora();
		if (!url.isEmpty()) {
			AdobeConnect credenciales = adobeConnectDao.listarAdobeConnect().get(0);
			String url_base = credenciales.getUrl();
			mensaje = mensaje + "<br><br><b>Link de acceso a la videoconferencia: " + url_base + url + "</b>";
		}
		if (solicitud.getMensaje() != null) {
			mensaje += "<br><b>Observación:</b>" + solicitud.getMensaje();
		}
		mensaje += "<br>Recuerde que las credenciales de acceso son su correo y como clave su número de identificación";
		mensaje = mensaje + "<br><br><br><br>" + footer;
		new TheadNotificacionCorreo(destinatario, asunto, mensaje, "").start();
	}

	@Override
	public int cantidadSolicitudesAdmin(int estado, String mes, String anio, int uaaPersona) {
		// TODO Auto-generated method stub
		return solicitudDao.cantidadSolicitudesAdmin(estado, mes, anio, uaaPersona);
	}

	@Override
	public float cantidadHorasSolicitudesAdmin(int estado, String mes, String anio, int uaaPersona) {
		// TODO Auto-generated method stub
		List<Solicitud> horas = solicitudDao.cantidadHorasSolicitudesAdmin(estado, mes, anio, uaaPersona);
		float horaToMinuto = 0;
		SimpleDateFormat dt2 = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		for (Solicitud sol : horas) {
			String fechaInicioSolicitud = dt2.format(sol.getFecha()) + " " + sol.getHoraInicio().getHora24h();
			String fechaFinSolicitud = dt2.format(sol.getFecha()) + " " + sol.getHoraFin().getHora24h();
			Date fechaInicio = null;
			Date fechaFin = null;

			try {
				fechaInicio = (Date) formatter.parse(fechaInicioSolicitud);
				fechaFin = (Date) formatter.parse(fechaFinSolicitud);
			} catch (ParseException ex) {
				return 0;
			}

			float minutos = (fechaFin.getTime() - fechaInicio.getTime()) / 60000;
			horaToMinuto = horaToMinuto + minutos;

		}

		return horaToMinuto / 60;
	}

	@Override
	public int cantidadUsuarios(int estado, Date fecha, String hora) {
		// TODO Auto-generated method stub
		return solicitudDao.cantidadUsuarios(estado, fecha, hora);
	}

}
