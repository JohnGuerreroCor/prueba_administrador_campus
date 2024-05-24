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
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Municipio;
import co.edu.usco.lcms.model.Sede;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.model.UaaTipo;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@Component
public class UaaDaoJDBCTempleteImpl implements UaaDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	SedeDao sedeDao;

	@Autowired
	Constantes constantes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.UaaDao#agregarUaa(co.edu.usco.lcms.model.Uaa)
	 * 
	 */
	@Override
	public boolean agregarUaa(Uaa uaa) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			int Municipio = sedeDao.listarSede(1, uaa.getSede().getCodigo()).get(0).getCodigoMunicipio();

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

			if (!adminGral && adminFacultad) {
				uaa.setDependencia(uaaUsuario);
			}

			String sql = "INSERT INTO uaa (uat_codigo, uaa_nombre, uaa_dependencia, uaa_nombre_corto, uaa_nombre_impresion,";
			sql = sql + " uaa_email, uaa_email_pqr, uaa_pagina, mun_codigo,	uaa_telefono, uaa_fax, uaa_direccion,";
			sql = sql + " sed_codigo, uaa_centro_costos, uaa_acronimo, uaa_estado, uaa_propietario_codigo)";
			sql = sql + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			int resultado = jdbcTemplate.update(sql, uaa.getUaaTipo().getCodigo(), uaa.getNombre().toUpperCase(),
					uaa.getDependencia(), uaa.getNombreCorto().toUpperCase(), uaa.getNombreImpresion().toUpperCase(),
					uaa.getEmail(), uaa.getEmailPqr(), uaa.getPagina(), Municipio, uaa.getTelefono(), uaa.getFax(),
					uaa.getDireccion(), uaa.getSede().getCodigo(), uaa.getCentroCostos().toUpperCase(),
					uaa.getAcronimo().toUpperCase(), 1, user.getId());

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
	 * @see co.edu.usco.lcms.dao.UaaDao#modificarUaa(int,
	 * co.edu.usco.lcms.model.Uaa)
	 */
	@Override
	public boolean modificarUaa(int id, Uaa uaa) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// update
		int Municipio = sedeDao.listarSede(1, uaa.getSede().getCodigo()).get(0).getCodigoMunicipio();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int uaaUsuario = 0;
		String rol = null;
		boolean adminGral = false;
		boolean adminFacultad = false;

		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
			uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			rol = uscoGrantedAuthority.getRole();
			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS)) {
				adminFacultad = true;
			}

			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_LCMS)) {
				adminGral = true;
			}
		}

		if (!adminGral && adminFacultad) {
			uaa.setDependencia(uaaUsuario);
		}

		String sql = "UPDATE uaa SET uat_codigo = ?, uaa_nombre = ?, uaa_dependencia = ?, uaa_nombre_corto = ?,";
		sql = sql + " uaa_nombre_impresion = ?, uaa_email = ?, uaa_email_pqr = ?, uaa_pagina = ?, mun_codigo = ?,";
		sql = sql + " uaa_telefono = ?, uaa_fax = ?,	uaa_direccion = ?, sed_codigo = ?, uaa_centro_costos = ?,";
		sql = sql + " uaa_acronimo = ?, uaa_estado = ?, uaa_ventanilla = ?, uaa_codigo_retencion = ?";
		sql = sql + " WHERE uaa_codigo=?";
		int resultado = jdbcTemplate.update(sql, uaa.getUaaTipo().getCodigo(), uaa.getNombre().toUpperCase(),
				uaa.getDependencia(), uaa.getNombreCorto().toUpperCase(), uaa.getNombreImpresion().toUpperCase(),
				uaa.getEmail(), uaa.getEmailPqr(), uaa.getPagina(), Municipio, uaa.getTelefono(), uaa.getFax(),
				uaa.getDireccion(), uaa.getSede().getCodigo(), uaa.getCentroCostos().toUpperCase(),
				uaa.getAcronimo().toUpperCase(), uaa.getEstado(), uaa.getVentanilla(), uaa.getCodigoRetencion(), id);

		if (resultado > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.UaaDao#eliminarUaa(int)
	 */
	@Override
	public boolean eliminarUaa(int id) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String tipoUaa = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		String rol = null;
		boolean adminGral = false;
		boolean adminFacultad = false;

		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
			rol = uscoGrantedAuthority.getRole();
			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS)) {
				adminFacultad = true;
			}

			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_LCMS)) {
				adminGral = true;
			}
		}

		String sql = "";
		int resultado = 0;

		if (adminGral == false && adminFacultad == true) {
			sql = "UPDATE uaa SET uaa_estado = 0 WHERE uaa_codigo=? AND uaa_propietario_codigo = ? AND uat_codigo in ("
					+ tipoUaa + ")";
			resultado = jdbcTemplate.update(sql, id, user.getId());
		} else {
			if (adminGral == true) {
				sql = "UPDATE uaa SET uaa_estado = 0 WHERE uaa_codigo=? AND uat_codigo in (" + tipoUaa + ")";
				resultado = jdbcTemplate.update(sql, id);
			}
		}

		if (resultado > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.UaaDao#listarUaa(int, int)
	 */
	@Override
	public List<Uaa> listarUaa(int uaaTipo, int codigo, boolean dependencia, int uaaMod, boolean uaaFormal) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int uaaUsuario = 0;
		String rol = null;
		boolean adminGral = false;
		boolean adminFacultad = false;

		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
			uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			rol = uscoGrantedAuthority.getRole();
			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS)) {
				adminFacultad = true;
			}

			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_LCMS)) {
				adminGral = true;
			}
		}

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Object[] obj = new Object[] {};

		String sql = "SELECT u.uaa_dependencia, u.uaa_codigo, ut.uat_codigo, ut.uat_nombre, UPPER(u.uaa_nombre) as uaa_nombre, ";
		sql = sql + " u.uaa_nombre_corto, u.uaa_nombre_impresion, u.uaa_email, u.uaa_email_pqr,";
		sql = sql + " u.uaa_pagina,	m.mun_codigo, m.mun_nombre,	d.dep_codigo, d.dep_nombre, u.uaa_telefono,";
		sql = sql + " u.uaa_fax, u.uaa_direccion, s.sed_codigo, s.sed_nombre, u.uaa_centro_costos,";
		sql = sql + " u.uaa_acronimo, u.uaa_estado, u.uaa_ventanilla, u.uaa_codigo_retencion, dep.uaa_nombre";
		sql = sql + " AS depUaa_nombre FROM dbo.uaa u ";
		sql = sql + " INNER JOIN dbo.sede s ON u.sed_codigo = s.sed_codigo ";
		sql = sql + " INNER JOIN dbo.uaa_tipo ut ON u.uat_codigo = ut.uat_codigo ";
		sql = sql + " LEFT JOIN dbo.uaa dep ON u.uaa_dependencia = dep.uaa_codigo ";
		sql = sql + " LEFT JOIN dbo.municipio m ON u.mun_codigo = m.mun_codigo ";
		sql = sql + " LEFT JOIN dbo.departamento d ON m.dep_codigo = d.dep_codigo ";
		sql = sql + " WHERE ";

		if (uaaMod > 0) {
			sql = sql + "  (u.uaa_estado = 1  OR u.uaa_codigo = " + uaaMod + ")";
		} else {
			sql = sql + " u.uaa_estado = 1 ";
		}

		if (uaaTipo > 0) {
			sql = sql + " AND ut.uat_codigo = ? ";
			obj = new Object[] { uaaTipo };
		}
		if (codigo > 0) {
			sql = sql + " AND u.uaa_codigo = ? ";
			obj = new Object[] { codigo };
		}
		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND  (u.uaa_codigo = " + uaaUsuario + " OR u.uaa_dependencia = " + uaaUsuario + ")";
		}

		sql = sql + " ORDER BY u.uaa_nombre ASC";
		System.out.println("sql uaa:" + sql);
		List<Uaa> listaUaa = jdbcTemplate.query(sql, obj, new RowMapper<Uaa>() {

			public Uaa mapRow(ResultSet rs, int rowNum) throws SQLException {
				Uaa uaa = new Uaa();

				uaa.setAcronimo(rs.getString("uaa_acronimo"));
				uaa.setCentroCostos(rs.getString("uaa_centro_costos"));
				uaa.setCodigo(rs.getInt("uaa_codigo"));
				uaa.setDependencia(rs.getInt("uaa_dependencia"));
				uaa.setDireccion(rs.getString("uaa_direccion"));
				uaa.setEmail(rs.getString("uaa_email"));
				uaa.setEmailPqr(rs.getString("uaa_email_pqr"));
				uaa.setEstado(rs.getInt("uaa_estado"));
				uaa.setFax(rs.getString("uaa_fax"));
				uaa.setNombre(rs.getString("uaa_nombre") + " (" + rs.getString("sed_nombre") + ")");
				uaa.setNombreCorto(rs.getString("uaa_nombre_corto"));
				uaa.setNombreImpresion(rs.getString("uaa_nombre_impresion"));
				uaa.setPagina(rs.getString("uaa_pagina"));
				uaa.setTelefono(rs.getString("uaa_telefono"));
				uaa.setNombreDependencia(rs.getString("depUaa_nombre"));

				Municipio municipio = new Municipio();
				municipio.setCodigo(rs.getInt("mun_codigo"));
				municipio.setNombre(rs.getString("mun_nombre"));
				uaa.setMunicipio(municipio);

				Sede sede = new Sede();
				sede.setCodigo(rs.getInt("sed_codigo"));
				sede.setNombre(rs.getString("sed_nombre"));
				uaa.setSede(sede);

				UaaTipo uaaTipo = new UaaTipo();
				uaaTipo.setCodigo(rs.getInt("uat_codigo"));
				uaaTipo.setNombre(rs.getString("uat_nombre"));

				return uaa;
			}

		});
		return listaUaa;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.UaaDao#listarTablaUaa(java.lang.String, int,
	 * int, int)
	 */
	@Override
	public JSONRespuesta listarTablaUaa(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int uaaUsuario = 0;
		String rol = null;

		boolean adminGral = false;
		boolean adminFacultad = false;

		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
			uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			rol = uscoGrantedAuthority.getRole();
			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS)) {
				adminFacultad = true;
			}

			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_LCMS)) {
				adminGral = true;
			}
		}

		JSONRespuesta respuesta = new JSONRespuesta();

		String uaaTipo = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		if (start == 0) {
			start = 1;
		}

		String[] campos = { "u.uaa_codigo", "ut.uat_nombre", "u.uaa_nombre", "u.uaa_nombre_corto",
				"u.uaa_nombre_impresion", "u.uaa_acronimo", "dep.uaa_nombre", "u.uaa_dependencia", "s.sed_nombre",
				"m.mun_nombre", "u.uaa_telefono", "u.uaa_fax", "u.uaa_direccion", "u.uaa_email", "u.uaa_email_pqr",
				"u.uaa_pagina", "u.uaa_centro_costos", "u.uaa_estado", "dep.uat_codigo", "d.dep_nombre" };

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT count(u.uaa_codigo) FROM dbo.uaa u ";
		sql = sql + " INNER JOIN dbo.sede s ON u.sed_codigo = s.sed_codigo ";
		sql = sql + " INNER JOIN dbo.uaa_tipo ut ON u.uat_codigo = ut.uat_codigo ";
		sql = sql + " LEFT JOIN dbo.uaa dep ON u.uaa_dependencia = dep.uaa_codigo ";
		sql = sql + " LEFT JOIN dbo.municipio m ON u.mun_codigo = m.mun_codigo ";
		sql = sql + " LEFT JOIN dbo.departamento d ON m.dep_codigo = d.dep_codigo ";
		sql = sql + " WHERE ut.uat_codigo in (" + uaaTipo + ")";
		sql = sql + " AND u.uaa_estado = 1 ";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND u.uaa_dependencia = " + uaaUsuario;
		}

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " AND (u.uaa_codigo like ? or u.uaa_nombre like ? or dep.uaa_nombre like ? ";
			sql = sql + " or s.sed_nombre like ? or m.mun_nombre like ? ";
			sql = sql + " or d.dep_nombre like ? or u.uaa_direccion like ?) ";
			filtrados = jdbcTemplate
					.queryForObject(sql,
							new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%",
									"%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%" },
							Integer.class);
		}
		sql = "SELECT uaa_dependencia, uaa_codigo, uat_codigo, uat_nombre, uaa_nombre, ";
		sql = sql + " uaa_nombre_corto, uaa_nombre_impresion, uaa_email, uaa_email_pqr,";
		sql = sql + " uaa_pagina, mun_codigo, mun_nombre, dep_codigo, dep_nombre, uaa_telefono,";
		sql = sql + " uaa_fax, uaa_direccion, sed_codigo, sed_nombre, uaa_centro_costos,";
		sql = sql + " uaa_acronimo, uaa_estado, uaa_ventanilla, uaa_codigo_retencion, depUaa_nombre, depTipoUaa";
		sql = sql + " from (select row_number() over(order by " + campos[posicion] + " " + direccion
				+ ") AS RowNumber,";
		sql = sql + " u.uaa_dependencia, u.uaa_codigo,	ut.uat_codigo, ut.uat_nombre, u.uaa_nombre, ";
		sql = sql + " u.uaa_nombre_corto, u.uaa_nombre_impresion, u.uaa_email, u.uaa_email_pqr,";
		sql = sql + " u.uaa_pagina,	m.mun_codigo, m.mun_nombre,	d.dep_codigo, d.dep_nombre, u.uaa_telefono,";
		sql = sql + " u.uaa_fax, u.uaa_direccion, s.sed_codigo, s.sed_nombre, u.uaa_centro_costos,";
		sql = sql + " u.uaa_acronimo, u.uaa_estado, u.uaa_ventanilla, u.uaa_codigo_retencion, dep.uaa_nombre";
		sql = sql + " AS depUaa_nombre, dep.uat_codigo AS depTipoUaa";
		sql = sql + " FROM dbo.uaa u ";
		sql = sql + "INNER JOIN dbo.sede s ON u.sed_codigo = s.sed_codigo ";
		sql = sql + "INNER JOIN dbo.uaa_tipo ut ON u.uat_codigo = ut.uat_codigo ";
		sql = sql + "LEFT JOIN dbo.uaa dep ON u.uaa_dependencia = dep.uaa_codigo ";
		sql = sql + "LEFT JOIN dbo.municipio m ON u.mun_codigo = m.mun_codigo ";
		sql = sql + "LEFT JOIN dbo.departamento d ON m.dep_codigo = d.dep_codigo ";
		sql = sql + "WHERE ut.uat_codigo in (" + uaaTipo + ") AND u.uaa_estado = 1 ";
		sql = sql + " AND (u.uaa_codigo like ? OR u.uaa_nombre like ? OR dep.uaa_nombre like ? ";
		sql = sql + " OR s.sed_nombre like ? OR m.mun_nombre like ? ";
		sql = sql + " OR d.dep_nombre like ? OR u.uaa_direccion like ?)";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND u.uaa_dependencia = " + uaaUsuario;
		}

		sql = sql + ") as tabla";
		sql = sql + " where tabla.RowNumber between ? and ?";

		List<Uaa> listaUaa = jdbcTemplate
				.query(sql,
						new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
								"%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
						new RowMapper<Uaa>() {

							public Uaa mapRow(ResultSet rs, int rowNum) throws SQLException {
								Uaa uaa = new Uaa();

								uaa.setAcronimo(rs.getString("uaa_acronimo"));
								uaa.setCentroCostos(rs.getString("uaa_centro_costos"));
								uaa.setCodigo(rs.getInt("uaa_codigo"));
								uaa.setDependencia(rs.getInt("uaa_dependencia"));
								uaa.setDireccion(rs.getString("uaa_direccion"));
								uaa.setEmail(rs.getString("uaa_email"));
								uaa.setEmailPqr(rs.getString("uaa_email_pqr"));
								uaa.setEstado(rs.getInt("uaa_estado"));
								uaa.setFax(rs.getString("uaa_fax"));
								uaa.setNombre(rs.getString("uaa_nombre"));
								uaa.setNombreCorto(rs.getString("uaa_nombre_corto"));
								uaa.setNombreImpresion(rs.getString("uaa_nombre_impresion"));
								uaa.setPagina(rs.getString("uaa_pagina"));
								uaa.setTelefono(rs.getString("uaa_telefono"));
								uaa.setNombreDependencia(rs.getString("depUaa_nombre"));
								uaa.setCodigoTipoUaaDep(rs.getInt("depTipoUaa"));

								Municipio municipio = new Municipio();
								municipio.setCodigo(rs.getInt("mun_codigo"));
								municipio.setNombre(rs.getString("mun_nombre"));
								municipio.setDepartamento(rs.getInt("dep_codigo"));
								uaa.setMunicipio(municipio);

								Sede sede = new Sede();
								sede.setCodigo(rs.getInt("sed_codigo"));
								sede.setNombre(rs.getString("sed_nombre"));
								uaa.setSede(sede);

								UaaTipo uaaTipo = new UaaTipo();
								uaaTipo.setCodigo(rs.getInt("uat_codigo"));
								uaaTipo.setNombre(rs.getString("uat_nombre"));
								uaa.setUaaTipo(uaaTipo);

								return uaa;
							}
						});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaUaa);

		return respuesta;
	}

}
