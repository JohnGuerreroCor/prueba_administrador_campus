/**
 * 
 */
package co.edu.usco.lcms.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.utility.Constantes;

/**
 * @author jankarlos
 *
 */
@RestController
public class Salir {

	@Autowired
	Constantes constantes;

	@RequestMapping(value = "/app.salir", method = RequestMethod.GET)
	public void logoutPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		// response.sendRedirect("j_spring_cas_security_logout");
		response.sendRedirect(constantes.RUTA_CAS + "cas/logout?service=" + constantes.RUTA_PORTAL);

	}
}
