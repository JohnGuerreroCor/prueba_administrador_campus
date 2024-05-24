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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.configuration.UscoGrantedAuthority;
import co.edu.usco.lcms.configuration.User;
import co.edu.usco.lcms.model.Persona;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.model.UaaTipo;
import co.edu.usco.lcms.model.reservaespacios.UaaPersonal;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@Component
public class DocenteDaoJDBCTempleteImpl implements DocenteDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	Constantes constantes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.reservaespacio.DocenteDao#listarDocentes()
	 */
	@Override
	public List<UaaPersonal> listarDocentes(String criterio) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int uaaUsuario = 0;
		String rol = null;
		boolean adminGral = false;
		boolean adminFacultad = false;
		boolean adminDocente = false;

		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
			UscoGrantedAuthority uscoGrantedAuthority = (UscoGrantedAuthority) grantedAuthority;
			rol = uscoGrantedAuthority.getRole();
			uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			if (rol.equals(constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_FACULTAD_LCMS)) {
				adminFacultad = true;
				uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			}

			if (rol.equals(constantes.WEP_ROLE_ADMIN_VIDEOCONFERENCIA_LCMS)) {
				adminGral = true;
				uaaUsuario = uscoGrantedAuthority.getUaa().getCodigo();
			}

			if (rol.equals(constantes.WEP_ROLE_DOCENTE)) {
				adminDocente = true;
				uaaUsuario = uscoGrantedAuthority.getUaa().getDependencia();
			}
		}

		String sql = "SELECT DISTINCT TOP 10 uap.uap_codigo, p.per_codigo, per_identificacion, p.per_nombre, per_apellido,";
		sql = sql + " per_fecha_nacimiento, per_direccion_residencia, uap_fecha_inicio, uap_fecha_fin,";
		sql = sql + " eps_nombre, vin_nombre, uaa.uaa_nombre_corto, uaa.uaa_codigo, uaa.uaa_nombre,";
		sql = sql + " uaa.uaa_dependencia, uaa.uat_codigo,";
		sql = sql + " (select u.uaa_nombre_corto from uaa u where u.uaa_codigo=uaa.uaa_dependencia  ) as facultad,";
		sql = sql + " coalesce ((select top 1 d.ded_nombre from dedicacion d where d.ded_codigo=uap.ded_codigo ),'') as dedicacion";
		sql = sql + " FROM uaa_personal uap";
		sql = sql + " INNER JOIN persona p ON uap.per_codigo = p.per_codigo";
		sql = sql + " INNER JOIN periodo pe ON ";
		sql = sql + " (per_fecha_inicio <= per_fecha_fin and uap_fecha_inicio <= uap_fecha_fin ) or uap_fecha_fin is null";
		sql = sql + " LEFT JOIN eps ON eps.eps_codigo = p.eps_codigo";
		sql = sql + " LEFT JOIN vinculacion v ON v.vin_codigo = uap.vin_codigo";
		sql = sql + " LEFT JOIN uaa ON (uaa.uaa_codigo= uap.uaa_codigo)";
		sql = sql + " WHERE ((per_fecha_inicio <= uap_fecha_inicio and per_fecha_fin >= uap_fecha_fin) OR";
		sql = sql + " (per_fecha_inicio >= uap_fecha_inicio and per_fecha_fin <= uap_fecha_fin) OR";
		sql = sql + " (per_fecha_inicio >= uap_fecha_inicio and per_fecha_inicio <= uap_fecha_fin) OR";
		sql = sql + " (per_fecha_fin >= uap_fecha_inicio and per_fecha_fin <= uap_fecha_fin) OR";
		sql = sql + " (per_fecha_fin >= uap_fecha_inicio and uap_fecha_fin is null ))"; 
		sql = sql + " AND (pe.per_nombre in (select wep_valor from web_parametro where wep_nombre = 'EDUCACION_VIRTUAL_CALENDARIO_ACTUAL'))";
		//sql = sql + " AND (v.vin_codigo in (select vin_codigo from vinculacion where vin_clase in (1,2)))"; 
		sql = sql + " AND (uap_fecha_fin is NULL or GETDATE() <= uap_fecha_fin)";
		sql = sql + " AND (p.per_nombre + ' ' + p.per_apellido like ?)";
		if (adminFacultad == false && adminGral == false && adminDocente == true) {
			sql = sql + " AND p.per_codigo = " + user.getPersona().getCodigo();
		}

		if (adminFacultad == true && adminGral == false) {
			sql = sql + " AND (uaa.uaa_dependencia = " + uaaUsuario + " OR uaa.uaa_codigo = " + uaaUsuario + ")";
		}
		sql = sql + " ORDER BY uap_fecha_inicio, uap_fecha_fin"; 
				
		System.out.println(sql);
		List<UaaPersonal> listaCurso = jdbcTemplate.query(sql,
				new Object[] {  "%" + criterio + "%" }, new RowMapper<UaaPersonal>() {

					public UaaPersonal mapRow(ResultSet rs, int rowNum) throws SQLException {

						UaaPersonal uaaPersonal = new UaaPersonal();
						uaaPersonal.setCodigo(rs.getInt("uap_codigo"));
						uaaPersonal.setFechaIncio(rs.getDate("uap_fecha_inicio"));
						uaaPersonal.setFechaFin(rs.getDate("uap_fecha_fin"));
						
						Persona persona = new Persona();
						persona.setCodigo(rs.getInt("per_codigo"));
						persona.setNombre(rs.getString("per_nombre"));
						persona.setApellido(rs.getString("per_apellido"));
						uaaPersonal.setPersona(persona);

						Uaa uaa = new Uaa();
						uaa.setCodigo(rs.getInt("uaa_codigo"));
						uaa.setNombre(rs.getString("uaa_nombre"));
						uaa.setDependencia(rs.getInt("uaa_dependencia"));

						UaaTipo uaaTipo = new UaaTipo();
						uaaTipo.setCodigo(rs.getInt("uat_codigo"));
						uaa.setUaaTipo(uaaTipo);
						uaaPersonal.setUaa(uaa);

						return uaaPersonal;
					}

				});
		return listaCurso;
	}

}
