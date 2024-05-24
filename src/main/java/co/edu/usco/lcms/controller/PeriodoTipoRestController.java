/**
 * Clase PeriodoTipoRestController para gestionar los servicios web de periodo tipo
 */
package co.edu.usco.lcms.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.model.PeriodoTipo;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class PeriodoTipoRestController {
	@Autowired
	DataSource dataSourceAcademiaInvitado;

	/**
	 * Metodo GET para listar los tipos de periodo
	 * @param id codigo para listar los tipos de periodo
	 * @return retorna la lista de tipos de periodo segun el parametro de busqueda
	 */
	@RequestMapping(value = "/PeriodoTipo/Listar", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({"ROLE_ADMINISTRADOR_LCMS","ROLE_ADMINISTRADOR_FACULTAD_LCMS"})
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<PeriodoTipo> getPeridoTipo(@RequestParam("id") int id) {
		List<PeriodoTipo> listPeriodoTipo = new ArrayList<PeriodoTipo>();
		listPeriodoTipo = crearListaPeriodoTipo(id);
		return listPeriodoTipo;
	}

	/**
	 * Metodo GET para listar tipos de periodo
	 * @return retornar la lista de tipos de periodo
	 */
	@RequestMapping(value = "/PeriodoTipo/Listar/", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({"ROLE_ADMINISTRADOR_LCMS","ROLE_ADMINISTRADOR_FACULTAD_LCMS"})
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<PeriodoTipo> getPeridoTipo() {
		List<PeriodoTipo> listPeriodoTipo = new ArrayList<PeriodoTipo>();
		listPeriodoTipo = crearListaPeriodoTipo(0);
		return listPeriodoTipo;
	}

	public List<PeriodoTipo> crearListaPeriodoTipo(int id) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);

		Object[] obj = new Object[] {};
		
		String sql = "select pet_nombre, pet_codigo from periodo_tipo";
		if (id != 0) {
			sql = sql + " where pet_codigo = ?";
			obj = new Object[] { id };
		}
		sql = sql + " order by pet_nombre";

		List<PeriodoTipo> listaTipoPeriodo = jdbcTemplate.query(sql, obj, new RowMapper<PeriodoTipo>() {

			public PeriodoTipo mapRow(ResultSet rs, int rowNum) throws SQLException {
				PeriodoTipo periodoTipo = new PeriodoTipo();

				periodoTipo.setCodigo(rs.getInt("pet_codigo"));
				periodoTipo.setNombre(rs.getString("pet_nombre"));

				return periodoTipo;
			}

		});
		return listaTipoPeriodo;
	}

}
