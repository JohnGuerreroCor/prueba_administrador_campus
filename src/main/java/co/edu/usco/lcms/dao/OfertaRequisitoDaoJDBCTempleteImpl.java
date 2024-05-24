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
import co.edu.usco.lcms.model.OfertaRequisito;
import co.edu.usco.lcms.model.OfertaRequisitoTipo;

/**
 * @author jankarlos
 *
 */
@Component
public class OfertaRequisitoDaoJDBCTempleteImpl implements OfertaRequisitoDao {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.ProgramaRequisitoDao#agregarProgramaRequisitos(org.usco
	 * .lcms.model.ProgramaRequisito)
	 */
	@Override
	public boolean agregarOfertaRequisitos(OfertaRequisito ofertaRequisito) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "INSERT INTO inscripciones.oferta_requisito (ore_descripcion, otr_codigo, ofa_codigo)"
					+ " VALUES (?, ?, ?)";
			int resultado = jdbcTemplate.update(sql, ofertaRequisito.getDescripcion(),
					ofertaRequisito.getOfertaRequisitoTipo().getCodigo(),
					ofertaRequisito.getOfertaAcademica().getCodigo());

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
	 * co.edu.usco.lcms.dao.ProgramaRequisitoDao#eliminarProgramaRequisitos(int)
	 */
	@Override
	public boolean eliminarOfertaRequisitos(int id) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "UPDATE inscripciones.oferta_requisito SET ore_estado = 0 WHERE ore_codigo=?";
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
	 * @see co.edu.usco.lcms.dao.ProgramaRequisitoDao#listarProgramaRequisitos(int)
	 */
	@Override
	public List<OfertaRequisito> listarOfertaRequisitos(int id) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] obj = new Object[] {};
		String sql = "SELECT o.ore_descripcion, o.ore_codigo, ot.otr_codigo, ot.otr_descripcion, "
				+ "oft.ofa_codigo "
				+ "FROM inscripciones.oferta_requisito o "
				+ "INNER JOIN inscripciones.oferta_requisito_tipo ot ON	o.otr_codigo = ot.otr_codigo "
				+ "INNER JOIN dbo.oferta_academica oft ON o.ofa_codigo = oft.ofa_codigo "
				+ "WHERE o.ore_estado = 1";

		if (id > 0) {
			sql = sql + " AND oft.ofa_codigo = ?";
			obj = new Object[] { id };
		}

		List<OfertaRequisito> listaPrograma = jdbcTemplate.query(sql, obj, new RowMapper<OfertaRequisito>() {

			public OfertaRequisito mapRow(ResultSet rs, int rowNum) throws SQLException {
				OfertaRequisito ofertaRequisito = new OfertaRequisito();

				ofertaRequisito.setCodigo(rs.getInt("ore_codigo"));
				ofertaRequisito.setDescripcion(rs.getString("ore_descripcion"));

				OfertaRequisitoTipo ofertaRequisitoTipo = new OfertaRequisitoTipo();
				ofertaRequisitoTipo.setCodigo(rs.getInt("otr_codigo"));
				ofertaRequisitoTipo.setNombre(rs.getString("otr_descripcion"));
				ofertaRequisito.setOfertaRequisitoTipo(ofertaRequisitoTipo);

				OfertaAcademica ofertaAcademica = new OfertaAcademica();
				ofertaAcademica.setCodigo(rs.getInt("ofa_codigo"));
				ofertaRequisito.setOfertaAcademica(ofertaAcademica);

				return ofertaRequisito;
			}

		});

		return listaPrograma;

	}

}
