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

import co.edu.usco.lcms.model.Grupo;
import co.edu.usco.lcms.model.Persona;
import co.edu.usco.lcms.model.TipoIdentificacion;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.model.Usuario;
import co.edu.usco.lcms.model.UsuarioGrupo;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@Component
public class PerfilDaoJDBCTempleteImpl implements PerfilDao {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.PerfilDao#listarCalendario(int)
	 */
	@Override
	public List<UsuarioGrupo> listarPerfil(int codigo) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "SELECT ug.usg_uaa, p.per_codigo, p.per_nombre, p.per_apellido, p.per_identificacion, ti.tii_codigo, "
					+ "ti.tii_nombre, p.per_direccion_residencia, p.per_telefono_movil, p.per_email, g.gru_nombre, ua.uaa_dependencia "
					+ "FROM persona p " + "INNER JOIN tipo_id ti ON p.tii_codigo = ti.tii_codigo "
					+ "INNER JOIN usuario_general u ON p.per_codigo = u.up "
					+ "INNER JOIN usuario_grupo ug ON u.uid = ug.usg_usuario "
					+ "INNER JOIN grupo g ON ug.usg_grupo = g.gru_codigo "
					+ "INNER JOIN uaa ua ON ug.usg_uaa = ua.uaa_codigo " + "WHERE p.per_codigo = ?"
					+ " GROUP BY ug.usg_uaa, p.per_codigo, p.per_nombre, p.per_apellido, p.per_identificacion, ti.tii_codigo,  "
					+ "ti.tii_nombre, p.per_direccion_residencia, p.per_telefono_movil, p.per_email, g.gru_nombre, ua.uaa_dependencia";

			List<UsuarioGrupo> listaPersona = jdbcTemplate.query(sql, new Object[] { codigo },
					new RowMapper<UsuarioGrupo>() {
						public UsuarioGrupo mapRow(ResultSet rs, int rowNum) throws SQLException {

							UsuarioGrupo usuarioGrupo = new UsuarioGrupo();

							Persona persona = new Persona();
							persona.setCodigo(rs.getInt("per_codigo"));
							persona.setNombre(rs.getString("per_nombre"));
							persona.setApellido(rs.getString("per_apellido"));
							persona.setIdentificacion(rs.getString("per_identificacion"));
							persona.setDireccionResidencia(rs.getString("per_direccion_residencia"));
							persona.setTelefono(rs.getString("per_telefono_movil"));
							persona.setEmail(rs.getString("per_email"));

							TipoIdentificacion ti = new TipoIdentificacion();
							ti.setCodigo(rs.getInt("tii_codigo"));
							ti.setNombre(rs.getString("tii_nombre"));
							persona.setTipoIdentificacion(ti);

							Usuario usuario = new Usuario();
							usuario.setPersona(persona);

							Grupo grupo = new Grupo();
							grupo.setNombre(rs.getString("gru_nombre"));

							Uaa uaa = new Uaa();
							uaa.setCodigo(rs.getInt("usg_uaa"));
							uaa.setDependencia(rs.getInt("uaa_dependencia"));

							usuarioGrupo.setGrupo(grupo);
							usuarioGrupo.setUaa(uaa);
							usuarioGrupo.setUsuario(usuario);
							return usuarioGrupo;
						}
					});
			return listaPersona;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
