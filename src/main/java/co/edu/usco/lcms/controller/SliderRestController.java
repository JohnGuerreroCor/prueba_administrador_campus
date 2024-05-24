/**
 * 
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Slider;
import co.edu.usco.lcms.servicios.SliderServicio;
import co.edu.usco.lcms.utility.FileUtil;
import co.edu.usco.lcms.utility.Respuesta;
import co.edu.usco.lcms.utility.ValidadorParametro;
import co.edu.usco.lcms.utility.ValidadorParametro.TipoValidador;

/**
 * @author jankarlos
 *
 */
@RestController
public class SliderRestController {

	@Autowired
	SliderServicio sliderServicio;

	@Autowired
	FileUtil fileUtil;

	/**
	 * Listar datos de Slider según la estructura que requiere DataTable de
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
	 * @return retorna la lista de UAA segun los parametros enviados
	 */
	
	@RequestMapping(value = "/imagenes-slider/listar", method = RequestMethod.POST,consumes="application/json",headers = "content-type=application/x-www-form-urlencoded")
	//@Secured("ROLE_ADMINISTRADOR_LCMS")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public JSONRespuesta getSlider(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int posicion,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String direccion) {

		JSONRespuesta listSlider = new JSONRespuesta();
		listSlider = sliderServicio.listarTablaSlider(search, start, length, draw, posicion, direccion);
		return listSlider;
	}

	@RequestMapping(value = "/imagenes-slider", method = RequestMethod.POST, headers = "Accept=application/json")
	//@Secured("ROLE_ADMINISTRADOR_LCMS")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public ResponseEntity<Respuesta> adicionar(@RequestBody Slider slider) {

		boolean nombre_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
				slider.getDescripcion(), 200);
		if (!nombre_valido) {
			return returnMensaje("Campo nombre no valido");
		}

		boolean secuencia_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_NUMERO,
				String.valueOf(slider.getSecuencia()), 200);
		if (!secuencia_valido) {
			return returnMensaje("Campo nombre no valido");
		}

		boolean alt_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO, slider.getDescripcion(),
				100);
		if (!alt_valido) {
			return returnMensaje("Campo título no valido");
		}

		String returnInsercion = sliderServicio.agregarSlider(slider);

		if (isNumeric(returnInsercion)) {
			if (!returnInsercion.equals("0")) {
				Respuesta respuesta = new Respuesta();
				respuesta.setMensaje(returnInsercion);
				respuesta.setCodigo(1);
				respuesta.setExito(true);
				respuesta.setBody(slider);
				return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
			} else {
				return returnMensaje("Ocurrio un inconveniente, vuelve a intentarlo mas tarde.");
			}
		} else {
			return returnMensaje(returnInsercion);
		}
	}

	/**
	 * Metodo PUT para modificar un registro de Slider
	 * 
	 * @param id
	 *            codigo de la slider que se quiere modificar
	 * @param slider
	 *            objeto con los datos de la slider que se quiere modificar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/imagenes-slider/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	//@Secured("ROLE_ADMINISTRADOR_LCMS")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody Slider slider) {
		if (id > 0) {
			boolean nombre_valido = ValidadorParametro.validarParametro(TipoValidador.VALIDADOR_TEXTO,
					slider.getDescripcion(), 200);

			if (!nombre_valido) {
				return returnMensaje("Campo nombre no valido");
			}

			String returnModificar = sliderServicio.modificarSlider(id, slider);
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
	 * Metodo DELETE para eliminar un registro de Slider
	 * 
	 * @param id
	 *            codigo de la Slider que se quiere eliminar
	 * @return retorna mensaje de operación exitosa o ocurrio un error.
	 */
	@RequestMapping(value = "/imagenes-slider/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	//@Secured("ROLE_ADMINISTRADOR_LCMS")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {
		String returnEliminar = sliderServicio.eliminarSlider(id);
		if (returnEliminar.equals("OK")) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		} else {
			return returnMensaje(returnEliminar);
		}
	}
	
	@RequestMapping(value = "sliderSerImagen/imagen", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getProductoImagen(@RequestParam("id") int id, HttpServletResponse reponse)
			throws IOException {
		Path imagePath = Paths.get(fileUtil.paths[0] + "/slider/slider" + id + ".jpg");
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		byte[] img;
		try {
			img = Files.readAllBytes(imagePath);
		} catch (Exception e) {
			e.printStackTrace();
			img = Files.readAllBytes(Paths.get(fileUtil.paths[0] + "oferta.jpg"));
		}
		return new ResponseEntity<byte[]>(img, headers, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/sliderSerImagen/{id}/{estado}", method = RequestMethod.POST)
	//@Secured("ROLE_ADMINISTRADOR_LCMS")
	@PreAuthorize("hasRole(${role.administrador.lcms})")
	public ResponseEntity<Respuesta> addSliderImagen(HttpServletResponse reponse, @PathVariable("id") int id,
			@PathVariable("estado") boolean estado, @RequestParam("file") MultipartFile imagen) throws IOException {

		String contentType = imagen.getContentType();
		String extencionFile = contentType.split("/")[1];

		if (extencionFile.equals("png") || extencionFile.equals("jpeg") || extencionFile.equals("jpg")) {
			if (!imagen.isEmpty()) {

				BufferedImage image = ImageIO.read(imagen.getInputStream());
				Integer width = image.getWidth();
				Integer height = image.getHeight();

				Float porcWidth = (float) ((width * 1) / 100);
				Float porcHeight = (float) ((height * 1) / 100);
				Float diferencia = porcWidth - porcHeight;

				if (imagen.getBytes().length <= 600000) {
					if (width >= 1920 && height >= 1080) {
						if (width <= 2020 && height <= 1180) {
							if (diferencia >= 5 && diferencia <= 15) {
								//if(estado) {
									String ruta = fileUtil.SaveFile(imagen, "slider/slider" + id + ".jpg", 0);
									sliderServicio.guardarUrl(id, ruta);
								//}
								Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
								return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
							} else {
								return returnMensaje("Tamaño no valido, máximo 2020px de ancho por 1180px de alto y minimó 1920px de ancho por 1080px de alto.");
							}
						} else {
							return returnMensaje("Tamaño no valido, máximo 2020px de ancho por 1180px de alto y minimó 1920px de ancho por 1080px de alto.");
						}
					} else {
						return returnMensaje("Tamaño no valido, máximo 2020px de ancho por 1180px de alto y minimó 1920px de ancho por 1080px de alto.");
					}
				} else {
					return returnMensaje("Excede el peso de la imagen, máximo 600kb");
				}
			} else {
				return returnMensaje("No se ha enviado una imagen");
			}
		} else {
			return returnMensaje("Archivo no valido.");
		}
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getSliderElements", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Slider> getSliderLista() {
		List<Slider> listOfSlider = new ArrayList<Slider>();
		listOfSlider = sliderServicio.listarSlider();
		return listOfSlider;
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
