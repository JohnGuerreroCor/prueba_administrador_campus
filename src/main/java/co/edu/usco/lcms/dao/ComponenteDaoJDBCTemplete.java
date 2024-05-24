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

import co.edu.usco.lcms.model.Componente;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author jankarlos
 *
 */
@Component
public class ComponenteDaoJDBCTemplete implements ComponenteDao {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.ComponenteDao#agregarComponente(co.edu.usco.lcms.model.
	 * Componente)
	 */
	@Override
	public boolean agregarComponente(Componente componente) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO componente (com_nombre, com_acronimo)";
			sql = sql + " VALUES (?, ?)";
			int resultado = jdbcTemplate.update(sql, componente.getNombreComponente(),
					componente.getAcronimoComponente());

			if (resultado > 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.ComponenteDao#modificarComponente(int,
	 * co.edu.usco.lcms.model.Componente)
	 */
	@Override
	public boolean modificarComponente(int id, Componente componente) {
		// TODO Auto-generated method stub
		if (id > 0) {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			// update
			String sql = "UPDATE componente SET com_nombre = ?, com_acronimo = ?";
			sql = sql + " WHERE com_codigo=?";
			int resultado = jdbcTemplate.update(sql, componente.getNombreComponente(),
					componente.getAcronimoComponente(), id);

			if (resultado > 0) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.ComponenteDao#eliminarComponente(int)
	 */
	@Override
	public boolean eliminarComponente(int id) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		//String sql = "DELETE FROM componente WHERE com_codigo=?";
		String sql = "UPDATE componente SET com_estado = 0 WHERE com_codigo=?";
		int resultado = jdbcTemplate.update(sql, id);

		if (resultado > 0) {
			return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.ComponenteDao#listarComponente(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Componente> listarComponente(int id) {
		// TODO Auto-generated method stub

		Object[] obj = new Object[] {};

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "SELECT c.com_codigo, c.com_nombre, c.com_acronimo FROM componente c ";
		if (id != 0) {
			sql = sql + " WHERE c.com_codigo = ? ";
			obj = new Object[] { id };
		}
		sql = sql + " ORDER BY c.com_nombre";
		List<Componente> listaComponente = jdbcTemplate.query(sql, obj, new RowMapper<Componente>() {

			public Componente mapRow(ResultSet rs, int rowNum) throws SQLException {
				Componente componente = new Componente();
				componente.setAcronimoComponente(rs.getString("com_acronimo"));
				componente.setCodigoComponente(rs.getInt("com_codigo"));
				componente.setNombreComponente(rs.getString("com_nombre"));
				return componente;
			}

		});
		return listaComponente;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.ComponenteDao#listarTablaComponente(java.lang.String,
	 * int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaComponente(String search, int start, int length, int draw) {
		// TODO Auto-generated method stub
		JSONRespuesta respuesta = new JSONRespuesta();

		if (start == 0) {
			start = 1;
		}

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT count(c.com_codigo) FROM componente c ";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " WHERE c.com_nombre LIKE ? ";
			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%" }, Integer.class);
		}

		sql = "SELECT com_codigo, com_nombre, com_acronimo";
		sql = sql + " FROM (select row_number() over(order by c.com_nombre ASC) AS RowNumber,";
		sql = sql + "c.com_codigo, c.com_nombre, c.com_acronimo FROM componente c ";
		sql = sql + " WHERE c.com_nombre like ? ";

		sql = sql + ") as tabla";
		sql = sql + " WHERE tabla.RowNumber BETWEEN ? AND ? ";

		List<Componente> listaComponente = jdbcTemplate.query(sql, new Object[] { "%" + search + "%", start, fin },
				new RowMapper<Componente>() {

					public Componente mapRow(ResultSet rs, int rowNum) throws SQLException {
						Componente componente = new Componente();

						componente.setAcronimoComponente(rs.getString("com_acronimo"));
						componente.setCodigoComponente(rs.getInt("com_codigo"));
						componente.setNombreComponente(rs.getString("com_nombre"));
						return componente;
					}

				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaComponente);

		return respuesta;
	}

}
