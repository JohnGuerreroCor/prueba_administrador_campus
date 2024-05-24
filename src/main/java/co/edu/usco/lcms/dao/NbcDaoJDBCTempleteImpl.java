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

import co.edu.usco.lcms.model.Area;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Nbc;

/**
 * @author jankarlos
 *
 */
@Component
public class NbcDaoJDBCTempleteImpl implements NbcDao {

	@Autowired
	DataSource dataSource;
	
	/* (non-Javadoc)
	 * @see co.edu.usco.lcms.dao.NbcDao#agregarNbc(co.edu.usco.lcms.model.Nbc)
	 */
	@Override
	public boolean agregarNbc(Nbc nbc) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO snies_nbc (nbc_nombre, sar_codigo) VALUES (?, ?)";
			int resultado = jdbcTemplate.update(sql, nbc.getNombre(), nbc.getArea().getCodigo());

			if (resultado > 0) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see co.edu.usco.lcms.dao.NbcDao#modificarNbc(java.lang.String, co.edu.usco.lcms.model.Nbc)
	 */
	@Override
	public boolean modificarNbc(int id, Nbc nbc) {
		// TODO Auto-generated method stub
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			// update
			String sql = "UPDATE snies_nbc SET nbc_nombre = ?, sar_codigo = ?";
			sql = sql + " WHERE nbc_codigo=?";
			int resultado = jdbcTemplate.update(sql, nbc.getNombre(), nbc.getArea().getCodigo(), id);

			if (resultado > 0) {
				return true;
			}
		}catch(Exception e){
			return false;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see co.edu.usco.lcms.dao.NbcDao#eliminarNbc(java.lang.String)
	 */
	@Override
	public boolean eliminarNbc(int id) {
		// TODO Auto-generated method stub
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "DELETE FROM snies_nbc WHERE nbc_codigo= ?";
			int resultado = jdbcTemplate.update(sql, id);
			if (resultado > 0) {
				return true;
			}
		}catch(Exception e){
			return false;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see co.edu.usco.lcms.dao.NbcDao#listarNbc()
	 */
	@Override
	public List<Nbc> listarNbc() {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "SELECT sn.nbc_codigo,	sn.nbc_nombre, sa.sar_codigo, sa.sar_nombre	"
				+ "FROM snies_nbc sn "
				+ "INNER JOIN snies_area sa ON sn.sar_codigo = sa.sar_codigo "
				+ " ORDER BY sn.nbc_nombre";
		List<Nbc> listaNbc = jdbcTemplate.query(sql, new RowMapper<Nbc>() {

			public Nbc mapRow(ResultSet rs, int rowNum) throws SQLException {
				Nbc nbc = new Nbc();
				nbc.setCodigo(rs.getInt("nbc_codigo"));
				nbc.setNombre(rs.getString("nbc_nombre"));
				
				Area area = new Area();
				area.setCodigo(rs.getInt("sar_codigo"));
				area.setNombre(rs.getString("sar_nombre"));
				nbc.setArea(area);
				return nbc;
			}

		});
		return listaNbc;
	}

	/* (non-Javadoc)
	 * @see co.edu.usco.lcms.dao.NbcDao#listarTablaNbc(java.lang.String, int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarTablaNbc(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub
		JSONRespuesta respuesta = new JSONRespuesta();

		if (start == 0) {
			start = 1;
		}

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String[] campos = { "sn.nbc_codigo","sn.nbc_nombre", "sa.sar_nombre" };
		
		String sql = "SELECT count(*) "
				+ "FROM snies_nbc as sn	inner join snies_area as sa on sn.sar_codigo = sa.sar_codigo ";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " WHERE sn.nbc_nombre LIKE ? OR sa.sar_nombre LIKE ?";
			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%", "%" + search + "%" },Integer.class);
		}

		sql = "SELECT nbc_codigo, nbc_nombre, sar_codigo, sar_nombre";
		sql = sql + " FROM (select row_number() over(order by " + campos[posicion] + " " + direccion+ ") AS RowNumber,";
		sql = sql + "sn.nbc_codigo,	sn.nbc_nombre, sa.sar_codigo, sa.sar_nombre "
				+ " FROM snies_nbc as sn inner join snies_area as sa on sn.sar_codigo = sa.sar_codigo"; 
		sql = sql + " WHERE sn.nbc_nombre like ? OR sa.sar_nombre like ?";
		sql = sql + ") as tabla";
		sql = sql + " where tabla.RowNumber between ? and ?";

		List<Nbc> listaNbc = jdbcTemplate.query(sql, new Object[] { "%" + search + "%", "%" + search + "%", start, fin }, new RowMapper<Nbc>() {

			public Nbc mapRow(ResultSet rs, int rowNum) throws SQLException {
				Nbc nbc = new Nbc();
				nbc.setCodigo(rs.getInt("nbc_codigo"));
				nbc.setNombre(rs.getString("nbc_nombre"));
				
				Area area = new Area();
				area.setCodigo(rs.getInt("sar_codigo"));
				area.setNombre(rs.getString("sar_nombre"));
				nbc.setArea(area);
				return nbc;
			}
		});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaNbc);

		return respuesta;
	}

}
