/**
 * 
 */
package co.edu.usco.lcms.dao.proacademica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.dao.WebParametroDao;
import co.edu.usco.lcms.model.Asignatura;
import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.model.Dia;
import co.edu.usco.lcms.model.Persona;
import co.edu.usco.lcms.model.reservaespacios.Espacio;
import co.edu.usco.lcms.model.reservaespacios.EspacioOcupacion;
import co.edu.usco.lcms.model.reservaespacios.Horas;
import co.edu.usco.lcms.model.reservaespacios.UaaPersonal;
import co.edu.usco.lcms.utility.Constantes;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@Component
public class EspacioOcupacionVirtualDaoJDBCTemplete implements EspacioOcupacionVirtualDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	WebParametroDao webParametroDao;

	@Autowired
	Constantes constantes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.proacademica.EspacioOcupacionVirtualDao#
	 * agregarEspacioOcupacionVirtual(co.edu.usco.lcms.model.reservaespacios.
	 * EspacioOcupacion)
	 */
	@Override
	public boolean agregarEspacioOcupacionVirtual(EspacioOcupacion espacioOcupacion) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO espacio_ocupacion_virtual (esp_codigo, uaa_codigo, eso_fecha, ";
			sql = sql + " dia_codigo, hra_codigo_inicio, hra_codigo_fin, eso_actividad, uap_codigo, ";
			sql = sql + " eso_estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			int resultado = jdbcTemplate.update(sql, espacioOcupacion.getEspacio().getCodigo(),
					espacioOcupacion.getCurso().getPlanAcademico().getPrograma().getUaa().getCodigo(),
					espacioOcupacion.getFecha(), espacioOcupacion.getDia().getCodigo(),
					espacioOcupacion.getHoraInicio().getCodigo(), espacioOcupacion.getHoraFin().getCodigo(),
					espacioOcupacion.getCurso().getCodigo(), espacioOcupacion.getCurso().getUaaPersonal().getCodigo(),
					1);
			if (resultado > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.edu.usco.lcms.dao.proacademica.EspacioOcupacionVirtualDao#
	 * consultarDisponibilidad(int, int, int, int)
	 */
	@Override
	public List<EspacioOcupacion> consultarDisponibilidad(int dia, int espacio, int espacioTipo, int docente,
			int actividad, String fecha, int semanas) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Object[] obj = new Object[] {};

		String sql = "SELECT v.eso_codigo, v.esp_codigo, v.uaa_codigo, v.eso_fecha, v.hor_codigo, ";
		sql = sql + " v.dia_codigo, v.tabla, v.codigo_hora, v.codigo_hora_inicio, v.hora_inicio_24h,";
		sql = sql + " v.codigo_hora_fin, v.hora_fin_24h, v.eso_actividad, v.eso_subgrupo, v.uap_codigo,";
		sql = sql + " v.eso_estado, v.oct_codigo, v.eso_descripcion, v.cdp_codigo, v.ctr_codigo, v.eso_url";
		sql = sql + " FROM v_espacio_ocupacion_virtual v";
		sql = sql + " INNER JOIN curso c ON v.eso_actividad = c.cur_codigo";
		sql = sql + " WHERE v.dia_codigo = ? AND v.eso_estado != '1'";

		obj = new Object[] { espacio };
		if (espacio > 0) {
			sql = sql + " AND v.esp_codigo = ?";
			obj = new Object[] { dia, espacio };
		}

		if (docente > 0) {
			sql = sql + " AND v.uap_codigo = ?";
			obj = new Object[] { dia, docente };
		}

		if (actividad > 0) {
			sql = sql + " AND v.eso_actividad = ?";
			obj = new Object[] { dia, actividad };
		}

		if (fecha != "" && semanas > 0) {
			sql = sql + " AND ((('" + fecha + "'";
			sql = sql + " between v.eso_fecha AND DATEADD(wk, c.cur_semanas, v.eso_fecha)))";
			sql = sql + " OR ((DATEADD(wk, " + semanas + ", '" + fecha
					+ "') between v.eso_fecha AND DATEADD(wk, c.cur_semanas, v.eso_fecha))))";
		}
		System.out.println(sql);
		System.out.println("dia:" + dia + " Espacio:" + espacio + " Espacio tipo:" + espacioTipo + " Docente:" + docente
				+ " Actividad:" + actividad + " Fecha:" + fecha + " Semanas:" + semanas);
		
		List<EspacioOcupacion> listaEspacioOcupacion = jdbcTemplate.query(sql, obj, new RowMapper<EspacioOcupacion>() {

			public EspacioOcupacion mapRow(ResultSet rs, int rowNum) throws SQLException {
				EspacioOcupacion espacioOcupacion = new EspacioOcupacion();
				espacioOcupacion.setCodigo(rs.getInt("eso_codigo"));
				espacioOcupacion.setFecha(rs.getDate("eso_fecha"));
				espacioOcupacion.setDescripcion(rs.getString("eso_descripcion"));

				Espacio espacio = new Espacio();
				espacio.setCodigo(rs.getInt("esp_codigo"));
				espacioOcupacion.setEspacio(espacio);

				Dia dia = new Dia();
				dia.setCodigo(rs.getInt("dia_codigo"));
				espacioOcupacion.setDia(dia);

				UaaPersonal uaaPersonal = new UaaPersonal();
				uaaPersonal.setCodigo(rs.getInt("uap_codigo"));

				espacioOcupacion.setUaaPersonal(uaaPersonal);

				Curso curso = new Curso();
				curso.setCodigo(rs.getInt("eso_actividad"));
				espacioOcupacion.setCurso(curso);

				Horas horaInicio = new Horas();
				horaInicio.setCodigo(rs.getInt("codigo_hora_inicio"));
				horaInicio.setHora24h(rs.getString("hora_inicio_24h"));
				espacioOcupacion.setHoraInicio(horaInicio);

				Horas horaFin = new Horas();
				horaFin.setCodigo(rs.getInt("codigo_hora_fin") - 1);
				horaFin.setHora24h(rs.getString("hora_fin_24h"));
				espacioOcupacion.setHoraFin(horaFin);

				return espacioOcupacion;
			}
		});

		return listaEspacioOcupacion;

	}

	@Override
	public List<EspacioOcupacion> listarEspacioOcupacion(int curso) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String uaaTipoNoFormal = webParametroDao.listarWebParametro(constantes.WEP_TIPO_UAA).get(0).getValor();

		String sql = "SELECT v.eso_codigo, v.esp_codigo, u.uaa_codigo, u.uaa_nombre, u.uat_codigo,";
		sql = sql + " v.eso_fecha, v.hor_codigo, d.dia_codigo, d.dia_nombre, v.tabla, v.codigo_hora,";
		sql = sql + " v.codigo_hora_inicio, v.hora_inicio_24h, v.codigo_hora_fin, v.hora_fin_24h,";
		sql = sql + " c.cur_codigo, c.cur_grupo, c.cur_cupo, a.asi_codigo, a.asi_nombre, a.asi_nombre_corto,";
		sql = sql + " up.uap_codigo, p.per_codigo, p.per_nombre, p.per_apellido, v.eso_subgrupo,";
		sql = sql + " v.eso_estado, v.oct_codigo, v.eso_descripcion, e.esp_codigo, e.esp_nombre,";
		sql = sql + " hi.hra_hora AS hora_inicio, hf.hra_hora AS hora_fin";
		sql = sql + " FROM v_espacio_ocupacion_virtual v";
		sql = sql + " INNER JOIN uaa u ON v.uaa_codigo = u.uaa_codigo";
		sql = sql + " INNER JOIN dia d ON v.dia_codigo = d.dia_codigo";
		sql = sql + " INNER JOIN curso c ON v.eso_actividad = c.cur_codigo";
		sql = sql + " INNER JOIN asignatura a ON c.asi_codigo = a.asi_codigo";
		sql = sql + " INNER JOIN uaa_personal up ON v.uap_codigo = up.uap_codigo";
		sql = sql + " INNER JOIN persona p ON up.per_codigo = p.per_codigo";
		sql = sql + " INNER JOIN espacio e ON v.esp_codigo = e.esp_codigo";
		sql = sql + " INNER JOIN videoconferencias.hora hi ON v.codigo_hora_inicio = hi.hra_codigo";
		sql = sql + " INNER JOIN videoconferencias.hora hf ON v.codigo_hora_fin = hf.hra_codigo";
		sql = sql + " WHERE c.cur_codigo = ? AND u.uat_codigo = " + uaaTipoNoFormal;
		sql = sql + " AND v.eso_estado = 1";
		sql = sql + " ORDER BY d.dia_codigo ASC";

		List<EspacioOcupacion> listaEspacioOcupacion = jdbcTemplate.query(sql, new Object[] { curso },
				new RowMapper<EspacioOcupacion>() {

					public EspacioOcupacion mapRow(ResultSet rs, int rowNum) throws SQLException {

						EspacioOcupacion espacioOcupacion = new EspacioOcupacion();
						espacioOcupacion.setCodigo(rs.getInt("eso_codigo"));
						espacioOcupacion.setFecha(rs.getDate("eso_fecha"));
						espacioOcupacion.setDescripcion(rs.getString("eso_descripcion"));

						Espacio espacio = new Espacio();
						espacio.setCodigo(rs.getInt("esp_codigo"));
						espacio.setNombre(rs.getString("esp_nombre"));
						espacioOcupacion.setEspacio(espacio);

						UaaPersonal uaaPersonal = new UaaPersonal();
						uaaPersonal.setCodigo(rs.getInt("uap_codigo"));

						Persona persona = new Persona();
						persona.setCodigo(rs.getInt("per_codigo"));
						persona.setNombre(rs.getString("per_nombre"));
						persona.setApellido(rs.getString("per_apellido"));
						uaaPersonal.setPersona(persona);
						espacioOcupacion.setUaaPersonal(uaaPersonal);

						Dia dia = new Dia();
						dia.setCodigo(rs.getInt("dia_codigo"));
						dia.setNombre(rs.getString("dia_nombre"));
						espacioOcupacion.setDia(dia);

						Curso curso = new Curso();
						curso.setCodigo(rs.getInt("cur_codigo"));
						curso.setCupo(rs.getInt("cur_cupo"));
						curso.setGrupo(rs.getString("cur_grupo"));
						Asignatura asignatura = new Asignatura();
						asignatura.setCodigo(rs.getInt("asi_codigo"));
						asignatura.setNombre(rs.getString("asi_nombre"));
						asignatura.setNombreCorto(rs.getString("asi_nombre_corto"));
						curso.setAsignatura(asignatura);
						espacioOcupacion.setCurso(curso);

						Horas horaInicio = new Horas();
						horaInicio.setCodigo(rs.getInt("codigo_hora_inicio"));
						horaInicio.setHora(rs.getString("hora_inicio"));
						horaInicio.setHora24h(rs.getString("hora_inicio_24h"));
						espacioOcupacion.setHoraInicio(horaInicio);

						Horas horaFin = new Horas();
						horaFin.setCodigo(rs.getInt("codigo_hora_fin"));
						horaFin.setHora(rs.getString("hora_fin"));
						horaFin.setHora24h(rs.getString("hora_fin_24h"));
						espacioOcupacion.setHoraFin(horaFin);

						return espacioOcupacion;
					}
				});

		return listaEspacioOcupacion;
	}

	@Override
	public boolean eliminarEspacioOcupacion(int id, int curso) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = "";
			int resultado = 0;
			if (curso > 0) {
				sql = "UPDATE espacio_ocupacion_virtual SET eso_estado = 0 WHERE eso_actividad=?";
				resultado = jdbcTemplate.update(sql, curso);
				return true;
			} else {
				sql = "UPDATE espacio_ocupacion_virtual SET eso_estado = 0 WHERE eso_codigo=?";
				resultado = jdbcTemplate.update(sql, id);
			}
			if (resultado > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
