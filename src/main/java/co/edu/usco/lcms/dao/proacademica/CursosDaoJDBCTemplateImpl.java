/**
 */
package co.edu.usco.lcms.dao.proacademica;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import co.edu.usco.lcms.dao.WebParametroDao;
import co.edu.usco.lcms.model.Asignatura;
import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Periodo;
import co.edu.usco.lcms.model.Persona;
import co.edu.usco.lcms.model.PlanAcademico;
import co.edu.usco.lcms.model.Programa;
import co.edu.usco.lcms.model.Sede;
import co.edu.usco.lcms.model.TipoCurso;
import co.edu.usco.lcms.model.TipoEscala;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.model.UaaTipo;
import co.edu.usco.lcms.model.reservaespacios.UaaPersonal;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@Component
public class CursosDaoJDBCTemplateImpl implements CursoDao {

	@Autowired
	Constantes constantes;

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.proacademica.CursoDao#listarTablaCursp(java.lang.
	 * String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarTablaCurso(String search, int start, int length, int draw, int posicion,
			String direccion, int calendario) {
		// TODO Auto-generated method stub
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
		JSONRespuesta respuesta = new JSONRespuesta();
		if (start == 0) {
			start = 1;
		}
		String estadoEliminado = webParametroDao.listarWebParametro(constantes.WEP_ESTADO_ELIMINADO_CURSO).get(0)
				.getValor();

		String[] campos = { "c.cur_codigo", "a.asi_nombre", "c.cur_grupo", "c.cur_cupo", "c.cur_fecha_inicio",
				"pe.per_nombre", "pa.pla_nombre", "ca.cal_nombre", "u.uaa_nombre", "ud.uaa_nombre", "tc.tic_nombre",
				"te.tes_nombre" };

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		String sql = "SELECT COUNT(c.cur_codigo)";
		sql = sql + " FROM curso_noformal c ";
		sql = sql + " INNER JOIN uaa_personal up ON c.uap_codigo = up.uap_codigo";
		sql = sql + " INNER JOIN persona pe ON up.per_codigo = pe.per_codigo";
		sql = sql + " INNER JOIN asignatura a ON c.asi_codigo = a.asi_codigo";
		sql = sql + " INNER JOIN sede s ON c.sed_codigo = s.sed_codigo";
		sql = sql + " INNER JOIN calendario ca ON c.cal_codigo = ca.cal_codigo";
		sql = sql + " INNER JOIN periodo per ON ca.per_codigo = per.per_codigo";
		sql = sql + " INNER JOIN tipo_curso tc ON c.tic_codigo = tc.tic_codigo";
		sql = sql + " INNER JOIN plan_academico pa ON c.pla_codigo = pa.pla_codigo";
		sql = sql + " INNER JOIN programa p ON pa.pro_codigo = p.pro_codigo";
		sql = sql + " INNER JOIN uaa u ON p.uaa_codigo = u.uaa_codigo";
		sql = sql + " INNER JOIN uaa ud ON u.uaa_dependencia = ud.uaa_codigo";
		sql = sql + " INNER JOIN tipo_escala te ON c.tes_codigo = te.tes_codigo";
		sql = sql + " WHERE u.uat_codigo in (" + uaaTipoNoFormal + ") AND c.cur_estado != " + estadoEliminado + " ";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
		}
		if (calendario > 0) {
			sql = sql + " AND ca.cal_codigo in (" + calendario + ")";
		}
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " AND (c.cur_grupo like ? OR a.asi_nombre like ? OR a.asi_nombre_corto like ? ";
			sql = sql + " OR u.uaa_nombre like ? OR c.cur_fecha_inicio like ? OR s.sed_codigo like ? ";
			sql = sql
					+ " OR p.pro_titulo_otorgado like ? OR pe.per_nombre like ? OR pe.per_apellido like ? OR pa.pla_nombre like ?)";
			filtrados = jdbcTemplate.queryForObject(sql,
					new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
							"%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
							"%" + search + "%", "%" + search + "%" },
					Integer.class);
		}

		sql = "SELECT cur_fecha_actualizacion, nombre_facultad, pla_nombre, cur_codigo, cur_semanas, cur_grupo, cur_cupo, cur_fecha_inicio, ";
		sql = sql + " uap_codigo, per_codigo, per_nombre, per_apellido, asi_codigo, ";
		sql = sql + " asi_nombre, asi_nombre_corto, sed_codigo, sed_nombre, cal_codigo, ";
		sql = sql + " cal_nombre, cal_fecha, per_codigo, per_fecha_inicio, tes_codigo, tes_nombre,";
		sql = sql + " per_fecha_fin, tic_codigo, tic_nombre, pla_codigo, pro_codigo, pro_titulo_otorgado, ";
		sql = sql + " uaa_codigo, uaa_nombre, uat_codigo ";
		sql = sql + " from (select row_number() over(order by " + campos[posicion] + " " + direccion
				+ ") AS RowNumber,";
		sql = sql
				+ " c.cur_fecha_actualizacion, ud.uaa_nombre as nombre_facultad, pa.pla_nombre, c.cur_codigo, c.cur_semanas, ";
		sql = sql + " c.cur_grupo, c.cur_cupo, c.cur_fecha_inicio, ";
		sql = sql + " up.uap_codigo, pe.per_codigo, pe.per_nombre, pe.per_apellido, a.asi_codigo, ";
		sql = sql + " a.asi_nombre, a.asi_nombre_corto, s.sed_codigo, s.sed_nombre, ca.cal_codigo, ";
		sql = sql + " ca.cal_nombre, ca.cal_fecha, per.per_fecha_inicio, te.tes_codigo, te.tes_nombre,";
		sql = sql + " per.per_fecha_fin, tc.tic_codigo, tc.tic_nombre, pa.pla_codigo, p.pro_codigo, ";
		sql = sql + " p.pro_titulo_otorgado, u.uaa_codigo, u.uaa_nombre, u.uat_codigo ";
		sql = sql + " FROM curso_noformal c ";
		sql = sql + " INNER JOIN uaa_personal up ON c.uap_codigo = up.uap_codigo";
		sql = sql + " INNER JOIN persona pe ON up.per_codigo = pe.per_codigo";
		sql = sql + " INNER JOIN asignatura a ON c.asi_codigo = a.asi_codigo";
		sql = sql + " INNER JOIN sede s ON c.sed_codigo = s.sed_codigo";
		sql = sql + " INNER JOIN calendario ca ON c.cal_codigo = ca.cal_codigo";
		sql = sql + " INNER JOIN periodo per ON ca.per_codigo = per.per_codigo";
		sql = sql + " INNER JOIN tipo_curso tc ON c.tic_codigo = tc.tic_codigo";
		sql = sql + " INNER JOIN plan_academico pa ON c.pla_codigo = pa.pla_codigo";
		sql = sql + " INNER JOIN programa p ON pa.pro_codigo = p.pro_codigo";
		sql = sql + " INNER JOIN uaa u ON p.uaa_codigo = u.uaa_codigo";
		sql = sql + " INNER JOIN uaa ud ON u.uaa_dependencia = ud.uaa_codigo";
		sql = sql + " INNER JOIN tipo_escala te ON c.tes_codigo = te.tes_codigo";
		sql = sql + " WHERE u.uat_codigo = " + uaaTipoNoFormal + " AND c.cur_estado != " + estadoEliminado + " ";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " or u.uaa_codigo = " + uaaUsuario + ")";
		}
		if (calendario > 0) {
			sql = sql + " AND ca.cal_codigo in (" + calendario + ")";
		}
		sql = sql + " AND (c.cur_grupo like ? OR a.asi_nombre like ? OR a.asi_nombre_corto like ? ";
		sql = sql + " OR u.uaa_nombre like ? OR c.cur_fecha_inicio like ? OR s.sed_codigo like ? ";
		sql = sql
				+ " OR p.pro_titulo_otorgado like ? OR pe.per_nombre like ? OR pe.per_apellido like ? OR pa.pla_nombre like ?)";

		sql = sql + ") as tabla";
		sql = sql + " where tabla.RowNumber between ? and ? ";

		List<Curso> listaCurso = jdbcTemplate.query(sql,
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
						"%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
						"%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Curso>() {

					public Curso mapRow(ResultSet rs, int rowNum) throws SQLException {
						Curso curso = new Curso();
						curso.setCodigo(rs.getInt("cur_codigo"));
						curso.setCupo(rs.getInt("cur_cupo"));
						curso.setGrupo(rs.getString("cur_grupo"));
						curso.setFechaInicio(rs.getDate("cur_fecha_inicio"));
						curso.setSemanas(rs.getInt("cur_semanas"));

						UaaPersonal uaaPersonal = new UaaPersonal();
						uaaPersonal.setCodigo(rs.getInt("uap_codigo"));

						Persona persona = new Persona();
						persona.setCodigo(rs.getInt("per_codigo"));
						persona.setNombre(rs.getString("per_nombre") + " " + rs.getString("per_apellido"));
						persona.setApellido(rs.getString("per_apellido"));
						uaaPersonal.setPersona(persona);

						curso.setUaaPersonal(uaaPersonal);

						Sede sede = new Sede();
						sede.setCodigo(rs.getInt("sed_codigo"));
						sede.setNombre(rs.getString("sed_nombre"));
						curso.setSede(sede);

						Asignatura asignatura = new Asignatura();
						asignatura.setCodigo(rs.getInt("asi_codigo"));
						asignatura.setNombre(rs.getString("asi_nombre"));
						asignatura.setNombreCorto(rs.getString("asi_nombre_corto"));
						Uaa uaaDep = new Uaa();
						uaaDep.setNombre(rs.getString("nombre_facultad"));
						asignatura.setUaa(uaaDep);
						curso.setAsignatura(asignatura);

						Calendario calendario = new Calendario();
						calendario.setCodigo(rs.getInt("cal_codigo"));
						calendario.setNombre(rs.getString("cal_nombre"));

						Periodo periodo = new Periodo();
						periodo.setCodigo(rs.getInt("per_codigo"));
						periodo.setFechaInicio(rs.getDate("per_fecha_inicio"));
						periodo.setFechaFin(rs.getDate("per_fecha_fin"));
						periodo.setNombre(rs.getString("per_nombre"));
						calendario.setPeriodo(periodo);
						curso.setCalendario(calendario);

						PlanAcademico planAcademico = new PlanAcademico();
						planAcademico.setCodigo(rs.getInt("pla_codigo"));
						planAcademico.setNombre(rs.getString("pla_nombre"));

						Programa programa = new Programa();
						programa.setCodigo(rs.getInt("pro_codigo"));
						programa.setTitulo_otorgado(rs.getString("pro_titulo_otorgado"));

						UaaTipo uaaTipo = new UaaTipo();
						uaaTipo.setCodigo((rs.getInt("uat_codigo")));

						Uaa uaa = new Uaa();
						uaa.setCodigo(rs.getInt("uaa_codigo"));
						uaa.setNombre(rs.getString("uaa_nombre"));
						uaa.setUaaTipo(uaaTipo);

						programa.setUaa(uaa);
						planAcademico.setPrograma(programa);
						curso.setPlanAcademico(planAcademico);

						TipoCurso tipoCurso = new TipoCurso();
						tipoCurso.setCodigo(rs.getInt("tic_codigo"));
						tipoCurso.setNombre(rs.getString("tic_nombre"));
						curso.setTipoCurso(tipoCurso);

						TipoEscala tipoEscala = new TipoEscala();
						tipoEscala.setCodigo(rs.getInt("tes_codigo"));
						tipoEscala.setNombre(rs.getString("tes_nombre"));
						curso.setTipoEscala(tipoEscala);
						return curso;
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaCurso);

		return respuesta;
	}

	@Override
	public boolean agregarCurso(Curso curso) {
		// TODO Auto-generated method stub
		System.out.println(":::::::::::::::Inicio Datos Curso:::::::::::::");
		System.out.println(curso.getPlanAcademico().getCodigo() + "," + curso.getTipoCurso().getCodigo() + ","
				+ curso.getCupo() + "," + curso.getGrupo() + "," + curso.getFechaInicio() + ","
				+ curso.getCalendario().getCodigo() + "," + curso.getSede().getCodigo() + ","
				+ curso.getAsignatura().getCodigo() + "," + curso.getUaaPersonal().getCodigo() + ","
				+ curso.getTipoEscala().getCodigo() + "," + curso.getSemanas() + "," + 1);
		System.out.println(":::::::::::::::Fin Datos Curso:::::::::::::");
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO curso_noformal (pla_codigo, tic_codigo, cur_cupo, cur_grupo, cur_fecha_inicio,";
			sql = sql + " cal_codigo, sed_codigo, asi_codigo, uap_codigo, tes_codigo, cur_semanas, cur_estado)";
			sql = sql + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			int resultado = jdbcTemplate.update(sql, curso.getPlanAcademico().getCodigo(),
					curso.getTipoCurso().getCodigo(), curso.getCupo(), curso.getGrupo(), curso.getFechaInicio(),
					curso.getCalendario().getCodigo(), curso.getSede().getCodigo(), curso.getAsignatura().getCodigo(),
					curso.getUaaPersonal().getCodigo(), curso.getTipoEscala().getCodigo(), curso.getSemanas(), 1);

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
	public boolean modificarCurso(int id, Curso curso) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			// update

			String sql = "UPDATE curso_noformal SET pla_codigo=?, tic_codigo=?, cur_cupo=?, cur_grupo=?, cur_fecha_inicio=?,";
			sql = sql + " cal_codigo=?, sed_codigo=?, asi_codigo=?, uap_codigo=?, tes_codigo=?,";
			sql = sql + " cur_semanas = ?, cur_fecha_actualizacion = CURRENT_TIMESTAMP WHERE cur_codigo=?";

			int resultado = jdbcTemplate.update(sql, curso.getPlanAcademico().getCodigo(),
					curso.getTipoCurso().getCodigo(), curso.getCupo(), curso.getGrupo(), curso.getFechaInicio(),
					curso.getCalendario().getCodigo(), curso.getSede().getCodigo(), curso.getAsignatura().getCodigo(),
					curso.getUaaPersonal().getCodigo(), curso.getTipoEscala().getCodigo(), curso.getSemanas(), id);
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
	public boolean eliminarCurso(int id) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String estadoEliminado = webParametroDao.listarWebParametro(constantes.WEP_ESTADO_ELIMINADO_CURSO).get(0)
					.getValor();

			String sql = "UPDATE curso_noformal SET cur_estado = " + estadoEliminado + " WHERE cur_codigo=?";
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

	@Override
	public List<Curso> buscarCurso(String criterio, int asignatura, String grupo) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

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

		String sql = " SELECT TOP 10 u.uaa_nombre, u.uaa_codigo, pa.pla_nombre, c.cur_codigo, c.cur_grupo, c.cur_cupo, c.cur_fecha_inicio, ";
		sql = sql + " up.uap_codigo, pe.per_codigo, pe.per_nombre, pe.per_apellido, a.asi_codigo, ";
		sql = sql + " a.asi_nombre, a.asi_nombre_corto, ca.cal_codigo, ca.cal_nombre, ca.cal_fecha,";
		sql = sql + " pa.pla_codigo, p.pro_codigo, p.pro_titulo_otorgado, tc.tic_codigo, tc.tic_nombre, ";
		sql = sql + " te.tes_codigo, te.tes_nombre FROM curso_noformal c ";
		sql = sql + " INNER JOIN uaa_personal up ON c.uap_codigo = up.uap_codigo";
		sql = sql + " INNER JOIN persona pe ON up.per_codigo = pe.per_codigo";
		sql = sql + " INNER JOIN asignatura a ON c.asi_codigo = a.asi_codigo";
		sql = sql + " INNER JOIN sede s ON c.sed_codigo = s.sed_codigo";
		sql = sql + " INNER JOIN calendario ca ON c.cal_codigo = ca.cal_codigo";
		sql = sql + " INNER JOIN periodo per ON ca.per_codigo = per.per_codigo";
		sql = sql + " INNER JOIN tipo_curso tc ON c.tic_codigo = tc.tic_codigo";
		sql = sql + " INNER JOIN plan_academico pa ON c.pla_codigo = pa.pla_codigo";
		sql = sql + " INNER JOIN programa p ON pa.pro_codigo = p.pro_codigo";
		sql = sql + " INNER JOIN uaa u ON p.uaa_codigo = u.uaa_codigo";
		sql = sql + " INNER JOIN tipo_escala te ON c.tes_codigo = te.tes_codigo";
		sql = sql + " WHERE u.uat_codigo in  (" + uaaTipoNoFormal + ") ";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
		}

		sql = sql + " AND (CONVERT(varchar(15), c.cur_codigo) + ' ' + a.asi_nombre + ' ' + c.cur_grupo like ?)";
		if (asignatura > 0) {
			sql = sql + " AND a.asi_codigo = " + asignatura;
		}
		if (grupo.length() > 0) {
			sql = sql + " AND c.cur_grupo = " + grupo;
		}
		sql = sql + " ORDER BY c.cur_grupo DESC";

		List<Curso> listaCurso = jdbcTemplate.query(sql, new Object[] { "%" + criterio + "%" }, new RowMapper<Curso>() {

			public Curso mapRow(ResultSet rs, int rowNum) throws SQLException {

				Curso curso = new Curso();
				curso.setCodigo(rs.getInt("cur_codigo"));
				curso.setCupo(rs.getInt("cur_cupo"));
				curso.setGrupo(rs.getString("cur_grupo"));
				curso.setFechaInicio(rs.getDate("cur_fecha_inicio"));

				UaaPersonal uaaPersonal = new UaaPersonal();
				uaaPersonal.setCodigo(rs.getInt("uap_codigo"));

				Persona persona = new Persona();
				persona.setCodigo(rs.getInt("per_codigo"));
				persona.setNombre(rs.getString("per_nombre") + " " + rs.getString("per_apellido"));
				persona.setApellido(rs.getString("per_apellido"));
				uaaPersonal.setPersona(persona);

				curso.setUaaPersonal(uaaPersonal);

				Asignatura asignatura = new Asignatura();
				asignatura.setCodigo(rs.getInt("asi_codigo"));
				asignatura.setNombre(rs.getString("asi_nombre"));
				asignatura.setNombreCorto(rs.getString("asi_nombre_corto"));
				curso.setAsignatura(asignatura);

				Calendario calendario = new Calendario();
				calendario.setCodigo(rs.getInt("cal_codigo"));
				calendario.setNombre(rs.getString("cal_nombre"));
				curso.setCalendario(calendario);

				PlanAcademico planAcademico = new PlanAcademico();
				planAcademico.setCodigo(rs.getInt("pla_codigo"));
				planAcademico.setNombre(rs.getString("pla_nombre"));

				Programa programa = new Programa();
				programa.setCodigo(rs.getInt("pro_codigo"));
				programa.setTitulo_otorgado(rs.getString("pro_titulo_otorgado"));

				Uaa uaa = new Uaa();
				uaa.setCodigo(rs.getInt("uaa_codigo"));
				uaa.setNombre(rs.getString("uaa_nombre"));

				programa.setUaa(uaa);
				planAcademico.setPrograma(programa);
				curso.setPlanAcademico(planAcademico);

				TipoCurso tipoCurso = new TipoCurso();
				tipoCurso.setCodigo(rs.getInt("tic_codigo"));
				tipoCurso.setNombre(rs.getString("tic_nombre"));
				curso.setTipoCurso(tipoCurso);

				TipoEscala tipoEscala = new TipoEscala();
				tipoEscala.setCodigo(rs.getInt("tes_codigo"));
				tipoEscala.setNombre(rs.getString("tes_nombre"));
				curso.setTipoEscala(tipoEscala);

				return curso;
			}

		});

		return listaCurso;

	}

}
