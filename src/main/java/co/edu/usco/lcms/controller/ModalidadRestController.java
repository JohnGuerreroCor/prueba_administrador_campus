/**
 * Clase ModalidadRestController para gestionar los servicios web de Modalidad
 */
package co.edu.usco.lcms.controller;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import co.edu.usco.lcms.dao.WebParametroDao;
import co.edu.usco.lcms.model.Modalidad;
import co.edu.usco.lcms.utility.Constantes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */

@RestController
public class ModalidadRestController {

	@Autowired
	DataSource dataSourceAcademiaInvitado;

	@Autowired
	WebParametroDao webParametroDao;
	
	@Autowired
	Constantes constantes;

	
	/**
	 * Metodo GET para listar las Modalidad
	 * @return retorna en una lista los datos de modalidad
	 */
	@RequestMapping(value = "/modalidadLista", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Modalidad> getModalidadLista() {
		List<Modalidad> listOfModalidad = new ArrayList<Modalidad>();
		listOfModalidad = crearListaModalidad();
		return listOfModalidad;
	}

	public List<Modalidad> crearListaModalidad() {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);
		String valorParamentro = webParametroDao.listarWebParametro(constantes.WEP_MODALIDAD).get(0).getValor();
		String sql = "select m.mod_codigo, m.mod_nombre from modalidad m ";
		sql = sql + " WHERE mod_codigo in (" + valorParamentro + ") order by m.mod_nombre";
		List<Modalidad> listaModalidad = jdbcTemplate.query(sql, new RowMapper<Modalidad>() {

			public Modalidad mapRow(ResultSet rs, int rowNum) throws SQLException {
				Modalidad modalidad = new Modalidad();
				modalidad.setCodigo(rs.getInt("mod_codigo"));
				modalidad.setNombre(rs.getString("mod_nombre"));

				return modalidad;
			}

		});
		return listaModalidad;
	}
}