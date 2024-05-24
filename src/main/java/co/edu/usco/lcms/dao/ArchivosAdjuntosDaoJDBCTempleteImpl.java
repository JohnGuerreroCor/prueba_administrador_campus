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

import co.edu.usco.lcms.model.ArchivosAdjuntos;

/**
 * @author jankarlos
 *
 */
@Component
public class ArchivosAdjuntosDaoJDBCTempleteImpl implements ArchivosAdjuntosDao {

	@Autowired
	DataSource dataSource;
	
	/* (non-Javadoc)
	 * @see co.edu.usco.lcms.dao.AreaDao#listarArea()
	 */
	@Override
	public ArchivosAdjuntos consultarRegistro(String nombreEncriptado) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			String sql = "SELECT id, nombre_completo, tipo, ruta, registro "
				+ "FROM inscripciones.documentosEstudiosAnteriores "
				+ "WHERE nombre_encriptado = '" + nombreEncriptado + "'";
			List<ArchivosAdjuntos> listaArchivosAdjuntos = jdbcTemplate.query(sql, new RowMapper<ArchivosAdjuntos>() {

				public ArchivosAdjuntos mapRow(ResultSet rs, int rowNum) throws SQLException {
					ArchivosAdjuntos aa = new ArchivosAdjuntos();
					aa.setId(rs.getInt("id"));
					aa.setNombreCompleto(rs.getString("nombre_completo"));
					aa.setTipo(rs.getString("tipo"));
					aa.setRuta(rs.getString("ruta"));
					aa.setRegistro(rs.getInt("registro"));
					return aa;
				}
			});
			if (listaArchivosAdjuntos.size() > 0) {
				return listaArchivosAdjuntos.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
