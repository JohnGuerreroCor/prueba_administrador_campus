package co.edu.usco.lcms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.configuration.UscoGrantedAuthority;
import co.edu.usco.lcms.configuration.User;
import co.edu.usco.lcms.model.Estudiante;
import co.edu.usco.lcms.model.Persona;

@Component
public class EstudianteDaoJDBCTemplateImpl implements EstudianteDao {

	@Autowired
	DataSource dataSource;

	@Override
	public boolean guardarEstudiante(final Estudiante estudiante) {

		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			int uid = user.getId();

			Date fechaActual = new Date();
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
			String cadenaFecha = formato.format(fechaActual);

			SimpleDateFormat formatoAnio = new SimpleDateFormat("yyyy");
			String anio = formatoAnio.format(fechaActual);

			String codigoEstudiante = anio + "N" + estudiante.getInscripcion().getId();

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			int codPersona = 0;
			int codTercero = 0;

			if (estudiante.getPersona().isTercero() == false) {
				codPersona = estudiante.getPersona().getId();
			} else {
				codTercero = estudiante.getPersona().getId();
			}
			

			String sql = "INSERT INTO estudiante (est_codigo, per_codigo, ter_codigo, est_fecha_ingreso, "
					+ "pro_codigo, ese_codigo, ins_codigo, est_registro_egresado, est_uid)"
					+ " VALUES (?, ?, ?, getDate(), ?, ?, ?, ?,?)";
			int resultado = jdbcTemplate.update(sql, codigoEstudiante, codPersona, codTercero,
					estudiante.getInscripcion().getOferta().getPrograma().getCodigo(), 1,
					estudiante.getInscripcion().getId(), 0, uid);

			if (resultado > 0) {
				estudiante.setCodigo(codigoEstudiante);
				return true;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Estudiante consultarEstudiante(long perCodigo) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "SELECT est_codigo " + "FROM estudiante " + "WHERE per_codigo = " + perCodigo;

			List<Estudiante> listaEstudiante = jdbcTemplate.query(sql, new RowMapper<Estudiante>() {

				public Estudiante mapRow(ResultSet rs, int rowNum) throws SQLException {
					Estudiante estudiante = new Estudiante();
					estudiante.setCodigo(rs.getString("est_codigo"));
					return estudiante;
				}
			});
			if (listaEstudiante.size() > 0) {
				return listaEstudiante.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Estudiante> buscarEstudiantesCurso(int curCodigo) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "SELECT e.est_codigo, p.per_codigo, p.per_nombre, p.per_apellido, "
					+ " p.per_identificacion, p.per_email, p.per_email_interno,m.mat_codigo, mc.mac_codigo, mc.cur_codigo"
					+ " FROM dbo.estudiante AS e " + " INNER JOIN dbo.persona AS p ON e.per_codigo = p.per_codigo "
					+ " INNER JOIN dbo.matricula AS m ON e.est_codigo = m.est_codigo "
					+ " INNER JOIN dbo.matricula_curso_actual AS mc ON m.mat_codigo = mc.mat_codigo "
					+ " WHERE mc.cur_codigo = " + curCodigo;

			List<Estudiante> listaEstudiante = jdbcTemplate.query(sql, new RowMapper<Estudiante>() {

				public Estudiante mapRow(ResultSet rs, int rowNum) throws SQLException {

					Estudiante estudiante = new Estudiante();
					estudiante.setCodigo(rs.getString("est_codigo"));
					Persona persona = new Persona();
					persona.setNombre(rs.getString("per_nombre"));
					persona.setApellido(rs.getString("per_apellido"));
					persona.setEmailPreInscripcion(rs.getString("per_email_interno"));
					persona.setEmail(rs.getString("per_email"));
					persona.setCodigo(rs.getInt("per_codigo"));
					persona.setIdentificacion(rs.getString("per_identificacion"));
					estudiante.setPersona(persona);

					return estudiante;
				}
			});
			return listaEstudiante;
		} catch (Exception e) {
			return null;
		}
	}
}
