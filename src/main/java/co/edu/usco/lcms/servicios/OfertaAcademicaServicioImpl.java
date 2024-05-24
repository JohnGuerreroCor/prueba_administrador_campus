/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.CalendarioDao;
import co.edu.usco.lcms.dao.OfertaAcademicaDao;
import co.edu.usco.lcms.dao.OfertaDao;
import co.edu.usco.lcms.dao.WebParametroDao;
import co.edu.usco.lcms.librerias.CompararTipoUaa;
import co.edu.usco.lcms.librerias.EncriptarPalabra;
import co.edu.usco.lcms.librerias.TheadEnvioCorreo;
import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Oferta;
import co.edu.usco.lcms.model.OfertaAcademica;
import co.edu.usco.lcms.model.PreInscripcion;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@Component
public class OfertaAcademicaServicioImpl implements OfertaAcademicaServicio {

	@Autowired
	OfertaAcademicaDao ofertaAcademicaDao;

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	Constantes constantes;

	@Autowired
	OfertaDao ofertaDao;

	@Autowired
	CompararTipoUaa compararTipoUaa;

	@Autowired
	CalendarioDao calendarioDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.OfertaAcademicaServicio#
	 * agregarOfertaAcademica( co.edu.usco.lcms.model.OfertaAcademica)
	 */
	@Override
	public String agregarOfertaAcademica(OfertaAcademica ofertaAcademica) {

		// TODO Auto-generated method stub
		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, -1); // numero de días a añadir, o

