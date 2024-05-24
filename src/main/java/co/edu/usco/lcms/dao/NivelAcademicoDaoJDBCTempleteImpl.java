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

import co.edu.usco.lcms.model.Formalidad;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.NivelAcademico;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author jankarlos
 *
 */
@Component
public class NivelAcademicoDaoJDBCTempleteImpl implements NivelAcademicoDao {

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
	 * co.edu.usco.lcms.dao.NivelAcademicoDao#agregarNivelAcademico(org.usco.
	 * lcms. model.NivelAcademico)
	 */
	@Override
	public boolean agregarNivelAcademico(NivelAcademico nivel) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO nivel_academico (nia_nombre, nia_orden, for_codigo, snies_codigo)"
					+ " VALUES (?, ?, ?, ?)";
			int resultado = jdbcTemplate.update(sql, nivel.getNombre(), nivel.getOrden(),
					nivel.getFormalidad().getCodigo(), nivel.getSnies());

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
	 * @see co.edu.usco.lcms.dao.NivelAcademicoDao#modificarNivelAcademico(int,
	 * co.edu.usco.lcms.model.NivelAcademico)
	 */
	@Override
	public boolean modificarNivelAcademico(int id, NivelAcademico nivel) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			// update
			String sql = "UPDATE nivel_academico SET nia_nombre=?, nia_orden=?, for_codigo=?, snies_codigo=?"
					+ " WHERE nia_codigo=?";
			int resultado = jdbcTemplate.update(sql, nivel.getNombre(), nivel.getOrden(),
					nivel.getFormalidad().getCodigo(), nivel.getSnies(), id);

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
	 * @see co.edu.usco.lcms.dao.NivelAcademicoDao#eliminarNivelAcademico(int)
	 */
	@Override
	public boolean eliminarNivelAcademico(int id) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "UPDATE nivel_academico SET nia_estado = 0 WHERE nia_codigo=?";
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
	 * @see co.edu.usco.lcms.dao.NivelAcademicoDao#listarNivelAcademico(int)
	 */
	@Override
	public List<NivelAcademico> listarNivelAcademico(int id) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String valorParamentro = webParametroDao.listarWebParametro(constantes.WEP_FORMALIDAD).get(0).getValor();
		String nivelAcademicoCodigo = webParametroDao.listarWebParametro(constantes.WEP_NIVEL_ACADEMICO).get(0)
				.getValor();
		String sql = "SELECT n.nia_codigo, n.nia_nombre ";
		sql = sql + " FROM nivel_academico n ";
		sql = sql + " INNER JOIN formalidad f ON n.for_codigo = f.for_codigo ";
		sql = sql + " WHERE f.for_codigo = " + valorParamentro + " AND n.nia_estado = 1 AND n.nia_codigo in ("
				+ nivelAcademicoCodigo + ")";
		sql = sql + "ORDER BY n.nia_nombre";
		List<NivelAcademico> listaNivelAcademico = jdbcTemplate.query(sql, new RowMapper<NivelAcademico>() {

			public NivelAcademico mapRow(ResultSet rs, int rowNum) throws SQLException {
				NivelAcademico nivelAcademico = new NivelAcademico();
				nivelAcademico.setCodigo(rs.getInt("nia_codigo"));
				nivelAcademico.setNombre(rs.getString("nia_nombre"));

				return nivelAcademico;
			}

		});
		return listaNivelAcademico;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.NivelAcademicoDao#listarTablaNivelAcademico(java.
	 * lang. String, int, int, int)
	 */
	@Override
	public JSONRespuesta listarTablaNivelAcademico(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub

		String[] campos = { "n.nia_codigo", "n.nia_nombre", "n.nia_orden", "f.for_nombre", "n.snies_codigo",
				"f.for_codigo" };

		JSONRespuesta respuesta = new JSONRespuesta();

		if (start == 0) {
			start = 1;
		}

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "SELECT count(n.nia_codigo) " + " FROM nivel_academico n "
				+ " INNER JOIN formalidad f ON n.for_codigo = f.for_codigo " + " WHERE n.nia_estado = 1 ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " AND nia_nombre LIKE ? ";
			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%" }, Integer.class);
		}

		sql = "SELECT nia_codigo, nia_nombre, nia_orden, for_codigo, for_nombre, snies_codigo"
				+ " FROM (select row_number() over(order by " + campos[posicion] + " " + direccion + ") AS RowNumber,"
				+ " n.nia_codigo, n.nia_nombre, n.nia_orden, f.for_codigo, f.for_nombre, n.snies_codigo"
				+ " FROM nivel_academico n " + " INNER JOIN formalidad f ON n.for_codigo = f.for_codigo "
				+ " WHERE n.nia_nombre LIKE ? AND n.nia_estado = 1 ) "
				+ " as tabla WHERE tabla.RowNumber between ? and ? ";

		List<NivelAcademico> nivelAcademico = jdbcTemplate.query(sql, new Object[] { "%" + search + "%", start, fin },
				new RowMapper<NivelAcademico>() {

					public NivelAcademico mapRow(ResultSet rs, int rowNum) throws SQLException {
						NivelAcademico nivelAcademico = new NivelAcademico();

						nivelAcademico.setCodigo(rs.getInt("nia_codigo"));
						nivelAcademico.setNombre(rs.getString("nia_nombre"));
						nivelAcademico.setOrden(rs.getInt("nia_orden"));
						Formalidad formalidad = new Formalidad();
						formalidad.setCodigo(rs.getInt("for_codigo"));
						formalidad.setNombre(rs.getString("for_nombre"));
						nivelAcademico.setFormalidad(formalidad);
						nivelAcademico.setSnies(rs.getString("snies_codigo"));

						return nivelAcademico;
					}

				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(nivelAcademico);

		return respuesta;
	}

}
