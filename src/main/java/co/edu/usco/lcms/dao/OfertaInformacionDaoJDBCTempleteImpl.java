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

import co.edu.usco.lcms.model.OfertaAcademica;
import co.edu.usco.lcms.model.OfertaInformacion;

/**
 * @author jankarlos
 *
 */
@Component
public class OfertaInformacionDaoJDBCTempleteImpl implements OfertaInformacionDao {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.OfertaInformacionDao#agregarOfertaInformacion(org.usco.
	 * lcms.model.OfertaInformacion)
	 */
	@Override
	public boolean agregarOfertaInformacion(OfertaInformacion ofertaInformacion) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO inscripciones.oferta_informacion (ofi_orden, ofi_estado, ofi_contenido, "
					+ "ofi_titulo, ofa_codigo) VALUES (?, ?, ?, ?, ?)";
			int resultado = jdbcTemplate.update(sql, ofertaInformacion.getOrden(), 1, ofertaInformacion.getContenido(),
					ofertaInformacion.getTitulo(), ofertaInformacion.getOfertaAcademica().getCodigo());

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
	 * @see
	 * co.edu.usco.lcms.dao.OfertaInformacionDao#modificarOfertaInformacion(int,
	 * co.edu.usco.lcms.model.OfertaInformacion)
	 */
	@Override
	public boolean modificarOfertaInformacion(int id, OfertaInformacion ofertaInformacion) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			// update
			String sql = "UPDATE inscripciones.oferta_informacion SET ofi_orden=?, ofi_contenido=?, "
					+ " ofi_titulo=? WHERE ofi_codigo=?";
			int resultado = jdbcTemplate.update(sql, ofertaInformacion.getOrden(), ofertaInformacion.getContenido(),
					ofertaInformacion.getTitulo(), id);

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
	 * @see
	 * co.edu.usco.lcms.dao.OfertaInformacionDao#eliminarOfertaInformacion(int)
	 */
	@Override
	public boolean eliminarOfertaInformacion(int id) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			// update
			String sql = "UPDATE inscripciones.oferta_informacion SET ofi_estado=? WHERE ofi_codigo=?";
			int resultado = jdbcTemplate.update(sql, 0, id);

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
	 * @see co.edu.usco.lcms.dao.OfertaInformacionDao#listarOfertaInformacion(int)
	 */
	@Override
	public List<OfertaInformacion> listarOfertaInformacion(int id) {
		// TODO Auto-generated method stub
		Object[] obj = new Object[] {};

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "select oi.ofi_codigo, oi.ofi_orden, oi.ofi_titulo, oi.ofi_estado, oi.ofi_contenido, "
				+ " oi.ofa_codigo from inscripciones.oferta_informacion as oi where oi.ofi_estado=1";
		if (id != 0) {
			sql = sql + " and oi.ofa_codigo = ? ";
			obj = new Object[] { id };
		}
		sql = sql + " order by oi.ofi_orden";
		List<OfertaInformacion> listaOfertaInformacion = jdbcTemplate.query(sql, obj, new RowMapper<OfertaInformacion>() {

			public OfertaInformacion mapRow(ResultSet rs, int rowNum) throws SQLException {
				OfertaInformacion ofertaInformacion = new OfertaInformacion();

				ofertaInformacion.setCodigo(rs.getInt("ofi_codigo"));
				ofertaInformacion.setOrden(rs.getInt("ofi_orden"));
				ofertaInformacion.setTitulo(rs.getString("ofi_titulo"));
				ofertaInformacion.setContenido(rs.getString("ofi_contenido"));
				ofertaInformacion.setEstado(rs.getInt("ofi_estado"));
				
				OfertaAcademica ofertaAcademica = new OfertaAcademica();
				ofertaAcademica.setCodigo(rs.getInt("ofa_codigo"));
				ofertaInformacion.setOfertaAcademica(ofertaAcademica);

				return ofertaInformacion;
			}

		});
		return listaOfertaInformacion;
	}

}
