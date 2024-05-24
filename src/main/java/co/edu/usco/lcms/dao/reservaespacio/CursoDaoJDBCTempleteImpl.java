/**
 * 
 */
package co.edu.usco.lcms.dao.reservaespacio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.Asignatura;
import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.model.Periodo;
import co.edu.usco.lcms.model.Persona;
import co.edu.usco.lcms.model.reservaespacios.UaaPersonal;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@Component
public class CursoDaoJDBCTempleteImpl implements CursoDao {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.reservaespacio.CursoDao#listarCurso(java.lang.
	 * String)
	 */
	@Override
	public List<Curso> listarCurso(int docente, String criterio) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT distinct top 10 c.cur_codigo, c.cur_fecha_inicio, c.cur_semanas, c.cur_grupo, a.asi_nombre, "
				+ "a.asi_nombre_corto, a.asi_codigo, ca.cal_codigo, ca.cal_nombre, up.uap_codigo, up.per_codigo as persona,"
				+ " p.per_codigo, p.per_nombre, p.per_fecha_inicio, p.per_fecha_fin, p.per_periodo";
		sql = sql + " FROM v_espacio_ocupacion_virtual v ";
		sql = sql + " INNER JOIN curso c ON v.eso_actividad = c.cur_codigo";
		sql = sql + " INNER JOIN calendario ca ON c.cal_codigo = ca.cal_codigo";
		sql = sql + " INNER JOIN uaa_personal up ON v.uap_codigo = up.uap_codigo";
		sql = sql + " INNER JOIN asignatura a ON c.asi_codigo = a.asi_codigo ";
		sql = sql + " INNER JOIN periodo as p on ca.per_codigo = p.per_codigo "
				+ " where (? between per_fecha_inicio and per_fecha_fin) "
				+ " and (a.asi_nombre like ? or c.cur_grupo like ?) and up.per_codigo = ? order by a.asi_nombre desc";

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		List<Curso> listaCurso = jdbcTemplate.query(sql,
				new Object[] { dateFormat.format(date), "%" + criterio + "%", "%" + criterio + "%", docente },
				new RowMapper<Curso>() {

					public Curso mapRow(ResultSet rs, int rowNum) throws SQLException {

						Curso curso = new Curso();
						curso.setCodigo(rs.getInt("cur_codigo"));
						curso.setSemanas(rs.getInt("cur_semanas"));
						curso.setFechaInicio(rs.getDate("cur_fecha_inicio"));
						curso.setGrupo(rs.getString("cur_grupo"));

						Asignatura asignatura = new Asignatura();
						asignatura.setCodigo(rs.getInt("asi_codigo"));
						asignatura.setNombre(rs.getString("asi_nombre"));
						asignatura.setNombreCorto(rs.getString("asi_nombre_corto"));
						curso.setAsignatura(asignatura);

						Calendario calendario = new Calendario();
						calendario.setCodigo(rs.getInt("cal_codigo"));
						calendario.setNombre(rs.getString("cal_nombre"));
						curso.setCalendario(calendario);

						UaaPersonal uaaPersonal = new UaaPersonal();
						uaaPersonal.setCodigo(rs.getInt("uap_codigo"));

						Persona persona = new Persona();
						persona.setCodigo(rs.getInt("persona"));
						uaaPersonal.setPersona(persona);
						curso.setUaaPersonal(uaaPersonal);

						Periodo periodo = new Periodo();
						periodo.setCodigo(rs.getInt("per_codigo"));
						periodo.setNombre(rs.getString("per_nombre"));

						return curso;
					}

				});
		return listaCurso;
	}

}
