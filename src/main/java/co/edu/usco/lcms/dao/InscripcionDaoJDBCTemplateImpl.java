package co.edu.usco.lcms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.Inscripcion;
import co.edu.usco.lcms.model.InscripcionPrograma;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Oferta;
import co.edu.usco.lcms.model.Persona;
import co.edu.usco.lcms.model.PreInscripcion;
import co.edu.usco.lcms.model.Programa;

@Component
public class InscripcionDaoJDBCTemplateImpl implements InscripcionDao {

	@Autowired
	DataSource dataSource;

	@Override
	public JSONRespuesta listaUsuariosInscritos(String search, long codigoOferta, int start, int length, int draw) {
		// TODO Auto-generated method stub
		JSONRespuesta respuesta = new JSONRespuesta();

		if (start == 0) {
			start = 1;
		}

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT COUNT(*) " + "FROM persona p "
				+ "INNER JOIN inscripcion i ON (p.per_codigo = i.per_codigo) "
				+ "INNER JOIN inscripcion_programa ip ON (i.ins_codigo = ip.ins_codigo) "
				+ "INNER JOIN oferta_academica o ON (ip.ofa_codigo = o.ofa_codigo) "
				+ "INNER JOIN programa pr ON (o.pro_codigo = pr.pro_codigo)"
				+ "WHERE pr.mod_codigo = 5 AND o.ofa_admision_automatica=1 ";

		if (codigoOferta > 0) {
			sql = sql + "AND o.ofa_codigo = " + codigoOferta;
		}

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " AND p.per_nombre + ' ' + p.per_apellido like '%" + search + "%'";
			filtrados = jdbcTemplate.queryForObject(sql, Integer.class);
		}

		sql = "SELECT per_identificacion, per_nombre, per_apellido, pro_titulo_otorgado " + "FROM ("
				+ "SELECT row_number() over(order by p.per_nombre ASC) AS RowNumber, p.per_identificacion, "
				+ "p.per_nombre, p.per_apellido, pr.pro_titulo_otorgado " + "FROM persona p "
				+ "INNER JOIN inscripcion i ON (p.per_codigo = i.per_codigo) "
				+ "INNER JOIN inscripcion_programa ip ON (i.ins_codigo = ip.ins_codigo) "
				+ "INNER JOIN oferta_academica o ON (ip.ofa_codigo = o.ofa_codigo) "
				+ "INNER JOIN programa pr ON (o.pro_codigo = pr.pro_codigo) "
				+ "WHERE pr.mod_codigo = 5 AND o.ofa_admision_automatica=1 ";
		if (search.length() > 0) {
			sql = sql + " and p.per_nombre + ' ' + p.per_apellido like '%" + search + "%'";
		}

		if (codigoOferta > 0) {
			sql = sql + " AND o.ofa_codigo = " + codigoOferta;
		}

		sql = sql + ") as tabla";
		sql = sql + " where tabla.RowNumber between " + start + " and " + fin;

