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

import co.edu.usco.lcms.model.Area;
import co.edu.usco.lcms.model.TipoEscala;

/**
 * @author jankarlos
 *
 */
@Component
public class TipoEscalaDaoJDBCTemplateImpl implements TipoEscalaDao {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.TipoEscalaDao#listarTipoEscala()
	 */
	@Override
	public List<TipoEscala> listarTipoEscala() {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "SELECT tes_codigo, tes_nombre FROM tipo_escala WHERE tes_estado = 1 ORDER BY tes_nombre";

			List<TipoEscala> listaTipoEscala = jdbcTemplate.query(sql, new RowMapper<TipoEscala>() {
				public TipoEscala mapRow(ResultSet rs, int rowNum) throws SQLException {
					TipoEscala tipoEscala = new TipoEscala();
					tipoEscala.setCodigo(rs.getInt("tes_codigo"));
					tipoEscala.setNombre(rs.getString("tes_nombre"));
					return tipoEscala;
				}

			});

			return listaTipoEscala;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
