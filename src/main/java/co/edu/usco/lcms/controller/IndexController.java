package co.edu.usco.lcms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import co.edu.usco.lcms.configuration.UscoGrantedAuthority;
import co.edu.usco.lcms.configuration.User;
import co.edu.usco.lcms.utility.Constantes;


@Controller
public class IndexController {

	@Autowired
	Constantes constantes;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getAreas(HttpServletRequest request) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
		}

		ModelAndView mv = new ModelAndView("index");
		mv.addObject("usuario", user);
		mv.addObject("constantes", constantes);		
		return mv;
	}
	
	
}
