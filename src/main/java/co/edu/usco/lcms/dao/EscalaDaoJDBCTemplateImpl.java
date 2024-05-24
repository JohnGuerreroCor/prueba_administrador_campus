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

import co.edu.usco.lcms.model.Escala;
import co.edu.usco.lcms.model.TipoEscala;

/**
 * @author jankarlos
 *
 */
@Component
public class EscalaDaoJDBCTemplateImpl implements EscalaDao {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.EscalaDao#listaEscala()
	 */
	@Override
	public List<Escala> listaEscala(int codigoTipo) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "SELECT e.esc_codigo, e.esc_nombre, e.esc_nota_min, e.esc_nota_max, te.tes_codigo, te.tes_nombre "
					+ " FROM escala e INNER JOIN tipo_escala te ON (te.tes_codigo = e.tes_codigo AND te.tes_estado = 1 AND e.esc_estado = 1)";
			if (codigoTipo > 0) {
				sql = sql + " WHERE te.tes_codigo = " + codigoTipo;
			}

			List<Escala> listaEscala = jdbcTemplate.query(sql, new RowMapper<Escala>() {
				public Escala mapRow(ResultSet rs, int rowNum) throws SQLException {
					Escala escala = new Escala();
					escala.setCodigo(rs.getInt("esc_codigo"));
					escala.setNombre(rs.getString("esc_nombre"));
					escala.setNotaMinima(rs.getFloat("esc_nota_min"));
					escala.setNotaMaxima(rs.getFloat("esc_nota_max"));

					TipoEscala tipoEscala = new TipoEscala();
					tipoEscala.setCodigo(rs.getInt("tes_codigo"));
					tipoEscala.setNombre(rs.getString("tes_nombre"));
					escala.setTipoEscala(tipoEscala);

					return escala;
				}

			});

			return listaEscala;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