		List<PreInscripcion> listaUsu = jdbcTemplate.query(sql, new RowMapper<PreInscripcion>() {

			public PreInscripcion mapRow(ResultSet rs, int rowNum) throws SQLException {
				PreInscripcion usuariosPreinscritos = new PreInscripcion();

				Programa programa = new Programa();
				Oferta oferta = new Oferta();
				Persona persona = new Persona();

				persona.setIdentificacion(rs.getString("per_identificacion"));
				persona.setNombre(rs.getString("per_nombre"));
				persona.setApellido(rs.getString("per_apellido"));
				programa.setNombre(rs.getString("pro_titulo_otorgado"));

				usuariosPreinscritos.setPersona(persona);
				oferta.setPrograma(programa);
				usuariosPreinscritos.setOferta(oferta);

				return usuariosPreinscritos;
			}

		});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaUsu);

		return respuesta;
	}

	@Override
	public List<Programa> ListaProgramasInscripciones() {
		// @RolesAllowed("INVITADO")

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT o.ofa_codigo,p.pro_titulo_otorgado,u.uaa_nombre " + "FROM programa p "
				+ "INNER JOIN uaa u ON (p.uaa_codigo=u.uaa_codigo) "
				+ "INNER JOIN oferta_academica o ON (p.pro_codigo=o.pro_codigo) "
				+ "INNER JOIN inscripcion_programa ip ON (o.ofa_codigo = ip.ofa_codigo) "
				+ "INNER JOIN inscripcion i ON (ip.ins_codigo = i.ins_codigo) "
				+ "WHERE p.mod_codigo = 5 AND o.ofa_admision_automatica=1 " // 1-manual
																			// 0-Automatica
				+ "AND GETDATE() between o.ofa_fecha_inscripcion_inicio AND o.ofa_fecha_fin "
				+ "GROUP BY o.ofa_codigo,p.pro_titulo_otorgado,u.uaa_nombre";

		List<Programa> listaProgramas = jdbcTemplate.query(sql, new RowMapper<Programa>() {

			public Programa mapRow(ResultSet rs, int rowNum) throws SQLException {
				Programa programa = new Programa();
				programa.setCodigo(rs.getInt("ofa_codigo"));
				programa.setNombre(rs.getString("pro_titulo_otorgado"));
				programa.setNombreUaa(rs.getString("uaa_nombre"));
				return programa;
			}

		});

		return listaProgramas;
	}

	@Override
	public Inscripcion consultarInscripcion(int ins_codigo) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "SELECT i.ins_usuario, i.ins_codigo, ip.inp_codigo, i.per_codigo, i.ter_codigo, i.ins_referencia, "
					+ " ip.ofa_codigo, ip.inp_opcion, ip.inp_nota_prueba_especial, pr.pro_titulo_otorgado,"
					+ " u.uaa_codigo, u.uaa_nombre, t.ter_identificacion, p.per_identificacion"
					+ " FROM inscripcion i"
					+ " INNER JOIN inscripcion_programa ip ON (i.ins_codigo = ip.ins_codigo)"
					+ " INNER JOIN oferta_academica o ON (ip.ofa_codigo = o.ofa_codigo)"
					+ " INNER JOIN programa pr ON (o.pro_codigo = pr.pro_codigo)"
					+ " INNER JOIN uaa u ON (pr.uaa_codigo = u.uaa_codigo)"
					+ " LEFT OUTER JOIN dbo.tercero t ON (t.ter_codigo = i.ter_codigo)"
					+ " LEFT OUTER JOIN dbo.persona p ON (p.per_codigo = i.per_codigo)"
					+ " WHERE i.ins_codigo = " + ins_codigo
					+ " ORDER BY o.ofa_codigo DESC";
			List<Inscripcion> listaInscripcion = jdbcTemplate.query(sql, new RowMapper<Inscripcion>() {

				public Inscripcion mapRow(ResultSet rs, int rowNum) throws SQLException {
					Inscripcion ins = new Inscripcion();
					ins.setId(rs.getInt("ins_codigo"));
					ins.setCodigoUsuario(rs.getInt("ins_usuario"));

					InscripcionPrograma insp = new InscripcionPrograma();
					insp.setId(rs.getInt("inp_codigo"));
					ins.setInscripcionPrograma(insp);

					Persona persona = new Persona();
					persona.setId(rs.getInt("per_codigo"));
					persona.setIdentificacion(rs.getString("per_identificacion"));
					persona.setTercero(false);
					if (persona.getId() == 0) {
						persona.setId(rs.getInt("ter_codigo"));
						persona.setIdentificacion(rs.getString("ter_identificacion"));
						persona.setTercero(true);
					}

					Oferta oferta = new Oferta();
					oferta.setCodigo(rs.getInt("ofa_codigo"));

					Programa programa = new Programa();
					programa.setNombreUaa(rs.getString("uaa_nombre"));
					programa.setNombre(rs.getString("pro_titulo_otorgado"));
					oferta.setPrograma(programa);
					ins.setOferta(oferta);
					ins.setPersona(persona);
					return ins;
				}
			});
			if (listaInscripcion.size() > 0) {
				return listaInscripcion.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
