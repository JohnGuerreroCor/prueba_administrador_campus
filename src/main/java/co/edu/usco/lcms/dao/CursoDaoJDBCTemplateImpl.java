package co.edu.usco.lcms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.utility.Constantes;

@Component
public class CursoDaoJDBCTemplateImpl implements CursoDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	Constantes constantes;

	@Autowired
	WebParametroDao webParametroDao;

	@Override
	public Curso buscarCurso(int asi_codigo, int pla_codigo, int cal_codigo) {
		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "SELECT cur_codigo " + "FROM curso " + "WHERE asi_codigo=" + asi_codigo + " AND pla_codigo = "
					+ pla_codigo + " AND cal_codigo = " + cal_codigo;

			System.out.println(sql);
			List<Curso> listaCurso = jdbcTemplate.query(sql, new RowMapper<Curso>() {

				public Curso mapRow(ResultSet rs, int rowNum) throws SQLException {
					Curso curso = new Curso();
					curso.setCodigo(rs.getInt("cur_codigo"));
					return curso;
				}

			});

			if (listaCurso.size() > 0) {
				return listaCurso.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String updateCurso() {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// update
		String sql = "UPDATE curso_noformal SET cal_codigo=? WHERE cur_codigo=?";
		int resultado = jdbcTemplate.update(sql, 202, 157671);

		if (resultado > 0) {
			return "ok";
		}
		return "error";
	}

}
