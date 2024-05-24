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

import co.edu.usco.lcms.model.EstudioAnterior;
import co.edu.usco.lcms.model.NivelAcademico;

/**
 * @author jankarlos
 *
 */
@Component
public class EstudioAnteriorDaoJDBCTemplateImpl implements EstudioAnteriorDao {


	@Autowired
	DataSource dataSource;
	/* (non-Javadoc)
	 * @see co.edu.usco.lcms.dao.EstudioAnteriorDao#consultarEstudioAnterior(int, int)
	 */
	@Override
	public List<EstudioAnterior> consultarEstudioAnterior(int persona, int tercero) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "select h.hia_codigo, h.hia_titulo, h.hia_institucion, h.hia_anio, n.nia_codigo,"
				+ " n.nia_nombre,h.per_codigo, h.ter_codigo from inscripciones.historial_academico h inner join nivel_academico n "
				+ "ON h.nia_codigo = n.nia_codigo";
		
		if (persona > 0) {
			sql = sql + " where h.per_codigo = " + persona;
		}
		
		if (tercero > 0) {
			sql = sql + " where h.ter_codigo = " + tercero;
		}

		List<EstudioAnterior> listaEstudioAnterior = jdbcTemplate.query(sql, new RowMapper<EstudioAnterior>() {

			public EstudioAnterior mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				EstudioAnterior estudioAnterior = new EstudioAnterior();
				estudioAnterior.setCodigo(rs.getInt("hia_codigo"));
				estudioAnterior.setTitulo(rs.getString("hia_titulo"));
				estudioAnterior.setInstitucion(rs.getString("hia_institucion"));
				estudioAnterior.setPersona(rs.getInt("per_codigo"));
				estudioAnterior.setTercero(rs.getInt("ter_codigo"));
				estudioAnterior.setAnio(rs.getString("hia_anio"));
				NivelAcademico nivelAcademico = new NivelAcademico();
				nivelAcademico.setNombre(rs.getString("nia_nombre"));
				estudioAnterior.setNivelAcademico(nivelAcademico);
				
				return estudioAnterior;
			}

		});

		return listaEstudioAnterior;
	}

}
