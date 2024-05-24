package co.edu.usco.lcms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.Oferta;
import co.edu.usco.lcms.model.OfertaRequisitos;
import co.edu.usco.lcms.model.Persona;
import co.edu.usco.lcms.model.PreInscripcion;
import co.edu.usco.lcms.model.Programa;
import co.edu.usco.lcms.model.RequisitosAdjuntos;
import co.edu.usco.lcms.model.Uaa;

@Component
public class OfertaDaoJDBCTemplateImpl implements OfertaDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	WebParametroDao webParametroDao;

	@Override
	public List<Oferta> crearListaOferta(String uaa, String requierePago) {
		// @RolesAllowed("INVITADO")

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "SELECT o.ofa_codigo,o.ofa_requiere_pago,o.ofa_imagen, o.ofa_fecha_inscripcion_inicio, ";
		sql = sql + " o.ofa_fecha_inscripcion_fin, o.ofa_fecha_inicio, o.ofa_fecha_fin, o.ofa_cupo_max, o.ofa_valor,";
		sql = sql + " p.pro_codigo,p.pro_titulo_otorgado,u.uaa_codigo,u.uaa_nombre ";
		sql = sql + " FROM programa p ";
		sql = sql + " INNER JOIN uaa u ON (p.uaa_codigo=u.uaa_codigo) ";
		sql = sql + " INNER JOIN oferta_academica o ON (p.pro_codigo=o.pro_codigo) ";
		sql = sql + " WHERE p.mod_codigo = 5 AND o.ofa_estado = 1 ";

		if (!requierePago.equals("") && !requierePago.equals("undefined")) {
			sql = sql + " AND o.ofa_requiere_pago in (" + requierePago + ") ";
		}

		if (!uaa.equals("")) {
			sql = sql + " AND u.uaa_codigo in (" + uaa + ") ";
		} else {
			sql = sql + " AND GETDATE() between o.ofa_fecha_inscripcion_inicio AND o.ofa_fecha_inscripcion_fin ";
		}

		sql = sql + " GROUP BY o.ofa_codigo,o.ofa_requiere_pago,o.ofa_imagen, o.ofa_fecha_inscripcion_inicio, ";
		sql = sql + " o.ofa_fecha_inscripcion_fin, o.ofa_fecha_inicio, o.ofa_fecha_fin, o.ofa_cupo_max, o.ofa_valor,";
		sql = sql + " p.pro_codigo,p.pro_titulo_otorgado,u.uaa_codigo,u.uaa_nombre ";
		List<Oferta> listaOferta = jdbcTemplate.query(sql, new RowMapper<Oferta>() {

			public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
				Oferta oferta = new Oferta();
				oferta.setCodigo(rs.getInt("ofa_codigo"));
				oferta.setRequierePago(rs.getInt("ofa_requiere_pago"));
				oferta.setImagen(rs.getString("ofa_imagen"));

				oferta.setFechaIncripcionInicio(rs.getDate("ofa_fecha_inscripcion_inicio"));
				oferta.setFechaIncripcionFin(rs.getDate("ofa_fecha_inscripcion_fin"));
				oferta.setFechaInicio(rs.getDate("ofa_fecha_inicio"));
				oferta.setFechaFin(rs.getDate("ofa_fecha_fin"));
				oferta.setCupoMaximo(rs.getInt("ofa_cupo_max"));
				oferta.setValor(rs.getInt("ofa_valor"));

				Programa programa = new Programa();
				programa.setCodigo(rs.getInt("pro_codigo"));
				programa.setNombre(rs.getString("pro_titulo_otorgado"));
				programa.setCodigoUaa(rs.getInt("uaa_codigo"));
				programa.setNombreUaa(rs.getString("uaa_nombre"));
				oferta.setPrograma(programa);
				return oferta;
			}
		});

		return listaOferta;
	}

	@Override
	public List<Oferta> crearListaOfertaEducacionFormal(String calendario) {
		// @RolesAllowed("INVITADO")

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "select ofa_codigo, uaa_nombre_corto, programa.pro_codigo, ";
		sql = sql + "programa.sed_codigo, oferta_academica.cal_codigo, sed_nombre, cal_nombre, ";
		sql = sql
				+ "ofa_fecha_inscripcion_inicio, ofa_fecha_inscripcion_fin, ofa_fecha_inicio, ofa_fecha_fin, ofa_cupo_max, ";
		sql = sql + "ofa_valor, pro_titulo_otorgado, uaa.uaa_codigo, uaa_nombre ";
		sql = sql + "from oferta_academica, programa, uaa, sede, calendario ";
		sql = sql + "where oferta_academica.pro_codigo = programa.pro_codigo ";
		sql = sql + "and programa.uaa_codigo = uaa.uaa_codigo ";
		sql = sql + "and programa.sed_codigo = sede.sed_codigo ";
		sql = sql + "and calendario.cal_codigo = oferta_academica.cal_codigo ";
		sql = sql + "and programa.nia_codigo in(9, 11) ";
		sql = sql + "and ofa_estado = 1 and programa.mod_codigo != 5 ";
		// sql = sql + "and getDate() between ofa_fecha_inicio and ofa_fecha_fin
		// ";
		sql = sql + "and oferta_academica.cal_codigo in (" + calendario + ") ";
		sql = sql + "order by uaa_nombre_corto ";

		List<Oferta> listaOferta = jdbcTemplate.query(sql, new RowMapper<Oferta>() {

			public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
				Oferta oferta = new Oferta();
				oferta.setCodigo(rs.getInt("ofa_codigo"));

				oferta.setFechaIncripcionInicio(rs.getDate("ofa_fecha_inscripcion_inicio"));
				oferta.setFechaIncripcionFin(rs.getDate("ofa_fecha_inscripcion_fin"));
				oferta.setFechaInicio(rs.getDate("ofa_fecha_inicio"));
				oferta.setFechaFin(rs.getDate("ofa_fecha_fin"));
				oferta.setCupoMaximo(rs.getInt("ofa_cupo_max"));
				oferta.setValor(rs.getInt("ofa_valor"));

				Programa programa = new Programa();
				programa.setCodigo(rs.getInt("pro_codigo"));
				programa.setNombre(rs.getString("pro_titulo_otorgado"));
				programa.setCodigoUaa(rs.getInt("uaa_codigo"));
				programa.setNombreUaa(rs.getString("uaa_nombre"));
				oferta.setPrograma(programa);
				return oferta;
			}
		});

		return listaOferta;
	}

	@Override
	public List<Oferta> crearListaFacultadesOferta(int requierePago) {
		// @RolesAllowed("INVITADO")
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = " SELECT u.uaa_codigo,UPPER(u.uaa_nombre) as uaa_nombre ";
		sql = sql + "FROM programa p ";
		sql = sql + "INNER JOIN uaa u ON (p.uaa_codigo=u.uaa_codigo) ";
		sql = sql + "INNER JOIN oferta_academica o ON (p.pro_codigo=o.pro_codigo) ";
		sql = sql + "WHERE p.mod_codigo = 5 AND o.ofa_estado = 1 ";
		if (requierePago != 2) {
			sql = sql + " AND o.ofa_requiere_pago = " + requierePago;
		}
		sql = sql + "AND GETDATE() between o.ofa_fecha_inscripcion_inicio AND o.ofa_fecha_inscripcion_fin ";
		sql = sql + "GROUP BY u.uaa_codigo,u.uaa_nombre ";
		sql = sql + "ORDER BY u.uaa_nombre ASC ";

		List<Oferta> listaOferta = jdbcTemplate.query(sql, new RowMapper<Oferta>() {

			public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
				Oferta oferta = new Oferta();
				Programa programa = new Programa();
				programa.setCodigoUaa(rs.getInt("uaa_codigo"));
				programa.setNombreUaa(rs.getString("uaa_nombre"));
				oferta.setPrograma(programa);
				return oferta;
			}
		});

		return listaOferta;
	}

	@Override
	public List<Oferta> crearListaOfertaInactiva() {
		// @RolesAllowed("INVITADO")

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT o.ofa_codigo,o.ofa_requiere_pago,o.ofa_imagen, o.ofa_fecha_inscripcion_inicio, ";
		sql = sql
				+ " o.ofa_fecha_inscripcion_fin, o.ofa_fecha_inicio, o.ofa_fecha_fin, o.ofa_fecha_fin, o.ofa_cupo_max, o.ofa_valor,";
		sql = sql + " p.pro_codigo,p.pro_titulo_otorgado,u.uaa_codigo,u.uaa_nombre ";
		sql = sql + " FROM programa p ";
		sql = sql + " INNER JOIN uaa u ON (p.uaa_codigo=u.uaa_codigo) ";
		sql = sql + " INNER JOIN oferta_academica o ON (p.pro_codigo=o.pro_codigo) ";
		sql = sql + " WHERE p.mod_codigo = 5 AND GETDATE() > o.ofa_fecha_inscripcion_fin ";
		sql = sql + " AND o.ofa_estado = 1";
		sql = sql + " GROUP BY o.ofa_codigo,o.ofa_requiere_pago,o.ofa_imagen, o.ofa_fecha_inscripcion_inicio, ";
		sql = sql + " o.ofa_fecha_inscripcion_fin, o.ofa_fecha_inicio, o.ofa_fecha_fin, o.ofa_cupo_max, o.ofa_valor,";
		sql = sql + " p.pro_codigo,p.pro_titulo_otorgado,u.uaa_codigo,u.uaa_nombre ";

		List<Oferta> listaOferta = jdbcTemplate.query(sql, new RowMapper<Oferta>() {

			public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
				Oferta oferta = new Oferta();
				oferta.setCodigo(rs.getInt("ofa_codigo"));
				oferta.setRequierePago(rs.getInt("ofa_requiere_pago"));
				oferta.setImagen(rs.getString("ofa_imagen"));

				oferta.setFechaIncripcionInicio(rs.getDate("ofa_fecha_inscripcion_inicio"));
				oferta.setFechaIncripcionFin(rs.getDate("ofa_fecha_inscripcion_fin"));
				oferta.setFechaInicio(rs.getDate("ofa_fecha_inicio"));
				oferta.setFechaFin(rs.getDate("ofa_fecha_fin"));
				oferta.setCupoMaximo(rs.getInt("ofa_cupo_max"));
				oferta.setValor(rs.getInt("ofa_valor"));

				Programa programa = new Programa();
				programa.setCodigo(rs.getInt("pro_codigo"));
				programa.setNombre(rs.getString("pro_titulo_otorgado"));
				programa.setCodigoUaa(rs.getInt("uaa_codigo"));
				programa.setNombreUaa(rs.getString("uaa_nombre"));
				oferta.setPrograma(programa);
				return oferta;
			}
		});

		return listaOferta;
	}

	@Override
	public Oferta consultarDatosOferta(int codigoOferta) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "SELECT o.ofa_codigo,o.cal_codigo,o.ofa_imagen, o.ofa_fecha_inscripcion_inicio, ";
			sql = sql
					+ " o.ofa_fecha_inscripcion_fin, o.ofa_fecha_inicio, o.ofa_fecha_fin, o.ofa_requiere_pago, o.ofa_cupo_max, o.ofa_valor,";
			sql = sql + "p.pro_codigo,p.pro_titulo_otorgado,u.uaa_nombre,p.pro_horario, u.uaa_codigo  ";
			sql = sql + "FROM programa p ";
			sql = sql + "INNER JOIN uaa u ON (p.uaa_codigo=u.uaa_codigo) ";
			sql = sql + "INNER JOIN oferta_academica o ON (p.pro_codigo=o.pro_codigo) ";
			sql = sql + "WHERE p.mod_codigo = 5 ";

			sql = sql + " AND o.ofa_codigo = " + codigoOferta;
			// sql = sql + " AND GETDATE() between
			// o.ofa_fecha_inscripcion_inicio AND o.ofa_fecha_inscripcion_fin ";

			sql = sql + " GROUP BY o.ofa_codigo,o.cal_codigo,o.ofa_imagen, o.ofa_fecha_inscripcion_inicio, ";
			sql = sql
					+ "o.ofa_fecha_inscripcion_fin, o.ofa_fecha_inicio, o.ofa_fecha_fin, o.ofa_requiere_pago, o.ofa_cupo_max, o.ofa_valor,";
			sql = sql + "p.pro_codigo,p.pro_titulo_otorgado,u.uaa_nombre,p.pro_horario, u.uaa_codigo  ";

			List<Oferta> listaOferta = jdbcTemplate.query(sql, new RowMapper<Oferta>() {

				public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
					Oferta oferta = new Oferta();
					oferta.setCodigo(rs.getInt("ofa_codigo"));
					oferta.setImagen(rs.getString("ofa_imagen"));
					oferta.setFechaIncripcionInicio(rs.getDate("ofa_fecha_inscripcion_inicio"));
					oferta.setFechaIncripcionFin(rs.getDate("ofa_fecha_inscripcion_fin"));
					oferta.setFechaInicio(rs.getDate("ofa_fecha_inicio"));
					oferta.setFechaFin(rs.getDate("ofa_fecha_fin"));
					oferta.setRequierePago(rs.getInt("ofa_requiere_pago"));
					oferta.setCupoMaximo(rs.getInt("ofa_cupo_max"));
					oferta.setValor(rs.getInt("ofa_valor"));

					Programa programa = new Programa();
					programa.setCodigo(rs.getInt("pro_codigo"));
					programa.setNombre(rs.getString("pro_titulo_otorgado"));
					programa.setNombreUaa(rs.getString("uaa_nombre"));
					programa.setHorario(rs.getString("pro_horario"));
					programa.setCodigoUaa(rs.getInt("uaa_codigo"));
					oferta.setPrograma(programa);
					return oferta;
				}

			});
			if (listaOferta.size() > 0) {
				return listaOferta.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Oferta> consultarInformacionOferta(int codigoOferta) {
		// @RolesAllowed("INVITADO")

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT ofi_titulo, ofi_contenido " + "FROM inscripciones.oferta_informacion "
				+ "WHERE ofa_codigo = " + codigoOferta + " AND ofi_estado = 1 " + "ORDER BY ofi_orden ASC";

		List<Oferta> listaRequisitos = jdbcTemplate.query(sql, new RowMapper<Oferta>() {

			public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
				Oferta oferta = new Oferta();
				oferta.setTitulo(rs.getString("ofi_titulo"));
				oferta.setContenido(rs.getString("ofi_contenido"));
				return oferta;
			}

		});

		return listaRequisitos;
	}

	@Override
	public List<OfertaRequisitos> consultarRequisitosOfertaAdjuntos(String codigoOferta) {
		// @RolesAllowed("INVITADO")

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT o.ore_codigo, o.ore_descripcion , ot.otr_descripcion "
				+ "FROM inscripciones.oferta_requisito o, inscripciones.oferta_requisito_tipo ot "
				+ "WHERE o.otr_codigo=ot.otr_codigo AND ot.otr_codigo = 1 "
				+ "AND CONVERT(VARCHAR(255),HASHBYTES('SHA1', CONVERT(VARCHAR(255),o.ofa_codigo))) = 0x" + codigoOferta;

		List<OfertaRequisitos> listaRequisitos = jdbcTemplate.query(sql, new RowMapper<OfertaRequisitos>() {

			public OfertaRequisitos mapRow(ResultSet rs, int rowNum) throws SQLException {
				OfertaRequisitos requisitos = new OfertaRequisitos();
				requisitos.setCodigo(rs.getInt("ore_codigo"));
				requisitos.setDescripcion(rs.getString("ore_descripcion"));
				requisitos.setNombreTipo(rs.getString("otr_descripcion"));
				return requisitos;
			}

		});

		return listaRequisitos;
	}

	@Override
	public Oferta consultarEstadoOferta(long codigoOferta, String codigoEncriptado) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "SELECT o.pro_codigo,p.pro_titulo_otorgado,p.pro_estado,o.cal_codigo,o.ofa_codigo,o.ofa_estado,";
			sql = sql + " o.ofa_admision_automatica,  o.ofa_requiere_pago, o.ofa_crear_usuario, ";
			sql = sql + " o.ofa_fecha_inscripcion_fin, o.ofa_cupo_max, o.ofa_valor, u.uaa_nombre,utd.uat_codigo, ";
			sql = sql + " oe.ofe_codigo as interna, o.ofa_fecha_inscripcion_inicio, utd.uat_nombre";
			sql = sql + " FROM oferta_academica o ";
			sql = sql + " INNER JOIN programa p ON (o.pro_codigo = p.pro_codigo)";
			sql = sql + " INNER JOIN uaa u ON p.uaa_codigo = u.uaa_codigo ";
			sql = sql + " INNER JOIN uaa ud ON u.uaa_dependencia = ud.uaa_codigo";
			sql = sql + " INNER JOIN uaa_tipo utd ON (ud.uat_codigo = utd.uat_codigo)";
			sql = sql + " LEFT JOIN inscripciones.oferta_estamento oe ON ";
			sql = sql + " (o.ofa_codigo = oe.ofa_codigo AND oe.ofe_estado = 1)";

			if (codigoOferta != 0) {
				sql = sql + " WHERE o.ofa_codigo = " + codigoOferta;
			} else {
				sql = sql + " WHERE CONVERT(VARCHAR(255),HASHBYTES('SHA1', CONVERT(VARCHAR(255),o.ofa_codigo))) = 0x"
						+ codigoEncriptado;
			}
			List<Oferta> listaEstadoOferta = jdbcTemplate.query(sql, new RowMapper<Oferta>() {

				public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
					Oferta oferta = new Oferta();
					oferta.setCodigo(rs.getInt("ofa_codigo"));
					oferta.setEstado(rs.getString("ofa_estado"));
					oferta.setAdmisionAutomatica(rs.getInt("ofa_admision_automatica"));
					oferta.setRequierePago(rs.getInt("ofa_requiere_pago"));
					oferta.setCrearUsuario(rs.getInt("ofa_crear_usuario"));
					oferta.setFechaIncripcionInicio(rs.getDate("ofa_fecha_inscripcion_inicio"));
					oferta.setFechaIncripcionFin(rs.getDate("ofa_fecha_inscripcion_fin"));
					oferta.setCupoMaximo(rs.getInt("ofa_cupo_max"));
					oferta.setValor(rs.getInt("ofa_valor"));

					if (rs.getString("interna") != null) {
						oferta.setInterna("1");
					} else {
						oferta.setInterna("0");
					}

					Calendario calendario = new Calendario();
					calendario.setCodigo(rs.getInt("cal_codigo"));
					oferta.setCalendario(calendario);

					Uaa uaa = new Uaa();
					uaa.setNombre(rs.getString("uaa_nombre"));
					Programa programa = new Programa();
					programa.setCodigo(rs.getInt("pro_codigo"));
					programa.setNombre(rs.getString("pro_titulo_otorgado"));
					programa.setEstado(rs.getString("pro_estado"));
					programa.setUaa(uaa);
					oferta.setPrograma(programa);
					return oferta;
				}
			});
			if (listaEstadoOferta.size() > 0) {
				return listaEstadoOferta.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Oferta consultarCantidadInscritos(long codigoOferta) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "SELECT COUNT(*) as cant " + "FROM persona p "
					+ "INNER JOIN inscripcion i ON (p.per_codigo = i.per_codigo) "
					+ "INNER JOIN inscripcion_programa ip ON (i.ins_codigo = ip.ins_codigo) " + "WHERE ip.ofa_codigo = "
					+ codigoOferta;

			List<Oferta> listaEstadoOferta = jdbcTemplate.query(sql, new RowMapper<Oferta>() {

				public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
					Oferta oferta = new Oferta();
					oferta.setCantidadInscritos(rs.getInt("cant"));
					return oferta;
				}
			});
			if (listaEstadoOferta.size() > 0) {
				return listaEstadoOferta.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Oferta> consultarRequisitosAdjuntosOferta(int codigoInscripcion, int oferta) {
		// @RolesAllowed("INVITADO")

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT o.ore_codigo, o.ore_descripcion , ot.otr_descripcion "
				+ "FROM inscripciones.oferta_requisito o "
				+ "INNER JOIN inscripciones.oferta_requisito_tipo ot ON o.otr_codigo=ot.otr_codigo "
				+ "INNER JOIN oferta_academica oa ON o.ofa_codigo = oa.ofa_codigo  ";
		
		if (codigoInscripcion > 0) {
			sql = sql + "INNER JOIN inscripcion_programa ip ON oa.ofa_codigo = ip.ofa_codigo " + "WHERE ot.otr_codigo = 1";
			sql = sql + " AND ip.ins_codigo = " + codigoInscripcion;
		}

		if (oferta > 0) {
			sql = sql + " WHERE ot.otr_codigo = 1";
			sql = sql + " AND oa.ofa_codigo = " + oferta;
		}

		List<Oferta> listaRequisitos = jdbcTemplate.query(sql, new RowMapper<Oferta>() {

			public Oferta mapRow(ResultSet rs, int rowNum) throws SQLException {
				Oferta oferta = new Oferta();
				oferta.setCodigo(rs.getInt("ore_codigo"));
				OfertaRequisitos requisitos = new OfertaRequisitos();
				requisitos.setDescripcion(rs.getString("ore_descripcion"));
				requisitos.setNombreTipo(rs.getString("otr_descripcion"));
				oferta.setOfertaRequisitos(requisitos);
				return oferta;
			}

		});

		return listaRequisitos;
	}

	@Override
	public List<RequisitosAdjuntos> consultarRequisitosAdjuntosInscripcion(int codigoInscripcion) {
		// @RolesAllowed("INVITADO")

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT ea.nombre_completo, ea.nombre_encriptado, ea.tipo, ea.ruta "
				+ "FROM inscripciones.documentosEstudiosAnteriores ea " + " WHERE registro = " + codigoInscripcion;
		List<RequisitosAdjuntos> listaArchivosAdjuntos = jdbcTemplate.query(sql, new RowMapper<RequisitosAdjuntos>() {

			public RequisitosAdjuntos mapRow(ResultSet rs, int rowNum) throws SQLException {
				RequisitosAdjuntos ra = new RequisitosAdjuntos();
				ra.setNombreCompleto(rs.getString("nombre_completo"));
				ra.setNombreEncriptado(rs.getString("nombre_encriptado"));
				ra.setTipo(rs.getString("tipo"));
				ra.setRuta(rs.getString("ruta"));
				return ra;
			}

		});

		return listaArchivosAdjuntos;
	}

	@Override
	public List<PreInscripcion> buscarPreInscritos(int codigoOferta) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "SELECT rt.tem_codigo, rt.tem_identificacion, rt.tem_nombres, rt.tem_apellidos, rt.tem_email, "
				+ "rt.tem_cod_oferta, p.pro_titulo_otorgado " + "FROM inscripciones.registroTemporal rt "
				+ "INNER JOIN oferta_academica o ON (rt.tem_cod_oferta = o.ofa_codigo) "
				+ "INNER JOIN programa p ON (o.pro_codigo = p.pro_codigo) "
				+ "WHERE rt.tem_email_verificado = 1 AND o.ofa_codigo = " + codigoOferta;
		List<PreInscripcion> listaPreInscripcion = jdbcTemplate.query(sql, new RowMapper<PreInscripcion>() {

			public PreInscripcion mapRow(ResultSet rs, int rowNum) throws SQLException {
				PreInscripcion usuariosPreinscritos = new PreInscripcion();

				Programa programa = new Programa();
				Oferta oferta = new Oferta();
				Persona persona = new Persona();

				usuariosPreinscritos.setCodigo(rs.getInt("tem_codigo"));
				persona.setIdentificacion(rs.getString("tem_identificacion"));
				persona.setNombre(rs.getString("tem_nombres"));
				persona.setApellido(rs.getString("tem_apellidos"));
				persona.setEmail(rs.getString("tem_email"));
				programa.setNombre(rs.getString("pro_titulo_otorgado"));

				usuariosPreinscritos.setPersona(persona);
				oferta.setCodigo(rs.getInt("tem_cod_oferta"));
				oferta.setPrograma(programa);
				usuariosPreinscritos.setOferta(oferta);
				return usuariosPreinscritos;
			}

		});
		if (listaPreInscripcion.size() > 0) {
			return listaPreInscripcion;
		} else {
			return null;
		}
	}

}
