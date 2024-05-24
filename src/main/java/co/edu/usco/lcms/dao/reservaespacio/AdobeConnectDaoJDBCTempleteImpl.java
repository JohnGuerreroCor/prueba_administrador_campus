/**
 * 
 */
package co.edu.usco.lcms.dao.reservaespacio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.reservaespacios.AdobeConnect;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@Component
public class AdobeConnectDaoJDBCTempleteImpl implements AdobeConnectDao {

	@Autowired
	DataSource dataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.reservaespacio.AdobeConnectDao#listarAdobeConnect()
	 */
	@Override
	public List<AdobeConnect> listarAdobeConnect() {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT TOP 1 ac.cac_url, ac.cac_codigo, ac.cac_usuario, ac.cac_password, "
				+ " ac.cac_numero_sesiones "
				+ "FROM videoconferencias.credenciales_adobeconnect AS ac ORDER BY ac.cac_codigo DESC";

		List<AdobeConnect> listaAdobeConnect = jdbcTemplate.query(sql, new RowMapper<AdobeConnect>() {

			public AdobeConnect mapRow(ResultSet rs, int rowNum) throws SQLException {

				int numTotalHoras = ((rs.getInt("cac_numero_sesiones") * 24) * 6) * 6;
				AdobeConnect adobeConnect = new AdobeConnect();
				adobeConnect.setCodigo(rs.getInt("cac_codigo"));
				adobeConnect.setUsuario(rs.getString("cac_usuario"));
				adobeConnect.setClave(rs.getString("cac_password"));
				adobeConnect.setNumSesiones(rs.getInt("cac_numero_sesiones"));
				adobeConnect.setNumTotalHoras(numTotalHoras);
				adobeConnect.setUrl(rs.getString("cac_url"));
				return adobeConnect;
			}
		});
		return listaAdobeConnect;
	}

}
