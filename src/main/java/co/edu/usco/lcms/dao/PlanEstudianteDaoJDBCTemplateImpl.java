package co.edu.usco.lcms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.PlanEstudiante;

@Component
public class PlanEstudianteDaoJDBCTemplateImpl implements PlanEstudianteDao {

	@Autowired
	DataSource dataSource;

	@Override
	public boolean agregarPlanEstudiante(final PlanEstudiante planEstudiante) {

		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			final String sql = "INSERT INTO plan_estudiante (est_codigo, pla_codigo, ple_estado) " + "VALUES (?, ?, ?)";
			KeyHolder keyHolder = new GeneratedKeyHolder();
			int resultado = jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					pstm.setString(1, planEstudiante.getInscripcion().getEstudiante().getCodigo());
					pstm.setInt(2, planEstudiante.getPlanAcademico().getCodigo());
					pstm.setInt(3, 1);
					return pstm;
				}
			}, keyHolder);

			if (resultado > 0) {
				planEstudiante.setId(keyHolder.getKey().intValue());
				return true;
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

}
