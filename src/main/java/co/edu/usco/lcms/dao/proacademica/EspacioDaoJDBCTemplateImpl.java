/**
 * 
 */
package co.edu.usco.lcms.dao.proacademica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.reservaespacios.Bloque;
import co.edu.usco.lcms.model.reservaespacios.Espacio;
import co.edu.usco.lcms.model.reservaespacios.EspacioTipo;

/**
 * @author jankarlos
 *
 */
@Component
public class EspacioDaoJDBCTemplateImpl implements EspacioDao {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.proacademica.EspacioDao#listarEspacios(int)
	 */
	@Override
	public List<Espacio> listarEspacios(int codigo, String criterio) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT e.esp_codigo, e.esp_nombre, e.esp_nombre_corto, e.esp_descripcion,"
				+ " b.blo_codigo, b.blo_nombre, et.eti_codigo, et.eti_nombre FROM espacio e"
				+ " INNER JOIN bloque b ON e.blo_codigo = b.blo_codigo "
				+ " INNER JOIN espacio_tipo et ON e.eti_codigo = et.eti_codigo";
		sql = sql + " WHERE e.esp_nombre like ? OR e.esp_nombre_corto like ?";

		List<Espacio> listaEspacio = jdbcTemplate.query(sql,
				new Object[] { "%" + criterio + "%", "%" + criterio + "%" }, new RowMapper<Espacio>() {

					public Espacio mapRow(ResultSet rs, int rowNum) throws SQLException {

						Espacio espacio = new Espacio();
						espacio.setCodigo(rs.getInt("esp_codigo"));
						espacio.setNombre(rs.getString("esp_nombre"));
						espacio.setNombreCorto(rs.getString("esp_nombre_corto"));
						espacio.setDescripcion(rs.getString("esp_descripcion"));

						Bloque bloque = new Bloque();
						bloque.setCodigo(rs.getInt("blo_codigo"));
						bloque.setNombre(rs.getString("blo_nombre"));
						espacio.setBloque(bloque);

						EspacioTipo espacioTipo = new EspacioTipo();
						espacioTipo.setCodigo(rs.getInt("eti_codigo"));
						espacioTipo.setNombre(rs.getString("eti_nombre"));
						espacio.setEspacioTipo(espacioTipo);

						return espacio;
					}

				});
		return listaEspacio;
	}

}
