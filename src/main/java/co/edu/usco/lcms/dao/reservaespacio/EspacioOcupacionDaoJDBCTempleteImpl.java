/**
 * 
 */
package co.edu.usco.lcms.dao.reservaespacio;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.configuration.UscoGrantedAuthority;
import co.edu.usco.lcms.configuration.User;
import co.edu.usco.lcms.model.Asignatura;
import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Persona;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.model.reservaespacios.AdobeConnect;
import co.edu.usco.lcms.model.reservaespacios.EspacioOcupacion;
import co.edu.usco.lcms.model.reservaespacios.Horas;
import co.edu.usco.lcms.model.reservaespacios.Solicitud;
import co.edu.usco.lcms.model.reservaespacios.UaaPersonal;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 **/

@Component
public class EspacioOcupacionDaoJDBCTempleteImpl implements EspacioOcupacionDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	AdobeConnectDao adobeConnectDao;

	@Autowired
	Constantes constantes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.reservaespacio.EspacioOcupacionDao#
	 * agregarEspacioOcupacion(co.edu.usco.lcms.model.reservaespacios.
	 * EspacioOcupacion)
	 */
	@Override
	public int agregarEspacioOcupacion(final EspacioOcupacion espacioOcupacion) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			final String sql = "INSERT INTO espacio_ocupacion_virtual (esp_codigo, uaa_codigo, eso_fecha, "
					+ " hor_codigo, hra_codigo, eso_actividad, uap_codigo, oct_codigo, eso_descripcion,"
					+ " usu_codigo, eso_url, eso_estado ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

			KeyHolder keyHolder = new GeneratedKeyHolder();
			int resultado = jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					pstm.setLong(1, espacioOcupacion.getEspacio().getCodigo());
					pstm.setInt(2, espacioOcupacion.getUaa().getCodigo());
					pstm.setDate(3, espacioOcupacion.getFecha());
					pstm.setLong(4, espacioOcupacion.getHorario().getCodigo());
					pstm.setLong(5, espacioOcupacion.getHora().getCodigo());
					pstm.setInt(6, espacioOcupacion.getCurso().getCodigo());
					pstm.setInt(7, espacioOcupacion.getUaaPersonal().getCodigo());
					pstm.setLong(8, espacioOcupacion.getOcupacionTipo().getCodigo());
					pstm.setString(9, espacioOcupacion.getDescripcion());
					pstm.setInt(10, espacioOcupacion.getUsuario().getCodigo());
					pstm.setString(11, espacioOcupacion.getUrl());
					pstm.setString(12, espacioOcupacion.getEstado());
					return pstm;
				}
			}, keyHolder);

			if (resultado > 0) {
				return keyHolder.getKey().intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.reservaespacio.EspacioOcupacionDao#
	 * modificarEspacioOcupacion(int,
	 * co.edu.usco.lcms.model.reservaespacios.EspacioOcupacion)
	 */
	@Override
	public boolean modificarEspacioOcupacion(int id, EspacioOcupacion espacioOcupacion) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// update
		String sql = "UPDATE espacio_ocupacion_virtual SET esp_codigo = ?, uaa_codigo = ?, eso_fecha = ?, "
				+ " hor_codigo = ?, hra_codigo = ?, eso_actividad = ?, uap_codigo = ?, oct_codigo = ?,"
				+ " eso_descripcion = ?, usu_codigo = ?, eso_url = ? WHERE eso_codigo = ? ";

		int resultado = jdbcTemplate.update(sql, espacioOcupacion.getEspacio().getCodigo(),
				espacioOcupacion.getUaa().getCodigo(), espacioOcupacion.getFecha(),
				espacioOcupacion.getHorario().getCodigo(), espacioOcupacion.getHora().getCodigo(),
				espacioOcupacion.getCurso().getCodigo(), espacioOcupacion.getUaaPersonal().getCodigo(),
				espacioOcupacion.getOcupacionTipo().getCodigo(), espacioOcupacion.getDescripcion(),
				espacioOcupacion.getUsuario().getCodigo(), espacioOcupacion.getUrl(), id);

		if (resultado > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.reservaespacio.EspacioOcupacionDao#
	 * eliminarEspacioOcupacion(int)
	 */
	@Override
	public boolean eliminarEspacioOcupacion(int id, Solicitud solicitud) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "UPDATE espacio_ocupacion_virtual SET eso_estado = 0 WHERE eso_codigo=?";
			int resultado = jdbcTemplate.update(sql, solicitud.getEspacioOcupacion().getCodigo());
			if (resultado > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.reservaespacio.EspacioOcupacionDao#
	 * listarEspacioOcupacion(long)
	 */
	@Override
	public List<EspacioOcupacionDao> listarEspacioOcupacion(long codigo) {
		// TODO Auto-generated method stub
		/*
		 * 
		 * */
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.reservaespacio.EspacioOcupacionDao#
	 * listarTablaEspacioOcupacion(java.lang.String, int, int, int, int,
	 * java.lang.String)
	 */
	@Override
	public JSONRespuesta listarTablaEspacioOcupacion(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int uaaUsuario = 0;
		String rol = null;
		boolean adminGral = false;
		boolean adminFacultad = false;
		boolean adminDocente = false;

		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
			rol = uscoGrantedAuthority.getRole();

			if (rol.equals(constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS)) {
				adminFacultad = true;
				uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			}

			if (rol.equals(constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_LCMS)) {
				adminGral = true;
				uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			}

			if (rol.equals(constantes.WEP_ROLE_DOCENTE)) {
				adminDocente = true;
				uaaUsuario = uscoGrantedAuthority.getUaa().getDependencia();
			}
		}
		JSONRespuesta respuesta = new JSONRespuesta();
		if (start == 0) {
			start = 1;
		}

		String[] campos = { "eo.eso_codigo", "s.sol_tema", "eo.eso_descripcion", "p.per_nombre", "a.asi_nombre",
				"eo.eso_fecha", "hi.hra_hora", "hf.hra_hora", "eo.eso_url", "s.sol_tipo_acceso" };

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT COUNT(*) FROM dbo.espacio_ocupacion_virtual eo"
				+ " INNER JOIN videoconferencias.solicitudes s ON eo.eso_codigo = s.eso_codigo"
				+ " INNER JOIN dbo.espacio e ON eo.esp_codigo = e.esp_codigo"
				+ " INNER JOIN dbo.uaa u ON eo.uaa_codigo = u.uaa_codigo"
				+ " INNER JOIN dbo.uaa ud ON u.uaa_dependencia = ud.uaa_codigo"
				+ " INNER JOIN dbo.curso c ON eo.eso_actividad = c.cur_codigo"
				+ " INNER JOIN dbo.asignatura a ON c.asi_codigo = a.asi_codigo"
				+ " INNER JOIN dbo.uaa_personal up ON eo.uap_codigo = up.uap_codigo"
				+ " INNER JOIN dbo.persona p ON up.per_codigo = p.per_codigo"
				+ " INNER JOIN videoconferencias.hora AS hi ON s.hra_codigo_inicio = hi.hra_codigo"
				+ " INNER JOIN videoconferencias.hora AS hf ON s.hra_codigo_fin = hf.hra_codigo"
				+ " WHERE s.sol_estado != 3 AND (eo.eso_fecha like ? OR eo.eso_descripcion like ? OR s.sol_tema like ? OR p.per_nombre like ?"
				+ " OR p.per_apellido like ? OR ud.uaa_nombre like ? OR a.asi_nombre like ? OR c.cur_grupo like ? )";

		if (adminFacultad == false && adminGral == false && adminDocente == true) {
			sql = sql + " AND ( p.per_codigo = " + user.getPersona().getCodigo() + " )";
		}

		if (adminFacultad == true && adminGral == false) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
		}

		int count = jdbcTemplate.queryForObject(sql,
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
						"%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%" },
				Integer.class);
		int filtrados = count;

		sql = "SELECT id_videoconferencia, eso_codigo, eso_url, eso_fecha, eso_descripcion, esp_codigo, esp_nombre, sol_codigo, sol_tema,"
				+ " sol_tipo_acceso, uaa_codigo, uaa_dependencia, uaa_nombre, cur_codigo, cur_grupo, asi_codigo, asi_nombre,"
				+ " asi_nombre_corto, uap_codigo, per_codigo, per_nombre, per_apellido, hra_codigo_inicio,"
				+ " hra_hora_24h_inicio, hra_hora_inicio, hra_codigo_fin, hra_hora_24h_fin, hra_hora_fin"
				+ " from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber,"
				+ " s.id_videoconferencia, eo.eso_codigo, eo.eso_url, eo.eso_fecha, eo.eso_descripcion, e.esp_codigo, e.esp_nombre,"
				+ " s.sol_codigo, s.sol_tema, s.sol_tipo_acceso, u.uaa_codigo, u.uaa_dependencia, ud.uaa_nombre, c.cur_codigo, c.cur_grupo,"
				+ " a.asi_codigo, a.asi_nombre, a.asi_nombre_corto, up.uap_codigo, p.per_codigo, p.per_nombre,"
				+ " p.per_apellido, hi.hra_codigo AS hra_codigo_inicio, hi.hra_hora_24h AS hra_hora_24h_inicio,"
				+ " hi.hra_hora AS hra_hora_inicio, hf.hra_codigo AS hra_codigo_fin, hf.hra_hora_24h AS hra_hora_24h_fin,"
				+ " hf.hra_hora AS hra_hora_fin FROM dbo.espacio_ocupacion_virtual AS eo"
				+ " INNER JOIN videoconferencias.solicitudes s ON eo.eso_codigo = s.eso_codigo"
				+ " INNER JOIN dbo.espacio e ON eo.esp_codigo = e.esp_codigo"
				+ " INNER JOIN dbo.uaa u ON eo.uaa_codigo = u.uaa_codigo"
				+ " INNER JOIN dbo.uaa ud ON u.uaa_dependencia = ud.uaa_codigo"
				+ " INNER JOIN dbo.curso c ON eo.eso_actividad = c.cur_codigo"
				+ " INNER JOIN dbo.asignatura a ON c.asi_codigo = a.asi_codigo"
				+ " INNER JOIN dbo.uaa_personal up ON eo.uap_codigo = up.uap_codigo"
				+ " INNER JOIN dbo.persona p ON up.per_codigo = p.per_codigo"
				+ " INNER JOIN videoconferencias.hora AS hi ON s.hra_codigo_inicio = hi.hra_codigo"
				+ " INNER JOIN videoconferencias.hora AS hf ON s.hra_codigo_fin = hf.hra_codigo "
				+ " WHERE s.sol_estado != 3 AND (eo.eso_fecha like ? OR eo.eso_descripcion like ? OR s.sol_tema like ? OR p.per_nombre like ?"
				+ " OR p.per_apellido like ? OR ud.uaa_nombre like ? OR a.asi_nombre like ? OR c.cur_grupo like ? )";

		if (adminFacultad == false && adminGral == false && adminDocente == true) {
			sql = sql + " AND ( p.per_codigo = " + user.getPersona().getCodigo() + " )";
		}

		if (adminFacultad == true && adminGral == false) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
		}

		sql = sql + ") AS tabla";
		sql = sql + " WHERE tabla.RowNumber BETWEEN ? AND ? ";

		List<Solicitud> listaSolicitud = jdbcTemplate.query(sql,
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
						"%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Solicitud>() {

					public Solicitud mapRow(ResultSet rs, int rowNum) throws SQLException {

						Solicitud solicitud = new Solicitud();

						solicitud.setCodigo(rs.getLong("sol_codigo"));
						solicitud.setTema(rs.getString("sol_tema"));
						solicitud.setTipoAcceso(rs.getString("sol_tipo_acceso"));
						solicitud.setCodigoVideoconferencia(rs.getLong("id_videoconferencia"));

						EspacioOcupacion espacioOcupacion = new EspacioOcupacion();

						espacioOcupacion.setCodigo(rs.getLong("eso_codigo"));

						AdobeConnect credenciales = adobeConnectDao.listarAdobeConnect().get(0);
						String url_base = credenciales.getUrl();

						espacioOcupacion.setUrl(url_base + rs.getString("eso_url"));
						espacioOcupacion.setFecha(rs.getDate("eso_fecha"));
						espacioOcupacion.setDescripcion(rs.getString("eso_descripcion"));
						solicitud.setEspacioOcupacion(espacioOcupacion);

						Curso curso = new Curso();
						curso.setCodigo(rs.getInt("cur_codigo"));
						curso.setGrupo(rs.getString("cur_grupo"));

						Asignatura asignatura = new Asignatura();
						asignatura.setCodigo(rs.getInt("asi_codigo"));
						asignatura.setNombre(rs.getString("asi_nombre"));
						curso.setAsignatura(asignatura);

						UaaPersonal uaaPersonal = new UaaPersonal();
						uaaPersonal.setCodigo(rs.getInt("uap_codigo"));
						Persona persona = new Persona();
						persona.setCodigo(rs.getInt("per_codigo"));
						persona.setNombre(rs.getString("per_nombre"));
						persona.setApellido(rs.getString("per_apellido"));
						uaaPersonal.setPersona(persona);

						Uaa uaa = new Uaa();
						uaa.setCodigo(rs.getInt("uaa_codigo"));
						uaa.setDependencia(rs.getInt("uaa_dependencia"));
						uaa.setNombre(rs.getString("uaa_nombre"));
						uaaPersonal.setUaa(uaa);

						solicitud.setCurso(curso);
						solicitud.setUaaPersonal(uaaPersonal);

						Horas horaInicio = new Horas();
						horaInicio.setCodigo(rs.getInt("hra_codigo_inicio"));
						horaInicio.setHora(rs.getString("hra_hora_inicio"));
						horaInicio.setHora24h(rs.getString("hra_hora_24h_inicio"));
						solicitud.setHoraInicio(horaInicio);

						Horas horaFin = new Horas();
						horaFin.setCodigo(rs.getInt("hra_codigo_fin"));
						horaFin.setHora(rs.getString("hra_hora_fin"));
						horaFin.setHora24h(rs.getString("hra_hora_24h_fin"));
						solicitud.setHoraFin(horaFin);

						return solicitud;
					}

				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaSolicitud);

		return respuesta;
	}

}
