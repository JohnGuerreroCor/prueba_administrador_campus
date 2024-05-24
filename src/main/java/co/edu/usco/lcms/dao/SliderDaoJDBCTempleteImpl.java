/**
 * 
 */
package co.edu.usco.lcms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Slider;

/**
 * @author jankarlos
 *
 */
@Component
public class SliderDaoJDBCTempleteImpl implements SliderDao {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.SliderDao#agregarSlider(co.edu.usco.lcms.model.
	 * Slider)
	 */
	@Override
	public int agregarSlider(final Slider slider) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			final String sql = "INSERT INTO portal.imagenes_slider (ims_url, ims_estado, ims_secuencia, ims_descripcion, ims_alt) VALUES (?, ?, ?, ?, ?)";

			KeyHolder keyHolder = new GeneratedKeyHolder();
			int resultado = jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					pstm.setInt(1, 0);
					pstm.setInt(2, 1);
					pstm.setInt(3, slider.getSecuencia());
					pstm.setString(4, slider.getDescripcion());
					pstm.setString(5, slider.getAlt());
					return pstm;
				}
			}, keyHolder);

			if (resultado > 0) {
				slider.setCodigo(keyHolder.getKey().intValue());
				return keyHolder.getKey().intValue();
			}

		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.SliderDao#modificarSlider(int,
	 * co.edu.usco.lcms.model.Slider)
	 */
	@Override
	public boolean modificarSlider(int id, Slider slider) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// update
		String sql = "UPDATE portal.imagenes_slider SET ims_secuencia=?, ims_descripcion=?, ims_alt=? "
				+ " WHERE ims_codigo=?";
		int resultado = jdbcTemplate.update(sql, slider.getSecuencia(), slider.getDescripcion(), slider.getAlt(), id);

		if (resultado > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.SliderDao#eliminarSlider(int)
	 */
	@Override
	public boolean eliminarSlider(int id) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// update
		String sql = "UPDATE portal.imagenes_slider SET ims_estado=? WHERE ims_codigo=?";
		int resultado = jdbcTemplate.update(sql, 0, id);

		if (resultado > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.SliderDao#listarSlider()
	 */
	@Override
	public List<Slider> listarSlider() {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "select i.ims_estado, i.ims_secuencia, i.ims_descripcion, i.ims_alt, i.ims_url, i.ims_codigo";
		sql = sql + " FROM portal.imagenes_slider as i WHERE i.ims_estado = 1 ";
		sql = sql + " ORDER BY i.ims_secuencia DESC";

		List<Slider> listaSlider = jdbcTemplate.query(sql, new RowMapper<Slider>() {

			public Slider mapRow(ResultSet rs, int rowNum) throws SQLException {

				Slider slider = new Slider();

				slider.setCodigo(rs.getInt("ims_codigo"));
				slider.setDescripcion(rs.getString("ims_descripcion"));
				slider.setAlt(rs.getString("ims_alt"));
				slider.setEstado(rs.getString("ims_estado"));
				slider.setSecuencia(rs.getInt("ims_secuencia"));
				slider.setUrl("sliderSerImagen/imagen?id=" + rs.getString("ims_codigo"));

				return slider;
			}

		});
		return listaSlider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.SliderDao#listarTablaSlider(java.lang.String,
	 * int, int, int, int, java.lang.String)
	 */
	@Override
	public JSONRespuesta listarTablaSlider(String search, int start, int length, int draw, int posicion,
			String direccion) {
		// TODO Auto-generated method stub

		JSONRespuesta respuesta = new JSONRespuesta();
		if (start == 0) {
			start = 1;
		}

		String[] campos = { "i.ims_codigo", "i.ims_descripcion", "i.ims_alt", "i.ims_url", "i.ims_secuencia",
				"i.ims_estado" };

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "select count(*) from portal.imagenes_slider as i WHERE i.ims_estado = 1 ";

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " where i.ims_descripcion like ? ";
			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%" }, Integer.class);
		}

		sql = "select ims_estado, ims_secuencia, ims_descripcion, ims_alt, ims_url, ims_codigo";
		sql = sql + " from (select row_number() over(order by " + campos[posicion] + " " + direccion
				+ ") AS RowNumber,";
		sql = sql + " i.ims_estado, i.ims_secuencia, i.ims_descripcion, i.ims_alt, i.ims_url, i.ims_codigo";
		sql = sql + " FROM portal.imagenes_slider as i WHERE i.ims_estado = 1 ";
		sql = sql + " AND i.ims_descripcion like ? ";
		sql = sql + ") as tabla";
		sql = sql + " where tabla.RowNumber between ? and ?";

		List<Slider> listaSlider = jdbcTemplate.query(sql, new Object[] { "%" + search + "%", start, fin },
				new RowMapper<Slider>() {

					public Slider mapRow(ResultSet rs, int rowNum) throws SQLException {
						Slider slider = new Slider();

						slider.setCodigo(rs.getInt("ims_codigo"));
						slider.setDescripcion(rs.getString("ims_descripcion"));
						slider.setAlt(rs.getString("ims_alt"));
						slider.setEstado(rs.getString("ims_estado"));
						slider.setSecuencia(rs.getInt("ims_secuencia"));
						slider.setUrl(rs.getString("ims_url"));

						return slider;
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaSlider);

		return respuesta;
	}

	@Override
	public boolean guardarUrl(int id, String url) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// update
		String sql = "UPDATE portal.imagenes_slider SET ims_url=? WHERE ims_codigo=?";
		int resultado = jdbcTemplate.update(sql, url, id);

		if (resultado > 0) {
			return true;
		}
		return false;
	}

}
