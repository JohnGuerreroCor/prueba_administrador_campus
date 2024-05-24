/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Periodo;

/**
 * @author Jankarlos Diaz Vieda
 *
 */
@Component
public class CalendarioDaoJDBCTempleteImpl implements CalendarioDao {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.CalendarioDao#agregarCalendario(co.edu.usco.lcms.
	 * model. Calendario)
	 */
	@Override
	public Calendario consultarCalendario(int codigoCalendario) {
		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "SELECT TOP 1 cal_codigo, cal_nombre " + "FROM calendario " + "WHERE cal_codigo = "
					+ codigoCalendario;
			List<Calendario> listaCalendario = jdbcTemplate.query(sql, new RowMapper<Calendario>() {

				public Calendario mapRow(ResultSet rs, int rowNum) throws SQLException {
					Calendario calendario = new Calendario();
					calendario.setCodigo(rs.getInt("cal_codigo"));
					calendario.setNombre(rs.getString("cal_nombre"));
					return calendario;
				}
			});
			if (listaCalendario.size() > 0) {
				return listaCalendario.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Calendario> listarCalendario(Timestamp fechaInicio) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT TOP 1 c.cal_codigo,c.cal_nombre, p.per_fecha_inicio, p.per_fecha_fin "
				+ " FROM calendario c, periodo p, oferta_academica o WHERE c.cal_nombre like '%C%' and c.per_codigo = p.per_codigo "
				+ " AND ? BETWEEN p.per_fecha_inicio AND p.per_fecha_fin ORDER BY c.cal_nombre ASC";

		List<Calendario> listaCaledarioOferta = jdbcTemplate.query(sql, new Object[] { fechaInicio },
				new RowMapper<Calendario>() {

					public Calendario mapRow(ResultSet rs, int rowNum) throws SQLException {

						Calendario calendario = new Calendario();
						calendario.setCodigo(rs.getInt("cal_codigo"));
						Periodo periodo = new Periodo();
						periodo.setFechaInicio(rs.getDate("per_fecha_inicio"));
						periodo.setFechaFin(rs.getDate("per_fecha_fin"));
						calendario.setPeriodo(periodo);
						return calendario;
					}

				});
		return listaCaledarioOferta;
	}

	@Override
	public boolean agregarCalendario(Calendario calendario) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO calendario (cal_codigo, cal_nombre, per_codigo)" + " VALUES (?, ?, ?)";
			int resultado = jdbcTemplate.update(sql, calendario.getCodigo(), calendario.getNombre(),
					calendario.getPeriodo());

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
	 * @see co.edu.usco.lcms.dao.CalendarioDao#modificarCalendario(int,
	 * co.edu.usco.lcms.model.Calendario)
	 */
	@Override
	public boolean modificarCalendario(int id, Calendario calendario) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// update
		String sql = "UPDATE calendario " + "SET cal_nombre=?, per_codigo=? " + "WHERE cal_codigo=?";
		int resultado = jdbcTemplate.update(sql, calendario.getNombre(), calendario.getPeriodo(), id);

		if (resultado > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.CalendarioDao#eliminarCalendario(int)
	 */
	@Override
	public boolean eliminarCalendario(int id) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "UPDATE calendario " + "SET cal_estado=0" + "WHERE cal_codigo=?";
		int resultado = jdbcTemplate.update(sql, id);

		if (resultado > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.CalendarioDao#listarTablaCalendario(java.lang.
	 * String, int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaCalendario(String search, int start, int length, int draw) {
		// TODO Auto-generated method stub
		JSONRespuesta respuesta = new JSONRespuesta();

		if (start == 0) {
			start = 1;
		}

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT count(cal_codigo) FROM calendario";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " WHERE cal_nombre like ?";
			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%" }, Integer.class);
		}

		sql = "SELECT cal_codigo, cal_nombre, per_codigo";
		sql = sql + " FROM (select row_number() over(order by cal_nombre ASC) AS RowNumber,";
		sql = sql + " cal_codigo, cal_nombre, per_codigo";
		sql = sql + " FROM calendario";
		sql = sql + " WHERE cal_nombre LIKE ? ";

		sql = sql + ") as tabla";
		sql = sql + " WHERE tabla.RowNumber between ? and ?";

		List<Calendario> listaCalendario = jdbcTemplate.query(sql, new Object[] { "%" + search + "%", start, fin },
				new RowMapper<Calendario>() {

					public Calendario mapRow(ResultSet rs, int rowNum) throws SQLException {
						Calendario calendario = new Calendario();

						calendario.setCodigo(rs.getInt("cal_codigo"));
						calendario.setNombre(rs.getString("cal_nombre"));
						Periodo periodo = new Periodo();
						periodo.setCodigo(rs.getInt("per_codigo"));
						calendario.setPeriodo(periodo);

						return calendario;
					}

				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaCalendario);

		return respuesta;
	}

	@Override
	public List<Calendario> listadoCalendarios() {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT TOP 20 c.cal_codigo,c.cal_nombre, p.per_fecha_inicio, p.per_fecha_fin";
		sql = sql + " FROM calendario c, periodo p WHERE c.per_codigo = p.per_codigo";
		sql = sql + " ORDER BY c.cal_codigo DESC";

		List<Calendario> listaCaledarioOferta = jdbcTemplate.query(sql, new RowMapper<Calendario>() {

			public Calendario mapRow(ResultSet rs, int rowNum) throws SQLException {

				Calendario calendario = new Calendario();
				calendario.setCodigo(rs.getInt("cal_codigo"));
				calendario.setNombre(rs.getString("cal_nombre"));
				Periodo periodo = new Periodo();
				periodo.setFechaInicio(rs.getDate("per_fecha_inicio"));
				periodo.setFechaFin(rs.getDate("per_fecha_fin"));
				calendario.setPeriodo(periodo);
				return calendario;
			}

		});
		return listaCaledarioOferta;
	}

}
