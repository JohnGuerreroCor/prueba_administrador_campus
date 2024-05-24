package co.edu.usco.lcms.configuration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import co.edu.usco.lcms.utility.Constantes;
import co.edu.usco.lcms.utility.FileUtil;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "co.edu.usco.lcms")

//@PropertySource(value = "file:/var/configCampus/configCampus.properties")
@PropertySource(value = "file:C:\\var\\configCampus\\configCampus.properties")
public class WebConfiguracion extends WebMvcConfigurerAdapter {

	@Autowired
	Environment environment;

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setRedirectHttp10Compatible(false);
		return viewResolver;
	}

	// public void addResourceHandlers(ResourceHandlerRegistry registry) {
	// registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	// }

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
			"classpath:/resources/", "classpath:/static/", "classpath:/public/" };

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
	}

	@Bean(name = "dataSourceAcademiaInvitado")
	public DataSource dataSourceAcademiaInvitado() throws Exception {
		Context ctx = new InitialContext();

		String dataSourceName = environment.getProperty("dataSource");
		return (DataSource) ctx.lookup(dataSourceName);
	}

	@Bean(name = "constantes")
	public Constantes constantes() {

		Constantes constantes = new Constantes();
		constantes.WEP_MODALIDAD = environment.getProperty("wepModalidad");
		constantes.WEP_NIVEL_ACADEMICO = environment.getProperty("wepNivelAcademico");
		constantes.WEP_NUCLEO = environment.getProperty("wepNucleo");
		constantes.WEP_FORMALIDAD = environment.getProperty("wepFormalidad");
		constantes.WEP_FACULTAD_TIPO = environment.getProperty("wepFaculadUaaTipo");
		constantes.WEP_TIPO_UAA = environment.getProperty("wepTipoUaa");
		constantes.WEP_SALAS = environment.getProperty("wepSalas");
		constantes.WEP_NUMERO_LICENCIAS = environment.getProperty("wepNumeroLicencias");
		constantes.WEP_HORAS = environment.getProperty("wepHoras");
		constantes.WEP_HORARIO = environment.getProperty("wepHorario");
		constantes.WEP_TIPO_OCUPACION = environment.getProperty("wepTipoOcupacion");
		constantes.WEP_ROLE_ADMINISTRADOR_LCMS = environment.getProperty("role.administrador.lcms");
		constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS = environment.getProperty("role.administrador.facultad.lcms");
		constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_LCMS = environment.getProperty("role.admin.videoconferencia.lcms");
		constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS = environment
				.getProperty("role.admin.videoconferencia.facultad.lcms");
		constantes.WEP_ROLE_DOCENTE = environment.getProperty("role.docente");
		constantes.WEP_EDUCACION_VIRTUAL_PERFILES_SISTEMA = environment.getProperty("wepPerfilesSistema");
		constantes.WEP_ROLE_ADMIN_PROG_ACADEMICA_LCMS = environment.getProperty("role.admin.prog.academica.lcms");
		constantes.WEP_ROLE_ADMIN_FACULTAD_PROG_ACADEMICA_LCMS = environment
				.getProperty("role.admin.prog.academica.facultad.lcms");
		constantes.RUTA = environment.getProperty("ruta");
		constantes.RUTA_CAS = environment.getProperty("rutaCas");
		constantes.RUTA_PORTAL = environment.getProperty("rutaServidorPortal");
		constantes.ADOBE_CONNECT_FOLDER_ID = environment.getProperty("adobeConnectFolderId");
		constantes.WEP_ESTADO_ELIMINADO_CURSO = environment.getProperty("wepEstadoEliminadoCurso");
		constantes.WEP_ESTADO_ELIMINADO_OFERTA = environment.getProperty("wepEstadoEliminadoOferta"); 
		constantes.WEP_TIPO_ESPACIO_VIRTUAL = environment.getProperty("wepTipoEspacio"); 
		constantes.WEP_CODIGO_HORA_FIN = environment.getProperty("wepCodigoHoraFin");
		return constantes;
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getMultipartResolver() {
		return new CommonsMultipartResolver();
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
		resource.setBasename("classpath:messages");
		resource.setDefaultEncoding("UTF-8");
		return resource;
	}

	@Bean(name = "fileUtil")
	public FileUtil getFileUtil() {
		String[] pahts = { environment.getProperty("imagePath"), environment.getProperty("certificadosPath") };
		return new FileUtil(pahts);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
