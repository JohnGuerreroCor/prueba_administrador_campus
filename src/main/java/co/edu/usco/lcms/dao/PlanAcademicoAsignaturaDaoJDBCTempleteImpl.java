package co.edu.usco.lcms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.model.Asignatura;
import co.edu.usco.lcms.model.Componente;
import co.edu.usco.lcms.model.Nucleo;
import co.edu.usco.lcms.model.PlanAcademicoAsignatura;

@Component
public class PlanAcademicoAsignaturaDaoJDBCTempleteImpl implements PlanAcademicoAsignaturaDao {

	@Autowired
	DataSource dataSource;

	@Override
	public List<PlanAcademicoAsignatura> buscarPlanAcademicoAsignatura(int pla_codigo, int asignatura) {
		try {

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "SELECT paa_codigo, asi_codigo, paa_nota_minima, paa_credito, paa_intensidad, com_codigo, ";
			sql = sql + " paa_semestre, paa_chequeo_requisito, paa_semanaxsemestre, paa_estado,paa_obligatoria, ";
			sql = sql + " paa_h_trabajo_ind_sem, paa_programable FROM plan_academico_asignatura ";

			if (pla_codigo > 0 && asignatura > 0) {
				sql = sql + " WHERE pla_codigo = " + pla_codigo + " AND asi_codigo = " + asignatura;
			} else {
				if (pla_codigo > 0) {
					sql = sql + " WHERE pla_codigo = " + pla_codigo + " AND paa_semestre = 1";
				}
			}
			List<PlanAcademicoAsignatura> listaPlanAcademicoAsignatura = jdbcTemplate.query(sql,
					new RowMapper<PlanAcademicoAsignatura>() {

						public PlanAcademicoAsignatura mapRow(ResultSet rs, int rowNum) throws SQLException {
							PlanAcademicoAsignatura paa = new PlanAcademicoAsignatura();
							paa.setNotaMinima(rs.getFloat("paa_nota_minima"));
							paa.setCodigo(rs.getInt("paa_codigo"));
							paa.setCreditos(rs.getInt("paa_credito"));
							paa.setIntensidad(rs.getInt("paa_intensidad"));
							paa.setSemestre(rs.getInt("paa_semestre"));
							paa.setRequisito(rs.getInt("paa_chequeo_requisito"));
							paa.setSemanasXSemestre(rs.getInt("paa_semanaxsemestre"));
							paa.setActivo(rs.getString("paa_estado"));
							paa.setPertenece(rs.getInt("paa_obligatoria"));
							paa.setTrabajoIndependiente(rs.getInt("paa_h_trabajo_ind_sem"));
							paa.setProgramable(rs.getInt("paa_programable"));

							Asignatura asignatura = new Asignatura();
							asignatura.setCodigo(rs.getInt("asi_codigo"));
							paa.setAsignatura(asignatura);

							Componente componente = new Componente();
							componente.setCodigoComponente(rs.getInt("com_codigo"));
							paa.setComponente(componente);
							return paa;
						}
					});

			return listaPlanAcademicoAsignatura;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<PlanAcademicoAsignatura> listarPlanAcademicoAsignatura(int codigo) {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Object[] obj = new Object[] {};

		String sql = "SELECT pa.paa_codigo,	pa.pla_codigo, a.asi_codigo, a.asi_nombre, a.asi_nombre_corto, pa.paa_semestre, "
				+ "pa.paa_credito,	pa.paa_intensidad, ISNULL(n.nuc_nombre,'No Especificado') AS nuc_nombre, n.nuc_codigo, pa.paa_estado, "
				+ "pa.paa_nota_minima, pa.paa_chequeo_requisito, pa.paa_obligatoria, pa.paa_programable, "
				+ "pa.paa_semanaxsemestre, pa.paa_h_trabajo_ind_sem, c.com_codigo, ISNULL(c.com_nombre, 'No Especificado') AS com_nombre "
				+ "FROM dbo.plan_academico_asignatura pa "
				+ "INNER JOIN dbo.asignatura_noformal a ON pa.asi_codigo = a.asi_codigo "
				+ "LEFT JOIN dbo.nucleo n ON pa.nuc_codigo = n.nuc_codigo "
				+ "LEFT JOIN dbo.componente c ON pa.com_codigo = c.com_codigo ";
		if (codigo > 0) {
			sql = sql + " WHERE pa.pla_codigo = ? AND pa.paa_estado = 1";
			obj = new Object[] { codigo };
		}

		List<PlanAcademicoAsignatura> listaPersona = jdbcTemplate.query(sql, obj,
				new RowMapper<PlanAcademicoAsignatura>() {

					public PlanAcademicoAsignatura mapRow(ResultSet rs, int rowNum) throws SQLException {
						PlanAcademicoAsignatura lista = new PlanAcademicoAsignatura();

						lista.setCodigo(rs.getInt("paa_codigo"));
						lista.setSemestre(rs.getInt("paa_semestre"));
						lista.setCreditos(rs.getInt("paa_credito"));
						lista.setIntensidad(rs.getInt("paa_intensidad"));
						lista.setActivo(rs.getString("paa_estado"));
						lista.setNotaMinima(rs.getFloat("paa_nota_minima"));
						lista.setRequisito(rs.getInt("paa_chequeo_requisito"));
						lista.setPertenece(rs.getInt("paa_obligatoria"));
						lista.setProgramable(rs.getInt("paa_programable"));
						lista.setSemanasXSemestre(rs.getInt("paa_semanaxsemestre"));
						lista.setTrabajoIndependiente(rs.getInt("paa_h_trabajo_ind_sem"));

						Asignatura asignatura = new Asignatura();
						asignatura.setCodigo(rs.getInt("asi_codigo"));
						asignatura.setNombre(rs.getString("asi_nombre"));
						asignatura.setNombreCorto(rs.getString("asi_nombre_corto"));
						lista.setAsignatura(asignatura);

						Nucleo nucleo = new Nucleo();
						nucleo.setCodigo(rs.getInt("nuc_codigo"));
						nucleo.setNombre(rs.getString("nuc_nombre"));
						lista.setNucleo(nucleo);

						Componente componente = new Componente();
						componente.setCodigoComponente(rs.getInt("com_codigo"));
						componente.setNombreComponente(rs.getString("com_nombre"));
						lista.setComponente(componente);

						return lista;
					}
				});

		return listaPersona;
	}

	@Override
	public boolean modificarPlanAcademicoAsignatura(int id, PlanAcademicoAsignatura a) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			// update
			String sql = "UPDATE dbo.plan_academico_asignatura SET pla_codigo=?, asi_codigo=?, "
					+ "paa_semestre=?, paa_intensidad=?, nuc_codigo=?, paa_estado=?, paa_nota_minima=?, "
					+ "paa_chequeo_requisito=?, paa_obligatoria=?, paa_programable=?, paa_semanaxsemestre=?, "
					+ "paa_h_trabajo_ind_sem=?, com_codigo=?, paa_credito = ? WHERE paa_codigo=?";

			int resultado = jdbcTemplate.update(sql, a.getPlanAcademico().getCodigo(), a.getCodigo(), a.getSemestre(),
					a.getIntensidad(), a.getNucleo().getCodigo(), 1, a.getNotaMinima(), a.getRequisito(),
					a.getPertenece(), a.getProgramable(), a.getSemanasXSemestre(), a.getTrabajoIndependiente(),
					a.getComponente().getCodigoComponente(), a.getCreditos(), id);

			if (resultado > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean agregarPlanAcademicoAsignatura(PlanAcademicoAsignatura a) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			String sql = "INSERT INTO plan_academico_asignatura (pla_codigo, asi_codigo, paa_semestre,"
					+ "paa_intensidad, nuc_codigo, paa_estado, paa_nota_minima, paa_chequeo_requisito,"
					+ "paa_obligatoria, paa_programable, paa_h_trabajo_ind_sem,"
					+ "com_codigo, paa_credito) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			int resultado = jdbcTemplate.update(sql, a.getPlanAcademico().getCodigo(), a.getCodigo(), a.getSemestre(),
					a.getIntensidad(), a.getNucleo().getCodigo(), 1, a.getNotaMinima(), a.getRequisito(),
					a.getPertenece(), a.getProgramable(), a.getTrabajoIndependiente(),
					a.getComponente().getCodigoComponente(), a.getCreditos());

			if (resultado > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean eliminarPlanAcademicoAsignatura(int id) {
		// TODO Auto-generated method stub
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String sql = " UPDATE plan_academico_asignatura SET paa_estado = 0 WHERE paa_codigo=? ";
			int resultado = jdbcTemplate.update(sql, id);
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
