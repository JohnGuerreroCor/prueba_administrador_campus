package co.edu.usco.lcms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.Matricula;

@Component
public class MatriculaDaoJDBCTemplateImpl implements MatriculaDao {

	@Autowired
	DataSource dataSource;

	Date fechaActual = new Date();
	SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	String cadenaFecha = formato.format(fechaActual);

	@Override
	public boolean agregarMatricula(final Matricula matricula) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String querySql = "INSERT INTO matricula (est_codigo, cal_codigo, mat_fecha, "
					+ "mat_estado, mat_tipo, sippa_calcodigo";
			if (matricula.getCodigoUsuario() > 0) {
				querySql = querySql + ",mat_usuario) VALUES (?, ?, convert(datetime,'" + cadenaFecha + "'), ?, ?, ?, ?)";
			} else {
				querySql = querySql + ")VALUES (?, ?, convert(datetime,'" + cadenaFecha + "'), ?, ?, ?)";
			}
			final String sql = querySql;

			KeyHolder keyHolder = new GeneratedKeyHolder();
			int resultado = jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					pstm.setString(1, matricula.getInscripcion().getEstudiante().getCodigo());
					pstm.setInt(2, matricula.getOferta().getCalendario().getCodigo());
					pstm.setString(3, "A");
					pstm.setInt(4, 1);
					pstm.setString(5, matricula.getOferta().getCalendario().getNombre());
					if (matricula.getCodigoUsuario() > 0) {
						pstm.setInt(6, matricula.getCodigoUsuario());
					}
					return pstm;
				}
			}, keyHolder);

			if (resultado > 0) {
				matricula.setId(keyHolder.getKey().intValue());
				return true;
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean agregarMatriculaCursoActual(int matricula, int curso, int paa) {

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "EXEC CrearMatriculaCursoActualNoFormal ?, ?, ?, ?";

			int resultado = jdbcTemplate.update(sql, matricula, curso, paa, cadenaFecha);
			if (resultado > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.toString());
		}
		return false;
	}

}
