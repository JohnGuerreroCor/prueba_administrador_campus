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

import co.edu.usco.lcms.dao.WebParametroDao;
import co.edu.usco.lcms.model.reservaespacios.Horas;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author jankarlos
 *
 */
@Component
public class HoraDaoJDBCTempleImpl implements HoraDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	Constantes constantes;

	@Autowired
	WebParametroDao webParametroDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.reservaespacio.HorasDao#listarHorass()
	 */
	@Override
	public List<Horas> listarHoras() {
		// TODO Auto-generated method stub

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String codigoHoraFin = webParametroDao.listarWebParametro(constantes.WEP_CODIGO_HORA_FIN).get(0).getValor();

		String sql = "SELECT h.hra_codigo, h.hra_nombre, h.hra_hora, h.hra_hora_24h FROM videoconferencias.hora h WHERE h.hra_codigo <= "
				+ codigoHoraFin;
		List<Horas> listaHoras = jdbcTemplate.query(sql, new RowMapper<Horas>() {

			public Horas mapRow(ResultSet rs, int rowNum) throws SQLException {

				Horas hora = new Horas();
				hora.setCodigo(rs.getInt("hra_codigo"));
				hora.setNombre(rs.getString("hra_nombre"));
				hora.setHora(rs.getString("hra_hora"));
				hora.setHora24h(rs.getString("hra_hora_24h"));

				return hora;
			}

		});
		return listaHoras;
	}

	@Override
	public Horas buscarHora(int codigo) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "select h.hra_codigo, h.hra_nombre, h.hra_hora, h.hra_hora_24h "
				+ "from videoconferencias.hora as h where h.hra_codigo = ?";
		List<Horas> listaHoras = jdbcTemplate.query(sql, new Object[] { codigo }, new RowMapper<Horas>() {

			public Horas mapRow(ResultSet rs, int rowNum) throws SQLException {

				Horas hora = new Horas();
				hora.setCodigo(rs.getInt("hra_codigo"));
				hora.setNombre(rs.getString("hra_nombre"));
				hora.setHora(rs.getString("hra_hora"));
				hora.setHora24h(rs.getString("hra_hora_24h"));

				return hora;
			}

		});
		return listaHoras.get(0);
	}

}
