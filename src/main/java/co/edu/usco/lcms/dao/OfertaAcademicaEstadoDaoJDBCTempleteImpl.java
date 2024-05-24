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

import co.edu.usco.lcms.model.OfertaAcademicaEstado;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author han
 *
 */
@Component
public class OfertaAcademicaEstadoDaoJDBCTempleteImpl implements OfertaAcademicaEstadoDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	Constantes constantes;

	@Autowired
	WebParametroDao webParametroDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.OfertaAcademicaEstadoDao#listarOfertaAcademicaEstado
	 * (int)
	 */
	@Override
	public List<OfertaAcademicaEstado> listarOfertaAcademicaEstado() {
		// TODO Auto-generated method stub

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String estado = webParametroDao.listarWebParametro(constantes.WEP_ESTADO_ELIMINADO_OFERTA).get(0).getValor();
		String sql = "SELECT o.ofat_codigo, o.ofat_nombre FROM oferta_academica_estado o WHERE o.ofat_codigo != "
				+ estado;
		List<OfertaAcademicaEstado> listaOfertaAcademicaEstado = jdbcTemplate.query(sql,
				new RowMapper<OfertaAcademicaEstado>() {

					public OfertaAcademicaEstado mapRow(ResultSet rs, int rowNum) throws SQLException {
						OfertaAcademicaEstado ofertaAcademicaEstado = new OfertaAcademicaEstado();
						ofertaAcademicaEstado.setCodigo(rs.getInt("ofat_codigo"));
						ofertaAcademicaEstado.setNombre(rs.getString("ofat_nombre"));

						return ofertaAcademicaEstado;
					}

				});
		return listaOfertaAcademicaEstado;
	}

}
