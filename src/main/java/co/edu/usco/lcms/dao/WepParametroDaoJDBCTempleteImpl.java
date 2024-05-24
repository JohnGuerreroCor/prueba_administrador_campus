/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.WebParametro;

/**
 * @author jankarlos
 *
 */
@Component
public class WepParametroDaoJDBCTempleteImpl implements WebParametroDao {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.WebParametroDao#listarWebParametro(int)
	 */
	@Override
	public List<WebParametro> listarWebParametro(String nombre) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = " SELECT wep_codigo, wep_descripcion, wep_valor, wep_nombre "
				+ "FROM web_parametro "
				+ "WHERE wep_nombre = ? ";

		List<WebParametro> listaWebParametro = jdbcTemplate.query(sql, new Object[] { nombre },
				new RowMapper<WebParametro>() {

					public WebParametro mapRow(ResultSet rs, int rowNum) throws SQLException {
						WebParametro webParametro = new WebParametro();
						webParametro.setCodigo(rs.getInt("wep_codigo"));
						webParametro.setNombre(rs.getString("wep_nombre"));
						webParametro.setDescripcion(rs.getString("wep_descripcion"));
						webParametro.setValor(rs.getString("wep_valor"));
						return webParametro;
					}
				});
		return listaWebParametro;
	}
}
