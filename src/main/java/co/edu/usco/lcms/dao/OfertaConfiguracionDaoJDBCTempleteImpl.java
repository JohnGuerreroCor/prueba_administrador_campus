package co.edu.usco.lcms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dto.OfertaConfiguracionDTO;
import co.edu.usco.lcms.model.OfertaAcademica;
import co.edu.usco.lcms.model.OfertaConfiguracion;

@Component
public class OfertaConfiguracionDaoJDBCTempleteImpl implements OfertaConfiguracionDao {

	@Autowired
	DataSource dataSource;

	@Override
	public String agregarOfertaConfiguracion(OfertaConfiguracion ofertaConfiguracion) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO inscripciones.oferta_estamento (uaa_codigo, ofe_tipo_usuario, ofa_codigo) VALUES (?, ?, ?)";
			int resultado = jdbcTemplate.update(sql, ofertaConfiguracion.getUaa(), ofertaConfiguracion.getTipoUsuario(),
					ofertaConfiguracion.getOfertaAcademica().getCodigo());

			if (resultado > 0) {
				return "OK";
			}
		} catch (Exception e) {
			return "ERROR";
		}
		return "ERROR";

	}

	@Override
	public List<OfertaConfiguracionDTO> listarOfertaConfiguracion(int oferta) {
		// TODO Auto-generated method stub
		Object[] obj = new Object[] {};

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "select e.ofe_codigo, e.ofa_codigo, e.ofe_tipo_usuario, e.uaa_codigo, e.ofe_estado from inscripciones.oferta_estamento e where e.ofe_estado = 1";

		if (oferta != 0) {
			sql = sql + " and e.ofa_codigo = ? ";
			obj = new Object[] { oferta };
		}
		sql = sql + " order by e.ofe_codigo";
		System.out.println(oferta);
		System.out.println(sql);

		List<OfertaConfiguracionDTO> listaOfertaConfiguracion = jdbcTemplate.query(sql, obj,
				new RowMapper<OfertaConfiguracionDTO>() {

					public OfertaConfiguracionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

						OfertaConfiguracionDTO ofertaConfiguracion = new OfertaConfiguracionDTO();

						ofertaConfiguracion.setCodigo(rs.getInt("ofe_codigo"));

						OfertaAcademica ofertaAcademica = new OfertaAcademica();
						ofertaAcademica.setCodigo(rs.getInt("ofa_codigo"));
						ofertaConfiguracion.setOfertaAcademica(ofertaAcademica);

						ofertaConfiguracion.setUaa(listarUaa(rs.getString("uaa_codigo")));
						ofertaConfiguracion.setUsuarios(listarUsuarios(rs.getString("ofe_tipo_usuario")));
						return ofertaConfiguracion;
					}

				});
		return listaOfertaConfiguracion;
	}

	@Override
	public List<String> listarUaa(String codigo) {
		// TODO Auto-generated method stub
		Object[] obj = new Object[] {};

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "select uaa_nombre from uaa where uaa_codigo in (" + codigo + ")";

		List<String> listaUaaNombres = jdbcTemplate.query(sql, obj, new RowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {

				String listaUaa = rs.getString("uaa_nombre");
				return listaUaa;
			}

		});
		return listaUaaNombres;
	}

	@Override
	public List<String> listarUsuarios(String codigo) {
		// TODO Auto-generated method stub
		List<String> listaUsNombres = new ArrayList<String>();

		String usu = codigo;
		String usuArray[] = usu.split(",");

		for (String us : usuArray) {

			switch (Integer.parseInt(us)) {
			case 1:
				listaUsNombres.add("Administrativos");
				break;
			case 2:
				listaUsNombres.add("Estudiante");
				break;
			case 3:
				listaUsNombres.add("Docentes");
				break;
			case 6:
				listaUsNombres.add("Proveedores");
				break;
			case 0:
				listaUsNombres.add("Todos");
				break;
			}
		}

		return listaUsNombres;
	}

}
