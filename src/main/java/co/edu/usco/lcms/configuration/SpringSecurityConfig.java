package co.edu.usco.lcms.configuration;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
///@PropertySource(value = "file:/var/configCampus/configCampus.properties")
@PropertySource(value = "file:C:\\var\\configCampus\\configCampus.properties")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	Environment environment;

	@Bean(name = "serviceProperties")
	public ServiceProperties serviceProperties() {
		ServiceProperties properties = new ServiceProperties();
		properties.setService(environment.getProperty("ruta") + "administrador_campus/j_spring_cas_security_check");
		// properties.setService(environment.getProperty("ruta") + "administrador_campus/login/cas");
		properties.setSendRenew(false);
		return properties;
	}

	@Bean
	public CasAuthenticationProvider casAuthenticationProvider() {
		CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
		casAuthenticationProvider.setAuthenticationUserDetailsService(authenticationUserDetailsService());
		casAuthenticationProvider.setServiceProperties(serviceProperties());
		casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
		casAuthenticationProvider.setKey("an_id_for_this_auth_provider_only");
		return casAuthenticationProvider;
	}

	@Bean
	public AuthenticationUserDetailsService authenticationUserDetailsService() {
		return (AuthenticationUserDetailsService) new UserDetailServiceUsco();
	}

	@Bean
	public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
		return new Cas20ServiceTicketValidator(environment.getProperty("rutaCas") + "cas");
	}

	@Bean
	public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
		CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
		// casAuthenticationFilter.setFilterProcessesUrl("administrador_campus/login/cas");
		casAuthenticationFilter.setAuthenticationManager(authenticationManager());
		return casAuthenticationFilter;
	}

	@Bean
	public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
		CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
		casAuthenticationEntryPoint.setLoginUrl(environment.getProperty("rutaCas") + "cas/login");
		casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
		return casAuthenticationEntryPoint;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// http.requiresChannel().anyRequest().requiresSecure();
		http.addFilter(casAuthenticationFilter());
		http.exceptionHandling().authenticationEntryPoint(casAuthenticationEntryPoint());

		http.logout();
		http.addFilterBefore(new SingleSignOutFilter(), LogoutFilter.class);
		LogoutFilter logoutFilter = new LogoutFilter(environment.getProperty("rutaCas") + "cas/logout",
				new SecurityContextLogoutHandler());
		logoutFilter.setFilterProcessesUrl("/j_spring_cas_security_logout");

		http.addFilterBefore(logoutFilter, CasAuthenticationFilter.class);
		http.authorizeRequests()
				// .antMatchers(HttpMethod.GET, "/app/**").authenticated()
				.antMatchers(HttpMethod.GET, "/ofertaAcademica/imagen/**").permitAll()
				.antMatchers(HttpMethod.GET, "/getSliderElements/**").permitAll()
				.antMatchers(HttpMethod.GET, "/sliderSerImagen/imagen/**").permitAll()
				.antMatchers(HttpMethod.GET, "/app/assets/**").authenticated()
				.antMatchers(HttpMethod.GET, "/app/controllers/menu/**").authenticated()
				.antMatchers(HttpMethod.GET, "/app/controllers/**")
				.access("hasRole('" + environment.getProperty("role.administrador.lcms") + "') or hasRole('"
						+ environment.getProperty("role.administrador.facultad.lcms") + "') or hasRole('"
						+ environment.getProperty("role.admin.videoconferencia.lcms") + "') or hasRole('"
						+ environment.getProperty("role.admin.videoconferencia.facultad.lcms") + "') or hasRole('"
						+ environment.getProperty("role.docente") + "') or hasRole('"
						+ environment.getProperty("role.admin.prog.academica.lcms") + "') or hasRole('"
						+ environment.getProperty("role.admin.prog.academica.facultad.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/asignatura/**")
				.access("hasRole('" + environment.getProperty("role.administrador.lcms") + "') or hasRole('"
						+ environment.getProperty("role.administrador.facultad.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/convenio/**")
				.access("hasRole('" + environment.getProperty("role.administrador.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/listaResoluciones/**")
				.access("hasRole('" + environment.getProperty("role.administrador.lcms") + "') or hasRole('"
						+ environment.getProperty("role.administrador.facultad.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/modalidad/**")
				.access("hasRole('" + environment.getProperty("role.administrador.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/nbc/**")
				.access("hasRole('" + environment.getProperty("role.administrador.lcms") + "')")
				
				.antMatchers(HttpMethod.GET, "/consultarPersona/**")
				.access("hasRole('" + environment.getProperty("role.administrador.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/persona-correo/**")
				.access("hasRole('" + environment.getProperty("role.administrador.lcms") + "')")
				
				.antMatchers(HttpMethod.GET, "/nivelAcademico/**")
				.access("hasRole('" + environment.getProperty("role.administrador.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/oferta/**")
				.access("hasRole('" + environment.getProperty("role.administrador.lcms") + "') or hasRole('"
						+ environment.getProperty("role.administrador.facultad.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/perfil/**")
				.access("hasRole('" + environment.getProperty("role.administrador.lcms") + "') or hasRole('"
						+ environment.getProperty("role.administrador.facultad.lcms") + "') or hasRole('"
						+ environment.getProperty("role.admin.videoconferencia.lcms") + "') or hasRole('"
						+ environment.getProperty("role.admin.videoconferencia.facultad.lcms") + "') or hasRole('"
						+ environment.getProperty("role.docente") + "') or hasRole('"
						+ environment.getProperty("role.admin.prog.academica.lcms") + "') or hasRole('"
						+ environment.getProperty("role.admin.prog.academica.facultad.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/planAcademico/**")
				.access("hasRole('" + environment.getProperty("role.administrador.lcms") + "') or hasRole('"
						+ environment.getProperty("role.administrador.facultad.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/programa/**")
				.access("hasRole('" + environment.getProperty("role.administrador.lcms") + "') or hasRole('"
						+ environment.getProperty("role.administrador.facultad.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/UAA/**")
				.access("hasRole('" + environment.getProperty("role.administrador.lcms") + "') or hasRole('"
						+ environment.getProperty("role.administrador.facultad.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/videoconferencia/agendaSala/**")
				.access("hasRole('" + environment.getProperty("role.admin.videoconferencia.lcms") + "') or hasRole('"
						+ environment.getProperty("role.admin.videoconferencia.facultad.lcms") + "') or hasRole('"
						+ environment.getProperty("role.docente") + "')")
				.antMatchers(HttpMethod.GET, "/videoconferencia/asignarHorasFacultad/**")
				.access("hasRole('" + environment.getProperty("role.admin.videoconferencia.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/videoconferencia/configuraciones/**")
				.access("hasRole('" + environment.getProperty("role.admin.videoconferencia.lcms") + "') or hasRole('"
						+ environment.getProperty("role.admin.videoconferencia.facultad.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/videoconferencia/solicitudes/**")
				.access("hasRole('" + environment.getProperty("role.admin.videoconferencia.lcms") + "') or hasRole('"
						+ environment.getProperty("role.admin.videoconferencia.facultad.lcms") + "') or hasRole('"
						+ environment.getProperty("role.docente") + "')")
				.antMatchers(HttpMethod.GET, "/prog-academica/cursos/**")
				.access("hasRole('" + environment.getProperty("role.admin.prog.academica.lcms") + "') or hasRole('"
						+ environment.getProperty("role.admin.prog.academica.facultad.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/prog-academica/horarios/**")
				.access("hasRole('" + environment.getProperty("role.admin.prog.academica.lcms") + "') or hasRole('"
						+ environment.getProperty("role.admin.prog.academica.facultad.lcms") + "')")
				.antMatchers(HttpMethod.GET, "/view/**").authenticated().anyRequest().authenticated().and().csrf()
				.disable();
	}

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(casAuthenticationProvider());
	}
}