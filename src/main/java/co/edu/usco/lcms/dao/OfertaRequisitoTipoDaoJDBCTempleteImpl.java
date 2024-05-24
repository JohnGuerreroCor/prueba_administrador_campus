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

import co.edu.usco.lcms.model.OfertaRequisitoTipo;

/**
 * @author jankarlos
 *
 */
@Component
public class OfertaRequisitoTipoDaoJDBCTempleteImpl implements OfertaRequisitoTipoDao {

	@Autowired
	DataSource dataSource;

	/* (non-Javadoc)
	 * @see co.edu.usco.lcms.dao.ProgramaRequisitoTipoDao#listarProgramaRequisitoTipo(int)
	 */
	@Override
	public List<OfertaRequisitoTipo> listarProgramaRequisitoTipo(int id) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT otr_descripcion, otr_codigo "
				+ "FROM inscripciones.oferta_requisito_tipo";
		if (id > 0) {
			sql = sql + " WHERE otr_codigo = " + id;
		}

		List<OfertaRequisitoTipo> listaPrograma = jdbcTemplate.query(sql, new RowMapper<OfertaRequisitoTipo>() {

			public OfertaRequisitoTipo mapRow(ResultSet rs, int rowNum) throws SQLException {
				OfertaRequisitoTipo ofertaRequisitoTipo = new OfertaRequisitoTipo();

				ofertaRequisitoTipo.setCodigo(rs.getInt("otr_codigo"));
				ofertaRequisitoTipo.setNombre(rs.getString("otr_descripcion"));
				
				return ofertaRequisitoTipo;
			}

		});

		return listaPrograma;
	}

}
