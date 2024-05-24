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

import co.edu.usco.lcms.model.Caracter;
import co.edu.usco.lcms.model.JSONRespuesta;

/**
 * @author jankarlos
 *
 */
@Component
public class CaracterDaoJDBCTempleteImpl implements CaracterDao {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.CaracterDao#agregarCaracter(co.edu.usco.lcms.model.
	 * Caracter)
	 */
	@Override
	public boolean agregarCaracter(Caracter caracter) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO caracter (car_codigo, car_nombre)" + " VALUES (?, ?)";
			int resultado = jdbcTemplate.update(sql, caracter.getCodigo(), caracter.getNombre());

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
	 * @see co.edu.usco.lcms.dao.CaracterDao#modificarCaracter(int,
	 * co.edu.usco.lcms.model.Caracter)
	 */
	@Override
	public boolean modificarCaracter(String id, Caracter caracter) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// update
		String sql = "UPDATE caracter SET car_nombre=?" + " WHERE car_codigo=?";
		int resultado = jdbcTemplate.update(sql, caracter.getNombre(), id);

		if (resultado > 0) {
			return false;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.CaracterDao#eliminarCaracter(int)
	 */
	@Override
	public boolean eliminarCaracter(String id) {
		// TODO Auto-generated method stub
		/*JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "DELETE FROM caracter WHERE car_codigo=?";
		int resultado = jdbcTemplate.update(sql, id);

		if (resultado > 0) {
			return true;
		}*/
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.CaracterDao#listarCaracter(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Caracter> listarCaracter() {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT car_codigo, car_nombre "
				+ "FROM caracter "
				+ "ORDER BY car_nombre";
		List<Caracter> listaCaracter = jdbcTemplate.query(sql, new RowMapper<Caracter>() {

			public Caracter mapRow(ResultSet rs, int rowNum) throws SQLException {
				Caracter caracter = new Caracter();
				caracter.setCodigo(rs.getString("car_codigo"));
				caracter.setNombre(rs.getString("car_nombre"));
				return caracter;
			}

		});
		return listaCaracter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.CaracterDao#listarTablaCaracter(java.lang.String,
	 * int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaCaracter(String search, int start, int length, int draw) {
		// TODO Auto-generated method stub
		JSONRespuesta respuesta = new JSONRespuesta();

		if (start == 0) {
			start = 1;
		}

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT count(car_codigo) FROM caracter";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " WHERE car_nombre LIKE ? ";
			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%" }, Integer.class);
		}

		sql = "SELECT car_codigo, car_nombre";
		sql = sql + " FROM (select row_number() over(order by car_nombre ASC) AS RowNumber,";
		sql = sql + " car_codigo, car_nombre";
		sql = sql + " FROM caracter";
		sql = sql + " WHERE car_nombre LIKE ? ";

		sql = sql + ") as tabla";
		sql = sql + " WHERE tabla.RowNumber between ? AND ?";

		List<Caracter> listaCaracter = jdbcTemplate.query(sql, new Object[] { "%" + search + "%", start, fin },
				new RowMapper<Caracter>() {

					public Caracter mapRow(ResultSet rs, int rowNum) throws SQLException {
						Caracter caracter = new Caracter();

						caracter.setCodigo(rs.getString("car_codigo"));
						caracter.setNombre(rs.getString("car_nombre"));

						return caracter;
					}

				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaCaracter);

		return respuesta;
	}

}
