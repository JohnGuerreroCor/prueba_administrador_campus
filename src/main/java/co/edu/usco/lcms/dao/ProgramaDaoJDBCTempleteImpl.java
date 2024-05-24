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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.configuration.UscoGrantedAuthority;
import co.edu.usco.lcms.configuration.User;
import co.edu.usco.lcms.model.Convenio;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Jornada;
import co.edu.usco.lcms.model.Modalidad;
import co.edu.usco.lcms.model.Nbc;
import co.edu.usco.lcms.model.NivelAcademico;
import co.edu.usco.lcms.model.Programa;
import co.edu.usco.lcms.model.ProgramaEstado;
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
public class ProgramaDaoJDBCTempleteImpl implements ProgramaDao {

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
	 * co.edu.usco.lcms.dao.ProgramaDao#agregarPrograma(co.edu.usco.lcms.model.
	 * Programa)
	 */
	@Override
	public boolean agregarPrograma(Programa programa) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO programa (sed_codigo, uaa_codigo, nia_codigo,jor_codigo, res_codigo,"
					+ " pro_titulo_otorgado, pro_fecha_creacion, mod_codigo, con_codigo, pro_horario, "
					+ " pro_estado, pro_propio, nbc_codigo, pro_calendario) VALUES "
					+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			int resultado = jdbcTemplate.update(sql, programa.getSede().getCodigo(), programa.getUaa().getCodigo(),
					programa.getNivelAcademico().getCodigo(), programa.getJornada().getCodigo(),
					programa.getResolucion().getCodigo(), programa.getTitulo_otorgado().toUpperCase(),
					programa.getCreacion(), programa.getModalidad().getCodigo(), programa.getConvenio().getCodigo(),
					programa.getHorario(), programa.getProgramaEstado().getCodigo(), programa.getPropio(),
					programa.getNbc().getCodigo(), programa.getCalendario());

			if (resultado > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.ProgramaDao#modificarPrograma(int,
	 * co.edu.usco.lcms.model.Programa)
	 */
	@Override
	public boolean modificarPrograma(int id, Programa programa) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// update
		String sql = "UPDATE programa SET sed_codigo=?, uaa_codigo=?, nia_codigo=?, "
				+ "jor_codigo=?, res_codigo=?, pro_titulo_otorgado=?, pro_fecha_creacion=?, "
				+ "mod_codigo=?, con_codigo=?, pro_horario=?, pro_estado=?, "
				+ "pro_propio=?, nbc_codigo=?, pro_calendario=? WHERE pro_codigo=?";
		int resultado = jdbcTemplate.update(sql, programa.getSede().getCodigo(), programa.getUaa().getCodigo(),
				programa.getNivelAcademico().getCodigo(), programa.getJornada().getCodigo(),
				programa.getResolucion().getCodigo(), programa.getTitulo_otorgado().toUpperCase(),
				programa.getCreacion(), programa.getModalidad().getCodigo(), programa.getConvenio().getCodigo(),
				programa.getHorario(), programa.getProgramaEstado().getCodigo(), programa.getPropio(),
				programa.getNbc().getCodigo(), programa.getCalendario(), id);
		if (resultado > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.ProgramaDao#eliminarPrograma(int)
	 */
	@Override
	public boolean eliminarPrograma(int id) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "UPDATE programa SET pro_estado = 02 WHERE pro_codigo=?";
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
	 * @see co.edu.usco.lcms.dao.ProgramaDao#listarPrograma()
	 */
	@Override
	public List<Programa> listarPrograma(int id, int proMod) {
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

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] obj = new Object[] {};

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();
		String valorModalidadParamentro = webParametroDao.listarWebParametro(constantes.WEP_MODALIDAD).get(0)
				.getValor();

		String sql = "SELECT pe.pre_nombre, p.pro_codigo, p.pro_programa, s.sed_codigo, "
				+ "s.sed_nombre, up.uaa_codigo, up.uaa_nombre, up.uat_codigo, na.nia_codigo, na.nia_nombre, "
				+ "p.pro_registro_icfes, p.pro_registro_snies, j.jor_codigo, j.jor_nombre, r.res_codigo, "
				+ "r.res_numero, p.pro_titulo_otorgado, p.pro_fecha_creacion, m.mod_codigo, m.mod_nombre,"
				+ " c.con_codigo, c.con_descripcion, p.pro_horario, p.pro_estado, p.pro_propio, n.nbc_codigo,"
				+ " n.nbc_nombre, p.pro_calendario FROM dbo.programa p "
				+ " LEFT JOIN dbo.sede s ON s.sed_codigo = p.sed_codigo"
				+ " LEFT JOIN dbo.resolucion r ON r.res_codigo = p.res_codigo"
				+ " LEFT JOIN dbo.uaa as up ON  p.uaa_codigo = up.uaa_codigo"
				+ " LEFT JOIN dbo.nivel_academico  na ON na.nia_codigo = p.nia_codigo"
				+ " LEFT JOIN dbo.jornada j ON j.jor_codigo = p.jor_codigo"
				+ " LEFT JOIN dbo.modalidad m ON m.mod_codigo = p.mod_codigo"
				+ " LEFT JOIN dbo.convenio c ON c.con_codigo = p.con_codigo"
				+ " LEFT JOIN dbo.snies_nbc n ON n.nbc_codigo = p.nbc_codigo"
				+ " LEFT JOIN dbo.programa_estado pe ON p.pro_estado = pe.pre_codigo" + " WHERE up.uat_codigo in ("
				+ uaaTipoNoFormal + ") AND m.mod_codigo in (" + valorModalidadParamentro + ")";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (up.uaa_dependencia = " + uaaUsuario + " OR up.uaa_codigo = " + uaaUsuario + ") ";
		}

		if (id > 0) {
			sql = sql + " AND p.sed_codigo = ? ";
			obj = new Object[] { id };
		}

		if (proMod > 0) {
			sql = sql + " AND (pe.pre_codigo != '02' OR p.pro_codigo = ?)";
			obj = new Object[] { proMod };
			if (id > 0) {
				obj = new Object[] { id, proMod };
			}
		} else {
			sql = sql + " AND pe.pre_codigo != '02' ";
		}

		sql = sql + " ORDER BY p.pro_titulo_otorgado ASC";

		List<Programa> listaPrograma = jdbcTemplate.query(sql, obj, new RowMapper<Programa>() {

			public Programa mapRow(ResultSet rs, int rowNum) throws SQLException {
				Programa programa = new Programa();

				programa.setCodigo(rs.getInt("pro_codigo"));
				programa.setDescripcion(rs.getString("pro_titulo_otorgado"));
				programa.setPrograma(rs.getString("pro_programa"));
				programa.setRegistro_icfes(rs.getString("pro_registro_icfes"));
				programa.setRegistro_snies(rs.getString("pro_registro_snies"));
				programa.setTitulo_otorgado(rs.getString("pro_titulo_otorgado"));
				programa.setCreacion(rs.getDate("pro_fecha_creacion"));
				programa.setHorario(rs.getString("pro_horario"));
				programa.setPropio(rs.getString("pro_propio"));
				programa.setCalendario(rs.getString("pro_calendario"));

				ProgramaEstado programaEstado = new ProgramaEstado();
				programaEstado.setCodigo(rs.getString("pro_estado"));
				programa.setProgramaEstado(programaEstado);

				Sede sede = new Sede();
				sede.setCodigo(rs.getInt("sed_codigo"));
				sede.setNombre(rs.getString("sed_nombre"));
				programa.setSede(sede);

				Uaa uaa = new Uaa();
				uaa.setCodigo(rs.getInt("uaa_codigo"));
				uaa.setNombre(rs.getString("uaa_nombre"));
				programa.setUaa(uaa);

				NivelAcademico nivelAcademico = new NivelAcademico();
				nivelAcademico.setCodigo(rs.getInt("nia_codigo"));
				nivelAcademico.setNombre(rs.getString("nia_nombre"));
				programa.setNivelAcademico(nivelAcademico);

				Jornada jornada = new Jornada();
				jornada.setCodigo(rs.getInt("jor_codigo"));
				jornada.setNombre(rs.getString("jor_nombre"));
				programa.setJornada(jornada);

				Resolucion resolucion = new Resolucion();
				resolucion.setCodigo(rs.getInt("res_codigo"));
				resolucion.setNumero(rs.getString("res_numero"));
				programa.setResolucion(resolucion);

				Modalidad modalidad = new Modalidad();
				modalidad.setCodigo(rs.getInt("mod_codigo"));
				modalidad.setNombre(rs.getString("mod_nombre"));
				programa.setModalidad(modalidad);

				Convenio convenio = new Convenio();
				convenio.setCodigo(rs.getInt("con_codigo"));
				convenio.setDescripcion(rs.getString("con_descripcion"));
				programa.setConvenio(convenio);

				Nbc nbc = new Nbc();
				nbc.setCodigo(rs.getInt("nbc_codigo"));
				nbc.setNombre(rs.getString("nbc_nombre"));
				programa.setNbc(nbc);

				return programa;
			}

		});

		return listaPrograma;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.ProgramaDao#listarTablaPrograma(java.lang.String,
	 * int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaPrograma(String search, int start, int length, int draw, int posicion,
			String direccion) {
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

		String[] campos = { "p.pro_codigo", "p.pro_titulo_otorgado", "up.uaa_nombre", "cant_preinscritos",
				"s.sed_nombre", "na.nia_nombre", "j.jor_nombre", "r.res_numero", "p.pro_fecha_creacion", "m.mod_nombre",
				"c.con_descripcion", "p.pro_horario", "pe.pre_nombre", "p.pro_propio", "n.nbc_nombre",
				"c.con_descripcion", "u.uat_codigo" };

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();
		String valorModalidadParamentro = webParametroDao.listarWebParametro(constantes.WEP_MODALIDAD).get(0)
				.getValor();

		String sql = "SELECT COUNT(p.pro_codigo) FROM dbo.programa p "
				+ " LEFT JOIN dbo.sede s ON s.sed_codigo = p.sed_codigo"
				+ " LEFT JOIN dbo.resolucion r ON r.res_codigo = p.res_codigo"
				+ " LEFT JOIN dbo.uaa up ON  p.uaa_codigo = up.uaa_codigo"
				+ " LEFT JOIN dbo.nivel_academico  na ON na.nia_codigo = p.nia_codigo"
				+ " LEFT JOIN dbo.jornada j ON j.jor_codigo = p.jor_codigo"
				+ " LEFT JOIN dbo.modalidad m ON m.mod_codigo = p.mod_codigo"
				+ " LEFT JOIN dbo.convenio c ON c.con_codigo = p.con_codigo"
				+ " LEFT JOIN dbo.snies_nbc n ON n.nbc_codigo = p.nbc_codigo"
				+ " LEFT JOIN dbo.programa_estado pe ON p.pro_estado = pe.pre_codigo";

		sql = sql + " WHERE pe.pre_codigo != '02' AND up.uat_codigo in (" + uaaTipoNoFormal + ") AND m.mod_codigo in ("
				+ valorModalidadParamentro + ")";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (up.uaa_dependencia = " + uaaUsuario + " or up.uaa_codigo = " + uaaUsuario + ")";
		}

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " AND (p.pro_codigo like ? OR p.pro_titulo_otorgado like ? OR up.uaa_nombre like ?) ";
			filtrados = jdbcTemplate.queryForObject(sql,
					new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%" }, Integer.class);
		}

		System.out.println(sql);

		sql = "SELECT pre_nombre, pro_codigo, pro_programa, sed_codigo, "
				+ " sed_nombre, uaa_codigo, uaa_nombre, uat_codigo, nia_codigo, nia_nombre, "
				+ " pro_registro_icfes, pro_registro_snies, jor_codigo, jor_nombre, res_codigo, "
				+ " res_numero, pro_titulo_otorgado, pro_fecha_creacion, mod_codigo, mod_nombre,"
				+ " con_codigo, con_descripcion, pro_horario, pro_estado, pro_propio, nbc_codigo,"
				+ " nbc_nombre, pro_calendario FROM (select row_number() over(order by " + campos[posicion] + " "
				+ direccion + ") AS RowNumber, " + " pe.pre_nombre, p.pro_codigo, p.pro_programa, s.sed_codigo, "
				+ " s.sed_nombre, up.uaa_codigo, up.uaa_nombre, up.uat_codigo, na.nia_codigo, na.nia_nombre, "
				+ " p.pro_registro_icfes, p.pro_registro_snies, j.jor_codigo, j.jor_nombre, r.res_codigo, "
				+ " r.res_numero, p.pro_titulo_otorgado, p.pro_fecha_creacion, m.mod_codigo, m.mod_nombre,"
				+ " c.con_codigo, c.con_descripcion, p.pro_horario, p.pro_estado, p.pro_propio, n.nbc_codigo,"
				+ " n.nbc_nombre, p.pro_calendario FROM dbo.programa p "
				+ " LEFT JOIN dbo.sede s ON s.sed_codigo = p.sed_codigo"
				+ " LEFT JOIN dbo.resolucion r ON r.res_codigo = p.res_codigo"
				+ " LEFT JOIN dbo.uaa as up ON  p.uaa_codigo = up.uaa_codigo"
				+ " LEFT JOIN dbo.nivel_academico  na ON na.nia_codigo = p.nia_codigo"
				+ " LEFT JOIN dbo.jornada j ON j.jor_codigo = p.jor_codigo"
				+ " LEFT JOIN dbo.modalidad m ON m.mod_codigo = p.mod_codigo"
				+ " LEFT JOIN dbo.convenio c ON c.con_codigo = p.con_codigo"
				+ " LEFT JOIN dbo.snies_nbc n ON n.nbc_codigo = p.nbc_codigo"
				+ " LEFT JOIN dbo.programa_estado pe ON p.pro_estado = pe.pre_codigo";

		sql = sql + " WHERE pe.pre_codigo != '02' AND up.uat_codigo in (" + uaaTipoNoFormal + ") AND m.mod_codigo in ("
				+ valorModalidadParamentro + ")";

		sql = sql + " AND (p.pro_codigo like ? OR p.pro_titulo_otorgado like ? OR up.uaa_nombre like ?) ";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (up.uaa_dependencia = " + uaaUsuario + " or up.uaa_codigo = " + uaaUsuario + ")";
		}

		sql = sql + ") as tabla";
		sql = sql + " where tabla.RowNumber between ? and ? ";

		List<Programa> listaPrograma = jdbcTemplate.query(sql,
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Programa>() {

					public Programa mapRow(ResultSet rs, int rowNum) throws SQLException {
						Programa programa = new Programa();

						programa.setCodigo(rs.getInt("pro_codigo"));
						programa.setDescripcion(rs.getString("pro_titulo_otorgado"));
						programa.setPrograma(rs.getString("pro_programa"));
						programa.setRegistro_icfes(rs.getString("pro_registro_icfes"));
						programa.setRegistro_snies(rs.getString("pro_registro_snies"));
						programa.setTitulo_otorgado(rs.getString("pro_titulo_otorgado"));
						programa.setCreacion(rs.getDate("pro_fecha_creacion"));
						programa.setHorario(rs.getString("pro_horario"));
						programa.setPropio(rs.getString("pro_propio"));
						programa.setCalendario(rs.getString("pro_calendario"));

						ProgramaEstado programaEstado = new ProgramaEstado();
						programaEstado.setCodigo(rs.getString("pro_estado"));
						programaEstado.setNombre(rs.getString("pre_nombre"));
						programa.setProgramaEstado(programaEstado);

						Sede sede = new Sede();
						sede.setCodigo(rs.getInt("sed_codigo"));
						sede.setNombre(rs.getString("sed_nombre"));
						programa.setSede(sede);

						Uaa uaa = new Uaa();
						uaa.setCodigo(rs.getInt("uaa_codigo"));
						uaa.setNombre(rs.getString("uaa_nombre"));

						UaaTipo uaaTipo = new UaaTipo();
						uaaTipo.setCodigo(rs.getInt("uat_codigo"));
						uaa.setUaaTipo(uaaTipo);

						programa.setUaa(uaa);

						NivelAcademico nivelAcademico = new NivelAcademico();
						nivelAcademico.setCodigo(rs.getInt("nia_codigo"));
						nivelAcademico.setNombre(rs.getString("nia_nombre"));
						programa.setNivelAcademico(nivelAcademico);

						Jornada jornada = new Jornada();
						jornada.setCodigo(rs.getInt("jor_codigo"));
						jornada.setNombre(rs.getString("jor_nombre"));
						programa.setJornada(jornada);

						Resolucion resolucion = new Resolucion();
						resolucion.setCodigo(rs.getInt("res_codigo"));
						resolucion.setNumero(rs.getString("res_numero"));
						programa.setResolucion(resolucion);

						Modalidad modalidad = new Modalidad();
						modalidad.setCodigo(rs.getInt("mod_codigo"));
						modalidad.setNombre(rs.getString("mod_nombre"));
						programa.setModalidad(modalidad);

						Convenio convenio = new Convenio();
						convenio.setCodigo(rs.getInt("con_codigo"));
						convenio.setDescripcion(rs.getString("con_descripcion"));
						programa.setConvenio(convenio);

						Nbc nbc = new Nbc();
						nbc.setCodigo(rs.getInt("nbc_codigo"));
						nbc.setNombre(rs.getString("nbc_nombre"));
						programa.setNbc(nbc);

						return programa;
					}

				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaPrograma);

		return respuesta;
	}

	@Override
	public Programa buscarPrograma(int codigo, int codResolucion, int uaa, int codigoModificar) {
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
		String sql = "SELECT pe.pre_nombre, p.pro_codigo, p.pro_programa, s.sed_codigo, "
				+ "s.sed_nombre, up.uaa_codigo, up.uaa_nombre, up.uat_codigo, na.nia_codigo, na.nia_nombre, "
				+ "p.pro_registro_icfes, p.pro_registro_snies, j.jor_codigo, j.jor_nombre, r.res_codigo, "
				+ "r.res_numero, p.pro_titulo_otorgado, p.pro_fecha_creacion, m.mod_codigo, m.mod_nombre,"
				+ " c.con_codigo, c.con_descripcion, p.pro_horario, p.pro_estado, p.pro_propio, n.nbc_codigo,"
				+ " n.nbc_nombre, p.pro_calendario FROM dbo.programa p "
				+ " LEFT JOIN dbo.sede s ON s.sed_codigo = p.sed_codigo"
				+ " LEFT JOIN dbo.resolucion r ON r.res_codigo = p.res_codigo"
				+ " LEFT JOIN dbo.uaa up ON  p.uaa_codigo = up.uaa_codigo"
				+ " LEFT JOIN dbo.nivel_academico  na ON na.nia_codigo = p.nia_codigo"
				+ " LEFT JOIN dbo.jornada j ON j.jor_codigo = p.jor_codigo"
				+ " LEFT JOIN dbo.modalidad m ON m.mod_codigo = p.mod_codigo"
				+ " LEFT JOIN dbo.convenio c ON c.con_codigo = p.con_codigo"
				+ " LEFT JOIN dbo.snies_nbc n ON n.nbc_codigo = p.nbc_codigo"
				+ " LEFT JOIN dbo.programa_estado pe ON p.pro_estado = pe.pre_codigo WHERE ";

		if (codigo > 0) {
			sql = sql + " p.pro_codigo = " + codigo;
		}

		if (codResolucion > 0) {
			sql = sql + " r.res_codigo = " + codResolucion;
		}

		if (codigoModificar > 0 && uaa > 0) {
			sql = sql + " up.uaa_codigo = " + uaa + " AND  p.pro_codigo != " + codigoModificar;
		} else {
			if (uaa > 0) {
				sql = sql + " up.uaa_codigo = " + uaa;
			}
		}

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (up.uaa_dependencia = " + uaaUsuario + " or up.uaa_codigo = " + uaaUsuario + ") ";

		}

		sql = sql + " ORDER BY p.pro_titulo_otorgado ASC";

		List<Programa> listaPrograma = jdbcTemplate.query(sql, new RowMapper<Programa>() {

			public Programa mapRow(ResultSet rs, int rowNum) throws SQLException {
				Programa programa = new Programa();

				programa.setCodigo(rs.getInt("pro_codigo"));
				programa.setDescripcion(rs.getString("pro_titulo_otorgado"));
				programa.setPrograma(rs.getString("pro_programa"));
				programa.setRegistro_icfes(rs.getString("pro_registro_icfes"));
				programa.setRegistro_snies(rs.getString("pro_registro_snies"));
				programa.setTitulo_otorgado(rs.getString("pro_titulo_otorgado"));
				programa.setCreacion(rs.getDate("pro_fecha_creacion"));
				programa.setHorario(rs.getString("pro_horario"));
				programa.setPropio(rs.getString("pro_propio"));
				programa.setCalendario(rs.getString("pro_calendario"));

				Uaa uaa = new Uaa();
				uaa.setNombre(rs.getString("uaa_nombre"));
				programa.setUaa(uaa);

				ProgramaEstado programaEstado = new ProgramaEstado();
				programaEstado.setCodigo(rs.getString("pro_estado"));
				programa.setProgramaEstado(programaEstado);

				return programa;
			}

		});
		if (listaPrograma.size() > 0) {
			return listaPrograma.get(0);
		} else {
			return null;
		}
	}

}
