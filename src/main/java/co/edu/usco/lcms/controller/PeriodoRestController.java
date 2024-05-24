/**
 * Clase PeriodoRestController para gestionar los servicio web de Periodo
 */
package co.edu.usco.lcms.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.model.Periodo;

/**
 * @author Jankarlos Diaz Vieda
 *
 */
@RestController
public class PeriodoRestController {

	@Autowired
	DataSource dataSourceAcademiaInvitado;

	/**
	 * Metodo GET para listar los datos de periodo
	 * @return retorna la lista de periodo
	 */
	@RequestMapping(value = "/periodoListar", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({"ROLE_ADMINISTRADOR_LCMS","ROLE_ADMINISTRADOR_FACULTAD_LCMS"})
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Periodo> getPeriodo() {
		List<Periodo> listPeriodo = new ArrayList<Periodo>();
		listPeriodo = crearListaPeriodo();
		return listPeriodo;
	}

	public List<Periodo> crearListaPeriodo() {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);

		String sql = "select p.per_codigo, p.per_nombre";
		sql = sql + " from periodo p order by per_nombre";

		List<Periodo> listaPeriodo = jdbcTemplate.query(sql, new RowMapper<Periodo>() {

			public Periodo mapRow(ResultSet rs, int rowNum) throws SQLException {
				Periodo Periodo = new Periodo();

				Periodo.setCodigo(rs.getInt("per_codigo"));
				Periodo.setNombre(rs.getString("per_nombre"));

				return Periodo;
			}

		});
		return listaPeriodo;
	}

	/*
	@RequestMapping(value = "/periodo", method = RequestMethod.GET, headers = "Accept=application/json")
	public JSONRespuesta getUAA(@RequestParam(value = "search[value]", defaultValue = "") String search,
			@RequestParam(value = "start", defaultValue = "1") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "draw", defaultValue = "1") int draw) {

		JSONRespuesta respuesta = new JSONRespuesta();

		if (start == 0) {
			start = 1;
		}

		int fin = start + length - 1;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);

		String sql = "select count(*) from periodo p, periodo_tipo pt WHERE p.pet_codigo = pt.pet_codigo";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		int filtrados = count;

		if (search.length() > 0) {
			sql = sql + " AND per_nombre like '%" + search + "%'";
			filtrados = jdbcTemplate.queryForObject(sql, Integer.class);
		}

		sql = "SELECT per_codigo, pet_codigo, pet_nombre, per_nombre, per_fecha_fin, per_fecha_inicio,";
		sql = sql + "per_periodo, per_año";
		sql = sql + " from (select row_number() over(order by p.per_nombre ASC) AS RowNumber,";
		sql = sql + " p.per_codigo,	pt.pet_codigo, pt.pet_nombre, p.per_nombre, p.per_fecha_fin, p.per_fecha_inicio,";
		sql = sql + "p.per_periodo,	p.per_año";

		sql = sql + " FROM periodo p, periodo_tipo pt WHERE";
		sql = sql + " p.pet_codigo = pt.pet_codigo";

		if (search.length() > 0) {
			sql = sql + " AND p.per_nombre like '%" + search + "%'";
		}

		sql = sql + ") as tabla";
		sql = sql + " where tabla.RowNumber between " + start + " and " + fin;

		List<Periodo> listaPeriodo = jdbcTemplate.query(sql, new RowMapper<Periodo>() {

			public Periodo mapRow(ResultSet rs, int rowNum) throws SQLException {
				Periodo periodo = new Periodo();

				periodo.setCodigo(rs.getInt("per_codigo"));
				periodo.setTipoCodigo(rs.getInt("pet_codigo"));
				periodo.setAnio(rs.getString("per_año"));
				periodo.setFechaFin(rs.getString("per_fecha_fin").substring(0, 10));
				periodo.setFechaInicio(rs.getString("per_fecha_inicio").substring(0, 10));
				periodo.setNombre(rs.getString("per_nombre"));
				periodo.setPeriodo(rs.getInt("per_periodo"));
				periodo.setTipoNombre(rs.getString("pet_nombre"));

				return periodo;
			}

		});

		respuesta.setDraw(draw);
		respuesta.setRecordsFiltered(filtrados);
		respuesta.setRecordsTotal(count);
		respuesta.setData(listaPeriodo);

		return respuesta;
	}

	
	@RequestMapping(value = "/periodo", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Respuesta> adicionar(@RequestBody Periodo periodo) {

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date fechaInicio = sdf.parse(periodo.getFechaInicio());
			java.util.Date fechaFin = sdf.parse(periodo.getFechaFin());

			String sql = "INSERT INTO periodo (per_codigo, pet_codigo, per_nombre, per_fecha_fin, ";
			sql = sql + "per_fecha_inicio, per_periodo, per_año)";
			sql = sql + " VALUES (?, ?, ?, ?, ?, ?, ?)";
			int resultado = jdbcTemplate.update(sql, periodo.getCodigo(), periodo.getTipoCodigo(), periodo.getNombre(),
					new java.sql.Date(fechaFin.getTime()), new java.sql.Date(fechaInicio.getTime()),
					periodo.getPeriodo(), periodo.getAnio());

			if (resultado > 0) {
				Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
				return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}

		Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/periodo/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody Periodo periodo)
			throws ParseException {

		if (id > 0) {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date fechaInicio = sdf.parse(periodo.getFechaInicio());
			java.util.Date fechaFin = sdf.parse(periodo.getFechaFin());

			// update
			String sql = "UPDATE periodo SET pet_codigo=?, per_nombre=?, per_fecha_fin=?, per_fecha_inicio=?,";
			sql = sql + " per_periodo=?, per_año=?";
			sql = sql + " WHERE per_codigo=?";
			int resultado = jdbcTemplate.update(sql, periodo.getTipoCodigo(), periodo.getNombre(),
					new java.sql.Date(fechaFin.getTime()), new java.sql.Date(fechaInicio.getTime()),
					periodo.getPeriodo(), periodo.getAnio(), id);

			if (resultado > 0) {
				Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
				return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
			}
		}

		Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/periodo/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<Respuesta> eliminar(@PathVariable int id) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);
		String sql = "DELETE FROM periodo WHERE per_codigo=?";
		int resultado = jdbcTemplate.update(sql, id);

		if (resultado > 0) {
			Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_OK);
			return new ResponseEntity<Respuesta>(respuesta, HttpStatus.OK);
		}

		Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR);
		return new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST);
	}

	public String validar(String nombre, int codigo) {
		return nombre;
	}
	*/
}
