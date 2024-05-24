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
import co.edu.usco.lcms.model.Nucleo;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author jankarlos
 *
 */
@Component
public class NucleoDaoJDBCTempleteImpl implements NucleoDao {

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
	 * co.edu.usco.lcms.dao.NucleoDao#agregarNucleo(co.edu.usco.lcms.model.Nucleo)
	 */
	@Override
	public boolean agregarNucleo(Nucleo nucleo) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO nucleo (nuc_acronimo, nuc_nombre, nuc_estado)" + " VALUES (?, ?, ?)";
			int resultado = jdbcTemplate.update(sql, nucleo.getAcronimo(), nucleo.getNombre(), nucleo.getEstado());

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
	 * @see co.edu.usco.lcms.dao.NucleoDao#modificarNucleo(int,
	 * co.edu.usco.lcms.model.Nucleo)
	 */
	@Override
	public boolean modificarNucleo(int id, Nucleo nucleo) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// update
		String sql = "UPDATE nucleo SET nuc_acronimo=?, nuc_nombre=?, nuc_estado=?" + " WHERE nuc_codigo=?";
		int resultado = jdbcTemplate.update(sql, nucleo.getAcronimo(), nucleo.getNombre(), nucleo.getEstado(), id);

		if (resultado > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.NucleoDao#eliminarNucleo(int)
	 */
	@Override
	public boolean eliminarNucleo(int id) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "UPDATE nucleo SET nuc_estado=0 WHERE nuc_codigo=?";
		int resultado = jdbcTemplate.update(sql, id);

		if (resultado > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.NucleoDao#listarNucleo(int)
	 */
	@Override
	public List<Nucleo> listarNucleo(int id) {
		// TODO Auto-generated method stub
		Object[] obj = new Object[] {};

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String valorParamentro = webParametroDao.listarWebParametro(constantes.WEP_NUCLEO).get(0).getValor();
		String sql = "select nuc_codigo, nuc_nombre, nuc_acronimo, nuc_estado ";
		sql = sql + " from nucleo where nuc_codigo in ("+valorParamentro+") ";

		if (id != 0) {
			sql = sql + " AND nuc_codigo = ? ";
			obj = new Object[] { id };
		}
		sql = sql + " ORDER BY nuc_nombre";
		List<Nucleo> listaNucleo = jdbcTemplate.query(sql, obj, new RowMapper<Nucleo>() {

			public Nucleo mapRow(ResultSet rs, int rowNum) throws SQLException {
				Nucleo nucleo = new Nucleo();

				nucleo.setCodigo(rs.getInt("nuc_codigo"));
				nucleo.setAcronimo(rs.getString("nuc_acronimo"));
				nucleo.setNombre(rs.getString("nuc_nombre"));
				nucleo.setEstado(rs.getString("nuc_estado"));

				return nucleo;
			}

		});
		return listaNucleo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.NucleoDao#listarTablaNucleo(java.lang.String, int,
	 * int, int)
	 */
	@Override
	public JSONRespuesta listarTablaNucleo(String search, int start, int length, int draw) {
		// TODO Auto-generated method stub
		JSONRespuesta respuesta = new JSONRespuesta();

		if (start == 0) {
			start = 1;
		}

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "select count(nuc_codigo) from nucleo";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " where nuc_nombre like ? ";
			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%" }, Integer.class);
		}

		sql = "select nuc_codigo, nuc_nombre, nuc_acronimo, nuc_estado";
		sql = sql + " from (select row_number() over(order by nuc_nombre ASC) AS RowNumber,";
		sql = sql + " nuc_codigo, nuc_nombre, nuc_acronimo, nuc_estado";
		sql = sql + " from nucleo";
		sql = sql + " where nuc_nombre like ? ";

		sql = sql + ") as tabla";
		sql = sql + " where tabla.RowNumber between ? and ? ";

		List<Nucleo> listaNucleo = jdbcTemplate.query(sql, new Object[] { "%" + search + "%", start, fin },
				new RowMapper<Nucleo>() {

					public Nucleo mapRow(ResultSet rs, int rowNum) throws SQLException {
						Nucleo nucleo = new Nucleo();

						nucleo.setCodigo(rs.getInt("nuc_codigo"));
						nucleo.setAcronimo(rs.getString("nuc_acronimo"));
						nucleo.setNombre(rs.getString("nuc_nombre"));
						nucleo.setEstado(rs.getString("nuc_estado"));

						return nucleo;
					}

				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaNucleo);

		return respuesta;
	}

}
