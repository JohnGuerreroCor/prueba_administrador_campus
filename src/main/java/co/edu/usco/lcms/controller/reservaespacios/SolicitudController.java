/**
 * Clase SolicitudController para administrar servicios web solicitud
 */
package co.edu.usco.lcms.controller.reservaespacios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.reservaespacios.Solicitud;
import co.edu.usco.lcms.servicios.reservaespacio.SolicitudServicio;
import co.edu.usco.lcms.utility.Respuesta;
import co.edu.usco.lcms.utility.ValidadorParametro;
import co.edu.usco.lcms.utility.ValidadorParametro.TipoValidador;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@RestController
public class SolicitudController {

	@Autowired
	SolicitudServicio solicitudServicio;

	/**
	 * Listar datos de Solicitudes según la estructura que requiere DataTable de
	 * JQuery
	 * 
	 * @param search
	 *            parametro a buscar en la consulta
	 * @param start
	 *            limite de inicio de los registros
	 * @param length
	 *            limite de fin hasta donde se consultaran los registros
	 * @param draw
	 *            número de veces que se realiza la consulta
	 * @param posicion
	 *            posición de la columna con la cual se quiere ordenar los
	 *            registros
	 * @param direccion
	 *            orden en que se quieren ordenar lso registros ascendente o
	 *            descendente
	 * @return retorna la lista de asignaturas segun los parametros enviados
	 */
	@RequestMapping(value = "/listaSolicitudesSer", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded")
	// @Secured({ "ROLE_ADMIN_VIDEOCONFERENCIA_LCMS",
	// "ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS", "ROLE_DOCENTE" })
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms}) OR hasRole(${role.docente})")
	public JSONRespuesta getAsignaturas(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {

		JSONRespuesta listSolicitudes = new JSONRespuesta();
		listSolicitudes = solicitudServicio.listarTablaSolicitud(search, start, length, draw, posicion, direccion);
		return listSolicitudes;
	}

	/**
	 * Método para listar solicitudes
	 * 
	 * @return lista solicitudes
	 */
	@RequestMapping(value = "/solicitudSer", method = RequestMethod.GET, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMIN_VIDEOCONFERENCIA_LCMS",
	// "ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS", "ROLE_DOCENTE" })
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms}) OR hasRole(${role.docente})")
	public List<Solicitud> getListadoSolicitud() {
		List<Solicitud> listSolicitud = new ArrayList<Solicitud>();
		listSolicitud = solicitudServicio.listarSolicitud();
		return listSolicitud;
	}

	/**
	 * Método para agregar una solicitud
	 * 
	 * @param solicitud
	 *            objeto con los datos de la solicitud
	 * @return mensaje de operación exitosa o ocurrio un error
	 * @throws ParseException
	 */
	@RequestMapping(value = "/solicitudSer", method = RequestMethod.POST, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMIN_VIDEOCONFERENCIA_LCMS",
	// "ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS", "ROLE_DOCENTE" })
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms}) OR hasRole(${role.docente})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody Solicitud solicitud) {

		boolean tema = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO, solicitud.getTema(), 100);
		boolean descripcion = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				solicitud.getDescripcion(), 200);
		boolean docente = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
				String.valueOf(solicitud.getUaaPersonal().getCodigo()), 100);
		boolean curso = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
				String.valueOf(solicitud.getCurso().getCodigo()), 100);

		if (!tema) {
			return returnMensaje("Campo Tema no valido");
		}
		if (!descripcion) {
			return returnMensaje("Campo Descripción no valido");
		}
		if (!docente) {
			return returnMensaje("Campo Docente no valido");
		}
		if (!curso) {
			return returnMensaje("Campo Curso no valido");
		}

		String returnInsercion = solicitudServicio.agregarSolicitud(solicitud);

		if (returnInsercion.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnInsercion);
		}
	}

	/**
	 * Método para modificar los datos de una solicitud
	 * 
	 * @param id
	 *            código del registro de la solicitud a modificar
	 * @param solicitud
	 *            objeto con los datos de la solicitud a modificar
	 * @return mensaje de operación exitosa o ocurrio un error
	 */
	@RequestMapping(value = "/solicitudSer/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMIN_VIDEOCONFERENCIA_LCMS",
	// "ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS", "ROLE_DOCENTE" })
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms}) OR hasRole(${role.docente})")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody Solicitud solicitud) {

		if (id > 0) {
			boolean tema = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO, solicitud.getTema(), 100);
			boolean descripcion = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					solicitud.getDescripcion(), 200);
			boolean docente = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
					String.valueOf(solicitud.getUaaPersonal().getCodigo()), 100);
			boolean curso = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
					String.valueOf(solicitud.getCurso().getCodigo()), 100);

			if (!tema) {
				return returnMensaje("Campo Tema no valido");
			}
			if (!descripcion) {
				return returnMensaje("Campo Descripción no valido");
			}
			if (!docente) {
				return returnMensaje("Campo Docente no valido");
			}
			if (!curso) {
				return returnMensaje("Campo Curso no valido");
			}

			String returnModificar = solicitudServicio.modificarSolicitud(id, solicitud);
			if (returnModificar.equals("OK")) {
				Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
				return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
			} else {
				return returnMensaje(returnModificar);
			}
		}

		Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Metodo para la adjudicacion o rechazo de solicitudes
	 * 
	 * @param id
	 *            codigo de la solicitud
	 * @param solicitud
	 *            objeto con los datos de la solicitud
	 * @return
	 */
	@RequestMapping(value = "/estadoSolicitudSer/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	// @Secured({ "ROLE_ADMIN_VIDEOCONFERENCIA_LCMS",
	// "ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms})")
	public ResponseEntity<Respuesta> adjudicar(@PathVariable int id, @RequestBody Solicitud solicitud) {
		if (id > 0) {
			boolean estado = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
					String.valueOf(solicitud.getEstado()), 100);

			if (!estado) {
				return returnMensaje("Ocurrio un inconveniente, vuelve a intentarlo.");
			}

			String returnModificar = solicitudServicio.adjudicar(id, solicitud);
			if (returnModificar.equals("OK")) {
				Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
				return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
			} else {
				return returnMensaje(returnModificar);
			}
		}
		Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/cantidadSolicitudSer/{estado}", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms})")
	public int getCatidadSolicitud(@PathVariable int estado) {
		int cantidad = solicitudServicio.cantidadSolicitudesAdmin(estado, "", "", 0);
		return cantidad;
	}

	@RequestMapping(value = "/cantidadUsuariosSolicitud/{estado}/{fecha}/{hora}", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms})")
	public int getCatidadUsuariosSolicitud(@PathVariable int estado, @PathVariable String fecha,
			@PathVariable String hora) {

		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
		Date fechaDate = null;
		try {
			fechaDate = formato.parse(fecha);
		} catch (ParseException ex) {
			System.out.println(ex);
		}
		int cantidad = solicitudServicio.cantidadUsuarios(estado, fechaDate, hora);
		return cantidad;
	}

	@RequestMapping(value = "/cantidadSolicitudDocenteSer", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms})")
	public int getCatidadSolicitudDoce(@RequestParam(required = false, value = "") String mes,
			@RequestParam(required = false, value = "") String anio, @RequestParam int uaaPersona) {
		int cantidad = solicitudServicio.cantidadSolicitudesAdmin(1, mes, anio, uaaPersona);
		return cantidad;
	}

	@RequestMapping(value = "/cantidadHorasSolicitudDocenteSer", method = RequestMethod.GET, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.admin.videoconferencia.lcms}) OR hasRole(${role.admin.videoconferencia.facultad.lcms})")
	public float getCatidadHorasSolicitudDoce(@RequestParam(required = false, value = "") String mes,
			@RequestParam(required = false, value = "") String anio, @RequestParam int uaaPersona) {
		float cantidad = solicitudServicio.cantidadHorasSolicitudesAdmin(1, mes, anio, uaaPersona);
		return cantidad;
	}

	/**
	 * @param mensaje
	 *            texto que se quiere retornar
	 * @return retorna de ResponseEntity un objeto JSON con el mensaje de
	 *         respuesta erroneo
	 */
	public ResponseEntity<Respuesta> returnMensaje(String mensaje) {
		Respuesta respuesta = new Respuesta();
		respuesta.setMensaje(mensaje);
		respuesta.setCodigo(0);
		respuesta.setExito(false);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}

}
