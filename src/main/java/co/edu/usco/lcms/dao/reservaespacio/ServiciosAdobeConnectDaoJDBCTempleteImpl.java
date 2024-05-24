/**
 * 
 */
package co.edu.usco.lcms.dao.reservaespacio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.EstudianteDao;
import co.edu.usco.lcms.model.Estudiante;
import co.edu.usco.lcms.model.Persona;
import co.edu.usco.lcms.model.reservaespacios.AdobeConnect;
import co.edu.usco.lcms.model.reservaespacios.EspacioOcupacion;
import co.edu.usco.lcms.model.reservaespacios.Solicitud;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@SuppressWarnings("deprecation")
@Component
public class ServiciosAdobeConnectDaoJDBCTempleteImpl implements ServiciosAdobeConnectDao {

	@Autowired
	HoraDao horaDao;

	@Autowired
	AdobeConnectDao adobeConnectDao;

	@Autowired
	EstudianteDao estudianteDao;

	@Autowired
	Constantes constantes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.reservaespacio.ServiciosAdobeConnectDao#
	 * crearReserva( co.edu.usco.lcms.model.reservaespacios.Solicitud)
	 */
	@Override
	public Solicitud crearReserva(Solicitud solicitud) {
		// TODO Auto-generated method stub
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = null;

		AdobeConnect credenciales = adobeConnectDao.listarAdobeConnect().get(0);
		String user = credenciales.getUsuario();
		String anfitrionPrincipal = credenciales.getUsuario();
		String password = credenciales.getClave();
		String url_base = credenciales.getUrl();

		try {
			// Inicio Autenticación
			user = URLEncoder.encode(user, "UTF-8");
			password = URLEncoder.encode(password, "UTF-8");
			String urlLogin = url_base + "/api/xml?action=login&login=" + user + "&password=" + password;
			HttpGet httpLogin = new HttpGet(urlLogin);
			response = client.execute(httpLogin);

			BufferedReader br2 = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output2;
			while ((output2 = br2.readLine()) != null) {
				output2 = output2;
			}
			// Fin autenticación

			// Inicio crear reunión
			SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd");

			String horaInicio = horaDao.buscarHora(solicitud.getHoraInicio().getCodigo()).getHora24h();
			String horaFin = horaDao.buscarHora(solicitud.getHoraFin().getCodigo()).getHora24h();

			String fechaInicioSolicitud = dt2.format(solicitud.getFecha()) + "T" + horaInicio;
			String fechaFinSolicitud = dt2.format(solicitud.getFecha()) + "T" + horaFin;

			String descripcion = solicitud.getDescripcion();
			String nombre = "0" + solicitud.getCodigo() + "-" + solicitud.getTema();
			String permiso = solicitud.getTipoAcceso();

			if (permiso.equals("0")) {
				permiso = "view-hidden";
			} else {
				if (permiso.equals("1")) {
					permiso = "remove";
				} else {
					if (permiso.equals("2")) {
						permiso = "denied";
					} else {
						permiso = "view-hidden";
					}
				}
			}

			nombre = URLEncoder.encode(nombre, "UTF-8");
			descripcion = URLEncoder.encode(descripcion, "UTF-8");
			fechaInicioSolicitud = URLEncoder.encode(fechaInicioSolicitud, "UTF-8");
			fechaFinSolicitud = URLEncoder.encode(fechaFinSolicitud, "UTF-8");
			permiso = URLEncoder.encode(permiso, "UTF-8");

			String urlCrearReunion = url_base + "/api/xml?action=sco-update&folder-id="
					+ constantes.ADOBE_CONNECT_FOLDER_ID + "&description=" + descripcion + "&name=" + nombre
					+ "&type=meeting&icon=virtual-classroom&lang=es&date-begin=" + fechaInicioSolicitud + "&date-end="
					+ fechaFinSolicitud;
			// https://meet45343301.adobeconnect.com/api/xml?action=sco-update&type=meeting&icon=virtual-classroom&name=Prueba%20Class%20Room%20Jankarlos&folder-id=1063738460&date-begin=2017-06-07&date-end=2017-06-09
			HttpGet httpCrearReunion = new HttpGet(urlCrearReunion);
			response = client.execute(httpCrearReunion);

			BufferedReader br3 = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output3 = "";
			String linea = "";
			while ((linea = br3.readLine()) != null) {
				output3 = output3 + linea;
			}

			// Fin crear reunión

			// Inicio obtener id y url de la reunión creada
			String marcaInicio = "<results>";
			String marcaFin = "</results>";

			int p1 = output3.indexOf(marcaInicio);
			int p2 = output3.indexOf(marcaFin);

			output3 = output3.substring(p1, p2 + marcaFin.length());
			SAXBuilder builder = new SAXBuilder();
			String urlVideoconferencia = "";
			long codigoVideoconferencia = 0;
			try {
				Document document = (Document) builder.build(new StringReader(output3));
				Element rootNode = document.getRootElement();
				List list = rootNode.getChildren("sco");

				String codigoReserva = rootNode.getChild("sco").getAttributeValue("sco-id");
				codigoVideoconferencia = new Long(codigoReserva).longValue();

				Element node = (Element) list.get(0);
				urlVideoconferencia = node.getChildText("url-path");
				System.out.println("La url es:" + urlVideoconferencia);

				// Fin obtener id de la reunión recien creada y url

				// Inicio actualizar tipo de acceso a la reunión creada
				String urlTipoAcceso = url_base + "/api/xml?action=permissions-update&acl-id=" + codigoReserva
						+ "&principal-id=public-access&permission-id=" + permiso;
				HttpGet httpTipoAcceso = new HttpGet(urlTipoAcceso);
				response = client.execute(httpTipoAcceso);

				BufferedReader br4 = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

				String output4 = "";
				while ((output4 = br4.readLine()) != null) {
					output4 = output4;
				}
				// Fin actualizar tipo de acceso a la reunión creada

				// Obtener lista de estudiantes del curso
				List<Estudiante> listaEstudiantes = estudianteDao
						.buscarEstudiantesCurso(solicitud.getCurso().getCodigo());

				Estudiante estudiante = new Estudiante();
				estudiante.setId(000);
				estudiante.setCodigo("SI");
				Persona persona = new Persona();
				persona.setApellido(solicitud.getUaaPersonal().getPersona().getApellido());
				persona.setNombre(solicitud.getUaaPersonal().getPersona().getNombre());
				persona.setEmail(solicitud.getUaaPersonal().getPersona().getEmail());
				persona.setIdentificacion(solicitud.getUaaPersonal().getPersona().getIdentificacion());
				estudiante.setPersona(persona);

				listaEstudiantes.add(estudiante);

				Estudiante estudianteUno = new Estudiante();
				estudianteUno.setId(0001);
				estudianteUno.setCodigo("SI");
				Persona personaUno = new Persona();
				personaUno.setApellido("");
				personaUno.setNombre("");
				personaUno.setEmail(anfitrionPrincipal);
				personaUno.setIdentificacion("");
				estudianteUno.setPersona(personaUno);

				listaEstudiantes.add(estudianteUno);

				System.out.println("Cantidad estudiantes:" + listaEstudiantes.size());
				if (listaEstudiantes.size() > 0 && listaEstudiantes != null) {
					for (Estudiante est : listaEstudiantes) {

						String first_name = est.getPersona().getNombre();
						String last_name = est.getPersona().getApellido();
						String login = est.getPersona().getIdentificacion();// est.getPersona().getEmail();
						String password2 = est.getPersona().getIdentificacion();
						String email = "";
						System.out.println("EL email es:" + est.getPersona().getEmailPreInscripcion());
						if (est.getPersona().getEmailPreInscripcion() == null) {
							System.out.println("NO:" + est.getPersona().getEmail());
							email = est.getPersona().getEmail();
						} else {
							email = est.getPersona().getEmailPreInscripcion();
							System.out.println("SI:" + est.getPersona().getEmailPreInscripcion());
						}

						// Inicio buscar estudiante en la base de datos de adobe
						// connect
						System.out.println("El email es:" + email);
						String urlBuscarUsuario = url_base + "/api/xml?action=principal-list&filter-email="
								+ URLEncoder.encode(email, "UTF-8") + "&filter-name=";
						if (first_name != "") {
							urlBuscarUsuario = urlBuscarUsuario
									+ URLEncoder.encode(first_name + " " + last_name, "UTF-8");
						}
						System.out.println("Buscar usuario:");
						System.out.println(urlBuscarUsuario);

						HttpGet httpBuscarUsuario = new HttpGet(urlBuscarUsuario);
						response = client.execute(httpBuscarUsuario);

						BufferedReader brBuscarUsuario = new BufferedReader(
								new InputStreamReader((response.getEntity().getContent())));

						String outputBuscarUsuario = "";
						String lineaBuscarUsuario = "";
						while ((lineaBuscarUsuario = brBuscarUsuario.readLine()) != null) {
							outputBuscarUsuario = outputBuscarUsuario + lineaBuscarUsuario;
						}
						// Fin buscar estudiante en la base de datos de adobe
						// connect
						if (!outputBuscarUsuario.contains("<principal-list>")) {
							System.out.println("nO lo encontro");
							// Inicio crear usuario en adobe connect
							String urlCrearUsuario = url_base + "/api/xml?action=principal-update&first-name="
									+ URLEncoder.encode(first_name, "UTF-8");
							urlCrearUsuario = urlCrearUsuario + "&last-name=" + URLEncoder.encode(last_name, "UTF-8")
									+ "&login=" + URLEncoder.encode(login, "UTF-8");
							urlCrearUsuario = urlCrearUsuario + "&password=" + URLEncoder.encode(password2, "UTF-8")
									+ "&type=user&send-email=false";
							urlCrearUsuario = urlCrearUsuario + "&has-children=0&email="
									+ URLEncoder.encode(email, "UTF-8");

							HttpGet httpCrearUsuario = new HttpGet(urlCrearUsuario);
							response = client.execute(httpCrearUsuario);

							BufferedReader brUsu = new BufferedReader(
									new InputStreamReader((response.getEntity().getContent())));

							String outputUsu = "";
							String lineaUsu = "";
							while ((lineaUsu = brUsu.readLine()) != null) {
								outputUsu = outputUsu + lineaUsu;
							}
							// Fin crear usuario en adobe connect

							// Inicio obtener id del usuario creado en adobe
							// connect
							SAXBuilder builderUsuNuevo = new SAXBuilder();
							Document documentUsuNuevo = builderUsuNuevo.build(new StringReader(outputUsu));
							String codigoUsuario = "";
							try {
								Element rootNodeUsuNuevo = documentUsuNuevo.getRootElement();
								codigoUsuario = rootNodeUsuNuevo.getChild("principal")
										.getAttributeValue("principal-id");
							} catch (Exception e) {
								return null;
							}
							// Fin obtener id del usuario creado en adobe
							// connect
							if (!codigoUsuario.isEmpty()) {
								// Inicio Registrar persona en la reunión

								String permision = "view";
								if (est.getCodigo().equals("SI")) {
									permision = "host";
								}
								// Agregar al grupo de anfitriones
								/*
								 * if (est.getCodigo().equals("SI")) { permision
								 * = "host"; String urlAnfrition = url_base +
								 * "/api/xml?action=group-membership-update&group-id=2163188255&principal-id="
								 * + URLEncoder.encode(codigoUsuario, "UTF-8") +
								 * "&is-member=true";
								 * 
								 * HttpGet httpAnfrition = new
								 * HttpGet(urlAnfrition); response =
								 * client.execute(httpAnfrition);
								 * 
								 * BufferedReader brAnfrition = new
								 * BufferedReader( new
								 * InputStreamReader((response.getEntity().
								 * getContent())));
								 * 
								 * String outputAnfrition; while
								 * ((outputAnfrition = brAnfrition.readLine())
								 * != null) { outputAnfrition = outputAnfrition;
								 * } }
								 */

								String urlAddUsuario = url_base + "/api/xml?action=permissions-update";
								urlAddUsuario = urlAddUsuario + "&acl-id=" + URLEncoder.encode(codigoReserva, "UTF-8");
								urlAddUsuario = urlAddUsuario + "&principal-id="
										+ URLEncoder.encode(codigoUsuario, "UTF-8") + "&permission-id="
										+ URLEncoder.encode(permision, "UTF-8");

								HttpGet httpAddUsuario = new HttpGet(urlAddUsuario);
								response = client.execute(httpAddUsuario);

								BufferedReader brAddUsu = new BufferedReader(
										new InputStreamReader((response.getEntity().getContent())));

								String outputAddUsu;
								while ((outputAddUsu = brAddUsu.readLine()) != null) {
									outputAddUsu = outputAddUsu;
								}
								// Fin Registrar persona en la reunión
							}
						} else {
							System.out.println("Encontro al usuario");
							// Inicio obtener id del usuario en adobe connect
							SAXBuilder builderBu = new SAXBuilder();
							Document documentBu = builderBu.build(new StringReader(outputBuscarUsuario));
							String codigoUsuario = "";
							try {
								Element rootNodeBu = documentBu.getRootElement().getChild("principal-list");
								codigoUsuario = rootNodeBu.getChild("principal").getAttributeValue("principal-id");
							} catch (Exception e) {
								e.printStackTrace();
							}
							// Fin obtener id del usuario en adobe connect
							if (!codigoUsuario.isEmpty()) {
								// Inicio Registrar persona en la reunión
								String permision = "";
								if (est.getCodigo().equals("SI")) {
									permision = "host";
								} else {
									permision = "view";
								}
								String urlAddUsuario = url_base + "/api/xml?action=permissions-update";
								urlAddUsuario = urlAddUsuario + "&acl-id=" + URLEncoder.encode(codigoReserva, "UTF-8");
								urlAddUsuario = urlAddUsuario + "&principal-id="
										+ URLEncoder.encode(codigoUsuario, "UTF-8") + "&permission-id="
										+ URLEncoder.encode(permision, "UTF-8");

								HttpGet httpAddUsuario = new HttpGet(urlAddUsuario);
								response = client.execute(httpAddUsuario);

								BufferedReader brAddUsu = new BufferedReader(
										new InputStreamReader((response.getEntity().getContent())));

								String outputAddUsu;
								while ((outputAddUsu = brAddUsu.readLine()) != null) {
									outputAddUsu = outputAddUsu;
								}
							}
							// Fin Registrar persona en la reunión
						}

					}
				}
				// }
			} catch (Exception io) {
				io.printStackTrace();
				return null;
			}
			client.getConnectionManager().shutdown();
			Solicitud sol = new Solicitud();
			sol.setCodigoVideoconferencia(codigoVideoconferencia);
			EspacioOcupacion esp = new EspacioOcupacion();
			esp.setUrl(urlVideoconferencia);
			sol.setEspacioOcupacion(esp);
			System.out.println("La url es despues de set:" + urlVideoconferencia);
			return sol;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean eliminarReunion(long codigoVideoconferencia) {
		// TODO Auto-generated method stub
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = null;

		AdobeConnect credenciales = adobeConnectDao.listarAdobeConnect().get(0);
		String user = credenciales.getUsuario();
		String password = credenciales.getClave();
		String url_base = credenciales.getUrl();

		try {
			// Inicio Autenticación
			user = URLEncoder.encode(user, "UTF-8");
			password = URLEncoder.encode(password, "UTF-8");
			String urlLogin = url_base + "/api/xml?action=login&login=" + user + "&password=" + password;
			HttpGet httpLogin = new HttpGet(urlLogin);
			response = client.execute(httpLogin);

			BufferedReader br2 = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output2;
			while ((output2 = br2.readLine()) != null) {
				output2 = output2;
			}
			// Fin autenticación

			String urlEliminar = url_base + "/api/xml?action=sco-delete&sco-id="
					+ URLEncoder.encode(Long.toString(codigoVideoconferencia), "UTF-8");

			HttpGet httpGet = new HttpGet(urlEliminar);
			response = client.execute(httpGet);

			BufferedReader br3 = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output3 = "";
			String linea = "";
			while ((linea = br3.readLine()) != null) {
				output3 = output3 + linea;
			}

			SAXBuilder builderBu = new SAXBuilder();
			Document documentBu = builderBu.build(new StringReader(output3));
			String result = "";

			try {
				Element rootNodeBu = documentBu.getRootElement();
				result = rootNodeBu.getChild("status").getAttributeValue("code");
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			client.getConnectionManager().shutdown();

			if (result.equals("ok")) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Solicitud> grabacionesReunion(long codigoVideoconferencia) {

		// TODO Auto-generated method stub
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = null;

		AdobeConnect credenciales = adobeConnectDao.listarAdobeConnect().get(0);
		String user = credenciales.getUsuario();
		String password = credenciales.getClave();
		String url_base = credenciales.getUrl();

		List<Solicitud> listGrabaciones = new ArrayList<>();

		try {
			// Inicio Autenticación
			user = URLEncoder.encode(user, "UTF-8");
			password = URLEncoder.encode(password, "UTF-8");
			String urlLogin = url_base + "/api/xml?action=login&login=" + user + "&password=" + password;
			HttpGet httpLogin = new HttpGet(urlLogin);
			response = client.execute(httpLogin);

			BufferedReader br2 = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output2;
			while ((output2 = br2.readLine()) != null) {
				output2 = output2;
			}
			// Fin autenticación

			String urlGrabaciones = url_base + "/api/xml?action=sco-contents&sco-id="
					+ URLEncoder.encode(Long.toString(codigoVideoconferencia), "UTF-8") + "&filter-icon=archive";

			HttpGet httpGet = new HttpGet(urlGrabaciones);
			response = client.execute(httpGet);

			BufferedReader br3 = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output3 = "";
			String linea = "";
			while ((linea = br3.readLine()) != null) {
				output3 = output3 + linea;
			}

			String marcaInicio = "<scos>";
			String marcaFin = "</scos>";

			int p1 = output3.indexOf(marcaInicio);
			int p2 = output3.indexOf(marcaFin);

			output3 = output3.substring(p1, p2 + marcaFin.length());

			SAXBuilder builder = new SAXBuilder();

			try {
				Document document = (Document) builder.build(new StringReader(output3));
				Element rootNode = document.getRootElement();
				List list = rootNode.getChildren("sco");

				// if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {

					Element node = (Element) list.get(i);

					Solicitud solicitud = new Solicitud();
					solicitud.setTitle(node.getChildText("name"));
					solicitud.setStart(node.getChildText("date-begin"));
					solicitud.setEnd(node.getChildText("date-end"));
					solicitud.setMensaje(url_base + node.getChildText("url-path"));
					listGrabaciones.add(solicitud);
				}
				client.getConnectionManager().shutdown();
				return listGrabaciones;
				// }
			} catch (Exception io) {
				return listGrabaciones;
			}

		} catch (Exception e) {
			return listGrabaciones;
		}
	}

}
