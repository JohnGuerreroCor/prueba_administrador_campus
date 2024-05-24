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
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.PlanAcademico;
import co.edu.usco.lcms.model.PlanAcademicoAsignatura;
import co.edu.usco.lcms.model.Programa;
import co.edu.usco.lcms.model.Resolucion;
import co.edu.usco.lcms.model.Sede;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.model.UaaTipo;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@Component
public class PlanAcademicoDaoJDBCTempleteImpl implements PlanAcademicoDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	Constantes constantes;

	@Override
	public PlanAcademico consultarPlanAcademico(int codigoPrograma) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "SELECT TOP 1 pla_codigo, pla_nombre ";
			sql = sql + " FROM plan_academico ";
			sql = sql + " WHERE pro_codigo = " + codigoPrograma;
			sql = sql + " AND pla_total_horas != 0  ORDER BY pla_codigo DESC";

			List<PlanAcademico> listaInscripcion = jdbcTemplate.query(sql, new RowMapper<PlanAcademico>() {

				public PlanAcademico mapRow(ResultSet rs, int rowNum) throws SQLException {
					PlanAcademico planAcademico = new PlanAcademico();
					planAcademico.setCodigo(rs.getInt("pla_codigo"));
					planAcademico.setNombre(rs.getString("pla_nombre"));
					return planAcademico;
				}
			});
			if (listaInscripcion.size() > 0) {
				return listaInscripcion.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.PlanAcademicoDao#agregarPlanAcademico(org.usco.lcms.
	 * model.PlanAcademico)
	 */
	@Override
	public boolean agregarPlanAcademico(final PlanAcademico planAcademico) {
		// TODO Auto-generated method stub
		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			final String sql = "INSERT INTO plan_academico (" + "pla_nombre, " + "pro_codigo, " + "pla_requisito_grado,"
					+ "pla_antiguo," + "pla_tipo_registro," + "pla_estado," + "pla_fecha," + "res_codigo, "
					+ "pla_actual," + "pla_total_horas," + "pla_total_creditos" + ") VALUES ("
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" + ")";
			KeyHolder keyHolder = new GeneratedKeyHolder();
			int resultado = jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					pstm.setString(1, planAcademico.getNombre());
					pstm.setInt(2, planAcademico.getPrograma().getCodigo());
					pstm.setInt(3, planAcademico.getRequisitoGrado());
					pstm.setInt(4, planAcademico.getAntiguo());
					pstm.setString(5, planAcademico.getTipoRegistro());
					pstm.setInt(6, 1);
					pstm.setDate(7, planAcademico.getFecha());
					pstm.setLong(8, planAcademico.getResolucion().getCodigo());
					pstm.setInt(9, planAcademico.getPlanActual());
					pstm.setInt(10, planAcademico.getTotalHoras());
					pstm.setInt(11, planAcademico.getTotalCreditos());
					return pstm;
				}

			}, keyHolder);
			planAcademico.setId(keyHolder.getKey().intValue());

			for (PlanAcademicoAsignatura a : planAcademico.getAsignaturas()) {
				String sqlAsignaturas = "";
				sqlAsignaturas = "INSERT INTO plan_academico_asignatura (" + "pla_codigo," + "asi_codigo, "
						+ "paa_semestre, " + "paa_intensidad, " + "nuc_codigo, " + "paa_estado, " + "paa_nota_minima, "
						+ "paa_chequeo_requisito, " + "paa_obligatoria, " + "paa_programable, "
						+ "paa_h_trabajo_ind_sem, " + "com_codigo, " + "paa_credito" + ") " + "VALUES " + "("
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" + ")";
				resultado = jdbcTemplate.update(sqlAsignaturas, planAcademico.getId(), a.getCodigo(), a.getSemestre(),
						a.getIntensidad(), a.getNucleo().getCodigo(), 1, a.getNotaMinima(), a.getRequisito(),
						a.getPertenece(), a.getProgramable(), a.getTrabajoIndependiente(),
						a.getComponente().getCodigoComponente(), a.getCreditos());
			}

			if (resultado > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.PlanAcademicoDao#modificarPlanAcademico(int,
	 * co.edu.usco.lcms.model.PlanAcademico)
	 */
	@Override
	public boolean modificarPlanAcademico(int id, PlanAcademico planAcademico) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			// update
			String sql = "UPDATE plan_academico SET pla_nombre=?, pro_codigo=?, pla_fecha=?,"
					+ " res_codigo=?, pla_actual=?, pla_total_horas=?, pla_total_creditos=? WHERE pla_codigo=?";

			int resultado = jdbcTemplate.update(sql, planAcademico.getNombre(), planAcademico.getPrograma().getCodigo(),
					planAcademico.getFecha(), planAcademico.getResolucion().getCodigo(), planAcademico.getPlanActual(),
					planAcademico.getTotalHoras(), planAcademico.getTotalCreditos(), id);

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
	 * @see co.edu.usco.lcms.dao.PlanAcademicoDao#eliminarPlanAcademico(int)
	 */
	@Override
	public boolean eliminarPlanAcademico(int id) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "UPDATE plan_academico SET pla_estado = 0 WHERE pla_codigo=?";
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
	 * @see
	 * co.edu.usco.lcms.dao.PlanAcademicoDao#listarTablaPlanAcademico(java.lang.
	 * String, int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaPlanAcademico(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		JSONRespuesta respuesta = new JSONRespuesta();

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

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();
		String valorModalidadParamentro = webParametroDao.listarWebParametro(constantes.WEP_MODALIDAD).get(0)
				.getValor();

		String[] campos = { "p.pla_codigo", "p.pla_nombre", "u.uaa_nombre", "p.pla_total_horas", "p.pla_estado",
				"p.pla_fecha", "r.res_numero", "p.pla_actual", "p.pla_total_creditos", "pr.pro_titulo_otorgado" };

		if (start == 0) {
			start = 1;
		}

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT COUNT(p.pla_codigo)  FROM dbo.plan_academico p ";
		sql = sql + " INNER JOIN dbo.programa pr ON p.pro_codigo = pr.pro_codigo ";
		sql = sql + " INNER JOIN dbo.resolucion rp ON pr.res_codigo = rp.res_codigo ";
		sql = sql + " INNER JOIN dbo.uaa up ON rp.res_dependencia = up.uaa_codigo ";
		sql = sql + " LEFT JOIN dbo.resolucion r ON p.res_codigo = r.res_codigo ";
		sql = sql + " LEFT JOIN dbo.uaa ur ON r.res_dependencia = ur.uaa_codigo ";
		sql = sql + " LEFT JOIN dbo.uaa u ON pr.uaa_codigo = u.uaa_codigo ";
		sql = sql + " WHERE u.uat_codigo in (" + uaaTipoNoFormal + ") AND pr.mod_codigo";
		sql = sql + " in (" + valorModalidadParamentro + ") AND p.pla_estado=1 ";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
			sql = sql + " AND (up.uaa_dependencia = " + uaaUsuario + " OR up.uaa_codigo = " + uaaUsuario + ")";
			sql = sql + " AND (ur.uaa_dependencia = " + uaaUsuario + " OR ur.uaa_codigo = " + uaaUsuario + ")";
		}

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql
					+ " AND (p.pla_codigo like ? OR p.pla_nombre like ? OR u.uaa_nombre like ? OR pr.pro_titulo_otorgado like ?) ";
			filtrados = jdbcTemplate.queryForObject(sql,
					new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%" },
					Integer.class);
		}

		sql = "SELECT pro_titulo_otorgado, sed_codigo, uaa_codigo, uaa_nombre, pla_codigo, pla_nombre, pro_codigo, pro_programa, pla_creditos, pla_total_creditos, "
				+ "pla_requisito_grado, pla_antiguo, pla_total_horas,	pla_estado, pla_fecha, res_codigo, "
				+ "res_numero, pla_actual, snies_numero_periodos";
		sql = sql + " from (select row_number() over(order by " + campos[posicion] + " " + direccion
				+ ") AS RowNumber, pr.pro_titulo_otorgado, pr.sed_codigo, u.uaa_codigo, u.uaa_nombre, p.pla_codigo, ";
		sql = sql + " p.pla_nombre, pr.pro_codigo, pr.pro_programa, p.pla_creditos, p.pla_total_creditos, "
				+ "p.pla_requisito_grado, p.pla_antiguo, p.pla_total_horas, p.pla_estado, p.pla_fecha, r.res_codigo, "
				+ "r.res_numero, p.pla_actual, p.snies_numero_periodos";
		sql = sql + " FROM dbo.plan_academico p INNER JOIN dbo.programa pr ON p.pro_codigo = pr.pro_codigo "
				+ " INNER JOIN dbo.resolucion rp ON pr.res_codigo = rp.res_codigo "
				+ " INNER JOIN dbo.uaa up ON rp.res_dependencia = up.uaa_codigo "
				+ " LEFT JOIN dbo.resolucion r ON p.res_codigo = r.res_codigo "
				+ " LEFT JOIN dbo.uaa ur ON r.res_dependencia = ur.uaa_codigo "
				+ " LEFT JOIN dbo.uaa u ON pr.uaa_codigo = u.uaa_codigo ";

		sql = sql + " WHERE u.uat_codigo in (" + uaaTipoNoFormal + ")  AND pr.mod_codigo in ("
				+ valorModalidadParamentro + ") AND p.pla_estado=1 ";
		sql = sql
				+ " AND (p.pla_codigo like ? OR p.pla_nombre like ? OR u.uaa_nombre like ? OR pr.pro_titulo_otorgado like ?) ";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
			sql = sql + " AND (up.uaa_dependencia = " + uaaUsuario + " OR up.uaa_codigo = " + uaaUsuario + ")";
			sql = sql + " AND (ur.uaa_dependencia = " + uaaUsuario + " OR ur.uaa_codigo = " + uaaUsuario + ")";
		}
		sql = sql + ") AS tabla";
		sql = sql + " WHERE tabla.RowNumber BETWEEN ? AND ? ";

		List<PlanAcademico> listaPlanAcademico = jdbcTemplate.query(sql, new Object[] { "%" + search + "%",
				"%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<PlanAcademico>() {

					public PlanAcademico mapRow(ResultSet rs, int rowNum) throws SQLException {
						PlanAcademico planAcademico = new PlanAcademico();

						planAcademico.setCodigo(rs.getInt("pla_codigo"));
						planAcademico.setNombre(rs.getString("pla_nombre"));

						Programa programa = new Programa();
						programa.setCodigo(rs.getInt("pro_codigo"));
						programa.setPrograma(rs.getString("pro_programa"));
						programa.setTitulo_otorgado(rs.getString("pro_titulo_otorgado"));

						Uaa uaa = new Uaa();
						uaa.setCodigo(rs.getInt("uaa_codigo"));
						uaa.setNombre(rs.getString("uaa_nombre"));
						programa.setUaa(uaa);

						Sede sede = new Sede();
						sede.setCodigo(rs.getInt("sed_codigo"));
						programa.setSede(sede);

						planAcademico.setPrograma(programa);

						planAcademico.setCreditos(rs.getInt("pla_creditos"));
						planAcademico.setTotalCreditos(rs.getInt("pla_total_creditos"));
						planAcademico.setRequisitoGrado(rs.getInt("pla_requisito_grado"));
						planAcademico.setAntiguo(rs.getInt("pla_antiguo"));
						planAcademico.setTotalHoras(rs.getInt("pla_total_horas"));
						planAcademico.setEstado(rs.getInt("pla_estado"));
						planAcademico.setFecha(rs.getDate("pla_fecha"));

						Resolucion resolucion = new Resolucion();
						resolucion.setCodigo(rs.getLong("res_codigo"));
						resolucion.setNumero(rs.getString("res_numero"));

						planAcademico.setResolucion(resolucion);
						planAcademico.setPlanActual(rs.getInt("pla_actual"));
						planAcademico.setSniesNumPeridos(rs.getInt("snies_numero_periodos"));
						planAcademico.setNombreUaa(rs.getString("uaa_nombre"));
						return planAcademico;
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaPlanAcademico);

		return respuesta;
	}

	@Override
	public PlanAcademico buscarPlanAcademico(int codigo) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
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

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		String sql = "SELECT u.uat_codigo, pr.pro_titulo_otorgado, pr.sed_codigo, u.uaa_nombre, p.pla_codigo, ";
		sql = sql + " p.pla_nombre, pr.pro_codigo, pr.pro_programa, p.pla_creditos, p.pla_total_creditos, ";
		sql = sql + " p.pla_requisito_grado, p.pla_antiguo, p.pla_total_horas, p.pla_estado, p.pla_fecha, ";
		sql = sql + " r.res_codigo, r.res_numero, p.pla_actual, p.snies_numero_periodos FROM dbo.plan_academico p ";
		sql = sql + " INNER JOIN dbo.programa pr ON p.pro_codigo = pr.pro_codigo";
		sql = sql + " INNER JOIN dbo.resolucion rp ON pr.res_codigo = rp.res_codigo ";
		sql = sql + " INNER JOIN dbo.uaa up ON rp.res_dependencia = up.uaa_codigo ";
		sql = sql + " LEFT JOIN dbo.resolucion r ON p.res_codigo = r.res_codigo";
		sql = sql + " LEFT JOIN dbo.uaa ur ON r.res_dependencia = ur.uaa_codigo ";
		sql = sql + " LEFT JOIN dbo.uaa u ON pr.uaa_codigo = u.uaa_codigo ";
		sql = sql + " WHERE p.pla_estado=1 ";

		if (uaaTipoNoFormal.length() > 0) {
			sql = sql + " AND u.uat_codigo in (" + uaaTipoNoFormal + ")";
		}
		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
			sql = sql + " AND (up.uaa_dependencia = " + uaaUsuario + " OR up.uaa_codigo = " + uaaUsuario + ")";
			sql = sql + " AND (ur.uaa_dependencia = " + uaaUsuario + " OR ur.uaa_codigo = " + uaaUsuario + ")";
		}

		if (codigo > 0) {
			sql = sql + " AND p.pla_codigo = " + codigo;
		}

		List<PlanAcademico> listaPlanAcademico = jdbcTemplate.query(sql, new RowMapper<PlanAcademico>() {

			public PlanAcademico mapRow(ResultSet rs, int rowNum) throws SQLException {
				PlanAcademico planAcademico = new PlanAcademico();

				planAcademico.setCreditos(rs.getInt("pla_creditos"));
				planAcademico.setTotalCreditos(rs.getInt("pla_total_creditos"));
				planAcademico.setRequisitoGrado(rs.getInt("pla_requisito_grado"));
				planAcademico.setAntiguo(rs.getInt("pla_antiguo"));
				planAcademico.setTotalHoras(rs.getInt("pla_total_horas"));
				planAcademico.setEstado(rs.getInt("pla_estado"));
				planAcademico.setFecha(rs.getDate("pla_fecha"));

				Programa programa = new Programa();
				Uaa uaa = new Uaa();
				UaaTipo uaaTipo = new UaaTipo();
				uaaTipo.setCodigo(rs.getInt("uat_codigo"));

				uaa.setUaaTipo(uaaTipo);

				programa.setUaa(uaa);

				planAcademico.setPrograma(programa);

				return planAcademico;
			}

		});
		if (listaPlanAcademico.size() > 0) {
			return listaPlanAcademico.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<PlanAcademico> listarPlanAcademico(String criterio) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int uaaUsuario = 0;
		String rol = null;
		boolean adminGral = false;
		boolean adminFacultad = false;

		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
			rol = uscoGrantedAuthority.getRole();
			if (rol.equals(constantes.WEP_ROLE_ADMIN_FACULTAD_PROG_ACADEMICA_LCMS)) {
				uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
				adminFacultad = true;
			}

			if (rol.equals(constantes.WEP_ROLE_ADMIN_PROG_ACADEMICA_LCMS)) {
				adminGral = true;
			}
		}

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		String sql = "SELECT TOP 10 u.uaa_codigo, u.uat_codigo, pr.pro_titulo_otorgado, pr.sed_codigo, u.uaa_nombre, p.pla_codigo, ";
		sql = sql + " p.pla_nombre, pr.pro_codigo, pr.pro_programa, p.pla_creditos, p.pla_total_creditos, ";
		sql = sql + " p.pla_requisito_grado, p.pla_antiguo, p.pla_total_horas, p.pla_estado, p.pla_fecha, ";
		sql = sql + " r.res_codigo, r.res_numero, p.pla_actual, p.snies_numero_periodos FROM dbo.plan_academico p ";
		sql = sql + " INNER JOIN dbo.programa pr ON p.pro_codigo = pr.pro_codigo";
		sql = sql + " INNER JOIN dbo.resolucion rp ON pr.res_codigo = rp.res_codigo ";
		sql = sql + " INNER JOIN dbo.uaa up ON rp.res_dependencia = up.uaa_codigo ";
		sql = sql + " LEFT JOIN dbo.resolucion r ON p.res_codigo = r.res_codigo";
		sql = sql + " LEFT JOIN dbo.uaa ur ON r.res_dependencia = ur.uaa_codigo ";
		sql = sql + " LEFT JOIN dbo.uaa u ON pr.uaa_codigo = u.uaa_codigo ";
		sql = sql + " WHERE u.uat_codigo in (" + uaaTipoNoFormal + ") AND p.pla_estado=1 ";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
			sql = sql + " AND (up.uaa_dependencia = " + uaaUsuario + " OR up.uaa_codigo = " + uaaUsuario + ")";
			sql = sql + " AND (ur.uaa_dependencia = " + uaaUsuario + " OR ur.uaa_codigo = " + uaaUsuario + ")";
		}

		sql = sql + " AND (p.pla_nombre like ? OR u.uaa_nombre like ? )";

		List<PlanAcademico> listaPlanAcademico = jdbcTemplate.query(sql,
				new Object[] { "%" + criterio + "%", "%" + criterio + "%" }, new RowMapper<PlanAcademico>() {

					public PlanAcademico mapRow(ResultSet rs, int rowNum) throws SQLException {

						PlanAcademico planAcademico = new PlanAcademico();
						planAcademico.setCodigo(rs.getInt("pla_codigo"));
						planAcademico.setNombre(rs.getString("pla_nombre"));

						Programa programa = new Programa();
						programa.setCodigo(rs.getInt("pro_codigo"));

						Uaa uaa = new Uaa();
						uaa.setCodigo(rs.getInt("uaa_codigo"));
						uaa.setNombre(rs.getString("uaa_nombre"));

						UaaTipo uaaTipo = new UaaTipo();
						uaaTipo.setCodigo(rs.getInt("uat_codigo"));
						uaa.setUaaTipo(uaaTipo);
						programa.setUaa(uaa);

						planAcademico.setPrograma(programa);

						return planAcademico;
					}

				});
		return listaPlanAcademico;
	}

}