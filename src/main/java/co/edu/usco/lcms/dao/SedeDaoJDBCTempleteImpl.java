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
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Sede;

/**
 * @author jankarlos
 *
 */
@Component
public class SedeDaoJDBCTempleteImpl implements SedeDao {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.SedeDao#agregarSede(co.edu.usco.lcms.model.Sede)
	 */
	@Override
	public boolean agregarSede(Sede sede) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO sede (sed_codigo, sed_nombre, mun_codigo, sed_estado)" + " VALUES (?, ?, ?, ?)";
			int resultado = jdbcTemplate.update(sql, sede.getCodigo(), sede.getNombre(), sede.getCodigoMunicipio(),
					sede.getEstado());

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
	 * @see co.edu.usco.lcms.dao.SedeDao#modificarSede(int,
	 * co.edu.usco.lcms.model.Sede)
	 */
	@Override
	public boolean modificarSede(int id, Sede sede) {
		// TODO Auto-generated method stub
		try {
			if (id > 0) {
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				// update
				String sql = "UPDATE sede SET sed_nombre=?, mun_codigo=?" + " WHERE sed_codigo=?";

				int resultado = jdbcTemplate.update(sql, sede.getNombre(), sede.getCodigoMunicipio(), id);

				if (resultado > 0) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.SedeDao#eliminarSede(int)
	 */
	@Override
	public boolean eliminarSede(int id) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "DELETE FROM sede WHERE sed_codigo=?";
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
	 * @see co.edu.usco.lcms.dao.SedeDao#listarSede(int)
	 */
	@Override
	public List<Sede> listarSede(int estado, int id) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] obj = new Object[] {};
		String sql = "SELECT s.sed_nombre, s.sed_estado, s.sed_codigo, m.mun_nombre, m.mun_codigo,";
		sql = sql + " d.dep_nombre, d.dep_codigo  from sede s, municipio m, departamento d";
		sql = sql + " where s.mun_codigo =  m.mun_codigo AND m.dep_codigo = d.dep_codigo AND s.sed_estado = ? ";
		if (id > 0) {
			sql = sql + " AND sed_codigo = ? ";
			obj = new Object[] { estado, id };
		} else {
			obj = new Object[] { estado };
		}
		sql = sql + " order by s.sed_nombre";

		List<Sede> listaSede = jdbcTemplate.query(sql, obj, new RowMapper<Sede>() {

			public Sede mapRow(ResultSet rs, int rowNum) throws SQLException {

				Sede sede = new Sede();
				sede.setCodigo(rs.getInt("sed_codigo"));
				sede.setCodigoDepartamento(rs.getInt("dep_codigo"));
				sede.setCodigoMunicipio(rs.getInt("mun_codigo"));
				sede.setNombre(rs.getString("sed_nombre"));
				sede.setEstado(rs.getString("sed_estado"));
				sede.setNombreDepartamento(rs.getString("dep_nombre"));
				sede.setNombreMunicipio(rs.getString("mun_nombre"));

				return sede;
			}

		});
		return listaSede;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.SedeDao#listarTablaSede(java.lang.String, int,
	 * int, int)
	 */
	@Override
	public JSONRespuesta listarTablaSede(String search, int start, int length, int draw) {
		// TODO Auto-generated method stub
		JSONRespuesta respuesta = new JSONRespuesta();

		if (start == 0) {
			start = 1;
		}

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT COUNT(*) FROM	sede, municipio, departamento WHERE ";
		sql = sql + "sede.mun_codigo =  municipio.mun_codigo AND municipio.dep_codigo = departamento.dep_codigo";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " and sede.sed_nombre like ? ";
			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { '%' + search + '%' }, Integer.class);
		}

		sql = "SELECT sed_nombre, sed_estado, sed_codigo, mun_nombre, mun_codigo, dep_nombre, dep_codigo";
		sql = sql + " from (select row_number() over(order by s.sed_nombre ASC) AS RowNumber,";
		sql = sql
				+ " s.sed_nombre, s.sed_estado, s.sed_codigo, m.mun_nombre, m.mun_codigo, d.dep_nombre, d.dep_codigo ";
		sql = sql + " from sede s, municipio m, departamento d where ";
		sql = sql + "s.mun_codigo =  m.mun_codigo AND m.dep_codigo = d.dep_codigo";
		sql = sql + " AND s.sed_nombre like ? ";

		sql = sql + ") as tabla";
		sql = sql + " where tabla.RowNumber between ? and ?";

		List<Sede> listaSede = jdbcTemplate.query(sql, new Object[] { '%' + search + '%', start, fin },
				new RowMapper<Sede>() {

					public Sede mapRow(ResultSet rs, int rowNum) throws SQLException {
						Sede sede = new Sede();

						sede.setCodigo(rs.getInt("sed_codigo"));
						sede.setCodigoDepartamento(rs.getInt("dep_codigo"));
						sede.setCodigoMunicipio(rs.getInt("mun_codigo"));
						sede.setNombre(rs.getString("sed_nombre"));
						sede.setEstado(rs.getString("sed_estado"));
						sede.setNombreDepartamento(rs.getString("dep_nombre"));
						sede.setNombreMunicipio(rs.getString("mun_nombre"));

						return sede;
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaSede);

		return respuesta;
	}

}
