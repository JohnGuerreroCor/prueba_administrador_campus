/**
 * 
 */
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.configuration.UscoGrantedAuthority;
import co.edu.usco.lcms.configuration.User;
import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.OfertaAcademica;
import co.edu.usco.lcms.model.OfertaAcademicaEstado;
import co.edu.usco.lcms.model.Programa;
import co.edu.usco.lcms.model.Sede;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.model.UaaTipo;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@Component
public class OfertaAcademicaDaoJDCBTempleteImpl implements OfertaAcademicaDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	Constantes constantes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.OfertaAcademicaDao#agregarOfertaAcademica(org.usco.
	 * lcms .model.OfertaAcademica)
	 */
	@Override
	public int agregarOfertaAcademica(final OfertaAcademica ofertaAcademica) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			final String sql = "INSERT INTO oferta_academica (ofa_requiere_pago, ofa_admision_automatica, "
					+ "ofa_fecha_inicio, ofa_fecha_fin, ofa_fecha_inscripcion_fin, "
					+ "ofa_fecha_inscripcion_inicio, pro_codigo, cal_codigo, ofa_crear_usuario, "
					+ "ofa_imagen, ofa_requiere_cupo, ofa_cupo_max, ofa_estado, ofa_valor) VALUES (?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			KeyHolder keyHolder = new GeneratedKeyHolder();
			int resultado = jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					pstm.setString(1, ofertaAcademica.getPago());
					pstm.setString(2, ofertaAcademica.getTipoAdmision());
					pstm.setTimestamp(3, ofertaAcademica.getFechaInicio());
					pstm.setTimestamp(4, ofertaAcademica.getFechaFin());
					pstm.setTimestamp(5, ofertaAcademica.getInscripcionFechaFin());
					pstm.setTimestamp(6, ofertaAcademica.getInscripcionFechaInicio());
					pstm.setInt(7, ofertaAcademica.getPrograma().getCodigo());
					pstm.setInt(8, ofertaAcademica.getCalendario().getCodigo());
					pstm.setInt(9, ofertaAcademica.getCrearUsuario());
					pstm.setString(10, ofertaAcademica.getImagen());
					pstm.setInt(11, ofertaAcademica.getRequiereCupo());
					pstm.setInt(12, ofertaAcademica.getCupoMax());
					pstm.setInt(13, ofertaAcademica.getOfertaAcademicaEstado().getCodigo());
					pstm.setFloat(14, ofertaAcademica.getValor());
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
	 * @see
	 * co.edu.usco.lcms.dao.OfertaAcademicaDao#modificarOfertaAcademica(int,
	 * co.edu.usco.lcms.model.OfertaAcademica)
	 */
	@Override
	public boolean modificarOfertaAcademica(int id, OfertaAcademica ofertaAcademica) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			// update

			String sql = "UPDATE oferta_academica SET ofa_requiere_pago=?, ofa_admision_automatica=?, "
					+ " ofa_fecha_inicio=?, ofa_fecha_fin=?, ofa_fecha_inscripcion_fin=?, "
					+ " ofa_fecha_inscripcion_inicio=?, pro_codigo=?, cal_codigo=?, ofa_crear_usuario=?,"
					+ " ofa_imagen=?, ofa_requiere_cupo=?, ofa_cupo_max=?, ofa_valor=?, ofa_estado = ? WHERE ofa_codigo=?";

			int resultado = jdbcTemplate.update(sql, ofertaAcademica.getPago(), ofertaAcademica.getTipoAdmision(),
					ofertaAcademica.getFechaInicio(), ofertaAcademica.getFechaFin(),
					ofertaAcademica.getInscripcionFechaFin(), ofertaAcademica.getInscripcionFechaInicio(),
					ofertaAcademica.getPrograma().getCodigo(), ofertaAcademica.getCalendario().getCodigo(),
					ofertaAcademica.getCrearUsuario(), ofertaAcademica.getImagen(), ofertaAcademica.getRequiereCupo(),
					ofertaAcademica.getCupoMax(), ofertaAcademica.getValor(),
					ofertaAcademica.getOfertaAcademicaEstado().getCodigo(), id);
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
	 * @see co.edu.usco.lcms.dao.OfertaAcademicaDao#eliminarOfertaAcademica(int)
	 */
	@Override
	public boolean eliminarOfertaAcademica(int id) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String estado = webParametroDao.listarWebParametro(constantes.WEP_ESTADO_ELIMINADO_OFERTA).get(0)
					.getValor();
			String sql = "UPDATE oferta_academica SET ofa_estado = " + estado + " WHERE ofa_codigo=?";
			int resultado = jdbcTemplate.update(sql, id);

			if (resultado > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.OfertaAcademicaDao#listarTablaOfertaAcademica(java.
	 * lang .String, int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaOfertaAcademica(String search, int start, int length, int draw, int posicion,
			String direccion, int facultad) {
		// TODO Auto-generated method stub
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int uaaUsuario = 0;
		String rol = null;
		boolean adminGral = false;
		boolean adminFacultad = false;

		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
			rol = uscoGrantedAuthority.getRole();
			uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS)) {
				adminFacultad = true;
			}

			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_LCMS)) {
				adminGral = true;
			}
		}
		JSONRespuesta respuesta = new JSONRespuesta();

		if (start == 0) {
			start = 1;
		}

		String[] campos = { "o.ofa_codigo", "p.pro_titulo_otorgado", "o.ofa_imagen", "c.cal_nombre",
				"o.ofa_fecha_inicio", "o.ofa_fecha_fin", "o.ofa_fecha_inscripcion_inicio",
				"o.ofa_fecha_inscripcion_fin", "cantidadInscritos", "o.ofa_admision_automatica", "o.ofa_requiere_pago",
				"p.sed_codigo", "o.ofa_crear_usuario", "o.ofa_imagen", "o.ofa_requiere_cupo", "o.ofa_cupo_max",
				"o.ofa_valor", "u.uaa_nombre" };

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String valorModalidadParamentro = webParametroDao.listarWebParametro(constantes.WEP_MODALIDAD).get(0)
				.getValor();
		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();
		String estado = webParametroDao.listarWebParametro(constantes.WEP_ESTADO_ELIMINADO_OFERTA).get(0).getValor();

		Object[] obj = new Object[] {};

		String sql = "SELECT COUNT(o.ofa_codigo) FROM oferta_academica o "
				+ " INNER JOIN programa p ON o.pro_codigo = p.pro_codigo "
				+ " INNER JOIN resolucion pr ON p.res_codigo = pr.res_codigo"
				+ " INNER JOIN uaa up ON pr.res_dependencia = up.uaa_codigo "
				+ " INNER JOIN calendario c ON o.cal_codigo = c.cal_codigo "
				+ " INNER JOIN dbo.uaa u ON u.uaa_codigo = p.uaa_codigo "
				+ " INNER JOIN dbo.oferta_academica_estado oe ON o.ofa_estado = oe.ofat_codigo "
				+ " WHERE u.uat_codigo in (" + uaaTipoNoFormal + ") " + " AND p.mod_codigo in ("
				+ valorModalidadParamentro + ")";

		sql = sql + " AND o.ofa_estado != " + estado;
		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " or u.uaa_codigo = " + uaaUsuario + ")";
			sql = sql + " AND (up.uaa_dependencia = " + uaaUsuario + " or up.uaa_codigo = " + uaaUsuario + ")";
		}

		sql = sql
				+ " AND ( o.ofa_codigo like ? OR CONVERT(VARCHAR(10),ofa_fecha_inicio,120) like ? or CONVERT(VARCHAR(10),ofa_fecha_fin,120) "
				+ "like ? or CONVERT(VARCHAR(10),ofa_fecha_inscripcion_fin,120) like ? "
				+ " or CONVERT(VARCHAR(10),ofa_fecha_inscripcion_inicio,120) like ? or pro_titulo_otorgado like ? or u.uaa_nombre like ?)";

		obj = new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
				"%" + search + "%", "%" + search + "%", "%" + search + "%" };

		if (adminGral == true && adminFacultad == false) {
			if (facultad > 0) {
				sql = sql + " AND (u.uaa_dependencia = ? or u.uaa_codigo = ?)";
				sql = sql + " AND (up.uaa_dependencia = ? or up.uaa_codigo = ?)";
				obj = new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
						"%" + search + "%", "%" + search + "%", "%" + search + "%", facultad, facultad, facultad,
						facultad };
			}
		}
		int count = jdbcTemplate.queryForObject(sql, obj, Integer.class);
		int filtrados = count;

		Object[] obje = new Object[] {};
		sql = "SELECT ofat_codigo, ofat_nombre, uaa_nombre, ofa_valor, ofa_requiere_cupo, ofa_cupo_max, ofa_imagen, ofa_crear_usuario, sed_codigo, ofa_codigo, ofa_requiere_pago, ofa_admision_automatica, ofa_fecha_inicio, ofa_fecha_fin, "
				+ " ofa_fecha_inscripcion_fin, ofa_fecha_inscripcion_inicio, pro_codigo, "
				+ " pro_titulo_otorgado, cal_codigo, cal_nombre, cantidadInscritos, cantidadPreInscritos"
				+ " from (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber,"
				+ " oe.ofat_codigo, oe.ofat_nombre, u.uaa_nombre, o.ofa_valor, o.ofa_requiere_cupo, o.ofa_cupo_max, o.ofa_imagen, o.ofa_crear_usuario, "
				+ " p.sed_codigo, o.ofa_codigo, o.ofa_requiere_pago, o.ofa_admision_automatica, o.ofa_fecha_inicio, o.ofa_fecha_fin,"
				+ " o.ofa_fecha_inscripcion_fin, o.ofa_fecha_inscripcion_inicio, p.pro_codigo, "
				+ " p.pro_titulo_otorgado, c.cal_codigo, c.cal_nombre, ";

		sql = sql + " (SELECT COUNT(*) as cant FROM persona p "
				+ "INNER JOIN inscripcion i ON (p.per_codigo = i.per_codigo) "
				+ "INNER JOIN inscripcion_programa ip ON (i.ins_codigo = ip.ins_codigo) "
				+ "WHERE ip.ofa_codigo = o.ofa_codigo) AS cantidadInscritos, ";
		sql = sql + " (SELECT COUNT(tem_codigo) AS cant FROM inscripciones.registroTemporal ";
		sql = sql + " WHERE tem_cod_oferta = o.ofa_codigo) AS cantidadPreInscritos ";

		sql = sql + " FROM oferta_academica o " + " INNER JOIN programa p ON o.pro_codigo = p.pro_codigo "
				+ " INNER JOIN resolucion pr ON p.res_codigo = pr.res_codigo"
				+ " INNER JOIN uaa up ON pr.res_dependencia = up.uaa_codigo "
				+ " INNER JOIN calendario c ON o.cal_codigo = c.cal_codigo "
				+ " INNER JOIN dbo.uaa u ON u.uaa_codigo = p.uaa_codigo "
				+ " INNER JOIN dbo.oferta_academica_estado oe ON o.ofa_estado = oe.ofat_codigo "
				+ " WHERE u.uat_codigo in (" + uaaTipoNoFormal + ") " + " AND p.mod_codigo in ("
				+ valorModalidadParamentro + ") ";
		sql = sql + " AND o.ofa_estado != " + estado;
		sql = sql
				+ " AND (o.ofa_codigo like ? OR CONVERT(VARCHAR(10),ofa_fecha_inicio,120) like ? or CONVERT(VARCHAR(10),ofa_fecha_fin,120) "
				+ "like ? or CONVERT(VARCHAR(10),ofa_fecha_inscripcion_fin,120) like ? "
				+ " or CONVERT(VARCHAR(10),ofa_fecha_inscripcion_inicio,120) like ? or pro_titulo_otorgado like ?  or u.uaa_nombre like ?)";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " or u.uaa_codigo = " + uaaUsuario + ")";
			sql = sql + " AND (up.uaa_dependencia = " + uaaUsuario + " or up.uaa_codigo = " + uaaUsuario + ")";
		}

		obje = new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
				"%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin };

		if (adminGral == true && adminFacultad == false) {
			if (facultad > 0) {
				sql = sql + " AND (u.uaa_dependencia = ? or u.uaa_codigo = ?)";
				sql = sql + " AND (up.uaa_dependencia = ? or up.uaa_codigo = ?)";
				obje = new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
						"%" + search + "%", "%" + search + "%", facultad, facultad, facultad, facultad, start, fin, };
			}
		}

		sql = sql + " ) as tabla";
		sql = sql + " where tabla.RowNumber between ? and ? ";

		List<OfertaAcademica> listaOferta = jdbcTemplate.query(sql, obje, new RowMapper<OfertaAcademica>() {

			public OfertaAcademica mapRow(ResultSet rs, int rowNum) throws SQLException {
				OfertaAcademica ofertaAcademica = new OfertaAcademica();

				ofertaAcademica.setCodigo(rs.getInt("ofa_codigo"));
				ofertaAcademica.setPago(rs.getString("ofa_requiere_pago"));
				ofertaAcademica.setTipoAdmision(rs.getString("ofa_admision_automatica"));
				ofertaAcademica.setFechaInicio(rs.getTimestamp("ofa_fecha_inicio"));
				ofertaAcademica.setFechaFin(rs.getTimestamp("ofa_fecha_fin"));
				ofertaAcademica.setInscripcionFechaInicio(rs.getTimestamp("ofa_fecha_inscripcion_inicio"));
				ofertaAcademica.setInscripcionFechaFin(rs.getTimestamp("ofa_fecha_inscripcion_fin"));
				ofertaAcademica.setCrearUsuario(rs.getInt("ofa_crear_usuario"));
				ofertaAcademica.setImagen(rs.getString("ofa_imagen"));
				ofertaAcademica.setRequiereCupo(rs.getInt("ofa_requiere_cupo"));
				ofertaAcademica.setCupoMax(rs.getInt("ofa_cupo_max"));
				ofertaAcademica.setValor(rs.getFloat("ofa_valor"));
				ofertaAcademica.setCantidadInscritos(rs.getInt("cantidadInscritos"));
				ofertaAcademica.setCantidadPreInscritos(rs.getInt("cantidadPreInscritos"));

				OfertaAcademicaEstado ofertaAcademicaEstado = new OfertaAcademicaEstado();
				ofertaAcademicaEstado.setCodigo(rs.getInt("ofat_codigo"));
				ofertaAcademicaEstado.setNombre(rs.getString("ofat_nombre"));
				ofertaAcademica.setOfertaAcademicaEstado(ofertaAcademicaEstado);

				Uaa uaa = new Uaa();
				uaa.setNombre(rs.getString("uaa_nombre"));

				Sede sede = new Sede();
				sede.setCodigo(rs.getInt("sed_codigo"));

				Programa programa = new Programa();
				programa.setCodigo(rs.getInt("pro_codigo"));
				programa.setTitulo_otorgado(rs.getString("pro_titulo_otorgado"));
				programa.setUaa(uaa);
				programa.setSede(sede);

				ofertaAcademica.setPrograma(programa);

				Calendario calendario = new Calendario();
				calendario.setCodigo(rs.getInt("cal_codigo"));
				calendario.setNombre(rs.getString("cal_nombre"));
				ofertaAcademica.setCalendario(calendario);

				return ofertaAcademica;
			}

		});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaOferta);

		return respuesta;
	}

	@Override
	public boolean guardarUrl(int id, String url) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "UPDATE oferta_academica SET ofa_imagen = ? WHERE ofa_codigo=?";
			int resultado = jdbcTemplate.update(sql, url, id);

			if (resultado > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public OfertaAcademica buscarOfertaAcademica(int codigo, int calendario, int programa, int codigoModificar) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int uaaUsuario = 0;
		String rol = null;
		boolean adminGral = false;
		boolean adminFacultad = false;
		Object[] obj = new Object[] {};

		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
			rol = uscoGrantedAuthority.getRole();
			uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS)) {
				adminFacultad = true;
			}

			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_LCMS)) {
				adminGral = true;
			}
		}

		String sql = "SELECT o.ofa_valor, o.ofa_requiere_cupo, o.ofa_cupo_max,"
				+ " o.ofa_crear_usuario, p.sed_codigo, o.ofa_codigo, o.ofa_requiere_pago, "
				+ " o.ofa_admision_automatica, o.ofa_fecha_inicio, o.ofa_fecha_fin, o.ofa_imagen, "
				+ " o.ofa_fecha_inscripcion_fin, o.ofa_fecha_inscripcion_inicio, p.pro_codigo, "
				+ " p.pro_titulo_otorgado, c.cal_codigo, c.cal_nombre, u.uat_codigo " + " FROM oferta_academica o "
				+ " INNER JOIN programa p ON o.pro_codigo = p.pro_codigo "
				+ " INNER JOIN resolucion pr ON p.res_codigo = pr.res_codigo "
				+ " INNER JOIN uaa up ON pr.res_dependencia = up.uaa_codigo "
				+ " INNER JOIN calendario c ON o.cal_codigo = c.cal_codigo "
				+ " INNER JOIN dbo.uaa u ON u.uaa_codigo = p.uaa_codigo ";

		if (codigo > 0) {
			sql = sql + " WHERE o.ofa_codigo = ?";
			obj = new Object[] { codigo };
		}

		if (calendario > 0 && programa > 0 && codigoModificar > 0) {
			sql = sql + " WHERE c.cal_codigo = ? AND p.pro_codigo = ? AND o.ofa_codigo != ?";
			obj = new Object[] { calendario, programa, codigoModificar };
		} else {
			if (calendario > 0 && programa > 0) {
				sql = sql + " WHERE c.cal_codigo = ? AND p.pro_codigo = ?";
				obj = new Object[] { calendario, programa };
			}
		}

		List<OfertaAcademica> listaOfertaAcademica = jdbcTemplate.query(sql, obj, new RowMapper<OfertaAcademica>() {

			public OfertaAcademica mapRow(ResultSet rs, int rowNum) throws SQLException {

				OfertaAcademica ofertaAcademica = new OfertaAcademica();

				ofertaAcademica.setCodigo(rs.getInt("ofa_codigo"));
				ofertaAcademica.setPago(rs.getString("ofa_requiere_pago"));
				ofertaAcademica.setTipoAdmision(rs.getString("ofa_admision_automatica"));
				ofertaAcademica.setFechaInicio(rs.getTimestamp("ofa_fecha_inicio"));
				ofertaAcademica.setFechaFin(rs.getTimestamp("ofa_fecha_fin"));
				ofertaAcademica.setInscripcionFechaInicio(rs.getTimestamp("ofa_fecha_inscripcion_inicio"));
				ofertaAcademica.setInscripcionFechaFin(rs.getTimestamp("ofa_fecha_inscripcion_fin"));
				ofertaAcademica.setCrearUsuario(rs.getInt("ofa_crear_usuario"));
				ofertaAcademica.setImagen(rs.getString("ofa_imagen"));
				ofertaAcademica.setRequiereCupo(rs.getInt("ofa_requiere_cupo"));
				ofertaAcademica.setCupoMax(rs.getInt("ofa_cupo_max"));
				ofertaAcademica.setValor(rs.getFloat("ofa_valor"));
				Programa programa = new Programa();
				Uaa uaa = new Uaa();
				UaaTipo uaaTipo = new UaaTipo();
				uaaTipo.setCodigo(rs.getInt("uat_codigo"));

				uaa.setUaaTipo(uaaTipo);

				programa.setUaa(uaa);

				ofertaAcademica.setPrograma(programa);

				return ofertaAcademica;
			}

		});
		if (listaOfertaAcademica.size() > 0) {
			return listaOfertaAcademica.get(0);
		} else {
			return null;
		}
	}

}
