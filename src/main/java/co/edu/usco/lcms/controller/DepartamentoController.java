/**
 * Clase DepartamentoController para getionar el servicio web de listar los departamentos
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

import co.edu.usco.lcms.model.Departamento;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class DepartamentoController {

	@Autowired
	DataSource dataSourceAcademiaInvitado;

	/**
	 * Metodo GET de departamento para listarlos segun un paramentro de busqueda
	 * 
	 * @param idPais
	 *            codigo de país con el cual se quieren listar los paises
	 * @return retorna en una lista los paises encontrados según el parametro de
	 *         busqueda idPais
	 */
	@RequestMapping(value = "/departamento", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Departamento> getDepartamento(@RequestParam("idPais") int idPais) {
		List<Departamento> listOfDepartamento = new ArrayList<Departamento>();
		listOfDepartamento = crearListaDepartamento(idPais);
		return listOfDepartamento;
	}

	public List<Departamento> crearListaDepartamento(int idPais) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);

		String sql = "select dep_codigo, dep_nombre, dane_codigo, pai_codigo from departamento";
		sql = sql + " where pai_codigo = " + idPais + " order by dep_nombre";
		List<Departamento> listaDepartamento = jdbcTemplate.query(sql, new RowMapper<Departamento>() {

			public Departamento mapRow(ResultSet rs, int rowNum) throws SQLException {
				Departamento departamento = new Departamento();

				departamento.setCodigo(rs.getInt("dep_codigo"));
				departamento.setNombre(rs.getString("dep_nombre"));
				departamento.setDane(rs.getString("dane_codigo"));
				departamento.setPais(rs.getInt("pai_codigo"));

				return departamento;
			}

		});
		return listaDepartamento;
	}
}
