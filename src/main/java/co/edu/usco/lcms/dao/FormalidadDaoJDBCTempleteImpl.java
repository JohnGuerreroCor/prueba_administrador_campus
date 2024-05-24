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

import co.edu.usco.lcms.model.Formalidad;

/**
 * @author jankarlos
 *
 */
@Component
public class FormalidadDaoJDBCTempleteImpl implements FormalidadDao {

	@Autowired
	DataSource dataSource;
	/* (non-Javadoc)
	 * @see co.edu.usco.lcms.dao.FormalidadDao#listarFormalidad(int)
	 */
	@Override
	public List<Formalidad> listarFormalidad() {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT f.for_codigo, f.for_nombre "
				+ "FROM formalidad f "
				+ "ORDER BY f.for_nombre";
		List<Formalidad> listaNivelAcademico = jdbcTemplate.query(sql, new RowMapper<Formalidad>() {

			public Formalidad mapRow(ResultSet rs, int rowNum) throws SQLException {
				Formalidad formalidad = new Formalidad();
				formalidad.setCodigo(rs.getInt("for_codigo"));
				formalidad.setNombre(rs.getString("for_nombre"));

				return formalidad;
			}

		});
		return listaNivelAcademico;
	}

}
