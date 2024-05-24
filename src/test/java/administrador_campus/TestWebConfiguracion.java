package administrador_campus;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
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
@ComponentScan(basePackages = { "co.edu.usco.lcms.utility", "co.edu.usco.lcms.librerias", "co.edu.usco.lcms.dao",
		"co.edu.usco.lcms.servicios" })

@PropertySource(value = "file:/var/configCampus/configCampus.properties")
public class TestWebConfiguracion {

	@Autowired
	Environment environment;

	@Bean(name = "dataSourceAcademiaInvitado")
	public DataSource dataSourceAcademiaInvitado() throws Exception {

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
		basicDataSource.setUsername("fhsm");
		basicDataSource.setPassword("123Fredy:.,");
		basicDataSource.setUrl("jdbc:jtds:sqlserver://172.16.1.85:1410/academia3000_lcms");
		
		basicDataSource.setValidationQuery("SELECT 1");
		return basicDataSource;
		/*
		 * Context ctx = new InitialContext();
		 * 
		 * String dataSourceName = environment.getProperty("dataSource"); return
		 * (DataSource) ctx.lookup(dataSourceName);
		 */
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

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
		resource.setBasename("classpath:messages");
		resource.setDefaultEncoding("UTF-8");
		return resource;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
