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
import co.edu.usco.lcms.model.Calendario;
import co.edu.usco.lcms.model.Eps;
import co.edu.usco.lcms.model.EpsTipoAfiliacion;
import co.edu.usco.lcms.model.EstadoCivil;
import co.edu.usco.lcms.model.EstratoSocioeconomico;
import co.edu.usco.lcms.model.Estudiante;
import co.edu.usco.lcms.model.GrupoSanguineo;
import co.edu.usco.lcms.model.Inscripcion;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.LenguaNativa;
import co.edu.usco.lcms.model.Matricula;
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
public class UsuariosMatriculadosDaoJDBCTempleteImpl implements UsuariosMatriculadosDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	Constantes constantes;

	@Autowired
	WebParametroDao webParametroDao;

	@Override
	public Matricula InformacionPersonalMatriculado(int matricula) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "SELECT "
					+ " per_identificacion, ter_identificacion,per_fecha_expedicion, ter_fecha_expedicion, per_email,"
					+ " per_direccion_residencia, ter_direccion, ter_direccion, ter_email, per_barrio,ter_barrio,"
					+ " per_telefono_movil, ter_telefono_movil, per_fecha_nacimiento, ter_fecha_nacimiento,"
					+ " m.mat_codigo,per_nombre,per_apellido, o.ofa_codigo, o.ofa_fecha_inscripcion_inicio, ter_apellido2,"
					+ " o.ofa_fecha_inscripcion_fin, o.ofa_fecha_inicio, o.ofa_fecha_fin, o.ofa_imagen, ter_apellido1,"
					+ " pr.pro_titulo_otorgado, u.uaa_codigo, u.uaa_nombre, tip.tii_nombre, tip.tii_nombre_corto,"
					+ " tit.tii_nombre as tii_nombreT, tit.tii_nombre_corto as tii_nombre_cortoT,ter_nombre1,ter_nombre2,"
					+ " mple.mun_nombre as lugar_expedicionP, mtle.mun_nombre as lugar_expedicionT, mpln.mun_nombre as lugar_nacimientoP,"
					+ " mtln.mun_nombre as lugar_nacimientoT, mlrp.mun_nombre as lugarResidenciaP, mlrt.mun_nombre as lugarResidenciaT,"
					+ " per_genero as generoP, ter_genero as generoT, ecp.esc_nombre as estadoCivilP, ect.esc_nombre as estadoCivilT,"
					+ " esp.ses_descripcion as estratoP, est.ses_descripcion as estratoT, gsp.grs_nombre as grupoSanguineoP, gst.grs_nombre as grupoSanguineoT,"
					+ " epsp.eps_nombre as epsP, epst.eps_nombre as epsT, etap.eta_nombre as epsTipoP, etat.eta_nombre as epsTipoT,"
					+ " lnp.sid_descripcion as lenguaNativaP, lnt.sid_descripcion as lenguaNativaT"
					+ " FROM matricula m" + " INNER JOIN estudiante e ON m.est_codigo = e.est_codigo"
					+ " LEFT OUTER JOIN dbo.tercero t ON (t.ter_codigo = e.ter_codigo) "
					+ " LEFT OUTER JOIN dbo.persona p ON (p.per_codigo = e.per_codigo)"
					+ " LEFT JOIN tipo_id tip ON (p.tii_codigo = tip.tii_codigo)"
					+ " LEFT JOIN tipo_id tit ON (t.tii_codigo = tit.tii_codigo)"
					+ " LEFT JOIN estado_civil ecp ON p.per_estado_civil = ecp.esc_codigo"
					+ " LEFT JOIN estado_civil ect ON t.ter_estado_civil = ect.esc_codigo"
					+ " LEFT JOIN snies_estrato esp ON p.per_estrato = esp.ses_codigo"
					+ " LEFT JOIN snies_estrato est ON t.ter_estrato = est.ses_codigo"
					+ " LEFT JOIN grupo_sanguineo gsp ON p.grs_codigo = gsp.grs_codigo"
					+ " LEFT JOIN grupo_sanguineo gst ON t.grs_codigo = gst.grs_codigo"
					+ " LEFT JOIN eps epsp ON p.eps_codigo = epsp.eps_codigo"
					+ " LEFT JOIN eps epst ON t.eps_codigo = epst.eps_codigo"
					+ " LEFT JOIN eps_tipo_afiliacion etap ON p.eta_codigo = etap.eta_codigo"
					+ " LEFT JOIN eps_tipo_afiliacion etat ON t.eta_codigo = etat.eta_codigo"
					+ " LEFT JOIN snies_idioma lnp ON p.lenguaNativa = lnp.sid_codigo "
					+ " LEFT JOIN snies_idioma lnt ON t.lenguaNativa = lnt.sid_codigo"
					+ " LEFT JOIN municipio mple ON p.per_lugar_expedicion = mple.mun_codigo"
					+ " LEFT JOIN municipio mtle ON t.ter_lugar_expedicion = mtle.mun_codigo"
					+ " LEFT JOIN municipio mpln ON p.per_lugar_nacimiento = mpln.mun_codigo"
					+ " LEFT JOIN municipio mtln ON t.ter_lugar_nacimiento = mtln.mun_codigo"
					+ " LEFT JOIN municipio mlrp ON p.per_lugar_residencia = mlrp.mun_codigo"
					+ " LEFT JOIN municipio mlrt ON t.ter_lugar_residencia = mlrt.mun_codigo"
					+ " INNER JOIN programa pr ON e.pro_codigo = pr.pro_codigo"
					+ " INNER JOIN uaa u ON pr.uaa_codigo = u.uaa_codigo"
					+ " INNER JOIN oferta_academica o ON (pr.pro_codigo=o.pro_codigo)" + " WHERE m.mat_codigo = "
					+ matricula;

			System.out.println(sql);

			List<Matricula> InfoMatricula = jdbcTemplate.query(sql, new RowMapper<Matricula>() {

				public Matricula mapRow(ResultSet rs, int rowNum) throws SQLException {
					Matricula ins = new Matricula();

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
					/*
					 * persona.setIdentificacionCotizante(rs.getLong("per_id_cotizante")); if
					 * (persona.getIdentificacionCotizante() == 0) {
					 * persona.setIdentificacionCotizante(rs.getLong("ter_id_cotizante")); }
					 */
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
			if (InfoMatricula.size() > 0) {
				return InfoMatricula.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.UaaDao#listarTablaUaa(java.lang.String, int, int,
	 * int)
	 */
	@Override
	public JSONRespuesta listarTablaUsuariosMatriculados(String search, int start, int length, int draw, int posicion,
			String direccion, int codigoOferta) {
		JSONRespuesta respuesta = new JSONRespuesta();

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();
		String valorModalidadParamentro = webParametroDao.listarWebParametro(constantes.WEP_MODALIDAD).get(0)
				.getValor();

		String[] campos = { "m.mat_codigo", "m.mat_fecha", "e.est_codigo", "identificacion", "nombreCompleto",
				"pr.pro_codigo", "u.uaa_codigo", "u.uaa_nombre", "o.ofa_codigo" };

		if (start == 0) {
			start = 1;
		}

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sql = "SELECT COUNT(m.mat_codigo)";
		sql = sql + " FROM matricula m";
		sql = sql + " INNER JOIN estudiante e ON m.est_codigo = e.est_codigo";
		sql = sql + " LEFT OUTER JOIN dbo.tercero t ON (t.ter_codigo = e.ter_codigo)";
		sql = sql + " LEFT OUTER JOIN dbo.persona p ON (p.per_codigo = e.per_codigo)";
		sql = sql + " INNER JOIN programa pr ON e.pro_codigo = pr.pro_codigo";
		sql = sql + " INNER JOIN uaa u ON pr.uaa_codigo = u.uaa_codigo";
		sql = sql + " INNER JOIN oferta_academica o ON (pr.pro_codigo=o.pro_codigo AND o.cal_codigo = m.cal_codigo)";
		sql = sql + " INNER JOIN inscripcion_programa inp ON (o.ofa_codigo = inp.ofa_codigo)";
		sql = sql + " INNER JOIN inscripcion i ON ((i.per_codigo = p.per_codigo or i.ter_codigo = t.ter_codigo) and inp.ins_codigo = i.ins_codigo)";
		sql = sql + " WHERE u.uat_codigo IN (" + uaaTipoNoFormal + ") AND pr.mod_codigo";
		sql = sql + " IN (" + valorModalidadParamentro + ") AND o.ofa_codigo = " + codigoOferta;
		System.out.println("QUERY MATRICULADOS POR OFERTA");
		System.out.println(sql);

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		System.out.println("CANTIDAD DE MATRICULADOS:" + count);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql
					+ " AND (m.mat_fecha like ? OR e.est_codigo like ? OR COALESCE(p.per_identificacion, t.ter_identificacion) like ? OR COALESCE(p.per_nombre+p.per_apellido, t.ter_nombre) like ? OR u.uaa_nombre like ?) ";
			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%", "%" + search + "%",
					"%" + search + "%", "%" + search + "%", "%" + search + "%" }, Integer.class);
		}
		
		
		sql = "SELECT ins_codigo, per_codigo, ter_codigo, mat_codigo, mat_fecha, est_codigo, identificacion, nombreCompleto, pro_codigo, uaa_codigo, uaa_nombre, ofa_codigo ";
		sql = sql + " from (select row_number() over(order by " + campos[posicion] + " " + direccion
				+ ") AS RowNumber, i.ins_codigo, e.per_codigo, e.ter_codigo, m.mat_codigo, m.mat_fecha, e.est_codigo, ";
		sql = sql + " COALESCE(p.per_identificacion, t.ter_identificacion) as identificacion, ";
		sql = sql + " COALESCE(p.per_nombre+' '+p.per_apellido, t.ter_nombre) as nombreCompleto, ";
		sql = sql + " pr.pro_codigo, u.uaa_codigo, u.uaa_nombre, o.ofa_codigo";
		sql = sql + " FROM matricula m";
		sql = sql + " INNER JOIN estudiante e ON m.est_codigo = e.est_codigo";
		sql = sql + " LEFT OUTER JOIN dbo.tercero t ON (t.ter_codigo = e.ter_codigo)";
		sql = sql + " LEFT OUTER JOIN dbo.persona p ON (p.per_codigo = e.per_codigo)";
		sql = sql + " INNER JOIN programa pr ON e.pro_codigo = pr.pro_codigo";
		sql = sql + " INNER JOIN uaa u ON pr.uaa_codigo = u.uaa_codigo";
		sql = sql + " INNER JOIN oferta_academica o ON (pr.pro_codigo=o.pro_codigo AND o.cal_codigo = m.cal_codigo)";
		sql = sql + " INNER JOIN inscripcion_programa inp ON (o.ofa_codigo = inp.ofa_codigo)";
		sql = sql + " INNER JOIN inscripcion i ON ((i.per_codigo = p.per_codigo or i.ter_codigo = t.ter_codigo) and inp.ins_codigo = i.ins_codigo)";
		
		sql = sql + " WHERE u.uat_codigo IN (" + uaaTipoNoFormal + ") AND pr.mod_codigo";
		sql = sql + " IN (" + valorModalidadParamentro + ") AND o.ofa_codigo = ? ";

		sql = sql
				+ " AND (m.mat_fecha like ? OR e.est_codigo like ? OR COALESCE(p.per_identificacion, t.ter_identificacion) like ? OR COALESCE(p.per_nombre+p.per_apellido, t.ter_nombre) like ? OR u.uaa_nombre like ?) ";

		sql = sql + ") as tabla";
		sql = sql + " where tabla.RowNumber between ? and ?";
		

		System.out.println(sql);
		List<Matricula> listaMatricula = jdbcTemplate.query(sql, new Object[] { codigoOferta, "%" + search + "%",
				"%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Matricula>() {

					public Matricula mapRow(ResultSet rs, int rowNum) throws SQLException {
						Matricula matricula = new Matricula();
						matricula.setCodigo(rs.getInt("mat_codigo"));
						matricula.setFecha(rs.getDate("mat_fecha"));
						
						Inscripcion inscripcion = new Inscripcion();
						inscripcion.setCodigo(rs.getInt("ins_codigo"));
						matricula.setInscripcion(inscripcion);

						Estudiante estudiante = new Estudiante();
						estudiante.setCodigo(rs.getString("est_codigo"));

						Persona persona = new Persona();
						persona.setCodigo(rs.getInt("per_codigo"));
						persona.setId(rs.getInt("ter_codigo"));
						persona.setIdentificacion(rs.getString("identificacion"));
						persona.setNombreCompleto(rs.getString("nombreCompleto"));
						estudiante.setPersona(persona);

						Programa programa = new Programa();
						programa.setCodigo(rs.getInt("pro_codigo"));

						Uaa uaa = new Uaa();
						uaa.setCodigo(rs.getInt("uaa_codigo"));
						uaa.setNombre(rs.getString("uaa_nombre"));
						programa.setUaa(uaa);
						estudiante.setPrograma(programa);

						Oferta oferta = new Oferta();
						oferta.setCodigo(rs.getInt("ofa_codigo"));
						matricula.setOferta(oferta);

						matricula.setEstudiante(estudiante);

						return matricula;
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaMatricula);

		return respuesta;
	}

	@Override
	public List<Programa> ListaProgramasMatriculados() {
		// @RolesAllowed("INVITADO")

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

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

		String codigoModalidad = webParametroDao.listarWebParametro(constantes.WEP_MODALIDAD).get(0).getValor();
		String codigoTipoUaa = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		String sql = "SELECT distinct u.uat_codigo, o.ofa_codigo, p.pro_titulo_otorgado, u.uaa_nombre ";
		sql = sql + "FROM programa p INNER JOIN uaa u ON (p.uaa_codigo=u.uaa_codigo) ";
		sql = sql + "INNER JOIN oferta_academica o ON (p.pro_codigo=o.pro_codigo ) ";
		sql = sql + "INNER JOIN estudiante e ON (p.pro_codigo = e.pro_codigo) ";
		sql = sql + "INNER JOIN matricula m ON (e.est_codigo = m.est_codigo) ";
		sql = sql + "WHERE p.mod_codigo in (" + codigoModalidad + ") ";
		sql = sql + "AND u.uat_codigo in (" + codigoTipoUaa + ") ";
		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " or u.uaa_codigo = " + uaaUsuario + ")";
		}
		sql = sql + "GROUP BY u.uat_codigo, o.ofa_codigo, p.pro_titulo_otorgado, u.uaa_nombre "
				+ "ORDER BY u.uaa_nombre ASC";

		System.out.println("QUERY MATRICULASO");
		
		System.out.println(sql);
		List<Programa> listaProgramas = jdbcTemplate.query(sql, new RowMapper<Programa>() {

			public Programa mapRow(ResultSet rs, int rowNum) throws SQLException {
				Programa programa = new Programa();
				programa.setCodigo(rs.getInt("ofa_codigo"));
				programa.setNombre(rs.getString("uaa_nombre"));
				programa.setNombreUaa(rs.getString("pro_titulo_otorgado"));
				return programa;
			}

		});

		return listaProgramas;
	}

	@Override
	public List<Matricula> ListaMatriculadosOferta(int oferta) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String codigoModalidad = webParametroDao.listarWebParametro(constantes.WEP_MODALIDAD).get(0).getValor();
		String codigoTipoUaa = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		String sql = "SELECT m.mat_codigo, m.mat_fecha, p.per_codigo, p.per_identificacion, p.per_nombre, p.per_apellido, c.cal_nombre "
				+ "FROM matricula m INNER JOIN estudiante e ON m.est_codigo = e.est_codigo "
				+ "INNER JOIN persona p ON (p.per_codigo = e.per_codigo) "
				+ "INNER JOIN programa pr ON e.pro_codigo = pr.pro_codigo "
				+ "INNER JOIN uaa u ON pr.uaa_codigo = u.uaa_codigo "
				+ "INNER JOIN oferta_academica o ON (pr.pro_codigo = o.pro_codigo AND o.cal_codigo = m.cal_codigo) "
				+ "INNER JOIN calendario c ON m.cal_codigo = c.cal_codigo " + "WHERE u.uat_codigo IN (" + codigoTipoUaa
				+ ") AND pr.mod_codigo IN (" + codigoModalidad + ") AND o.ofa_codigo = ?";
		List<Matricula> listaMatricula = jdbcTemplate.query(sql, new Object[] { oferta }, new RowMapper<Matricula>() {

			public Matricula mapRow(ResultSet rs, int rowNum) throws SQLException {
				Matricula matricula = new Matricula();
				matricula.setCodigo(rs.getInt("mat_codigo"));
				matricula.setFecha(rs.getDate("mat_fecha"));
				Persona persona = new Persona();
				persona.setCodigo(rs.getInt("per_codigo"));
				persona.setNombre(rs.getString("per_nombre"));
				persona.setApellido(rs.getString("per_apellido"));
				persona.setIdentificacion(rs.getString("per_identificacion"));
				matricula.setPersona(persona);
				Calendario calendario = new Calendario();
				calendario.setNombre(rs.getString("cal_nombre"));
				matricula.setCalendario(calendario);

				return matricula;
			}

		});

		return listaMatricula;
	}

}
