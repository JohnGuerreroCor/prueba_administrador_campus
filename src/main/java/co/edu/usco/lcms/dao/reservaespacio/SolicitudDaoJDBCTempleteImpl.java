/**
 * 
 */
package co.edu.usco.lcms.dao.reservaespacio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
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
import co.edu.usco.lcms.dao.CalendarioDao;
import co.edu.usco.lcms.dao.OfertaAcademicaDao;
import co.edu.usco.lcms.model.Asignatura;
import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Persona;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.model.reservaespacios.AdobeConnect;
import co.edu.usco.lcms.model.reservaespacios.Horas;
import co.edu.usco.lcms.model.reservaespacios.Solicitud;
import co.edu.usco.lcms.model.reservaespacios.UaaPersonal;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@Component
public class SolicitudDaoJDBCTempleteImpl implements SolicitudDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	OfertaAcademicaDao ofertaAcademicaDao;

	@Autowired
	Constantes constantes;

	@Autowired
	CalendarioDao calendarioDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.reservaespacio.SolicitudDao#agregarSolicitud(org.
	 * usco. lcms.model.reservaespacios.Solicitud)
	 */
	@Override
	public boolean agregarSolicitud(Solicitud solicitud) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "INSERT INTO videoconferencias.solicitudes (cur_codigo,uap_codigo,sol_tema,"
					+ "sol_descripcion,sol_tipo_acceso,sol_fecha,hra_codigo_inicio,hra_codigo_fin,sol_estado) values "
					+ "(?,?,?,?,?,?,?,?,?)";
			int resultado = jdbcTemplate.update(sql, solicitud.getCurso().getCodigo(),
					solicitud.getUaaPersonal().getCodigo(), solicitud.getTema(), solicitud.getDescripcion(),
					solicitud.getTipoAcceso(), solicitud.getFecha(), solicitud.getHoraInicio().getCodigo(),
					solicitud.getHoraFin().getCodigo(), 2);
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
	 * @see
	 * co.edu.usco.lcms.dao.reservaespacio.SolicitudDao#modificarSolicitud(int,
	 * co.edu.usco.lcms.model.reservaespacios.Solicitud)
	 */
	@Override
	public boolean modificarSolicitud(int id, Solicitud solicitud) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// update
		String sql = "UPDATE videoconferencias.solicitudes SET cur_codigo=?, uap_codigo=?, sol_tema=?, "
				+ "sol_descripcion=?, sol_tipo_acceso=?, sol_fecha=?, hra_codigo_inicio=?, hra_codigo_fin=? "
				+ " WHERE sol_codigo=?";

		int resultado = jdbcTemplate.update(sql, solicitud.getCurso().getCodigo(),
				solicitud.getUaaPersonal().getCodigo(), solicitud.getTema(), solicitud.getDescripcion(),
				solicitud.getTipoAcceso(), solicitud.getFecha(), solicitud.getHoraInicio().getCodigo(),
				solicitud.getHoraFin().getCodigo(), id);

		if (resultado > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.reservaespacio.SolicitudDao#eliminarSolicitud(int)
	 */
	@Override
	public boolean eliminarSolicitud(int id) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "UPDATE videoconferencias.solicitudes set sol_estado = 3 where sol_codigo = ?";
			int resultado = jdbcTemplate.update(sql, id);
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
	 * @see co.edu.usco.lcms.dao.reservaespacio.SolicitudDao#listarSolicitud()
	 */
	@Override
	public List<Solicitud> listarSolicitud(long codigo) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] obj = new Object[] {};
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

		java.util.Date date = new java.util.Date();
		Timestamp fechaActual = new Timestamp(date.getTime());

		Date fInicio = calendarioDao.listarCalendario(fechaActual).get(0).getPeriodo().getFechaInicio();
		Date fFin = calendarioDao.listarCalendario(fechaActual).get(0).getPeriodo().getFechaFin();

		String sql = "SELECT u.uaa_codigo, u.uaa_nombre, u.uaa_dependencia,s.sol_estado, s.sol_codigo, s.sol_tema, "
				+ " s.sol_descripcion, s.sol_tipo_acceso, s.sol_fecha, "
				+ " c.cur_codigo, c.cur_grupo, a.asi_nombre, a.asi_codigo, up.uap_codigo, p.per_codigo, p.per_nombre,"
				+ " p.per_apellido, hi.hra_codigo as hri_codigo, hi.hra_nombre as hri_nombre, hi.hra_hora as hri_hora, "
				+ " hi.hra_hora_24h as hri_hra_24h, hf.hra_codigo as hrf_codigo, hf.hra_nombre as hrf_nombre, "
				+ " hf.hra_hora as hrf_hora, hf.hra_hora_24h as hrf_hra_24h " + " FROM videoconferencias.solicitudes s "
				+ " INNER JOIN dbo.curso c ON s.cur_codigo = c.cur_codigo "
				+ " INNER JOIN dbo.asignatura a ON c.asi_codigo = a.asi_codigo"
				+ " INNER JOIN dbo.uaa_personal up ON s.uap_codigo = up.uap_codigo"
				+ " LEFT JOIN dbo.uaa u ON up.uaa_codigo = u.uaa_codigo"
				+ " LEFT JOIN dbo.uaa ud ON u.uaa_dependencia = ud.uaa_codigo"
				+ " INNER JOIN dbo.persona p ON up.per_codigo = p.per_codigo"
				+ " INNER JOIN videoconferencias.hora hi ON s.hra_codigo_inicio = hi.hra_codigo"
				+ " INNER JOIN videoconferencias.hora hf ON s.hra_codigo_fin = hf.hra_codigo "
				+ " WHERE ( s.sol_estado = 2 OR s.sol_estado = 1 OR s.sol_estado = 0 )" + " AND (s.sol_fecha between '"
				+ fInicio + "' and '" + fFin + "')";

		if (codigo > 0) {
			sql = sql + " AND s.sol_codigo = ?";
			obj = new Object[] { codigo };
		}
		if (adminFacultad == false && adminGral == false && adminDocente == true) {
			sql = sql + " AND( p.per_codigo = " + user.getPersona().getCodigo() + " )";
		}
		if (adminFacultad == true && adminGral == false) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
		}

		List<Solicitud> listaSolicitud = jdbcTemplate.query(sql, obj, new RowMapper<Solicitud>() {

			public Solicitud mapRow(ResultSet rs, int rowNum) throws SQLException {

				Solicitud solicitud = new Solicitud();

				solicitud.setCodigo(rs.getLong("sol_codigo"));
				solicitud.setTitle(rs.getString("sol_tema"));
				solicitud.setStart(rs.getString("sol_fecha") + " " + rs.getString("hri_hra_24h") + ":00");
				solicitud.setEnd(rs.getString("sol_fecha") + " " + rs.getString("hrf_hra_24h") + ":00");
				solicitud.setTema(rs.getString("sol_tema"));
				solicitud.setDescripcion(rs.getString("sol_descripcion"));
				solicitud.setTipoAcceso(rs.getString("sol_tipo_acceso"));
				solicitud.setFecha(rs.getDate("sol_fecha"));
				solicitud.setEstado(rs.getString("sol_estado"));
				String color = "";
				String textColor = "";

				if (rs.getString("sol_estado").equals("1")) {
					solicitud.setColor("rgba(44, 241, 91, 0.58)");
					solicitud.setTextColor("#534b4b");
				}
				if (rs.getString("sol_estado").equals("0")) {
					solicitud.setColor("#ff0000");
					solicitud.setTextColor("#534b4b");
				}

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

				curso.setUaaPersonal(uaaPersonal);
				solicitud.setCurso(curso);
				solicitud.setUaaPersonal(uaaPersonal);

				Horas horaInicio = new Horas();
				horaInicio.setCodigo(rs.getInt("hri_codigo"));
				horaInicio.setNombre(rs.getString("hri_nombre"));
				horaInicio.setHora(rs.getString("hri_hora"));
				horaInicio.setHora24h(rs.getString("hri_hra_24h"));
				solicitud.setHoraInicio(horaInicio);

				Horas horaFin = new Horas();
				horaFin.setCodigo(rs.getInt("hrf_codigo"));
				horaFin.setNombre(rs.getString("hrf_nombre"));
				horaFin.setHora(rs.getString("hrf_hora"));
				horaFin.setHora24h(rs.getString("hrf_hra_24h"));
				solicitud.setHoraFin(horaFin);

				return solicitud;
			}

		});
		return listaSolicitud;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.reservaespacio.SolicitudDao#listarTablaSolicitud(
	 * java. lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarTablaSolicitud(String search, int start, int length, int draw, int posicion,
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

		String[] campos = { "s.sol_codigo", "s.sol_tema", "s.sol_descripcion", "p.per_nombre", "a.asi_nombre",
				"s.sol_fecha", "hi.hra_hora", "hf.hra_hora", "s.sol_estado" };

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT COUNT(*) FROM videoconferencias.solicitudes as s "
				+ " INNER JOIN dbo.curso AS c ON s.cur_codigo = c.cur_codigo "
				+ " INNER JOIN dbo.asignatura AS a ON c.asi_codigo = a.asi_codigo"
				+ " INNER JOIN dbo.uaa_personal AS up ON s.uap_codigo = up.uap_codigo"
				+ " LEFT JOIN dbo.uaa AS u ON up.uaa_codigo = u.uaa_codigo"
				+ " LEFT JOIN dbo.uaa AS ud ON u.uaa_dependencia = ud.uaa_codigo"
				+ " INNER JOIN dbo.persona AS p ON up.per_codigo = p.per_codigo"
				+ " INNER JOIN videoconferencias.hora AS hi ON s.hra_codigo_inicio = hi.hra_codigo"
				+ " INNER JOIN videoconferencias.hora AS hf ON s.hra_codigo_fin = hf.hra_codigo "
				+ " WHERE ( s.sol_estado = 2 OR s.sol_estado = 0 )";

		if (adminFacultad == false && adminGral == false && adminDocente == true) {
			sql = sql + " AND ( p.per_codigo = " + user.getPersona().getCodigo() + " )";
		}
		if (adminFacultad == true && adminGral == false) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
		}

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " AND (s.sol_descripcion like ? OR a.asi_nombre like ? OR p.per_nombre like ? OR "
					+ " p.per_apellido like ? OR ud.uaa_nombre like ?)";
			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%", "%" + search + "%",
					"%" + search + "%", "%" + search + "%", "%" + search + "%" }, Integer.class);
		}

		sql = "SELECT per_email_interno,sol_estado, sol_codigo, sol_tema, sol_descripcion, sol_tipo_acceso, sol_fecha, cur_codigo, cur_grupo,"
				+ " asi_nombre, asi_codigo, uap_codigo, per_codigo, per_nombre, per_apellido, per_identificacion, per_email,"
				+ " hri_codigo, hri_nombre, hri_hora, hri_hra_24h, hrf_codigo, hrf_nombre, hrf_hora, hrf_hra_24h , "
				+ " uaa_codigo, uaa_dependencia, uaa_nombre, cantidadAlumnos from (select row_number() over(order by "
				+ campos[posicion] + " " + direccion + ") AS RowNumber,"
				+ " p.per_email_interno, s.sol_estado, s.sol_codigo, s.sol_tema, s.sol_descripcion, s.sol_tipo_acceso, s.sol_fecha, c.cur_codigo, "
				+ " c.cur_grupo, a.asi_nombre, a.asi_codigo, up.uap_codigo, p.per_codigo, p.per_nombre, "
				+ " p.per_apellido, p.per_identificacion, p.per_email, hi.hra_codigo AS hri_codigo, hi.hra_nombre AS hri_nombre, hi.hra_hora AS "
				+ " hri_hora, hi.hra_hora_24h AS hri_hra_24h, hf.hra_codigo AS hrf_codigo, hf.hra_nombre AS "
				+ " hrf_nombre, hf.hra_hora AS hrf_hora, hf.hra_hora_24h AS hrf_hra_24h , u.uaa_codigo, "
				+ " u.uaa_dependencia, ud.uaa_nombre," + " (SELECT COUNT(e.est_codigo)" + " FROM dbo.estudiante AS e"
				+ " INNER JOIN dbo.persona AS p ON e.per_codigo = p.per_codigo"
				+ " INNER JOIN dbo.matricula AS m ON e.est_codigo = m.est_codigo"
				+ " INNER JOIN dbo.matricula_curso_actual AS mc ON m.mat_codigo = mc.mat_codigo"
				+ " WHERE mc.cur_codigo = c.cur_codigo) AS cantidadAlumnos"
				+ " FROM videoconferencias.solicitudes as s "
				+ " INNER JOIN dbo.curso AS c ON s.cur_codigo = c.cur_codigo "
				+ " INNER JOIN dbo.asignatura AS a ON c.asi_codigo = a.asi_codigo"
				+ " INNER JOIN dbo.uaa_personal AS up ON s.uap_codigo = up.uap_codigo"
				+ " LEFT JOIN dbo.uaa AS u ON up.uaa_codigo = u.uaa_codigo"
				+ " LEFT JOIN dbo.uaa AS ud ON u.uaa_dependencia = ud.uaa_codigo"
				+ " INNER JOIN dbo.persona AS p ON up.per_codigo = p.per_codigo"
				+ " INNER JOIN videoconferencias.hora AS hi ON s.hra_codigo_inicio = hi.hra_codigo"
				+ " INNER JOIN videoconferencias.hora AS hf ON s.hra_codigo_fin = hf.hra_codigo"
				+ " WHERE ( s.sol_estado = 2 OR s.sol_estado = 0 ) AND (s.sol_descripcion like ? OR a.asi_nombre like ? OR p.per_nombre like ? OR "
				+ " p.per_apellido like ? OR ud.uaa_nombre like ?) ";

		if (adminFacultad == true && adminGral == false) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
		}
		if (adminFacultad == false && adminGral == false && adminDocente == true) {
			sql = sql + " AND p.per_codigo = " + user.getPersona().getCodigo();
		}
		sql = sql + ") as tabla";
		sql = sql + " where tabla.RowNumber between ? and ? ";

		List<Solicitud> listaSolicitud = jdbcTemplate.query(sql, new Object[] { "%" + search + "%", "%" + search + "%",
				"%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin }, new RowMapper<Solicitud>() {

					public Solicitud mapRow(ResultSet rs, int rowNum) throws SQLException {

						Solicitud solicitud = new Solicitud();

						solicitud.setCodigo(rs.getLong("sol_codigo"));
						solicitud.setTitle(rs.getString("sol_tema"));
						solicitud.setStart(rs.getString("sol_fecha") + " " + rs.getString("hri_hra_24h") + ":00");
						solicitud.setEnd(rs.getString("sol_fecha") + " " + rs.getString("hrf_hra_24h") + ":00");
						solicitud.setTema(rs.getString("sol_tema"));
						solicitud.setDescripcion(rs.getString("sol_descripcion"));
						solicitud.setTipoAcceso(rs.getString("sol_tipo_acceso"));
						solicitud.setFecha(rs.getDate("sol_fecha"));
						solicitud.setEstado(rs.getString("sol_estado"));

						Curso curso = new Curso();
						curso.setCodigo(rs.getInt("cur_codigo"));
						curso.setGrupo(rs.getString("cur_grupo"));
						curso.setCantidad(rs.getInt("cantidadAlumnos"));

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
						persona.setEmail(rs.getString("per_email_interno"));
						persona.setIdentificacion(rs.getString("per_identificacion"));
						uaaPersonal.setPersona(persona);

						Uaa uaa = new Uaa();
						uaa.setCodigo(rs.getInt("uaa_codigo"));
						uaa.setDependencia(rs.getInt("uaa_dependencia"));
						uaa.setNombre(rs.getString("uaa_nombre"));
						uaaPersonal.setUaa(uaa);

						solicitud.setCurso(curso);
						solicitud.setUaaPersonal(uaaPersonal);

						Horas horaInicio = new Horas();
						horaInicio.setCodigo(rs.getInt("hri_codigo"));
						horaInicio.setNombre(rs.getString("hri_nombre"));
						horaInicio.setHora(rs.getString("hri_hora"));
						horaInicio.setHora24h(rs.getString("hri_hra_24h"));
						solicitud.setHoraInicio(horaInicio);

						Horas horaFin = new Horas();
						horaFin.setCodigo(rs.getInt("hrf_codigo"));
						horaFin.setNombre(rs.getString("hrf_nombre"));
						horaFin.setHora(rs.getString("hrf_hora"));
						horaFin.setHora24h(rs.getString("hrf_hra_24h"));
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

	@Override
	public int cantidadSolicitudes(boolean dias, Date fecha, int docente, boolean estado, String hora) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] obj = new Object[] {};

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		String sql = "SELECT COUNT(*) AS cantidad FROM videoconferencias.solicitudes as s "
				+ " INNER JOIN dbo.curso AS c ON s.cur_codigo = c.cur_codigo "
				+ " INNER JOIN dbo.asignatura AS a ON c.asi_codigo = a.asi_codigo"
				+ " INNER JOIN dbo.uaa_personal AS up ON s.uap_codigo = up.uap_codigo"
				+ " LEFT JOIN dbo.uaa AS u ON up.uaa_codigo = u.uaa_codigo"
				+ " LEFT JOIN dbo.uaa AS ud ON u.uaa_dependencia = ud.uaa_codigo"
				+ " INNER JOIN dbo.persona AS p ON up.per_codigo = p.per_codigo"
				+ " INNER JOIN videoconferencias.hora AS hi ON s.hra_codigo_inicio = hi.hra_codigo"
				+ " INNER JOIN videoconferencias.hora AS hf ON s.hra_codigo_fin = hf.hra_codigo ";

		if (!hora.isEmpty()) {
			int hInicio = Integer.parseInt(hora) - 1;
			int hFin = Integer.parseInt(hora) + 1;

			String shInicio = String.valueOf(hInicio);
			shInicio = "0" + hInicio + ":00";
			String shFin = String.valueOf(hFin);
			shFin = "0" + hFin + ":00";

			sql = sql + " WHERE (hi.hra_hora_24h BETWEEN '" + shInicio + "' AND '" + shFin + "') ";
		}

		if (docente > 0) {
			sql = sql + " WHERE s.uap_codigo = ?";
		}

		if (estado) {
			sql = sql + " AND s.sol_estado = 1";
		}

		if (dias) {
			sql = sql + " AND s.sol_fecha = ? ";
			if (docente > 0) {
				obj = new Object[] { docente, dateFormat.format(fecha) };
			} else {
				obj = new Object[] { dateFormat.format(fecha) };
			}
		} else {
			sql = sql + " AND (s.sol_fecha between (SELECT DATEADD(wk,DATEDIFF(wk,0,?),0)) AND "
					+ "(SELECT DATEADD(wk,DATEDIFF(wk,0,?),6)))";
			obj = new Object[] { docente, dateFormat.format(fecha), dateFormat.format(fecha) };
		}
		List<Solicitud> listaSolicitud = jdbcTemplate.query(sql, obj, new RowMapper<Solicitud>() {

			public Solicitud mapRow(ResultSet rs, int rowNum) throws SQLException {

				Solicitud solicitud = new Solicitud();

				solicitud.setCantidad(rs.getInt("cantidad"));

				return solicitud;
			}

		});
		return listaSolicitud.get(0).getCantidad();
	}

	@Override
	public boolean actualizarEstado(int id, Solicitud solicitud) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			// update
			String sql = "UPDATE videoconferencias.solicitudes SET sol_estado=?, eso_codigo=?, id_videoconferencia = ? WHERE sol_codigo=?";

			String codigo = null;
			if (solicitud.getEspacioOcupacion().getCodigo() > 0) {
				codigo = String.valueOf(solicitud.getEspacioOcupacion().getCodigo());
			}

			int resultado = jdbcTemplate.update(sql, solicitud.getEstado(), codigo,
					solicitud.getCodigoVideoconferencia(), id);

			if (resultado > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int cantidadSolicitudesAdmin(int estado, String mes, String anio, int uaaPersona) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int uaaUsuario = 0;
		String rol = null;
		boolean adminGral = false;
		boolean adminFacultad = false;
		boolean adminDocente = false;

		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
			rol = uscoGrantedAuthority.getRole();
			uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();

			if (rol.equals(constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS)) {
				adminFacultad = true;
			}

			if (rol.equals(constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_LCMS)) {
				adminGral = true;
			}

			if (rol.equals(constantes.WEP_ROLE_DOCENTE)) {
				adminDocente = true;
			}
		}

		Object[] obj = new Object[] {};

		String sql = "SELECT COUNT(*) AS cantidad FROM videoconferencias.solicitudes as s "
				+ " INNER JOIN dbo.curso c ON s.cur_codigo = c.cur_codigo "
				+ " INNER JOIN dbo.asignatura a ON c.asi_codigo = a.asi_codigo"
				+ " INNER JOIN dbo.uaa_personal up ON s.uap_codigo = up.uap_codigo"
				+ " LEFT JOIN dbo.uaa u ON up.uaa_codigo = u.uaa_codigo"
				+ " LEFT JOIN dbo.uaa ud ON u.uaa_dependencia = ud.uaa_codigo"
				+ " INNER JOIN dbo.persona p ON up.per_codigo = p.per_codigo"
				+ " INNER JOIN videoconferencias.hora AS hi ON s.hra_codigo_inicio = hi.hra_codigo"
				+ " INNER JOIN videoconferencias.hora AS hf ON s.hra_codigo_fin = hf.hra_codigo "
				+ " WHERE s.sol_estado = ? ";

		if (!mes.isEmpty() && !anio.isEmpty()) {
			sql = sql + " AND MONTH(s.sol_fecha)= ? AND YEAR(s.sol_fecha)= ? ";
			obj = new Object[] { estado, mes, anio };
			if (uaaPersona > 0) {
				sql = sql + " AND up.uap_codigo = ? ";
				obj = new Object[] { estado, mes, anio, uaaPersona };
			}

		} else {
			if (uaaPersona > 0) {
				sql = sql + " AND up.uap_codigo = ? ";
				obj = new Object[] { estado, uaaPersona };
			} else {
				obj = new Object[] { estado };
			}
		}

		if (adminFacultad == true && adminGral == false) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
		}

		List<Solicitud> listaSolicitud = jdbcTemplate.query(sql, obj, new RowMapper<Solicitud>() {

			public Solicitud mapRow(ResultSet rs, int rowNum) throws SQLException {

				Solicitud solicitud = new Solicitud();

				solicitud.setCantidad(rs.getInt("cantidad"));

				return solicitud;
			}

		});
		return listaSolicitud.get(0).getCantidad();
	}

	@Override
	public List<Solicitud> cantidadHorasSolicitudesAdmin(int estado, String mes, String anio, int uaaPersona) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int uaaUsuario = 0;
		String rol = null;
		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
			uaaUsuario = uscoGrantedAuthority.getUaa().getDependencia();
			rol = uscoGrantedAuthority.getRole();
		}

		Object[] obj = new Object[] {};

		String sql = "SELECT eo.eso_codigo, eo.eso_fecha, s.sol_codigo, up.uap_codigo, p.per_codigo,"
				+ " hi.hra_hora_24h AS hra_hora_24h_inicio, hf.hra_hora_24h AS hra_hora_24h_fin"
				+ " FROM dbo.espacio_ocupacion AS eo"
				+ " INNER JOIN videoconferencias.solicitudes AS s ON eo.eso_codigo = s.eso_codigo"
				+ " INNER JOIN dbo.espacio AS e ON eo.esp_codigo = e.esp_codigo"
				+ " INNER JOIN dbo.uaa AS u ON eo.uaa_codigo = u.uaa_codigo"
				+ " INNER JOIN dbo.uaa AS ud ON u.uaa_dependencia = ud.uaa_codigo"
				+ " INNER JOIN dbo.curso AS c ON eo.eso_actividad = c.cur_codigo"
				+ " INNER JOIN dbo.asignatura AS a ON c.asi_codigo = a.asi_codigo"
				+ " INNER JOIN dbo.uaa_personal AS up ON eo.uap_codigo = up.uap_codigo"
				+ " INNER JOIN dbo.persona AS p ON up.per_codigo = p.per_codigo"
				+ " INNER JOIN videoconferencias.hora AS hi ON s.hra_codigo_inicio = hi.hra_codigo"
				+ " INNER JOIN videoconferencias.hora AS hf ON s.hra_codigo_fin = hf.hra_codigo"
				+ " WHERE s.sol_estado = ? AND up.uap_codigo = ? ";

		if (!mes.isEmpty() && !anio.isEmpty()) {
			sql = sql + " AND MONTH(s.sol_fecha)= ? AND YEAR(s.sol_fecha)= ? ";
			obj = new Object[] { estado, uaaPersona, mes, anio };
		} else {
			obj = new Object[] { estado, uaaPersona };
		}

		List<Solicitud> listaSolicitud = jdbcTemplate.query(sql, obj, new RowMapper<Solicitud>() {

			public Solicitud mapRow(ResultSet rs, int rowNum) throws SQLException {

				Solicitud solicitud = new Solicitud();
				solicitud.setFecha(rs.getDate("eso_fecha"));
				Horas hi = new Horas();
				hi.setHora24h(rs.getString("hra_hora_24h_inicio"));
				Horas hf = new Horas();
				hf.setHora24h(rs.getString("hra_hora_24h_fin"));
				solicitud.setHoraInicio(hi);
				solicitud.setHoraFin(hf);

				return solicitud;
			}

		});
		return listaSolicitud;
	}

	@Override
	public int cantidadUsuarios(int estado, Date fecha, String hora) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] obj = new Object[] {};

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		String sql = "SELECT s.cur_codigo FROM videoconferencias.solicitudes as s "
				+ " INNER JOIN videoconferencias.hora AS hi ON s.hra_codigo_inicio = hi.hra_codigo"
				+ " INNER JOIN videoconferencias.hora AS hf ON s.hra_codigo_fin = hf.hra_codigo "
				+ " WHERE s.sol_estado = ?";

		if (!hora.isEmpty()) {
			int hInicio = Integer.parseInt(hora) - 1;
			int hFin = Integer.parseInt(hora) + 1;

			String shInicio = String.valueOf(hInicio);
			shInicio = "0" + hInicio + ":00";
			String shFin = String.valueOf(hFin);
			shFin = "0" + hFin + ":00";

			sql = sql + " AND (hi.hra_hora_24h BETWEEN '" + shInicio + "' AND '" + shFin + "') ";
		}

		sql = sql + " AND s.sol_fecha = ? ";
		List<Curso> listaCurso = jdbcTemplate.query(sql, new Object[] { estado, dateFormat.format(fecha) },
				new RowMapper<Curso>() {

					public Curso mapRow(ResultSet rs, int rowNum) throws SQLException {

						Curso curso = new Curso();

						curso.setCodigo(rs.getInt("cur_codigo"));

						return curso;
					}

				});
		int cantidadUsuarios = 0;

		for (Curso sol : listaCurso) {
			String query = "SELECT COUNT(*) AS cantidad FROM dbo.estudiante AS e "
					+ " INNER JOIN dbo.persona AS p ON e.per_codigo = p.per_codigo "
					+ " INNER JOIN dbo.matricula AS m ON e.est_codigo = m.est_codigo "
					+ " INNER JOIN dbo.matricula_curso_actual AS mc ON m.mat_codigo = mc.mat_codigo "
					+ " WHERE mc.cur_codigo = " + sol.getCodigo();

			List<AdobeConnect> listaAdobeConnect = jdbcTemplate.query(query, new RowMapper<AdobeConnect>() {

				public AdobeConnect mapRow(ResultSet rs, int rowNum) throws SQLException {

					AdobeConnect adobeConnect = new AdobeConnect();
					adobeConnect.setNumSesiones(rs.getInt("cantidad") + 1);
					return adobeConnect;
				}
			});

			cantidadUsuarios = cantidadUsuarios + listaAdobeConnect.get(0).getNumSesiones();
		}
		return cantidadUsuarios;
	}

}
