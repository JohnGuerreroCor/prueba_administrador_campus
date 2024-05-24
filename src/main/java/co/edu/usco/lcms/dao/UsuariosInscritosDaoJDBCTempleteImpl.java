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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.configuration.UscoGrantedAuthority;
import co.edu.usco.lcms.configuration.User;
import co.edu.usco.lcms.model.Eps;
import co.edu.usco.lcms.model.EpsTipoAfiliacion;
import co.edu.usco.lcms.model.EstadoCivil;
import co.edu.usco.lcms.model.EstratoSocioeconomico;
import co.edu.usco.lcms.model.GrupoSanguineo;
import co.edu.usco.lcms.model.Inscripcion;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.LenguaNativa;
import co.edu.usco.lcms.model.Municipio;
import co.edu.usco.lcms.model.Oferta;
import co.edu.usco.lcms.model.Persona;
import co.edu.usco.lcms.model.Programa;
import co.edu.usco.lcms.model.TipoIdentificacion;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author jankarlos
 *
 */
@Component
public class UsuariosInscritosDaoJDBCTempleteImpl implements UsuariosInscritosDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	Constantes constantes;

	@Override
	public List<Inscripcion> InformacionPersonalInscrito(int ins_codigo, int persona, int tercero) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "SELECT per_identificacion,per_fecha_expedicion,per_lugar_expedicion, "
					+ "per_nombre,per_apellido,per_genero,per_estado_civil,p.grs_codigo, per_email, "
					+ "per_fecha_nacimiento, per_pais_residencia, per_departamento_residencia,per_lugar_nacimiento, "
					+ "per_direccion_residencia, per_barrio, per_telefono_movil, per_estrato, p.eps_codigo, "
					+ "p.eta_codigo, per_id_cotizante, p.lenguaNativa, ter_identificacion, ter_nombre1, "
					+ "ter_nombre2, ter_apellido1, ter_apellido2, ter_fecha_expedicion, ter_email,"
					+ "ter_lugar_expedicion, ter_genero, ter_estado_civil, t.grs_codigo, ter_pais_residencia, "
					+ "ter_departamento_residencia, ter_lugar_residencia, ter_fecha_nacimiento, ter_lugar_nacimiento, "
					+ "ter_direccion, ter_barrio, ter_telefono_movil, ter_estrato, t.eps_codigo, "
					+ "t.eta_codigo, ter_id_cotizante, t.lenguaNativa, inp_codigo, o.ofa_codigo, o.ofa_fecha_inscripcion_inicio, "
					+ "o.ofa_fecha_inscripcion_fin, o.ofa_fecha_inicio, o.ofa_fecha_fin, o.ofa_imagen, "
					+ "pr.pro_titulo_otorgado, u.uaa_codigo, u.uaa_nombre, tip.tii_nombre, tip.tii_nombre_corto, "
					+ "tit.tii_nombre as tii_nombreT, tit.tii_nombre_corto as tii_nombre_cortoT, "
					+ "mple.mun_nombre as lugar_expedicionP, mtle.mun_nombre as lugar_expedicionT, mpln.mun_nombre as lugar_nacimientoP, "
					+ "mtln.mun_nombre as lugar_nacimientoT, mlrp.mun_nombre as lugarResidenciaP, mlrt.mun_nombre as lugarResidenciaT, "
					+ "per_genero as generoP, ter_genero as generoT, ecp.esc_nombre as estadoCivilP, ect.esc_nombre as estadoCivilT, "
					+ "esp.ses_descripcion as estratoP, est.ses_descripcion as estratoT, gsp.grs_nombre as grupoSanguineoP, gst.grs_nombre as grupoSanguineoT, "
					+ "epsp.eps_nombre as epsP, epst.eps_nombre as epsT, etap.eta_nombre as epsTipoP, etat.eta_nombre as epsTipoT, "
					+ "lnp.sid_descripcion as lenguaNativaP, lnt.sid_descripcion as lenguaNativaT "
					+ "FROM inscripcion i " + "LEFT JOIN persona p ON (i.per_codigo = p.per_codigo) "
					+ "LEFT JOIN tipo_id tip ON (p.tii_codigo = tip.tii_codigo) "
					+ "LEFT JOIN tercero t ON (i.ter_codigo = t.ter_codigo) "
					+ "LEFT JOIN tipo_id tit ON (t.tii_codigo = tit.tii_codigo) "
					+ "LEFT JOIN estado_civil ecp ON p.per_estado_civil = ecp.esc_codigo "
					+ "LEFT JOIN estado_civil ect ON t.ter_estado_civil = ect.esc_codigo "
					+ "LEFT JOIN snies_estrato esp ON p.per_estrato = esp.ses_codigo "
					+ "LEFT JOIN snies_estrato est ON t.ter_estrato = est.ses_codigo "
					+ "LEFT JOIN grupo_sanguineo gsp ON p.grs_codigo = gsp.grs_codigo "
					+ "LEFT JOIN grupo_sanguineo gst ON t.grs_codigo = gst.grs_codigo "
					+ "LEFT JOIN eps epsp ON p.eps_codigo = epsp.eps_codigo "
					+ "LEFT JOIN eps epst ON t.eps_codigo = epst.eps_codigo "
					+ "LEFT JOIN eps_tipo_afiliacion etap ON p.eta_codigo = etap.eta_codigo "
					+ "LEFT JOIN eps_tipo_afiliacion etat ON t.eta_codigo = etat.eta_codigo "
					+ "LEFT JOIN snies_idioma lnp ON p.lenguaNativa = lnp.sid_codigo "
					+ "LEFT JOIN snies_idioma lnt ON t.lenguaNativa = lnt.sid_codigo "
					+ "LEFT JOIN municipio mple ON p.per_lugar_expedicion = mple.mun_codigo "
					+ "LEFT JOIN municipio mtle ON t.ter_lugar_expedicion = mtle.mun_codigo "
					+ "LEFT JOIN municipio mpln ON p.per_lugar_nacimiento = mpln.mun_codigo "
					+ "LEFT JOIN municipio mtln ON t.ter_lugar_nacimiento = mtln.mun_codigo "
					+ "LEFT JOIN municipio mlrp ON p.per_lugar_residencia = mlrp.mun_codigo "
					+ "LEFT JOIN municipio mlrt ON t.ter_lugar_residencia = mlrt.mun_codigo "
					+ "INNER JOIN inscripcion_programa ip ON (i.ins_codigo = ip.ins_codigo) "
					+ "INNER JOIN oferta_academica o ON (ip.ofa_codigo = o.ofa_codigo) "
					+ "INNER JOIN programa pr ON (o.pro_codigo = pr.pro_codigo) "
					+ "INNER JOIN uaa u ON (pr.uaa_codigo = u.uaa_codigo) ";

			if (ins_codigo > 0) {
				sql = sql + "WHERE i.ins_codigo = " + ins_codigo;
			}
			
			if (persona > 0) {
				sql = sql + "WHERE p.per_codigo = " + persona;
			}
			
			if (tercero > 0) {
				sql = sql + "WHERE t.ter_codigo = " + tercero;
			}
			
			sql = sql + " ORDER BY o.ofa_codigo DESC";

			List<Inscripcion> InfoInscripcion = jdbcTemplate.query(sql, new RowMapper<Inscripcion>() {

				public Inscripcion mapRow(ResultSet rs, int rowNum) throws SQLException {
					Inscripcion ins = new Inscripcion();

					Persona persona = new Persona();
					persona.setIdentificacion(rs.getString("per_identificacion"));
					if (persona.getIdentificacion() == null) {
						persona.setIdentificacion(rs.getString("ter_identificacion"));
					}
					TipoIdentificacion tii = new TipoIdentificacion();
					tii.setNombre(rs.getString("tii_nombre"));
					tii.setNombreCorto(rs.getString("tii_nombre_corto"));
					if (tii.getNombre() == null && tii.getNombreCorto() == null) {
						tii.setNombre(rs.getString("tii_nombreT"));
						tii.setNombreCorto(rs.getString("tii_nombre_cortoT"));
					}

					EstadoCivil estadoCivil = new EstadoCivil();
					estadoCivil.setNombre(rs.getString("estadoCivilP"));
					if (estadoCivil.getNombre() == null) {
						estadoCivil.setNombre(rs.getString("estadoCivilT"));
					}

					EstratoSocioeconomico estrato = new EstratoSocioeconomico();
					estrato.setNombre(rs.getString("estratoP"));
					if (estrato.getNombre() == null) {
						estrato.setNombre(rs.getString("estratoT"));
					}

					GrupoSanguineo grupoSanguineo = new GrupoSanguineo();
					grupoSanguineo.setNombre(rs.getString("grupoSanguineoP"));
					if (grupoSanguineo.getNombre() == null) {
						grupoSanguineo.setNombre(rs.getString("grupoSanguineoT"));
					}

					Eps eps = new Eps();
					eps.setNombre(rs.getString("epsP"));
					if (eps.getNombre() == null) {
						eps.setNombre(rs.getString("epsT"));
					}

					EpsTipoAfiliacion tipoAfiliacion = new EpsTipoAfiliacion();
					tipoAfiliacion.setNombre(rs.getString("epsTipoP"));
					if (tipoAfiliacion.getNombre() == null) {
						tipoAfiliacion.setNombre(rs.getString("epsTipoT"));
					}

					LenguaNativa lenguaNativa = new LenguaNativa();
					lenguaNativa.setNombre(rs.getString("lenguaNativaP"));
					if (lenguaNativa.getNombre() == null) {
						lenguaNativa.setNombre(rs.getString("lenguaNativaT"));
					}

					persona.setNombre(rs.getString("per_nombre"));
					if (persona.getNombre() == null) {
						persona.setNombre(rs.getString("ter_nombre1") + " " + rs.getString("ter_nombre2"));
					}
					persona.setApellido(rs.getString("per_apellido"));
					if (persona.getApellido() == null) {
						persona.setApellido(rs.getString("ter_apellido1") + " " + rs.getString("ter_apellido2"));
					}
					persona.setFechaExpedicion(rs.getDate("per_fecha_expedicion"));
					if (persona.getFechaExpedicion() == null) {
						persona.setFechaExpedicion(rs.getDate("ter_fecha_expedicion"));
					}
					persona.setFechaNacimiento(rs.getDate("per_fecha_nacimiento"));
					if (persona.getFechaNacimiento() == null) {
						persona.setFechaNacimiento(rs.getDate("ter_fecha_nacimiento"));
					}
					persona.setDireccion(rs.getString("per_direccion_residencia"));
					if (persona.getDireccion() == null) {
						persona.setDireccion(rs.getString("ter_direccion"));
					}
					persona.setEmail(rs.getString("per_email"));
					if (persona.getEmail() == null) {
						persona.setEmail(rs.getString("ter_email"));
					}
					persona.setBarrio(rs.getString("per_barrio"));
					if (persona.getBarrio() == null) {
						persona.setBarrio(rs.getString("ter_barrio"));
					}
					persona.setTelefono(rs.getString("per_telefono_movil"));
					if (persona.getTelefono() == null) {
						persona.setTelefono(rs.getString("ter_telefono_movil"));
					}
					/*persona.setIdentificacionCotizante(rs.getInt("per_id_cotizante"));
					if (persona.getIdentificacionCotizante() == 0) {
						persona.setIdentificacionCotizante(rs.getInt("ter_id_cotizante"));
					}*/

					Municipio lugarExpedicion = new Municipio();
					lugarExpedicion.setNombre(rs.getString("lugar_expedicionP"));
					if (lugarExpedicion.getNombre() == null) {
						lugarExpedicion.setNombre(rs.getString("lugar_expedicionT"));
					}
					Municipio lugarNacimiento = new Municipio();
					lugarNacimiento.setNombre(rs.getString("lugar_nacimientoP"));
					if (lugarNacimiento.getNombre() == null) {
						lugarNacimiento.setNombre(rs.getString("lugar_nacimientoT"));
					}

					Municipio lugarResidencia = new Municipio();
					lugarResidencia.setNombre(rs.getString("lugarResidenciaP"));
					if (lugarResidencia.getNombre() == null) {
						lugarResidencia.setNombre(rs.getString("lugarResidenciaT"));
					}

					persona.setGenero(rs.getString("generoP"));
					if (persona.getGenero() == null) {
						persona.setGenero(rs.getString("generoT"));
					}

					persona.setTipoIdentificacion(tii);
					persona.setEstadoCivil(estadoCivil);
					persona.setEstrato(estrato);
					persona.setGrupoSanguineo(grupoSanguineo);
					persona.setEps(eps);
					persona.setTipoAfiliacion(tipoAfiliacion);
					persona.setLenguaNativa(lenguaNativa);
					persona.setLugarExpedicion(lugarExpedicion);
					persona.setLugarNacimiento(lugarNacimiento);
					persona.setLugarResidencia(lugarResidencia);

					/*
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * inp_codigo
					 */

					Oferta oferta = new Oferta();
					oferta.setCodigo(rs.getInt("ofa_codigo"));
					oferta.setFechaIncripcionInicio(rs.getDate("ofa_fecha_inscripcion_inicio"));
					oferta.setFechaIncripcionFin(rs.getDate("ofa_fecha_inscripcion_fin"));
					oferta.setFechaInicio(rs.getDate("ofa_fecha_inicio"));
					oferta.setFechaFin(rs.getDate("ofa_fecha_fin"));
					oferta.setImagen(rs.getString("ofa_imagen"));

					Programa programa = new Programa();
					programa.setTitulo_otorgado(rs.getString("pro_titulo_otorgado"));
					Uaa uaa = new Uaa();
					uaa.setNombre(rs.getString("uaa_nombre"));
					programa.setUaa(uaa);
					oferta.setPrograma(programa);
					ins.setOferta(oferta);
					ins.setPersona(persona);
					return ins;
				}
			});
			if (InfoInscripcion.size() > 0) {
				return InfoInscripcion;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.UaaDao#listarTablaUaa(java.lang.String, int,
	 * int, int)
	 */
	@Override
	public JSONRespuesta listarTablaUsuariosInscritos(String search, int start, int length, int draw, int posicion,
			String direccion, int codigoOferta) {
		// TODO Auto-generated method stub
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int uaaUsuario = 0;
		String rol = null;

		boolean adminGral = false;
		boolean adminFacultad = false;

		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
			rol = uscoGrantedAuthority.getRole();
			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS)) {
				adminFacultad = true;
				uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			}

			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_LCMS)) {
				adminGral = true;
				uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			}
		}

		JSONRespuesta respuesta = new JSONRespuesta();

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		String[] campos = { "i.ins_codigo", "p.per_identificacion + ' ' + t.ter_identificacion",
				"p.per_nombre + ' ' + t.ter_nombre1 + ' ' + t.ter_nombre2",
				"p.per_apellido + ' ' + t.ter_apellido1 + ' ' + t.ter_apellido2", "pr.pro_titulo_otorgado" };

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT COUNT(*) as cant ";
		sql = sql + "FROM inscripcion i ";
		sql = sql + "LEFT JOIN persona p ON (i.per_codigo = p.per_codigo) ";
		sql = sql + "LEFT JOIN tercero t ON (i.ter_codigo = t.ter_codigo) ";
		sql = sql + "INNER JOIN inscripcion_programa ip ON (i.ins_codigo = ip.ins_codigo) ";
		sql = sql + "INNER JOIN oferta_academica o ON (ip.ofa_codigo = o.ofa_codigo) ";
		sql = sql + "INNER JOIN programa pr ON (o.pro_codigo = pr.pro_codigo) ";
		sql = sql + "INNER JOIN uaa u ON (pr.uaa_codigo = u.uaa_codigo) ";
		sql = sql + "WHERE pr.mod_codigo = 5 AND o.ofa_admision_automatica=1 ";
		sql = sql + "AND ip.inp_codigo not in (select inp_codigo from admision_resultado_lcms) "
				+ "AND u.uat_codigo in (" + uaaTipoNoFormal + ") ";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
		}

		if (codigoOferta > 0) {
			sql = sql + "AND o.ofa_codigo = " + codigoOferta;
		}

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + "AND (i.ins_codigo like ? or p.per_identificacion like ? or p.per_nombre like ? ";
			sql = sql + "or p.per_apellido like ? or t.ter_identificacion like ? ";
			sql = sql + "or t.ter_nombre1 + ' ' + t.ter_nombre2 like ? ";
			sql = sql + "or t.ter_apellido1 + ' ' + t.ter_apellido2 like ? ";
			sql = sql + "or pr.pro_titulo_otorgado like ?) ";
			filtrados = jdbcTemplate.queryForObject(sql,
					new Object[] { "&" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
							"%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%" },
					Integer.class);
		}

		System.out.println("***************1: " + sql);

		sql = "SELECT per_codigo, ter_codigo, ins_codigo, per_identificacion, per_nombre, per_apellido, ter_identificacion, "
				+ "ter_nombre1, ter_nombre2, ter_apellido1, ter_apellido2, pro_titulo_otorgado";
		sql = sql + " from (select row_number() over(order by " + campos[posicion] + " " + direccion
				+ ") AS RowNumber, ";
		sql = sql + "p.per_codigo, t.ter_codigo, i.ins_codigo, p.per_identificacion, p.per_nombre, p.per_apellido, t.ter_identificacion, "
				+ "t.ter_nombre1, t.ter_nombre2, t.ter_apellido1, t.ter_apellido2, pr.pro_titulo_otorgado";
		sql = sql + " FROM inscripcion i ";
		sql = sql + "LEFT JOIN persona p ON (i.per_codigo = p.per_codigo) ";
		sql = sql + "LEFT JOIN tercero t ON (i.ter_codigo = t.ter_codigo) ";
		sql = sql + "INNER JOIN inscripcion_programa ip ON (i.ins_codigo = ip.ins_codigo) ";
		sql = sql + "INNER JOIN oferta_academica o ON (ip.ofa_codigo = o.ofa_codigo) ";
		sql = sql + "INNER JOIN programa pr ON (o.pro_codigo = pr.pro_codigo) ";
		sql = sql + "INNER JOIN uaa u ON (pr.uaa_codigo = u.uaa_codigo) ";
		sql = sql + "WHERE pr.mod_codigo = 5 AND o.ofa_admision_automatica=1 ";
		sql = sql + "AND (i.ins_codigo like ? or p.per_identificacion like ? or p.per_nombre like ? ";
		sql = sql + "or p.per_apellido like ? or t.ter_identificacion like ? ";
		sql = sql + "or t.ter_nombre1 + ' ' + t.ter_nombre2 like ? ";
		sql = sql + "or t.ter_apellido1 + ' ' + t.ter_apellido2 like ? ";
		sql = sql + "or pr.pro_titulo_otorgado like ?) ";
		sql = sql + "AND ip.inp_codigo not in (select inp_codigo from admision_resultado_lcms) ";
		sql = sql + "AND u.uat_codigo in (" + uaaTipoNoFormal + ") ";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
		}

		if (codigoOferta > 0) {
			sql = sql + " AND o.ofa_codigo = " + codigoOferta;
		}

		sql = sql + ") as tabla";
		sql = sql + " where tabla.RowNumber between ? and ?";
		
		
		System.out.println("2: " + sql);
		List<Inscripcion> listaUsu = jdbcTemplate.query(sql,
				new Object[] { "%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%",
						"%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Inscripcion>() {

					public Inscripcion mapRow(ResultSet rs, int rowNum) throws SQLException {
						Inscripcion usuariosInscritos = new Inscripcion();

						Programa programa = new Programa();
						Oferta oferta = new Oferta();
						Persona persona = new Persona();
						persona.setCodigo(rs.getInt("per_codigo"));
						persona.setId(rs.getInt("ter_codigo"));
						usuariosInscritos.setCodigo(rs.getInt("ins_codigo"));
						persona.setIdentificacion(rs.getString("per_identificacion"));
						if (persona.getIdentificacion() == null) {
							persona.setIdentificacion(rs.getString("ter_identificacion"));
						}
						persona.setNombre(rs.getString("per_nombre"));
						if (persona.getNombre() == null) {
							persona.setNombre(rs.getString("ter_nombre1") + " " + rs.getString("ter_nombre2"));
						}
						persona.setApellido(rs.getString("per_apellido"));
						if (persona.getApellido() == null) {
							persona.setApellido(rs.getString("ter_apellido1") + " " + rs.getString("ter_apellido2"));
						}
						programa.setNombre(rs.getString("pro_titulo_otorgado"));

						usuariosInscritos.setPersona(persona);
						oferta.setPrograma(programa);
						usuariosInscritos.setOferta(oferta);
						return usuariosInscritos;
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

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int uaaUsuario = 0;
		String rol = null;

		boolean adminGral = false;
		boolean adminFacultad = false;

		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
			rol = uscoGrantedAuthority.getRole();
			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS)) {
				adminFacultad = true;
				uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			}

			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_LCMS)) {
				adminGral = true;
				uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			}
		}

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT o.ofa_codigo,p.pro_titulo_otorgado,u.uaa_nombre " + "FROM programa p "
				+ "INNER JOIN uaa u ON (p.uaa_codigo=u.uaa_codigo) "
				+ "INNER JOIN oferta_academica o ON (p.pro_codigo=o.pro_codigo) "
				+ "INNER JOIN inscripcion_programa ip ON (o.ofa_codigo = ip.ofa_codigo) "
				+ "INNER JOIN inscripcion i ON (ip.ins_codigo = i.ins_codigo) "
				+ "WHERE p.mod_codigo = 5 AND o.ofa_admision_automatica=1 " // 1-manual
																			// 0-Automatica
				+ "AND GETDATE() between o.ofa_fecha_inscripcion_inicio AND o.ofa_fecha_fin "
				+ "AND ip.inp_codigo not in (select inp_codigo from admision_resultado_lcms) " + "AND u.uat_codigo in ("
				+ uaaTipoNoFormal + ") AND o.ofa_estado=1";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " OR u.uaa_codigo = " + uaaUsuario + ")";
		}

		sql = sql + "GROUP BY o.ofa_codigo,p.pro_titulo_otorgado,u.uaa_nombre";

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

}