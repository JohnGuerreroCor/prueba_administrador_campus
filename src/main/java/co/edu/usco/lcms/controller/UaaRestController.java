/**
 * Clase UaaRestController para gestionar los servicios web de UAA (Listar, Agregar, Modificar, Eliminar)
 */
package co.edu.usco.lcms.controller;

import java.util.ArrayList;
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
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.servicios.UaaServicio;
import co.edu.usco.lcms.utility.Respuesta;
import co.edu.usco.lcms.utility.ValidadorParametro;
import co.edu.usco.lcms.utility.ValidadorParametro.TipoValidador;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@RestController
public class UaaRestController {

	@Autowired
	UaaServicio uaaServicio;

	/**
	 * Listar datos de UAA según la estructura que requiere DataTable de JQuery
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
	 * @return retorna la lista de UAA segun los parametros enviados
	 */
	@RequestMapping(value = "/uaa/listar", method = RequestMethod.POST,consumes="application/json",headers = "content-type=application/x-www-form-urlencoded")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public JSONRespuesta getUAA(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {

		JSONRespuesta listUaa = new JSONRespuesta();
		listUaa = uaaServicio.listarTablaUaa(search, start, length, draw, posicion, direccion);
		return listUaa;
	}

	/**
	 * Metodo GET para listar los registros de UAA según los parametros
	 * establecidos
	 * 
	 * @param uaaTipo
	 *            codigo del tipo de uaa para asi filtrar las UAA
	 * @param uaaMod
	 *            codigo de la uaa que se quiere listar
	 * @return retorna lista de UAA según los parametros
	 */
	@RequestMapping(value = "/uaaAll", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Uaa> getUaaAll(@RequestParam("tipoUaa") int uaaTipo, @RequestParam("uaaMod") int uaaMod) {
		List<Uaa> listOfUaa = new ArrayList<Uaa>();
		listOfUaa = uaaServicio.listarUaa(uaaTipo, 0, false, uaaMod, false);
		return listOfUaa;
	}

	/**
	 * Metodo GET para listar las UAA de la cual depende un usuario de tipo
	 * facultad
	 * 
	 * @param uaaTipo
	 *            codigo del tipo uaa
	 * @return retorna lista de UAA según los parametros
	 */
	@RequestMapping(value = "/uaaDep", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Uaa> getUaaDepAll(@RequestParam("tipoUaa") int uaaTipo, @RequestParam("uaaMod") int uaaMod) {
		List<Uaa> listOfUaa = new ArrayList<Uaa>();
		listOfUaa = uaaServicio.listarUaa(uaaTipo, 0, true, uaaMod, true);
		return listOfUaa;
	}

	/**
	 * Metodo GET para consultar una uaa según el codigo que llegue como
	 * parametro
	 * 
	 * @param id
	 *            codigo de uaa que se quiere consultar
	 * @return retorna lista de UAA según los parametros
	 */
	@RequestMapping(value = "/uaaAll/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Uaa> getUaaAllId(@PathVariable int id) {
		List<Uaa> uaas = uaaServicio.listarUaa(0, id, false, 0, false);
		if (uaas.size() > 0) {
			return new ResponseEntity<Uaa>(uaas.get(0), HttpStatus.OK);
		}
		return new ResponseEntity<Uaa>(new Uaa(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Metodo POST para registrar una UAA
	 * 
	 * @param uaa
	 *            objeto con los datos del registro que se quiere agregar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/uaa", method = RequestMethod.POST, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody Uaa uaa) {
		boolean nombre_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO, uaa.getNombre(),
				200);
		boolean nombre_corto_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				uaa.getNombreCorto(), 100);
		boolean nombre_impresion_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				uaa.getNombreImpresion(), 20);
		boolean email_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_EMAIL, uaa.getEmail(), 50);
		boolean email_pqr_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_EMAIL, uaa.getEmailPqr(),
				50);
		boolean pagina_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO, uaa.getPagina(), 50);
		boolean direccion_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				uaa.getDireccion(), 50);
		boolean telefono_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO, uaa.getTelefono(),
				50);
		boolean fax_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO, uaa.getFax(), 50);
		boolean centro_costo_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				uaa.getCentroCostos(), 3);
		boolean acronimo_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO, uaa.getAcronimo(),
				2);

		if (!nombre_valido) {
			return returnMensaje("Campo nombre no valido");
		}
		if (!nombre_corto_valido) {
			return returnMensaje("Campo nombre corto no valido");
		}
		if (!nombre_impresion_valido) {
			return returnMensaje("Campo nombre impresión no valido");
		}
		if (!email_valido) {
			return returnMensaje("Campo email no valido");
		}
		if (!email_pqr_valido) {
			return returnMensaje("Campo email PQR no valido");
		}
		if (!pagina_valido) {
			return returnMensaje("Campo página no valido");
		}
		if (!direccion_valido) {
			return returnMensaje("Campo dirección no valido");
		}
		if (!telefono_valido) {
			return returnMensaje("Campo telefono no valido");
		}
		if (!fax_valido) {
			return returnMensaje("Campo fax no valido");
		}
		if (!centro_costo_valido) {
			return returnMensaje("Campo centro de costo no valido");
		}
		if (!acronimo_valido) {
			return returnMensaje("Campo acrónimo no valido");
		}

		String returnInsercion = uaaServicio.agregarUaa(uaa);

		if (returnInsercion.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnInsercion);
		}
	}

	/**
	 * Metodo PUT para modificar un registro de UAA
	 * 
	 * @param id
	 *            codigo de la UAA que se quiere modificar
	 * @param uaa
	 *            objeto con los datos de la UAA que se quiere modificar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/uaa/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody Uaa uaa) {
		if (id > 0) {
			boolean nombre_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO, uaa.getNombre(),
					200);
			boolean nombre_corto_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					uaa.getNombreCorto(), 100);
			boolean nombre_impresion_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					uaa.getNombreImpresion(), 20);
			boolean email_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_EMAIL, uaa.getEmail(),
					50);
			boolean email_pqr_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_EMAIL,
					uaa.getEmailPqr(), 50);
			boolean pagina_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO, uaa.getPagina(),
					50);
			boolean direccion_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					uaa.getDireccion(), 50);
			boolean telefono_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					uaa.getTelefono(), 50);
			boolean fax_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO, uaa.getFax(), 50);
			boolean centro_costo_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					uaa.getCentroCostos(), 3);
			boolean acronimo_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					uaa.getAcronimo(), 2);

			if (!nombre_valido) {
				return returnMensaje("Campo nombre no valido");
			}
			if (!nombre_corto_valido) {
				return returnMensaje("Campo nombre corto no valido");
			}
			if (!nombre_impresion_valido) {
				return returnMensaje("Campo nombre impresión no valido");
			}
			if (!email_valido) {
				return returnMensaje("Campo email no valido");
			}
			if (!email_pqr_valido) {
				return returnMensaje("Campo email PQR no valido");
			}
			if (!pagina_valido) {
				return returnMensaje("Campo página no valido");
			}
			if (!direccion_valido) {
				return returnMensaje("Campo dirección no valido");
			}
			if (!telefono_valido) {
				return returnMensaje("Campo telefono no valido");
			}
			if (!fax_valido) {
				return returnMensaje("Campo fax no valido");
			}
			if (!centro_costo_valido) {
				return returnMensaje("Campo centro de costo no valido");
			}
			if (!acronimo_valido) {
				return returnMensaje("Campo acrónimo no valido");
			}
			String returnModificar = uaaServicio.modificarUaa(id, uaa);
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
	 * Metodo DELETE para eliminar un registro de UAA
	 * 
	 * @param id
	 *            codigo de la UAA que se quiere eliminar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/uaa/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {
		String returnEliminar = uaaServicio.eliminarUaa(id);
		if (returnEliminar.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnEliminar);
		}
	}

	/**
	 * 
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
