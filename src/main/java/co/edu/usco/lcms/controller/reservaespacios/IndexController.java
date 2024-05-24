package co.edu.usco.lcms.controller.reservaespacios;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import co.edu.usco.lcms.utility.Constantes;

@Controller(value="solicitudesIndex")
public class IndexController {
	
	@Autowired
	Constantes constantes;
	
	@RequestMapping(value = "videoconferencia/solicitudes/", method = RequestMethod.GET)
	public ModelAndView getAreas(HttpServletRequest request) {		
		ModelAndView mv = new ModelAndView("lista");
		mv.addObject("constantes", constantes);
		return mv;
	}
	

}
