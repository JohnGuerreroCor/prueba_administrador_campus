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
import co.edu.usco.lcms.model.Asignatura;
import co.edu.usco.lcms.model.Caracter;
import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Nbc;
import co.edu.usco.lcms.model.Nucleo;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.model.UaaTipo;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@Component
public class AsignaturaDaoJDBCTempleteImpl implements AsignaturaDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	Constantes constantes;

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.AsignaturaDao#agregarAsignatura(co.edu.usco.lcms.
	 * model. Asignatura)
	 */
	@Override
	public boolean agregarAsignatura(Asignatura asignatura) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO asignatura (asi_nombre, asi_nombre_corto, asi_nombre_impresion, "
					+ " uaa_codigo, asi_publicar, car_codigo, asi_trabajo_presencial, asi_trabajo_independiente,"
					+ " asi_estado, nuc_codigo, nbc_codigo, asi_creditos)";
			sql = sql + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			int resultado = jdbcTemplate.update(sql, asignatura.getNombre().toUpperCase(),
					asignatura.getNombreCorto().toUpperCase(), asignatura.getNombreImpresion().toUpperCase(),
					asignatura.getUaa().getCodigo(), asignatura.getPublicar(), asignatura.getCaracter().getCodigo(),
					asignatura.getTrabajoPresencial(), asignatura.getTrabajoIndependiente(), 1,
					asignatura.getNucleo().getCodigo(), asignatura.getNbc().getCodigo(), asignatura.getCreditos());

			if (resultado > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.AsignaturaDao#modificarAsignatura(co.edu.usco.lcms.
	 * model. Asignatura)
	 */
	@Override
	public boolean modificarAsignatura(int id, Asignatura asignatura) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// update
		String sql = "UPDATE asignatura SET asi_nombre=?,";
		sql = sql + " asi_nombre_impresion=?, uaa_codigo=?, asi_publicar=?, ";
		sql = sql + " car_codigo=?, asi_trabajo_presencial=?, asi_trabajo_independiente=?,";
		sql = sql + " nuc_codigo=?, nbc_codigo=?, asi_creditos = ?";
		sql = sql + " WHERE asi_codigo=?";

		int resultado = jdbcTemplate.update(sql, asignatura.getNombre().toUpperCase(),
				asignatura.getNombreImpresion().toUpperCase(), asignatura.getUaa().getCodigo(),
				asignatura.getPublicar(), asignatura.getCaracter().getCodigo(), asignatura.getTrabajoPresencial(),
				asignatura.getTrabajoIndependiente(), asignatura.getNucleo().getCodigo(),
				asignatura.getNbc().getCodigo(), asignatura.getCreditos(), id);

		if (resultado > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.AsignaturaDao#eliminarAsignatura(co.edu.usco.lcms.
	 * model. Asignatura)
	 */
	@Override
	public boolean eliminarAsignatura(int id) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// String sql = "DELETE FROM asignatura WHERE asi_codigo=?";
		String sql = "UPDATE asignatura SET asi_estado = 0 WHERE asi_codigo=?";
		int resultado = jdbcTemplate.update(sql, id);

		if (resultado > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.edu.usco.lcms.dao.AsignaturaDao#listarAsignatura(java.lang.String,
	 * int, int, int) /** Retorna todas las asignaturas si criterio es vacio
	 * \"\", de lo cotnrario lista aquellas que cumplan con el criterio
	 * 
	 * @param criterio
	 * 
	 * @return Lista de asignaturas
	 */
	@Override
	public List<Asignatura> listarAsignatura(String criterio, String acronimo) {
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

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		String sql = " SELECT TOP 10 ut.uat_codigo, ut.uat_nombre, a.asi_codigo, a.asi_nombre, a.asi_nombre_corto,";
		sql = sql + " a.asi_nombre_impresion, u.uaa_codigo,u.uaa_nombre, a.asi_publicar, a.asi_creditos, ";
		sql = sql + " a.asi_creditos_teoria, a.asi_creditos_practica, c.car_codigo, c.car_nombre, ";
		sql = sql + " a.asi_trabajo_presencial, a.asi_trabajo_independiente, a.asi_estado, ";
		sql = sql + " ISNULL(n.nuc_codigo,'') AS nuc_codigo, ISNULL(n.nuc_nombre,'') AS nuc_nombre,";
		sql = sql + " a.asi_semanasxsemestre, ISNULL(nb.nbc_codigo,'') AS nbc_codigo, ISNULL(nb.nbc_nombre,'')";
		sql = sql + " AS nbc_nombre";
		sql = sql + " FROM dbo.asignatura a INNER JOIN dbo.uaa u ON ";
		sql = sql + " a.uaa_codigo = u.uaa_codigo INNER JOIN dbo.caracter c ON ";
		sql = sql + " a.car_codigo = c.car_codigo	INNER JOIN dbo.nucleo n ON ";
		sql = sql + " a.nuc_codigo = n.nuc_codigo INNER JOIN dbo.snies_nbc nb ON ";
		sql = sql + " a.nbc_codigo = nb.nbc_codigo INNER JOIN dbo.uaa_tipo ut ON ";
		sql = sql + " ut.uat_codigo = u.uat_codigo WHERE ";
		sql = sql + " ut.uat_codigo in (" + uaaTipoNoFormal + ") AND asi_estado = 1 ";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " or u.uaa_codigo = " + uaaUsuario + ")";
		}

		sql = sql + " AND (CONVERT(varchar(15), a.asi_codigo) like ? or a.asi_nombre like ?";
		sql = sql + " AND asi_nombre_corto like ? ) order by asi_codigo desc";
		
		List<Asignatura> listaAsignaturas = jdbcTemplate.query(sql,
				new Object[] { criterio + "%", criterio + "%", acronimo + "%" }, new RowMapper<Asignatura>() {

					public Asignatura mapRow(ResultSet rs, int rowNum) throws SQLException {

						Asignatura asignatura = new Asignatura();

						asignatura.setPublicar(rs.getInt("asi_publicar"));
						asignatura.setCreditos(rs.getInt("asi_creditos"));
						asignatura.setCreditosTeoricos(rs.getInt("asi_creditos_teoria"));
						asignatura.setCreditosPractico(rs.getInt("asi_creditos_practica"));
						asignatura.setSemanasXSemestre(rs.getInt("asi_semanasxsemestre"));
						asignatura.setCodigo(rs.getInt("asi_codigo"));
						asignatura.setNombre(rs.getString("asi_nombre"));
						asignatura.setNombreCorto(rs.getString("asi_nombre_corto"));
						asignatura.setNombreImpresion(rs.getString("asi_nombre_impresion"));
						asignatura.setTrabajoPresencial(rs.getInt("asi_trabajo_presencial"));
						asignatura.setTrabajoIndependiente(rs.getInt("asi_trabajo_independiente"));
						asignatura.setEstado(rs.getInt("asi_estado"));

						UaaTipo uaaTipo = new UaaTipo();
						uaaTipo.setCodigo((rs.getInt("uat_codigo")));
						uaaTipo.setNombre(rs.getString("uat_nombre"));
						asignatura.setUaaTipo(uaaTipo);

						Uaa uaa = new Uaa();
						uaa.setCodigo(rs.getInt("uaa_codigo"));
						uaa.setNombre(rs.getString("uaa_nombre"));
						asignatura.setUaa(uaa);

						Caracter caracter = new Caracter();
						caracter.setCodigo(rs.getString("car_codigo"));
						caracter.setNombre(rs.getString("car_nombre"));
						asignatura.setCaracter(caracter);

						Nucleo nucleo = new Nucleo();
						nucleo.setCodigo(rs.getInt("nuc_codigo"));
						nucleo.setNombre(rs.getString("nuc_nombre"));
						asignatura.setNucleo(nucleo);

						Nbc nbc = new Nbc();
						nbc.setCodigo(rs.getInt("nbc_codigo"));
						nbc.setNombre(rs.getString("nbc_nombre"));
						asignatura.setNbc(nbc);

						return asignatura;
					}

				});
		return listaAsignaturas;
	}

	@Override
	public JSONRespuesta listarTablaAsignatura(String search, int start, int length, int draw, int posicion,
			String direccion) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int uaaUsuario = 0;
		String rol = null;
		boolean adminGral = false;
		boolean adminFacultad = false;

		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
			uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			rol = uscoGrantedAuthority.getRole();
			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS)) {
				adminFacultad = true;
			}

			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_LCMS)) {
				adminGral = true;
			}
		}
		JSONRespuesta respuesta = new JSONRespuesta();
		if (start == 0) {
			start = 1;
		}

		String[] campos = { "a.asi_codigo", "a.asi_nombre", "a.asi_nombre_impresion", "u.uaa_nombre", "nbc_nombre",
				"c.car_nombre", "nuc_nombre", "a.asi_trabajo_presencial", "a.asi_trabajo_independiente", "a.asi_estado",
				"a.asi_publicar", "ut.uat_nombre", "u.uaa_codigo", "a.asi_creditos" };

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		String sql = "SELECT COUNT(a.asi_codigo) FROM dbo.asignatura a INNER JOIN dbo.uaa u ON ";
		sql = sql + " a.uaa_codigo = u.uaa_codigo INNER JOIN dbo.caracter c ON ";
		sql = sql + " a.car_codigo = c.car_codigo	INNER JOIN dbo.nucleo n ON ";
		sql = sql + " a.nuc_codigo = n.nuc_codigo INNER JOIN dbo.snies_nbc nb ON ";
		sql = sql + " a.nbc_codigo = nb.nbc_codigo INNER JOIN dbo.uaa_tipo ut ON ut.uat_codigo = u.uat_codigo";
		sql = sql + " WHERE ut.uat_codigo in (" + uaaTipoNoFormal + ") AND a.asi_estado = 1 ";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " or u.uaa_codigo = " + uaaUsuario + ")";
		}

		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " AND (a.asi_codigo like ? OR a.asi_nombre like ? OR a.asi_nombre_corto like ? ";
			sql = sql + " OR u.uaa_nombre like ? OR n.nuc_nombre like ?)";
			filtrados = jdbcTemplate.queryForObject(sql, new Object[] { "%" + search + "%", "%" + search + "%",
					"%" + search + "%", "%" + search + "%", "%" + search + "%" }, Integer.class);
		}

		sql = "SELECT uat_codigo, uat_nombre, asi_codigo, asi_nombre, asi_nombre_corto, asi_nombre_impresion, uaa_codigo, uaa_nombre,";
		sql = sql + " asi_publicar, asi_creditos, asi_creditos_teoria, asi_creditos_practica, car_codigo,";
		sql = sql + " car_nombre, asi_trabajo_presencial, asi_trabajo_independiente, asi_estado,";
		sql = sql + " nuc_codigo, nuc_nombre,	asi_semanasxsemestre,  nbc_codigo, nbc_nombre";
		sql = sql + " from (select row_number() over(order by " + campos[posicion] + " " + direccion
				+ ") AS RowNumber,";
		sql = sql + " ut.uat_codigo, ut.uat_nombre, a.asi_codigo, a.asi_nombre, a.asi_nombre_corto,";
		sql = sql + " a.asi_nombre_impresion, u.uaa_codigo,u.uaa_nombre, a.asi_publicar, a.asi_creditos, ";
		sql = sql + " a.asi_creditos_teoria, a.asi_creditos_practica, c.car_codigo, c.car_nombre,	";
		sql = sql + " a.asi_trabajo_presencial, a.asi_trabajo_independiente, a.asi_estado, ISNULL(n.nuc_codigo,'')";
		sql = sql + " AS nuc_codigo, ISNULL(n.nuc_nombre,'') AS nuc_nombre,	a.asi_semanasxsemestre,";
		sql = sql + " ISNULL(nb.nbc_codigo,'') AS nbc_codigo, ISNULL(nb.nbc_nombre,'') AS nbc_nombre";
		sql = sql + " FROM dbo.asignatura a INNER JOIN dbo.uaa u ON ";
		sql = sql + " a.uaa_codigo = u.uaa_codigo INNER JOIN dbo.caracter c ON ";
		sql = sql + " a.car_codigo = c.car_codigo	INNER JOIN dbo.nucleo n ON ";
		sql = sql + " a.nuc_codigo = n.nuc_codigo INNER JOIN dbo.snies_nbc nb ON ";
		sql = sql + " a.nbc_codigo = nb.nbc_codigo INNER JOIN dbo.uaa_tipo ut on ut.uat_codigo = u.uat_codigo";
		sql = sql + " WHERE (a.asi_codigo like ? OR a.asi_nombre like ? OR a.asi_nombre_corto like ? ";
		sql = sql + " OR u.uaa_nombre like ? OR n.nuc_nombre like ?)";
		sql = sql + " AND ut.uat_codigo in (" + uaaTipoNoFormal + ")  AND a.asi_estado = 1 ";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " or u.uaa_codigo = " + uaaUsuario + ")";
		}

		sql = sql + ") as tabla";
		sql = sql + " where tabla.RowNumber between ? and ? ";

		List<Asignatura> listaAsignatura = jdbcTemplate.query(sql, new Object[] { "%" + search + "%",
				"%" + search + "%", "%" + search + "%", "%" + search + "%", "%" + search + "%", start, fin },
				new RowMapper<Asignatura>() {

					public Asignatura mapRow(ResultSet rs, int rowNum) throws SQLException {
						Asignatura asignatura = new Asignatura();

						asignatura.setPublicar(rs.getInt("asi_publicar"));
						asignatura.setCreditos(rs.getInt("asi_creditos"));
						asignatura.setCreditosTeoricos(rs.getInt("asi_creditos_teoria"));
						asignatura.setCreditosPractico(rs.getInt("asi_creditos_practica"));
						asignatura.setSemanasXSemestre(rs.getInt("asi_semanasxsemestre"));
						asignatura.setCodigo(rs.getInt("asi_codigo"));
						asignatura.setNombre(rs.getString("asi_nombre"));
						asignatura.setNombreCorto(rs.getString("asi_nombre_corto"));
						asignatura.setNombreImpresion(rs.getString("asi_nombre_impresion"));
						asignatura.setTrabajoPresencial(rs.getInt("asi_trabajo_presencial"));
						asignatura.setTrabajoIndependiente(rs.getInt("asi_trabajo_independiente"));
						asignatura.setEstado(rs.getInt("asi_estado"));

						UaaTipo uaaTipo = new UaaTipo();
						uaaTipo.setCodigo((rs.getInt("uat_codigo")));
						uaaTipo.setNombre(rs.getString("uat_nombre"));
						asignatura.setUaaTipo(uaaTipo);

						Uaa uaa = new Uaa();
						uaa.setCodigo(rs.getInt("uaa_codigo"));
						uaa.setNombre(rs.getString("uaa_nombre"));
						asignatura.setUaa(uaa);

						Caracter caracter = new Caracter();
						caracter.setCodigo(rs.getString("car_codigo"));
						caracter.setNombre(rs.getString("car_nombre"));
						asignatura.setCaracter(caracter);

						Nucleo nucleo = new Nucleo();
						nucleo.setCodigo(rs.getInt("nuc_codigo"));
						nucleo.setNombre(rs.getString("nuc_nombre"));
						asignatura.setNucleo(nucleo);

						Nbc nbc = new Nbc();
						nbc.setCodigo(rs.getInt("nbc_codigo"));
						nbc.setNombre(rs.getString("nbc_nombre"));
						asignatura.setNbc(nbc);
						return asignatura;
					}
				});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaAsignatura);

		return respuesta;
	}

	@Override
	public Asignatura buscarAsignatura(int codigo) {
		// TODO Auto-generated method stub
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int uaaUsuario = 0;
		String rol = null;
		boolean adminGral = false;
		boolean adminFacultad = false;

		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
			uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			rol = uscoGrantedAuthority.getRole();
			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_FACULTAD_LCMS)) {
				adminFacultad = true;
			}

			if (rol.equals(constantes.WEP_ROLE_ADMINISTRADOR_LCMS)) {
				adminGral = true;
			}
		}
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		String sql = " SELECT TOP 1 ut.uat_codigo, ut.uat_nombre, a.asi_codigo, a.asi_nombre, a.asi_nombre_corto, a.asi_nombre_impresion, u.uaa_codigo,u.uaa_nombre,";
		sql = sql + " a.asi_publicar, a.asi_creditos, a.asi_creditos_teoria, a.asi_creditos_practica, c.car_codigo,";
		sql = sql + " c.car_nombre,	a.asi_trabajo_presencial, a.asi_trabajo_independiente, a.asi_estado, ";
		sql = sql + " ISNULL(n.nuc_codigo,'') AS nuc_codigo, ISNULL(n.nuc_nombre,'') AS nuc_nombre, ";
		sql = sql + " a.asi_semanasxsemestre, ISNULL(nb.nbc_codigo,'') AS nbc_codigo, ISNULL(nb.nbc_nombre,'') ";
		sql = sql + " AS nbc_nombre";
		sql = sql + " FROM dbo.asignatura a INNER JOIN dbo.uaa u ON ";
		sql = sql + " a.uaa_codigo = u.uaa_codigo INNER JOIN dbo.caracter c ON ";
		sql = sql + " a.car_codigo = c.car_codigo	INNER JOIN dbo.nucleo n ON ";
		sql = sql + " a.nuc_codigo = n.nuc_codigo INNER JOIN dbo.snies_nbc nb ON ";
		sql = sql + " a.nbc_codigo = nb.nbc_codigo INNER JOIN dbo.uaa_tipo ut ON ";
		sql = sql + " ut.uat_codigo = u.uat_codigo WHERE ";
		sql = sql + " ut.uat_codigo in (" + uaaTipoNoFormal + ") ";

		if (adminGral == false && adminFacultad == true) {
			sql = sql + " AND (u.uaa_dependencia = " + uaaUsuario + " or u.uaa_codigo = " + uaaUsuario + ")";
		}

		if (codigo > 0) {
			sql = sql + " AND a.asi_codigo = ?";
		}

		List<Asignatura> listaAsignaturas = jdbcTemplate.query(sql, new Object[] { codigo },
				new RowMapper<Asignatura>() {

					public Asignatura mapRow(ResultSet rs, int rowNum) throws SQLException {

						Asignatura asignatura = new Asignatura();

						asignatura.setCodigo(rs.getInt("asi_codigo"));
						asignatura.setNombre(rs.getString("asi_nombre"));
						asignatura.setNombreCorto(rs.getString("asi_nombre_corto"));
						asignatura.setNombreImpresion(rs.getString("asi_nombre_impresion"));
						asignatura.setTrabajoPresencial(rs.getInt("asi_trabajo_presencial"));
						asignatura.setEstado(rs.getInt("asi_estado"));

						Uaa uaa = new Uaa();
						UaaTipo uaaTipo = new UaaTipo();
						uaaTipo.setCodigo(rs.getInt("uat_codigo"));
						uaa.setUaaTipo(uaaTipo);
						asignatura.setUaa(uaa);

						return asignatura;
					}

				});

		if (listaAsignaturas.size() > 0) {
			return listaAsignaturas.get(0);
		} else {
			return null;
		}
	}

}
