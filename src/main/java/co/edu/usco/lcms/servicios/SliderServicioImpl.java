/**
 * 
 */
package co.edu.usco.lcms.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.SliderDao;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Slider;

/**
 * @author jankarlos
 *
 */
@Component
public class SliderServicioImpl implements SliderServicio {

	@Autowired
	SliderDao sliderDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.servicios.SliderServicio#agregarSlider(co.edu.usco.lcms.model.
	 * Slider)
	 */
	@Override
	public String agregarSlider(Slider slider) {
		// TODO Auto-generated method stub
		if (slider.getDescripcion() != null && slider.getSecuencia() > 0 && slider.getAlt() != null) {
			return guardar(slider);
		} else {
			return "Por favor llenar todos los campos del formulario.";
		}
	}

	
	public String guardar(Slider slider) {
		int returnInsercion = sliderDao.agregarSlider(slider);

		if (returnInsercion > 0) {
			return ""+returnInsercion;
		} else {
			return "0";
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.SliderServicio#modificarSlider(int,
	 * co.edu.usco.lcms.model.Slider)
	 */
	@Override
	public String modificarSlider(int id, Slider slider) {
		// TODO Auto-generated method stub
		boolean returnModificar = sliderDao.modificarSlider(id, slider);
		if (returnModificar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, vuelve a intentarlo";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.SliderServicio#eliminarSlider(int)
	 */
	@Override
	public String eliminarSlider(int id) {
		// TODO Auto-generated method stub
		boolean returnEliminar = sliderDao.eliminarSlider(id);
		if (returnEliminar) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, no se puede eliminar este registro.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.SliderServicio#listarSlider()
	 */
	@Override
	public List<Slider> listarSlider() {
		// TODO Auto-generated method stub
		return sliderDao.listarSlider();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.servicios.SliderServicio#listarTablaSlider(java.lang.
	 * String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarTablaSlider(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		return sliderDao.listarTablaSlider(search, start, length, draw, posicion, direccion);
	}

	@Override
	public String guardarUrl(int id, String ruta) {
		// TODO Auto-generated method stub
		boolean returnUrl = sliderDao.guardarUrl(id, ruta);
		if (returnUrl) {
			return "OK";
		} else {
			return "Ocurrio un inconveniente, no se puede eliminar este registro.";
		}
	}

}
