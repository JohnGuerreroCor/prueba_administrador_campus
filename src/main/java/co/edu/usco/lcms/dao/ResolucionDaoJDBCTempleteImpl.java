/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
import co.edu.usco.lcms.model.Resolucion;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.model.UaaTipo;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@Component
public class ResolucionDaoJDBCTempleteImpl implements ResolucionDao {

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
	 * co.edu.usco.lcms.dao.ResolucionDao#agregarResolucion(co.edu.usco.lcms.
	 * model. Resolucion)
	 */
	@Override
	public boolean agregarResolucion(Resolucion resolucion) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO resolucion (res_fecha, res_dependencia, res_descripcion, res_numero, res_estado)"
					+ " VALUES (?, ?, ?, ?, ?)";

			int resultado = jdbcTemplate.update(sql, new java.sql.Date(resolucion.getFecha().getTime()),
					resolucion.getUaa().getCodigo(), resolucion.getDescripcion().toUpperCase(),
					resolucion.getNumero().toUpperCase(), 1);

			if (resultado > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.ResolucionDao#modificarResolucion(int,
	 * co.edu.usco.lcms.model.Resolucion)
	 */
	@Override
	public boolean modificarResolucion(int id, Resolucion resolucion) throws ParseException {
		// TODO Auto-generated method stub
		try {
			if (id > 0) {
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				// update
				String sql = "UPDATE resolucion SET res_fecha=?, res_dependencia=?, res_descripcion=?, res_numero=?"
						+ " WHERE res_codigo=?";

				int resultado = jdbcTemplate.update(sql, resolucion.getFecha(), resolucion.getUaa().getCodigo(),
						resolucion.getDescripcion().toUpperCase(), resolucion.getNumero().toUpperCase(), id);

				if (resultado > 0) {
					return true;
				}
				return false;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.ResolucionDao#eliminarResolucion(int)
	 */
	@Override
	public boolean eliminarResolucion(int id) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "UPDATE resolucion SET res_estado = 0 WHERE res_codigo=?";
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
	 * co.edu.usco.lcms.dao.ResolucionDao#listarResolucion(java.lang.String)
	 */
	@Override
	public List<Resolucion> listarResolucion(int codigo) {
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

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		String sql = "SELECT r.res_codigo, r.res_fecha, r.res_dependencia, UPPER(r.res_descripcion), ";
		sql = sql + " u.uaa_nombre, UPPER(ISNULL(res_numero,'Sin Número')) AS res_numero, ut.uat_codigo ";
		sql = sql + " FROM dbo.resolucion AS r ";
		sql = sql + " INNER JOIN dbo.uaa u ON r.res_dependencia = u.uaa_codigo";
		sql = sql + " INNER JOIN dbo.uaa_tipo ut ON u.uat_codigo = ut.uat_codigo ";
		sql = sql + " WHERE ut.uat_codigo in (" + uaaTipoNoFormal + ")";

		if (codigo > 0) {
			sql = sql + " AND (r.res_estado = 1 OR r.res_codigo = " + codigo + ")";
		} else {
			sql = sql + " AND r.res_estado = 1 ";
			if (adminGral == false && adminFacultad == true) {
				sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " or u.uaa_codigo = " + uaaUsuario + ")";
			}
		}
		sql = sql + "  ORDER BY r.res_numero DESC";
		System.out.println(sql);
		List<Resolucion> listaResolucion = jdbcTemplate.query(sql, new RowMapper<Resolucion>() {

			public Resolucion mapRow(ResultSet rs, int rowNum) throws SQLException {
				Resolucion resolucion = new Resolucion();
				resolucion.setCodigo(rs.getLong("res_codigo"));
				resolucion.setNumero(rs.getString("res_numero"));
				Uaa uaa = new Uaa();
				uaa.setCodigo(rs.getInt("res_dependencia"));
				resolucion.setUaa(uaa);

				return resolucion;
			}

		});
		return listaResolucion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.ResolucionDao#listarTablaResolucion(java.lang.
	 * String, int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaResolucion(String search, int start, int length, int draw, int posicion,
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

		String[] campos = { "r.res_codigo", "u.uaa_codigo", "res_numero", "r.res_descripcion", "r.res_fecha",
				"u.uat_codigo", "u.uaa_nombre" };

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		String sql = "SELECT COUNT(r.res_codigo) from dbo.resolucion r ";
		sql = sql + " INNER JOIN dbo.uaa u ON r.res_dependencia = u.uaa_codigo";
		sql = sql + " WHERE r.res_estado = 1 AND u.uat_codigo in (" + uaaTipoNoFormal + ")";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
		}

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql
					+ " AND (r.res_codigo like ? OR r.res_descripcion like ? OR r.res_numero like ? OR u.uaa_nombre like ?) ";
			if (adminGral == false && adminFacultad == true) {
				sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
			}
			filtrados = jdbcTemplate.queryForObject(sql,
					new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%" },
					Integer.class);
		}

		sql = "SELECT uat_codigo, res_codigo, res_fecha, res_dependencia, res_descripcion,";
		sql = sql + " uaa_nombre, ISNULL(res_numero,'Sin Número') AS res_numero";
		sql = sql + " from (select row_number() over(order by " + campos[posicion];
		sql = sql + " " + direccion + ") AS RowNumber,";
		sql = sql + " u.uat_codigo, r.res_codigo, r.res_fecha, r.res_dependencia, r.res_descripcion, ";
		sql = sql + " u.uaa_nombre, ISNULL(r.res_numero,'Sin Número') AS res_numero ";
		sql = sql + " FROM dbo.resolucion r ";
		sql = sql + " INNER JOIN dbo.uaa u ON r.res_dependencia = u.uaa_codigo";
		sql = sql + " WHERE r.res_estado = 1 AND (r.res_codigo like ? OR r.res_descripcion ";
		sql = sql + " like ? OR r.res_numero like ? OR u.uaa_nombre like ?) ";
		sql = sql + " AND u.uat_codigo in (" + uaaTipoNoFormal + ")";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " or u.uaa_codigo = " + uaaUsuario + ")";

		}
		sql = sql + ") AS tabla";
		sql = sql + " WHERE tabla.RowNumber BETWEEN ? AND ? ";

		List<Resolucion> listaResolucion = jdbcTemplate.query(sql, new Object[] { "%" + search + "%",
				"%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin }, new RowMapper<Resolucion>() {

					public Resolucion mapRow(ResultSet rs, int rowNum) throws SQLException {
						Resolucion resolucion = new Resolucion();

						resolucion.setCodigo(rs.getLong("res_codigo"));
						resolucion.setFecha(rs.getDate("res_fecha"));
						resolucion.setDescripcion(rs.getString("res_descripcion"));
						resolucion.setNumero(rs.getString("res_numero"));

						Uaa uaa = new Uaa();
						uaa.setCodigo(rs.getInt("res_dependencia"));
						uaa.setNombre(rs.getString("uaa_nombre"));

						UaaTipo uaaTipo = new UaaTipo();
						uaaTipo.setCodigo(rs.getInt("uat_codigo"));

						uaa.setUaaTipo(uaaTipo);
						resolucion.setUaa(uaa);

						return resolucion;
					}

				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaResolucion);

		return respuesta;
	}

	@Override
	public Resolucion buscarResolucion(int codigo) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		String sql = "SELECT TOP 1 r.res_codigo,r.res_fecha,r.res_dependencia,UPPER(r.res_descripcion), ";
		sql = sql + " u.uaa_nombre,UPPER(ISNULL(res_numero,'Sin Número')) AS res_numero,ut.uat_codigo, ";
		sql = sql + " ut.uat_codigo FROM dbo.resolucion AS r ";
		sql = sql + " INNER JOIN dbo.uaa AS u ON r.res_dependencia = u.uaa_codigo ";
		sql = sql + " INNER JOIN dbo.uaa_tipo AS ut ON u.uat_codigo = ut.uat_codigo ";
		sql = sql + " WHERE r.res_codigo = ? AND ut.uat_codigo in (" + uaaTipoNoFormal + ")";

		List<Resolucion> listaResolucion = jdbcTemplate.query(sql, new Object[] { codigo },
				new RowMapper<Resolucion>() {

					public Resolucion mapRow(ResultSet rs, int rowNum) throws SQLException {
						Resolucion resolucion = new Resolucion();
						resolucion.setCodigo(rs.getLong("res_codigo"));
						resolucion.setNumero(rs.getString("res_numero"));

						Uaa uaa = new Uaa();
						uaa.setCodigo(rs.getInt("res_dependencia"));

						UaaTipo uaaTipo = new UaaTipo();
						uaaTipo.setCodigo(rs.getInt("uat_codigo"));
						uaa.setUaaTipo(uaaTipo);

						resolucion.setUaa(uaa);
						return resolucion;
					}

				});
		if (listaResolucion.size() > 0) {
			return listaResolucion.get(0);
		} else {
			return null;
		}
	}

}
