/**
 * Clase OfertaAcademicaController para gestionar los servicios web de Oferta Acádemica listar, agregar, modificar, eliminar
 */
package co.edu.usco.lcms.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.usco.lcms.dao.OfertaDao;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Oferta;
import co.edu.usco.lcms.model.OfertaAcademica;
import co.edu.usco.lcms.model.RequisitosAdjuntos;
import co.edu.usco.lcms.servicios.OfertaAcademicaServicio;
import co.edu.usco.lcms.utility.FileUtil;
import co.edu.usco.lcms.utility.Respuesta;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@RestController
public class OfertaAcademicaController {

	@Autowired
	OfertaAcademicaServicio ofertaAcademicaServicio;

	@Autowired
	FileUtil fileUtil;

	@Autowired
	OfertaDao ofertaDao;

	/**
	 * Listar datos de Oferta Acádemica según la estructura que requiere
	 * DataTable de JQuery
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
	 * @return retorna la lista de Oferta Acádemica segun los parametros
	 *         enviados
	 */
	@RequestMapping(value = "/ofertaAcademicaSer/lista", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded")
	// @PreAuthorize({ "${role.administrador.lcms}",
	// "${role.administrador.facultad.lcms}" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public JSONRespuesta getUAA(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion,
			@RequestParam(value = "facultad", defaultValue = "0") int facultad) {

		JSONRespuesta listUaa = new JSONRespuesta();
		listUaa = ofertaAcademicaServicio.listarTablaOfertaAcademica(search, start, length, draw, posicion, direccion,
				facultad);
		return listUaa;
	}

	/**
	 * Metodo POST para agregar un registro de oferta acádemica
	 * 
	 * @param ofertaAcademica
	 *            objeto con los datos de oferta acádemica que se quiere
	 *            registrar
	 * @return retorna un listado de las asignaturas encontradas
	 */
	@RequestMapping(value = "/ofertaAcademicaSer", method = RequestMethod.POST, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody OfertaAcademica ofertaAcademica) {
		String returnInsercion = ofertaAcademicaServicio.agregarOfertaAcademica(ofertaAcademica);

		if (isNumeric(returnInsercion)) {
			if (!returnInsercion.equals("0")) {
				Respuesta respuesta = new Respuesta();
				respuesta.setMensaje(returnInsercion);
				respuesta.setCodigo(1);
				respuesta.setExito(true);
				return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
			} else {
				return returnMensaje("Ocurrio un inconveniente, vuelve a intentarlo mas tarde.");
			}
		} else {
			return returnMensaje(returnInsercion);
		}
	}

