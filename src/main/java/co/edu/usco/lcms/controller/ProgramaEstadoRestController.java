/**
 * Clase ProgramaEstadoRestController para gestionar los servicios web de Programa estado
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
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.model.ProgramaEstado;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class ProgramaEstadoRestController {

	@Autowired
	DataSource dataSourceAcademiaInvitado;

	/**
	 * Metodo GET para listar los registros de programa estado
	 * @return retorno lista de programa estado
	 */
	@RequestMapping(value = "/programasEstadoLista", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({"ROLE_ADMINISTRADOR_LCMS","ROLE_ADMINISTRADOR_FACULTAD_LCMS"})
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<ProgramaEstado> getProgramaEstados() {
		List<ProgramaEstado> listaProgramas = new ArrayList<ProgramaEstado>();
		listaProgramas = crearListaProgramaEstado();
		return listaProgramas;
	}

	public List<ProgramaEstado> crearListaProgramaEstado() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);

		String sql = "SELECT p.pre_nombre, p.pre_codigo FROM programa_estado AS p";

		List<ProgramaEstado> listaProgramaEstado = jdbcTemplate.query(sql, new RowMapper<ProgramaEstado>() {

			public ProgramaEstado mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProgramaEstado programa = new ProgramaEstado();

				programa.setCodigo(rs.getString("pre_codigo"));
				programa.setNombre(rs.getString("pre_nombre"));
				return programa;
			}

		});

		return listaProgramaEstado;
	}

}
