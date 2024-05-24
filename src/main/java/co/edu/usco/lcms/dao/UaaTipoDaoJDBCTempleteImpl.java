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
import co.edu.usco.lcms.model.UaaTipo;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author ankarlos
 *
 */
@Component
public class UaaTipoDaoJDBCTempleteImpl implements UaaTipoDao {

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
	 * co.edu.usco.lcms.dao.UaaTipoDao#agregarUaaTipo(co.edu.usco.lcms.model.
	 * UaaTipo)
	 */
	@Override
	public boolean agregarUaaTipo(UaaTipo uaaTipo) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO uaa_tipo (uat_tmp, uat_nombre)" + " VALUES (?, ?)";
			int resultado = jdbcTemplate.update(sql, uaaTipo.getTemporal(), uaaTipo.getNombre());

			if (resultado > 0) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.UaaTipoDao#modificarUaaTipo(int,
	 * co.edu.usco.lcms.model.UaaTipo)
	 */
	@Override
	public boolean modificarUaaTipo(int id, UaaTipo uaaTipo) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// update
		String sql = "UPDATE uaa_tipo SET uat_tmp=?, uat_nombre=?" + " WHERE uat_codigo=?";
		int resultado = jdbcTemplate.update(sql, uaaTipo.getTemporal(), uaaTipo.getNombre(), id);

		if (resultado > 0) {

			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.UaaTipoDao#eliminarUaaTipo(int)
	 */
	@Override
	public boolean eliminarUaaTipo(int id) {
		// TODO Auto-generated method stub
		/*
		 * JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); String sql
		 * = "DELETE FROM uaa_tipo WHERE uat_codigo=?"; int resultado =
		 * jdbcTemplate.update(sql, id);
		 * 
		 * if (resultado > 0) { return true; }
		 */
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.UaaTipoDao#listarUaaTipo(int)
	 */
	@Override
	public List<UaaTipo> listarUaaTipo(int codigo) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String valorParamentro = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();
		String codigoTipoUaaFac = webParametroDao.listarWebParametro(constantes.WEP_FACULTAD_TIPO).get(0).getValor();

		String sql = "SELECT uat_codigo, uat_nombre, uat_tmp FROM uaa_tipo";
		if (codigo != 1) {
			sql = sql + " WHERE uat_codigo in (" + valorParamentro + ")";
		} else {

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
				sql = sql + " WHERE uat_codigo in (" + codigoTipoUaaFac + ")";
			}

		}
		sql = sql + " ORDER BY uat_nombre DESC";

		List<UaaTipo> listaUaaTipo = jdbcTemplate.query(sql, new RowMapper<UaaTipo>() {

			public UaaTipo mapRow(ResultSet rs, int rowNum) throws SQLException {
				UaaTipo uaaTipo = new UaaTipo();

				uaaTipo.setCodigo(rs.getInt("uat_codigo"));
				uaaTipo.setNombre(rs.getString("uat_nombre"));
				uaaTipo.setTemporal(rs.getInt("uat_tmp"));
				if (rs.getInt("uat_tmp") == 1) {
					uaaTipo.setTemporalNombre("Activo");
				} else {
					uaaTipo.setTemporalNombre("Inactivo");
				}
				return uaaTipo;
			}

		});
		return listaUaaTipo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.UaaTipoDao#listarTablaUaaTipo(java.lang.String,
	 * int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaUaaTipo(String search, int start, int length, int draw) {
		// TODO Auto-generated method stub
		JSONRespuesta respuesta = new JSONRespuesta();
		String valorParamentro = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();
		if (start == 0) {
			start = 1;
		}

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "select count(*) from uaa_tipo";
		sql = sql + " WHERE uat_codigo in (" + valorParamentro + ")";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " AND uat_nombre like ? ";
			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%" }, Integer.class);
		}

		sql = "select uat_codigo, uat_nombre, uat_tmp";
		sql = sql + " from (select row_number() over(order by uat_nombre ASC) AS RowNumber,";
		sql = sql + " uat_codigo, uat_nombre, uat_tmp";
		sql = sql + " from uaa_tipo";
		sql = sql + " WHERE uat_codigo in (" + valorParamentro + ")";
		sql = sql + " AND uat_nombre like ? ";
		sql = sql + ") as tabla";
		sql = sql + " where tabla.RowNumber between ? and ?";

		List<UaaTipo> listaUaaTipo = jdbcTemplate.query(sql, new Object[] { "%" + search + "%", start, fin },
				new RowMapper<UaaTipo>() {

					public UaaTipo mapRow(ResultSet rs, int rowNum) throws SQLException {

						UaaTipo uaaTipo = new UaaTipo();
						uaaTipo.setCodigo(rs.getInt("uat_codigo"));
						uaaTipo.setNombre(rs.getString("uat_nombre"));
						uaaTipo.setTemporal(rs.getInt("uat_tmp"));
						if (rs.getInt("uat_tmp") == 1) {
							uaaTipo.setTemporalNombre("Activo");
						} else {
							uaaTipo.setTemporalNombre("Inactivo");
						}
						return uaaTipo;
					}

				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaUaaTipo);
		return respuesta;

	}

}
