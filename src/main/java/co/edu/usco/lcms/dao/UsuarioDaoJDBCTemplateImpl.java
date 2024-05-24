package co.edu.usco.lcms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.configuration.UscoGrantedAuthority;
import co.edu.usco.lcms.configuration.User;
import co.edu.usco.lcms.model.Persona;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.utility.Constantes;

@Component
public class UsuarioDaoJDBCTemplateImpl implements UsuarioDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	Constantes constantes;

	public User obtenerPorNombre(final String username) throws UsernameNotFoundException {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String codigosPerfiles = webParametroDao
					.listarWebParametro(constantes.WEP_EDUCACION_VIRTUAL_PERFILES_SISTEMA).get(0).getValor();
			// quinchana

			String sql = "SELECT distinct p.per_codigo, p.per_nombre, p.per_apellido, p.per_genero, p.per_email, p.per_identificacion, ugeneral.uid, ugeneral.up, ugeneral.us, ugeneral.sys, u.uaa_codigo, uaa_nombre, ug.usg_grupo "
					+ "FROM persona p " + "INNER JOIN usuario_general ugeneral ON ugeneral.up = p.per_codigo "
					+ "INNER JOIN usuario_grupo ug ON ug.usg_usuario = ugeneral.uid "
					+ "INNER JOIN uaa u ON ug.usg_uaa = u.uaa_codigo " + "WHERE ugeneral.us = ? AND ug.usg_grupo in ("
					+ codigosPerfiles + ") AND ug.usg_estado = 1;";
			// local
			/*
			 * String sql =
			 * "SELECT u.usu_codigo, p.per_codigo, p.per_nombre, p.per_email, "
			 * +
			 * " p.per_apellido, p.per_identificacion, u.usu_estado FROM usuario u "
			 * +
			 * " INNER JOIN persona p ON p.per_codigo = u.usu_persona WHERE u.usu_login = ? "
			 * ;
			 */
			List<User> user = jdbcTemplate.query(sql, new Object[] { username }, new RowMapper<User>() {

				public User mapRow(ResultSet rs, int arg1) throws SQLException {
					Persona persona = new Persona();
					persona.setCodigo(rs.getInt("per_codigo"));
					persona.setNombre(rs.getString("per_nombre"));
					persona.setEmail(rs.getString("per_email"));
					persona.setApellido(rs.getString("per_apellido"));
					persona.setIdentificacion(rs.getString("per_identificacion"));

					// Hiperconvergente

					User user = new User(username, "", listarRoles(rs.getInt("uid")));
					user.setId(rs.getInt("uid"));

					// local
					/*
					 * User user = new User(username, "",
					 * listarRoles(rs.getInt("usu_codigo")));
					 * user.setId(rs.getInt("usu_codigo"));
					 */
					user.setPersona(persona);

					return user;
				}

			});

			if (user.isEmpty())
				throw new UsernameNotFoundException("Usuario " + username + " no encontrado");
			return user.get(0);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<UscoGrantedAuthority> listarRoles(int usuario) {

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String codigosPerfiles = webParametroDao
					.listarWebParametro(constantes.WEP_EDUCACION_VIRTUAL_PERFILES_SISTEMA).get(0).getValor();

			String sql = "SELECT g.gru_id, u.uaa_codigo, u.uaa_nombre, u.uaa_dependencia FROM grupo g "
					+ "INNER JOIN usuario_grupo ug ON ug.usg_grupo = g.gru_codigo "
					+ "INNER JOIN uaa u ON u.uaa_codigo = ug.usg_uaa WHERE ug.usg_estado = 1 AND ug.usg_usuario = ?"
					+ " AND ug.usg_grupo in (" + codigosPerfiles + ")";

			List<UscoGrantedAuthority> roles = jdbcTemplate.query(sql, new Object[] { usuario },
					new RowMapper<UscoGrantedAuthority>() {

						public UscoGrantedAuthority mapRow(ResultSet rs, int arg1) throws SQLException {
							Uaa uaa = new Uaa();
							uaa.setCodigo(rs.getInt("uaa_codigo"));
							uaa.setNombre(rs.getString("uaa_nombre"));
							uaa.setDependencia(rs.getInt("uaa_dependencia"));

							UscoGrantedAuthority simpleGrantedAuthority = new UscoGrantedAuthority(
									"ROLE_" + rs.getString("gru_id"), uaa);
							return simpleGrantedAuthority;
						}

					});
			return roles;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}