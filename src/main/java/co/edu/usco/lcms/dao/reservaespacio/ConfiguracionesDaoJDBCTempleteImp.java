/**
 * 
 */
package co.edu.usco.lcms.dao.reservaespacio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.model.reservaespacios.Configuraciones;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Viea
 * @version 1.0
 */
@Component
public class ConfiguracionesDaoJDBCTempleteImp implements ConfiguracionesDao {

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
	 * @see co.edu.usco.lcms.dao.reservaespacio.ConfiguracionesDao#
	 * agregarConfiguraciones(co.edu.usco.lcms.model.reservaespacios.
	 * Configuraciones)
	 */
	@Override
	public boolean agregarConfiguraciones(Configuraciones configuraciones) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO videoconferencias.reserva_configuraciones (cal_codigo, uaa_codigo, "
					+ "rco_dias_anticipacion, rco_max_solicitudes_dia, rco_max_solicitudes_semana) VALUES"
					+ " (?, ?, ?, ?, ?)";
			int resultado = jdbcTemplate.update(sql, configuraciones.getCalendario().getCodigo(),
					configuraciones.getUaa().getCodigo(), configuraciones.getDiasAnticipacion(),
					configuraciones.getMaxSolicitudesDias(), configuraciones.getMaxSolicitudesSemana());

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
	 * @see co.edu.usco.lcms.dao.reservaespacio.ConfiguracionesDao#
	 * modificarConfiguraciones(int,
	 * co.edu.usco.lcms.model.reservaespacios.Configuraciones)
	 */
	@Override
	public boolean modificarConfiguraciones(int id, Configuraciones configuraciones) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// update
		String sql = "UPDATE videoconferencias.reserva_configuraciones SET cal_codigo=?, uaa_codigo=?, "
				+ "rco_dias_anticipacion=?, rco_max_solicitudes_dia=?, rco_max_solicitudes_semana=? "
				+ " WHERE rco_codigo=?";

		int resultado = jdbcTemplate.update(sql, configuraciones.getCalendario().getCodigo(),
				configuraciones.getUaa().getCodigo(), configuraciones.getDiasAnticipacion(),
				configuraciones.getMaxSolicitudesDias(), configuraciones.getMaxSolicitudesSemana(), id);

		if (resultado > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.reservaespacio.ConfiguracionesDao#
	 * eliminarConfiguraciones(int)
	 */
	@Override
	public boolean eliminarConfiguraciones(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.reservaespacio.ConfiguracionesDao#
	 * listarConfiguraciones(int, int, int)
	 */
	@Override
	public List<Configuraciones> listarConfiguraciones(int uaaCodigo, int codigoReg, int codCalendario) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT u.uaa_codigo, u.uaa_nombre, r.rco_codigo, r.rco_max_solicitudes_dia, "
				+ " r.rco_max_solicitudes_semana, r.rco_dias_anticipacion, c.cal_codigo, c.cal_nombre"
				+ " FROM dbo.uaa AS u" + " LEFT JOIN videoconferencias.reserva_configuraciones AS r"
				+ " ON u.uaa_codigo = r.uaa_codigo AND r.cal_codigo = ?" + " LEFT JOIN dbo.calendario AS c"
				+ " ON r.cal_codigo = c.cal_codigo" + " WHERE u.uat_codigo = 1 ";

		if (uaaCodigo > 0) {
			sql = sql + " AND r.uaa_codigo = " + uaaCodigo;
		}

		if (codigoReg > 0) {
			sql = sql + " AND r.rco_codigo = " + codigoReg;
		}

		List<Configuraciones> listaHoras = jdbcTemplate.query(sql, new Object[] { codCalendario },
				new RowMapper<Configuraciones>() {

					public Configuraciones mapRow(ResultSet rs, int rowNum) throws SQLException {

						Configuraciones reservaHorasUaa = new Configuraciones();

						reservaHorasUaa.setMaxSolicitudesDias(rs.getInt("rco_max_solicitudes_dia"));
						reservaHorasUaa.setMaxSolicitudesSemana(rs.getInt("rco_max_solicitudes_semana"));
						reservaHorasUaa.setDiasAnticipacion(rs.getInt("rco_dias_anticipacion"));

						Uaa uaa = new Uaa();
						uaa.setCodigo(rs.getInt("uaa_codigo"));
						uaa.setNombre(rs.getString("uaa_nombre"));
						reservaHorasUaa.setUaa(uaa);
						Calendario calendario = new Calendario();

						calendario.setCodigo(rs.getInt("cal_codigo"));
						calendario.setNombre(rs.getString("cal_nombre"));

						reservaHorasUaa.setCodigo(rs.getInt("rco_codigo"));

						reservaHorasUaa.setCalendario(calendario);

						return reservaHorasUaa;
					}

				});
		return listaHoras;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.reservaespacio.ConfiguracionesDao#
	 * listarTablaConfiguraciones(java.lang.String, int, int, int, int,
	 * java.lang.String)
	 */
	@Override
	public JSONRespuesta listarTablaConfiguraciones(String search, int start, int length, int draw, int posicion,
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
		JSONRespuesta respuesta = new JSONRespuesta();
		if (start == 0) {
			start = 1;
		}
		java.util.Date date = new java.util.Date();
		Timestamp fechaActual = new Timestamp(date.getTime());

		int codCalendario = calendarioDao.listarCalendario(fechaActual).get(0).getCodigo();

		String[] campos = { "u.uaa_codigo", "u.uaa_nombre", "r.rco_codigo", "r.rco_max_solicitudes_dia",
				"r.rco_max_solicitudes_semana", "r.rco_dias_anticipacion", "c.cal_codigo", "c.cal_nombre" };

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT COUNT(u.uaa_codigo) FROM dbo.uaa AS u LEFT JOIN videoconferencias.reserva_configuraciones AS r ON"
				+ " u.uaa_codigo = r.uaa_codigo AND r.cal_codigo = " + codCalendario
				+ " LEFT JOIN dbo.calendario AS c ON r.cal_codigo = c.cal_codigo WHERE u.uat_codigo = 1 ";

		if (adminFacultad == true && adminGral == false) {
			sql = sql + " AND u.uaa_codigo = " + uaaUsuario;
		}
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " AND (u.uaa_nombre like ? or c.cal_nombre like ? )";
			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%", "%" + search + "%" },
					Integer.class);
		}

		sql = "SELECT uaa_codigo, uaa_nombre, rco_codigo, rco_max_solicitudes_dia, "
				+ " rco_max_solicitudes_semana, rco_dias_anticipacion, cal_codigo, cal_nombre";
		sql = sql + " from (select row_number() over(order by " + campos[posicion] + " " + direccion
				+ ") AS RowNumber,";
		sql = sql + " u.uaa_codigo, u.uaa_nombre, r.rco_codigo, r.rco_max_solicitudes_dia, "
				+ " r.rco_max_solicitudes_semana, r.rco_dias_anticipacion, c.cal_codigo, c.cal_nombre"
				+ " FROM dbo.uaa AS u" + " LEFT JOIN videoconferencias.reserva_configuraciones AS r ON "
				+ " u.uaa_codigo = r.uaa_codigo AND r.cal_codigo = " + codCalendario
				+ " LEFT JOIN dbo.calendario AS c ON r.cal_codigo = c.cal_codigo WHERE u.uat_codigo = 1";

		sql = sql + " AND (u.uaa_nombre like ? or c.cal_nombre like ? )";
		if (adminFacultad == true && adminGral == false) {
			sql = sql + " AND u.uaa_codigo = " + uaaUsuario;
		}
		sql = sql + ") as tabla";
		sql = sql + " where tabla.RowNumber between ? and ? ";

		List<Configuraciones> listaConfiguraciones = jdbcTemplate.query(sql,
				new Object[] { "%" + search + "%", "%" + search + "%", start, fin }, new RowMapper<Configuraciones>() {

					public Configuraciones mapRow(ResultSet rs, int rowNum) throws SQLException {

						Configuraciones reservaHorasUaa = new Configuraciones();

						reservaHorasUaa.setCodigo(rs.getInt("rco_codigo"));
						reservaHorasUaa.setMaxSolicitudesDias(rs.getInt("rco_max_solicitudes_dia"));
						reservaHorasUaa.setMaxSolicitudesSemana(rs.getInt("rco_max_solicitudes_semana"));
						reservaHorasUaa.setDiasAnticipacion(rs.getInt("rco_dias_anticipacion"));

						Uaa uaa = new Uaa();
						uaa.setCodigo(rs.getInt("uaa_codigo"));
						uaa.setNombre(rs.getString("uaa_nombre"));
						reservaHorasUaa.setUaa(uaa);

						Calendario calendario = new Calendario();
						calendario.setCodigo(rs.getInt("cal_codigo"));
						calendario.setNombre(rs.getString("cal_nombre"));
						reservaHorasUaa.setCalendario(calendario);

						return reservaHorasUaa;
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaConfiguraciones);

		return respuesta;
	}

}
