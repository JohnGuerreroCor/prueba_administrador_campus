package co.edu.usco.lcms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.librerias.TheadEnvioCorreo;
import co.edu.usco.lcms.model.Estudiante;
import co.edu.usco.lcms.model.UsuarioEstudiante;
import co.edu.usco.lcms.utility.Encriptar;
import co.edu.usco.lcms.utility.UtilGenerarPinClave;

@Component
public class UsuarioEstudiantesDaoJDBCTemplateImpl implements UsuarioEstudiantesDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	WebParametroDao webParametroDao;

	@Override
	public UsuarioEstudiante consultarUID() {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "SELECT MAX(ug.uid)+1 AS uid FROM usuario_general ug";
			List<UsuarioEstudiante> listaIUD = jdbcTemplate.query(sql, new RowMapper<UsuarioEstudiante>() {

				public UsuarioEstudiante mapRow(ResultSet rs, int rowNum) throws SQLException {
					UsuarioEstudiante usuEst = new UsuarioEstudiante();
					usuEst.setUid(rs.getInt("uid"));
					return usuEst;
				}
			});
			if (listaIUD.size() > 0) {
				return listaIUD.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean guardarUsuarioEstudiantes(final Estudiante estudiante) {
		try {
			String clave = Encriptar.toMD5String(Encriptar.toSha1Byte("" + estudiante.getUsuarioestudiante().getUid()));
			String est_codigo = estudiante.getCodigo();
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String linkCambiarClave = webParametroDao.listarWebParametro("EDUCACION_VIRTUAL_LINK_CAMBIAR_CLAVE_PORTAL")
					.get(0).getValor();

			System.out.println("Link cambiar clave: " + linkCambiarClave);

			int codPersona = 0;
			int codTercero = 0;

			if (estudiante.getPersona().isTercero() == true) {
				codTercero = estudiante.getPersona().getId();
			} else {
				codPersona = estudiante.getPersona().getId();
			}

			String sql = "INSERT INTO usuario_estudiantes_virtual (uid, up, ut, us, uwd2, istipo, ischange, us_encriptado, state) ";
			sql += "VALUES(?, ?, ?, ?, CONVERT(varbinary(MAX), ? , 2), 5, 1, CONVERT(varbinary(MAX), ? , 2), 1);";
			int resultado = jdbcTemplate.update(sql, estudiante.getUsuarioestudiante().getUid(), codPersona, codTercero,
					estudiante.getPersona().getIdentificacion(), clave,
					Encriptar.toSha1String(estudiante.getPersona().getIdentificacion()));

			if (resultado > 0) {

				String destinatarioBCC = estudiante.getPersona().getEmailPreInscripcion();
				String destinatario = estudiante.getPersona().getEmail();
				String footer = "NOTA CONFIDENCIAL:<br>"
						+ "Este mensaje es exclusivamente para el uso de la persona o entidad a quien está dirigido; La información contenida en este e-mail y en todos sus archivos anexos es completamente confidencial, de igual manera las opiniones expresadas son netamente personales, y no necesariamente transmiten el pensamiento de la Universidad Surcolombiana. Si usted no es el destinatario por favor háganoslo saber respondiendo a este correo y por favor destruya cualquier copia del mismo y sus adjuntos, cualquier almacenamiento, distribución, divulgación o copia de este mensaje está estrictamente prohibida y sancionada por la ley."
						+ "Si por error recibe este mensaje, ofrecemos disculpas.<br><br>" + "CONFIDENTIAL NOTE:<br>"
						+ "This message is exclusively for  use of the individual or entity to whom it is forwarded.  The information of this e-mail and all its attachments is completely confidential; likewise, the opinions expressed are purely personal and they do not necessarily convey the thought of  Surcolombiana University.  If you are not the addressee, please let us know it by replying to this e-mail and please delete any copy and its attachments.  Any storage, distribution, dissemination or copy of this information is strictly prohibited and punished  by law.";

				String asunto = "Datos acceso al portal - Universidad Surcolombiana";
				/*
				 * String mensaje = "Cordial saludo " +
				 * estudiante.getPersona().getNombre().toUpperCase() + " " +
				 * estudiante.getPersona().getApellido().toUpperCase() +
				 * ",<br><br>Bienvenido a la Universidad Surcolombiana " +
				 * "<br><br>Los datos de acceso al Campus Virtual son:" +
				 * "<br><br>Codigo Estudiante: " + est_codigo + "<br>" + "<br>Usuario: " +
				 * estudiante.getPersona().getIdentificacion() + "<br>Clave temporal: " +
				 * estudiante.getUsuarioestudiante().getUid() + "<br><br>" +
				 * "Link del portal: <a href='https://quinchana.usco.edu.co/cas/login?service=https%3A%2F%2Fcampusvirtual.usco.edu.co%2Fsakai-login-tool%2Fcontainer'>Campus virtual</a><br><br>"
				 * //+ "Debe ingresar al sigiente link y cambiar su clave: <a href='" +
				 * linkCambiarClave //+ "'>Cambiar clave</a>" + "<br><br>" + footer;
				 */
				// System.out.println(destinatario + " : " + asunto + " : " +
				// mensaje);
				String mensaje = "";
				String pin = UtilGenerarPinClave.generarPin();

				int estado = obligarCambio(estudiante.getUsuarioestudiante().getUid());

				if (estado == 1) {

					String usuarioCambio = "";
					usuarioCambio = estudiante.getPersona().getIdentificacion();

					boolean programado = programarCambio(usuarioCambio, pin);

					if (programado == true) {
						mensaje = mensaje
								+ "<div style='margin: 0 auto; padding: 30px; width: 80%; background-color: #f1f1f1;'>";
						mensaje = mensaje + "<center>";
						mensaje = mensaje
								+ "<img alt='Universidad Surcolombiana' class='img-responsive' src='https://www.usco.edu.co/imagen-institucional/logo/universidad-surcolombiana.png'>";
						mensaje = mensaje + "<h3>Universidad Surcolombiana - Restablecer Clave</h3>";
						mensaje = mensaje + "</center>";
						mensaje = mensaje
								+ "<div style='padding: 20px; background-color: #FFFFFF' class='w3-code notranslate htmlHigh'>";
						mensaje = mensaje + "<p><span style='color: black'>Se&ntilde;or(a):</span>"
								+ estudiante.getPersona().getNombre().toUpperCase();
						if (estudiante.getPersona().getApellido() != null
								&& estudiante.getPersona().getApellido() != "") {
							mensaje = mensaje + estudiante.getPersona().getApellido().toUpperCase();
						}
						mensaje = mensaje + "</p>";
						mensaje = mensaje
								+ "<p>Para la asignación de su clave de click en el boton cambiar clave para continuar con el proceso. Este link solo sera valido durante las siguientes 24 horas.</p>";
						mensaje = mensaje + "</div><br><br>";
						mensaje = mensaje + "<a href='https://www.usco.edu.co/cambio_clave/ticket/" + pin
								+ "' target='_blank'	style='padding: 5px; color: #FFFFFF; text-decoration: none; background-color: #B81726'>Cambiar la clave</a>";
						mensaje = mensaje + "</div>";
					} else {
						return false;
					}
				} else {
					return false;
				}

				new TheadEnvioCorreo(destinatario, asunto, mensaje, destinatarioBCC).start();

				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean guardarUsuarioGrupo(final Estudiante estudiante) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "INSERT INTO usuario_grupo (usg_usuario, usg_grupo, usg_uaa) VALUES(?, ?, ?)";
			int resultado = jdbcTemplate.update(sql, estudiante.getUsuarioestudiante().getUid(), 4,
					estudiante.getInscripcion().getOferta().getPrograma().getCodigoUaa());
			if (resultado > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public UsuarioEstudiante consultarUsuarioEstudiantes(String identificacion) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "SELECT uid ";
			sql = sql + "FROM usuario_estudiantes_virtual ";
			sql = sql + "WHERE us = '" + identificacion + "'";

			List<UsuarioEstudiante> listaUsuarioEstudiante = jdbcTemplate.query(sql,
					new RowMapper<UsuarioEstudiante>() {
						public UsuarioEstudiante mapRow(ResultSet rs, int rowNum) throws SQLException {
							UsuarioEstudiante ue = new UsuarioEstudiante();
							ue.setUid(rs.getInt("uid"));
							ue.setIsTipo(rs.getInt("istipo"));
							return ue;
						}
					});
			if (listaUsuarioEstudiante.size() > 0) {
				return listaUsuarioEstudiante.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public UsuarioEstudiante consultarUsuarioGeneral(String identificacion) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "SELECT istipo, uid ";
			sql = sql + "FROM usuario_estudiantes_virtual ";
			sql = sql + "WHERE us = '" + identificacion + "'";
			System.out.println(sql);
			List<UsuarioEstudiante> listaUsuarioEstudiante = jdbcTemplate.query(sql,
					new RowMapper<UsuarioEstudiante>() {
						public UsuarioEstudiante mapRow(ResultSet rs, int rowNum) throws SQLException {
							UsuarioEstudiante ue = new UsuarioEstudiante();
							ue.setUid(rs.getInt("uid"));
							ue.setIsTipo(rs.getInt("istipo"));
							return ue;
						}
					});
			if (listaUsuarioEstudiante.size() > 0) {
				return listaUsuarioEstudiante.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UsuarioEstudiante consultarUsuario(int persona) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "SELECT uid ";
			sql = sql + "FROM usuario_general ";
			sql = sql + "WHERE up = " + persona;
			List<UsuarioEstudiante> listaUsuarioEstudiante = jdbcTemplate.query(sql,
					new RowMapper<UsuarioEstudiante>() {
						public UsuarioEstudiante mapRow(ResultSet rs, int rowNum) throws SQLException {
							UsuarioEstudiante ue = new UsuarioEstudiante();
							ue.setUid(rs.getInt("uid"));
							return ue;
						}
					});
			if (listaUsuarioEstudiante.size() > 0) {
				return listaUsuarioEstudiante.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int obligarCambio(int uid) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "UPDATE usuario_estudiantes_virtual SET ischange = ? WHERE uid = ?;";
		// String sql = "UPDATE usuario_estudiantes_virtual SET ischange = ?,
		// us_encriptado = HASHBYTES('SHA1', us) WHERE uid = ?;";
		jdbcTemplate.update(sql, new Object[] { 1, uid });

		sql = "SELECT ischange FROM usuario_estudiantes_virtual WHERE uid = ?;";

		return jdbcTemplate.queryForObject(sql, new Object[] { uid }, Integer.class);
	}

	@Override
	public boolean programarCambio(final String usuario, final String pin) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		final String sql = "INSERT INTO recuperar_clave(rec_usuario, rec_clave, rec_istipo, rec_estado) VALUES (?, ?, ?, ?);";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		int resultado = jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstm.setString(1, "0x" + Encriptar.toSha1String(usuario).toUpperCase());
				pstm.setBytes(2, Encriptar.toSha1Byte(pin));
				pstm.setInt(3, 5);
				pstm.setInt(4, 1);
				return pstm;
			}
		}, keyHolder);

		return resultado > 0 ? true : false;
	}

	@Override
	public boolean actualizarEstadoUsuarioVirtual(int usuario) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "EXEC HabilitarUsuarioVirtual ?";

			int resultado = jdbcTemplate.update(sql, usuario);
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
