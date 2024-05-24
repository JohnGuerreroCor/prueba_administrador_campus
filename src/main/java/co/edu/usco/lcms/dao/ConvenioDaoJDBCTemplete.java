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

import co.edu.usco.lcms.model.Convenio;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Resolucion;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.model.UaaTipo;

/**
 * @author jankarlos
 *
 */
@Component
public class ConvenioDaoJDBCTemplete implements ConvenioDao {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.ConvenioDao#agregarConvenio(co.edu.usco.lcms.model.
	 * Convenio)
	 */
	@Override
	public boolean agregarConvenio(Convenio convenio) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO convenio (con_descripcion, con_documento, con_entidad, con_fecha_creacion, con_fecha_terminacion)"
					+ " VALUES (?, ?, ?, ?, ?)";
			int resultado = jdbcTemplate.update(sql, convenio.getDescripcion(), convenio.getResolucion().getCodigo(),
					convenio.getUaa().getCodigo(), convenio.getFecha_creacion(), convenio.getFecha_terminacion());

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
	 * @see co.edu.usco.lcms.dao.ConvenioDao#modificarConvenio(int,
	 * co.edu.usco.lcms.model.Convenio)
	 */
	@Override
	public boolean modificarConvenio(int id, Convenio convenio) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			// update
			String sql = "UPDATE convenio SET  con_descripcion=?, con_documento=?, con_entidad=?, con_fecha_creacion=?, con_fecha_terminacion=?"
					+ " WHERE con_codigo=?";
			int resultado = jdbcTemplate.update(sql, convenio.getDescripcion(), convenio.getResolucion().getCodigo(),
					convenio.getUaa().getCodigo(), convenio.getFecha_creacion(), convenio.getFecha_terminacion(), id);

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
	 * @see co.edu.usco.lcms.dao.ConvenioDao#eliminarConvenio(int)
	 */
	@Override
	public boolean eliminarConvenio(int id) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "UPDATE convenio SET con_estado = 0 WHERE con_codigo=?";
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
	 * @see co.edu.usco.lcms.dao.ConvenioDao#listarConvenio(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Convenio> listarConvenio() {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "SELECT c.con_codigo, c.con_descripcion FROM convenio c "
				+ " WHERE c.con_estado = 1 ORDER BY c.con_descripcion";
		List<Convenio> listaConvenio = jdbcTemplate.query(sql, new RowMapper<Convenio>() {

			public Convenio mapRow(ResultSet rs, int rowNum) throws SQLException {
				Convenio convenio = new Convenio();
				convenio.setCodigo(rs.getInt("con_codigo"));
				convenio.setDescripcion(rs.getString("con_descripcion"));

				return convenio;
			}

		});
		return listaConvenio;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.ConvenioDao#listarTablaConvenio(java.lang.String,
	 * int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarTablaConvenio(String search, int start, int length, int draw, int posicion,String direccion) {
		// TODO Auto-generated method stub
		
		String[] campos = {"c.con_codigo", "c.con_descripcion", "r.res_numero", "u.uaa_nombre","c.con_fecha_creacion", "c.con_fecha_terminacion","u.uat_codigo"};

		JSONRespuesta respuesta = new JSONRespuesta();

		if (start == 0) {
			start = 1;
		}

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT count(*) "
				+ "FROM dbo.convenio AS c "
				+ "LEFT JOIN dbo.resolucion AS r ON r.res_codigo = c.con_documento "
				+ "LEFT JOIN dbo.uaa AS u ON u.uaa_codigo = c.con_entidad";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " WHERE c.con_descripcion LIKE ? or r.res_numero LIKE ? or u.uaa_nombre like ?";
			filtrados = jdbcTemplate.queryForObject(sql,
					new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%" }, Integer.class);
		}
		sql = "SELECT con_codigo, con_descripcion, res_codigo, res_numero, uat_codigo, uaa_codigo, uaa_nombre, "
				+ "con_fecha_creacion, con_fecha_terminacion "
				+ "FROM (select row_number() over(order by " + campos[posicion] + " " + direccion+ ") AS RowNumber,"
				+ " c.con_codigo, c.con_descripcion, r.res_codigo, r.res_numero, u.uat_codigo, u.uaa_codigo, u.uaa_nombre, "
				+ "c.con_fecha_creacion, c.con_fecha_terminacion "
				+ "FROM dbo.convenio AS c LEFT JOIN dbo.resolucion AS r ON r.res_codigo = c.con_documento "
				+ "LEFT JOIN dbo.uaa AS u ON u.uaa_codigo = c.con_entidad";
		sql = sql + " where (c.con_descripcion like ? or r.res_numero like ? or u.uaa_nombre like ? )";
		sql = sql + " and c.con_estado = 1) as tabla";
		sql = sql + " where tabla.RowNumber between ? and ?";

		List<Convenio> convenio = jdbcTemplate.query(sql,
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Convenio>() {

					public Convenio mapRow(ResultSet rs, int rowNum) throws SQLException {
						Convenio convenio = new Convenio();

						convenio.setCodigo(rs.getInt("con_codigo"));
						convenio.setDescripcion(rs.getString("con_descripcion"));

						Resolucion resolucion = new Resolucion();
						resolucion.setCodigo(rs.getInt("res_codigo"));
						resolucion.setNumero(rs.getString("res_numero"));
						convenio.setResolucion(resolucion);

						Uaa uaa = new Uaa();
						uaa.setCodigo(rs.getInt("uaa_codigo"));
						uaa.setNombre(rs.getString("uaa_nombre"));
						
						UaaTipo uaaTipo = new UaaTipo();
						uaaTipo.setCodigo(rs.getInt("uat_codigo"));
						uaa.setUaaTipo(uaaTipo);
						convenio.setUaa(uaa);

						convenio.setFecha_creacion(rs.getDate("con_fecha_creacion"));
						convenio.setFecha_terminacion(rs.getDate("con_fecha_terminacion"));

						return convenio;
					}

				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(convenio);

		return respuesta;
	}

}