	/**
	 * Metodo Para convertir cadena a string
	 * 
	 * @param cadena
	 *            string a convertir
	 * @return caracter numerico
	 */
	private boolean isNumeric(String cadena) {
		// TODO Auto-generated method stub
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	/**
	 * Metodo para mostrar imagen
	 * 
	 * @param id
	 *            codigo de la oferta con la que se quiere buscar la imagen
	 * @param reponse
	 * @return retorna bites imagen
	 * @throws IOException
	 */
	@RequestMapping(value = "ofertaAcademica/imagen", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getProductoImagen(@RequestParam("id") int id, HttpServletResponse reponse)
			throws IOException {
		Path imagePath = Paths.get(fileUtil.paths[0] + "ofertas/oferta" + id + ".jpg");
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		byte[] img;
		try {
			img = Files.readAllBytes(imagePath);
		} catch (Exception e) {
			img = Files.readAllBytes(Paths.get(fileUtil.paths[0] + "oferta.jpg"));
		}
		return new ResponseEntity<byte[]>(img, headers, HttpStatus.OK);
	}

	/**
	 * Metodo para guardar imagen
	 * 
	 * @param reponse
	 * @param id
	 *            codigo de la oferta
	 * @param estado
	 *            false si solo se quiere validar y true si se quiere validar y
	 *            guardar
	 * @param imagen
	 *            archivo file imagen a guardar
	 * @return mensaje de operación exitosa o archivo invalido
	 * @throws IOException
	 */
	@RequestMapping(value = "/ofertaAcademicaSerImagen/{id}/{estado}", method = RequestMethod.POST)
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> addProductoImagen(HttpServletResponse reponse, @PathVariable("id") int id,
			@PathVariable("estado") boolean estado, @RequestParam("file") MultipartFile imagen) {

		String contentType = imagen.getContentType();
		String extencionFile = contentType.split("/")[1];

		if (extencionFile.equals("png") || extencionFile.equals("jpeg") || extencionFile.equals("jpg")) {
			if (!imagen.isEmpty()) {

				BufferedImage image;
				try {
					image = ImageIO.read(imagen.getInputStream());

					Integer width = image.getWidth();
					Integer height = image.getHeight();

					Float porcWidth = (float) ((width * 1) / 100);
					Float porcHeight = (float) ((height * 1) / 100);
					Float diferencia = porcWidth - porcHeight;

					if (imagen.getBytes().length <= 200000) {
						if (width >= 640 && height >= 425) {
							if (diferencia >= 2 && diferencia <= 2.5) {
								if (!estado) {
									String ruta = fileUtil.SaveFile(imagen, "ofertas/oferta" + id + ".jpg", 0);
									ofertaAcademicaServicio.guardarUrl(id, ruta);
								}
								Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
								return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
							} else {
								return returnMensaje(
										"Tamaño no valido, la imagen debe ser de 640px de ancho y 425px de alto");
							}
						} else {
							return returnMensaje("Tamaño no valido, minimo 640px de ancho y 425px de alto");
						}
					} else {
						return returnMensaje("Excede el peso de la imagen, máximo 200kb");
					}
				} catch (IOException e) {
					e.printStackTrace();
					return returnMensaje("Ocurrio un error al guardar la imagen.");
				}
			} else {
				return returnMensaje("No se ha enviado una imagen");
			}
		} else {
			return returnMensaje("Archivo no valido.");
		}
	}

	/**
	 * Metodo PUT para modificar los datos de un registro de oferta acádemica
	 * 
	 * @param id
	 *            codigo de la oferta acádemica que se quiere modificar
	 * @param ofertaAcademica
	 *            objeto con los datos del registro que se quiere modificar
	 * @return retorna un listado de las asignaturas encontradas
	 */
	@RequestMapping(value = "/ofertaAcademicaSer/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody OfertaAcademica ofertaAcademica) {

		if (id > 0) { 
			String returnModificar = ofertaAcademicaServicio.modificarOfertaAcademica(id, ofertaAcademica);
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
	 * Metodo DELETE para eliminar un registro de oferta acádemica
	 * 
	 * @param id
	 *            codigo del registro que se quiere eliminar
	 * @return retorna un listado de las asignaturas encontradas
	 */
	@RequestMapping(value = "/ofertaAcademicaSer/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {
		String returnEliminar = ofertaAcademicaServicio.eliminarOfertaAcademica(id);
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

	/**
	 * Traer los requisitos adjuntos de la oferta de la inscripcion
	 * 
	 * @param codigoInscripcion
	 * @return
	 */
	@RequestMapping(value = "/consultarRequisitosAdjuntosOferta", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Oferta> getRequisitos(@RequestParam("codigo") int codigoInscripcion, @RequestParam("oferta") int oferta) {
		List<Oferta> ofertaRequisitos = new ArrayList<Oferta>();
		ofertaRequisitos = ofertaDao.consultarRequisitosAdjuntosOferta(codigoInscripcion, oferta);
		return ofertaRequisitos;
	}

	@RequestMapping(value = "/consultarRequisitosAdjuntosInscripcion", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<RequisitosAdjuntos> getRequisitosAdjuntos(@RequestParam("codigo") int codigoInscripcion) {
		System.out.println("Codigo inscripcion: " + codigoInscripcion);
		List<RequisitosAdjuntos> ofertaRequisitos = new ArrayList<RequisitosAdjuntos>();
		ofertaRequisitos = ofertaDao.consultarRequisitosAdjuntosInscripcion(codigoInscripcion);
		return ofertaRequisitos;
	}
}
