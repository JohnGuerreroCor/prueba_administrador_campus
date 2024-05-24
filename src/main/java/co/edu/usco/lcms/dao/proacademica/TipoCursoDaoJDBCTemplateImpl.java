/**
 * 
 */
package co.edu.usco.lcms.dao.proacademica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.TipoCurso;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author jankarlos
 *
 */
@Component
public class TipoCursoDaoJDBCTemplateImpl implements TipoCursoDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	Constantes constantes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.proacademica.TipoCursoDao#listarTipoCurso()
	 */
	@Override
	public List<TipoCurso> listarTipoCurso() {
		// TODO Auto-generated method stub

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = " SELECT tc.tic_codigo, tc.tic_nombre, tc.tic_nombre_corto FROM tipo_curso tc";

		List<TipoCurso> listaTipoCurso = jdbcTemplate.query(sql, new RowMapper<TipoCurso>() {

			public TipoCurso mapRow(ResultSet rs, int rowNum) throws SQLException {

				TipoCurso tipoCurso = new TipoCurso();
				tipoCurso.setCodigo(rs.getInt("tic_codigo"));
				tipoCurso.setNombre(rs.getString("tic_nombre"));
				tipoCurso.setNombreCorto(rs.getString("tic_nombre_corto"));

				return tipoCurso;
			}

		});
		return listaTipoCurso;
	}
}
