package co.edu.usco.lcms.dao.proacademica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.Dia;

/**
 * 
 * @author jankarlos
 *
 */
@Component
public class DiaDaoJDBCTemplate implements DiaDao {

	@Autowired
	DataSource dataSource;

	@Override
	public List<Dia> listaDia() {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = " SELECT dia_posicion_semana, dia_codigo, dia_nombre FROM dia";

		List<Dia> listaDia = jdbcTemplate.query(sql, new RowMapper<Dia>() {

			public Dia mapRow(ResultSet rs, int rowNum) throws SQLException {

				Dia dia = new Dia();
				dia.setCodigo(rs.getInt("dia_codigo"));
				dia.setNombre(rs.getString("dia_nombre"));
				dia.setPosicion(rs.getInt("dia_posicion_semana"));

				return dia;
			}

		});
		return listaDia;
	}
}
