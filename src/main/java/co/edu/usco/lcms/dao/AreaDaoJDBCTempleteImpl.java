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

/**
 * @author jankarlos
 *
 */
@Component
public class AreaDaoJDBCTempleteImpl implements AreaDao {

	@Autowired
	DataSource dataSource;
	
	/* (non-Javadoc)
	 * @see co.edu.usco.lcms.dao.AreaDao#listarArea()
	 */
	public List<Area> listarArea() {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "SELECT sar_codigo, sar_nombre "
				+ "FROM snies_area "
				+ "ORDER BY sar_nombre";

		List<Area> listaArea = jdbcTemplate.query(sql, new RowMapper<Area>() {
			public Area mapRow(ResultSet rs, int rowNum) throws SQLException {
				Area area = new Area();
				area.setCodigo(rs.getInt("sar_codigo"));
				area.setNombre(rs.getString("sar_nombre"));
				return area;
			}

		});

		return listaArea;
	}

}