		if (ofertaAcademica.getPrograma().getCodigo() > 0 && ofertaAcademica.getTipoAdmision() != null
				&& ofertaAcademica.getPago() != null && ofertaAcademica.getFechaInicio() != null
				&& ofertaAcademica.getFechaFin() != null && ofertaAcademica.getInscripcionFechaInicio() != null
				&& ofertaAcademica.getInscripcionFechaFin() != null
				&& ofertaAcademica.getOfertaAcademicaEstado().getCodigo() > -1) {
			if (calendar.getTime().before(ofertaAcademica.getInscripcionFechaInicio())) {
				if (ofertaAcademica.getInscripcionFechaInicio().before(ofertaAcademica.getInscripcionFechaFin())) {
					if (ofertaAcademica.getInscripcionFechaFin().before(ofertaAcademica.getFechaInicio())) {
						if (ofertaAcademica.getFechaInicio().before(ofertaAcademica.getFechaFin())) {

							if(ofertaAcademica.getCalendario().getCodigo() > 0){
							//if (calendarioDao.listarCalendario(ofertaAcademica.getFechaInicio()).size() > 0) {
								int codCalendario = ofertaAcademica.getCalendario().getCodigo();//calendarioDao.listarCalendario(ofertaAcademica.getFechaInicio()).get(0).getCodigo();

								OfertaAcademica validarProgramaCalendario = ofertaAcademicaDao.buscarOfertaAcademica(0,
										codCalendario, ofertaAcademica.getPrograma().getCodigo(), 0);

								if (validarProgramaCalendario == null) {
									Calendario calendario = new Calendario();
									calendario.setCodigo(codCalendario);
									ofertaAcademica.setCalendario(calendario);

									if (ofertaAcademica.getRequiereCupo() == 1) {
										if (ofertaAcademica.getCupoMax() > 0) {
											return guardar(ofertaAcademica);
										} else {
											return "Si la oferta requiere un limite de cupos, por favor ingresar el número de cupos máximo.";
										}
									} else {
										ofertaAcademica.setCupoMax(-1);
										return guardar(ofertaAcademica);
									}
								} else {
									return "Este programa ya se encuentra ofertado con el mismo calendario.";
								}
							} else {
								return "No se encuentra establecido un periodo y calendario para estas fechas, por favor comunicarse con el area encargada.";
							}
						} else {
							return "La fecha fin no puede ser menor a la fecha de inicio";
						}
					} else {
						return "La fecha inicio no puede ser menor a la fecha fin de inscripción";
					}
				} else {
					return "La fecha fin de inscripción no puede ser menor a la fecha de inicio de inscripción";
				}
			} else {
				return "La fecha inicio de inscripción no pude ser menor a la fecha actual.";
			}
		} else {
			return "Todos los campos del formulario son requeridos.";
		}
	}

	public String guardar(OfertaAcademica ofertaAcademica) {
		int returnInsercion = ofertaAcademicaDao.agregarOfertaAcademica(ofertaAcademica);

		if (returnInsercion > 0) {
			return "" + returnInsercion;
		} else {
			return "0";
		}
	}

	public String modificar(OfertaAcademica ofertaAcademica, int id) {
		// consultar estado del programa si es 02 Estado de la oferta en
		// preInscripcion
		Oferta estadoOferta = ofertaDao.consultarEstadoOferta(id, "");

		boolean returnModificar = ofertaAcademicaDao.modificarOfertaAcademica(id, ofertaAcademica);
		if (returnModificar) {

			if (estadoOferta.getEstado().equals("2")) {

				List<PreInscripcion> usuariosPreInscritos = ofertaDao.buscarPreInscritos(id);
				if (usuariosPreInscritos != null) {
					for (PreInscripcion usuariosPreInscritos1 : usuariosPreInscritos) {

						try {
							String oferta = EncriptarPalabra
									.SHA1(Integer.toString(usuariosPreInscritos1.getOferta().getCodigo())); // tem_ofa_codigo
							String hashCorreo = EncriptarPalabra
									.SHA1(Integer.toString(usuariosPreInscritos1.getCodigo())); // tem_codigo

							String destinatario = usuariosPreInscritos1.getPersona().getEmail();
							String footer = "NOTA CONFIDENCIAL:<br>"
									+ "Este mensaje es exclusivamente para el uso de la persona o entidad a quien está dirigido; La información contenida en este e-mail y en todos sus archivos anexos es completamente confidencial, de igual manera las opiniones expresadas son netamente personales, y no necesariamente transmiten el pensamiento de la Universidad Surcolombiana. Si usted no es el destinatario por favor háganoslo saber respondiendo a este correo y por favor destruya cualquier copia del mismo y sus adjuntos, cualquier almacenamiento, distribución, divulgación o copia de este mensaje está estrictamente prohibida y sancionada por la ley."
									+ "Si por error recibe este mensaje, ofrecemos disculpas.<br><br>"
									+ "CONFIDENTIAL NOTE:<br>"
									+ "This message is exclusively for  use of the individual or entity to whom it is forwarded.  The information of this e-mail and all its attachments is completely confidential; likewise, the opinions expressed are purely personal and they do not necessarily convey the thought of  Surcolombiana University.  If you are not the addressee, please let us know it by replying to this e-mail and please delete any copy and its attachments.  Any storage, distribution, dissemination or copy of this information is strictly prohibited and punished  by law.";
							String asunto = "Inscripciones abiertas curso "
									+ estadoOferta.getPrograma().getUaa().getNombre()
									+ " - Inscripciones Universidad Surcolombiana";
							String mensaje = "Cordial saludo "
									+ usuariosPreInscritos1.getPersona().getNombre().toUpperCase() + " "
									+ usuariosPreInscritos1.getPersona().getApellido().toUpperCase()
									+ ",<br><br>El programa " + estadoOferta.getPrograma().getUaa().getNombre()
									+ " abrío sus inscripciones, has clic en el siguiente enlace para continuar con el proceso "
									+ "<a href='" + constantes.RUTA_PORTAL + "/inscripcion?codigo=" + oferta + "&id="
									+ hashCorreo + "'>Inscribirme</a><br><br>" + footer;
							new TheadEnvioCorreo(destinatario, asunto, mensaje, "").start();
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
			}

			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.OfertaAcademicaServicio#
	 * modificarOfertaAcademica( int, co.edu.usco.lcms.model.OfertaAcademica)
	 */
	@Override
	public String modificarOfertaAcademica(int id, OfertaAcademica ofertaAcademica) {
		// TODO Auto-generated method stub
		if (id > 0) {
			OfertaAcademica ofertaAcademicaInfo = ofertaAcademicaDao.buscarOfertaAcademica(id, 0, 0, 0);

			if (ofertaAcademicaInfo != null) {
				if (compararTipoUaa.tipoUaa(ofertaAcademicaInfo.getPrograma().getUaa().getUaaTipo().getCodigo())) {
					if (ofertaAcademica.getPrograma().getCodigo() > 0 && ofertaAcademica.getTipoAdmision() != null
							&& ofertaAcademica.getPago() != null && ofertaAcademica.getFechaInicio() != null
							&& ofertaAcademica.getFechaFin() != null
							&& ofertaAcademica.getInscripcionFechaInicio() != null
							&& ofertaAcademica.getInscripcionFechaFin() != null
							&& ofertaAcademica.getOfertaAcademicaEstado().getCodigo() > -1) {

						if (ofertaAcademica.getInscripcionFechaInicio()
								.before(ofertaAcademica.getInscripcionFechaFin())) {
							if (ofertaAcademica.getInscripcionFechaFin().before(ofertaAcademica.getFechaInicio())) {
								if (ofertaAcademica.getFechaInicio().before(ofertaAcademica.getFechaFin())) {

									int codCalendario = ofertaAcademica.getCalendario().getCodigo();////calendarioDao.listarCalendario(ofertaAcademica.getFechaInicio()).get(0).getCodigo();

									OfertaAcademica validarProgramaCalendario = ofertaAcademicaDao
											.buscarOfertaAcademica(0, codCalendario,
													ofertaAcademica.getPrograma().getCodigo(),
													ofertaAcademica.getCodigo());

									if (validarProgramaCalendario == null) {

										Calendario calendario = new Calendario();
										calendario.setCodigo(codCalendario);
										ofertaAcademica.setCalendario(calendario);

										if (ofertaAcademica.getRequiereCupo() == 1) {
											if (ofertaAcademica.getCupoMax() > 0) {
												return modificar(ofertaAcademica, id);
											} else {
												return "Si la oferta requiere un limite de cupos, por favor ingresar el número de cupos máximo.";
											}
										} else {
											ofertaAcademica.setCupoMax(-1);
											return modificar(ofertaAcademica, id);
										}
									} else {
										return "Este programa ya se encuentra ofertado con el mismo calendario.";
									}

								} else {
									return "La fecha fin no puede ser menor a la fecha de inicio";
								}
							} else {
								return "La fecha inicio no puede ser menor a la fecha fin de inscripción";
							}
						} else {
							return "La fecha fin de inscripción no puede ser menor a la fecha de inicio de inscripción";
						}
					} else {
						return "Todos los campos del formulario son requeridos.";
					}
				} else {
					return "No tiene permisos para modificar este registro.";
				}
			} else {
				return "No se puede modificar este registro.";
			}
		} else {
			return "No se puede modificar.";
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.OfertaAcademicaServicio#
	 * eliminarOfertaAcademica( int)
	 */
	@Override
	public String eliminarOfertaAcademica(int id) {
		// TODO Auto-generated method stub
		if (id > 0) {
			OfertaAcademica ofertaAcademicaInfo = ofertaAcademicaDao.buscarOfertaAcademica(id, 0, 0, 0);
			if (ofertaAcademicaInfo != null) {
				if (compararTipoUaa.tipoUaa(ofertaAcademicaInfo.getPrograma().getUaa().getUaaTipo().getCodigo())) {
					boolean returnEliminar = ofertaAcademicaDao.eliminarOfertaAcademica(id);
					if (returnEliminar) {
						return "OK";
					} else {
						return "Ocurrio un inconveniente, No se puede eliminar este registro.";
					}
				} else {
					return "No tiene permisos para eliminar este registro.";
				}
			} else {
				return "No se puede eliminar este registro.";
			}
		} else {
			return "No se puede eliminar.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.OfertaAcademicaServicio#
	 * listarTablaOfertaAcademica(java.lang.String, int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaOfertaAcademica(String search, int start, int length, int draw, int posicion,
			String direccion, int facultad) {
		// TODO Auto-generated method stub
		return ofertaAcademicaDao.listarTablaOfertaAcademica(search, start, length, draw, posicion, direccion,
				facultad);
	}

	@Override
	public String guardarUrl(int id, String url) {
		// TODO Auto-generated method stub
		boolean returnModificar = ofertaAcademicaDao.guardarUrl(id, url);
		if (returnModificar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo.";
		}
	}

}
