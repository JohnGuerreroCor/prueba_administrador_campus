package co.edu.usco.lcms.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import co.edu.usco.lcms.dao.UsuarioDao;

public class UserDetailServiceUsco implements AuthenticationUserDetailsService<Authentication> {

	@Autowired
	UsuarioDao usuarioDao;

	public UserDetails loadUserDetails(Authentication auth) throws UsernameNotFoundException {
		User user = usuarioDao.obtenerPorNombre(auth.getName());
		return user;
	}

}