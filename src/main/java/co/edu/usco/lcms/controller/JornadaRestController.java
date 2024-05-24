/**
 * Clase JornadaRestController para gestionar el servicio web de listar Jornadas
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

import co.edu.usco.lcms.model.Jornada;

/**
 * 
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class JornadaRestController {

	@Autowired
	DataSource dataSourceAcademiaInvitado;

	/**
	 * Metodo GET para listar Jornadas
	 * 
	 * @return retorna una lista de jornadas
	 */
	@RequestMapping(value = "/jornadaLista", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Jornada> getJornadaLista() {
		List<Jornada> listOfJornada = new ArrayList<Jornada>();
		listOfJornada = crearListaJornada();
		return listOfJornada;
	}

	public List<Jornada> crearListaJornada() {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);
		String sql = "select j.jor_codigo, j.jor_nombre from jornada j " + "order by j.jor_nombre";
		List<Jornada> listaJornada = jdbcTemplate.query(sql, new RowMapper<Jornada>() {

			public Jornada mapRow(ResultSet rs, int rowNum) throws SQLException {
				Jornada jornada = new Jornada();
				jornada.setCodigo(rs.getInt("jor_codigo"));
				jornada.setNombre(rs.getString("jor_nombre"));

				return jornada;
			}

		});
		return listaJornada;
	}
	/*
	 * @RequestMapping(value = "/jornadas", method = RequestMethod.GET, headers
	 * = "Accept=application/json") public JSONRespuesta
	 * getJornadas(@RequestParam(value = "search[value]", defaultValue = "")
	 * String search,
	 * 
	 * @RequestParam(value = "start", defaultValue = "1") int start,
	 * 
	 * @RequestParam(value = "length", defaultValue = "10") int length,
	 * 
	 * @RequestParam(value = "draw", defaultValue = "1") int draw) {
	 * 
	 * JSONRespuesta respuesta = new JSONRespuesta();
	 * 
	 * if (start == 0) { start = 1; }
	 * 
	 * int fin = start + length - 1;
	 * 
	 * JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);
	 * 
	 * String sql = "select count(*) from jornada"; int count =
	 * jdbcTemplate.queryForObject(sql, Integer.class); int filtrados = count;
	 * 
	 * if (search.length() > 0) { sql = sql + " where jor_nombre like '%" +
	 * search + "%'"; filtrados = jdbcTemplate.queryForObject(sql,
	 * Integer.class); }
	 * 
	 * sql = "select jor_codigo, jor_nombre"; sql = sql +
	 * " from (select row_number() over(order by jor_nombre ASC) AS RowNumber,";
	 * sql = sql + " jor_codigo, jor_nombre"; sql = sql + " from jornada";
	 * 
	 * if (search.length() > 0) { sql = sql + " where jor_nombre like '%" +
	 * search + "%'"; }
	 * 
	 * sql = sql + ") as tabla"; sql = sql + " where tabla.RowNumber between " +
	 * start + " and " + fin;
	 * 
	 * List<Jornada> listaJornada = jdbcTemplate.query(sql, new
	 * RowMapper<Jornada>() {
	 * 
	 * public Jornada mapRow(ResultSet rs, int rowNum) throws SQLException {
	 * Jornada jornada = new Jornada();
	 * 
	 * jornada.setCodigo(rs.getInt("jor_codigo"));
	 * jornada.setNombre(rs.getString("jor_nombre"));
	 * 
	 * return jornada; }
	 * 
	 * });
	 * 
	 * respuesta.setDraw(draw); respuesta.setRecordsFiltered(filtrados);
	 * respuesta.setRecordsTotal(count); respuesta.setData(listaJornada);
	 * 
	 * return respuesta; }
	 * 
	 * // ------------ POST - INSERT -----------
	 * 
	 * @RequestMapping(value = "/jornadas", method = RequestMethod.POST, headers
	 * = "Accept=application/json") public ResponseEntity<Respuesta>
	 * adicionar(@RequestBody Jornada jornada) {
	 * 
	 * try { JdbcTemplate jdbcTemplate = new
	 * JdbcTemplate(dataSourceAcademiaInvitado);
	 * 
	 * String sql = "INSERT INTO jornada (jor_nombre)" + " VALUES (?)"; int
	 * resultado = jdbcTemplate.update(sql, jornada.getNombre());
	 * 
	 * if (resultado > 0) { Respuesta respuesta = new
	 * Respuesta(Respuesta.EJECUCION_OK); return new
	 * ResponseEntity<Respuesta>(respuesta, HttpStatus.OK); } } catch (Exception
	 * e) { System.out.println("Error: " + e.toString()); }
	 * 
	 * Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR); return
	 * new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST); }
	 * 
	 * // ---------------- PUT - UPDATE -----------
	 * 
	 * @RequestMapping(value = "/jornadas/{id}", method = RequestMethod.PUT,
	 * headers = "Accept=application/json") public ResponseEntity<Respuesta>
	 * modificar(@PathVariable int id, @RequestBody Jornada jornada) {
	 * 
	 * if (id > 0) { JdbcTemplate jdbcTemplate = new
	 * JdbcTemplate(dataSourceAcademiaInvitado); // update String sql =
	 * "UPDATE jornada SET jor_nombre=?" + " WHERE jor_codigo=?"; int resultado
	 * = jdbcTemplate.update(sql, jornada.getNombre(), id);
	 * 
	 * if (resultado > 0) { Respuesta respuesta = new
	 * Respuesta(Respuesta.EJECUCION_OK); return new
	 * ResponseEntity<Respuesta>(respuesta, HttpStatus.OK); } }
	 * 
	 * Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR); return
	 * new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST); }
	 * 
	 * // ------------------- DELETE - ELIMINAR ----------
	 * 
	 * @RequestMapping(value = "/jornadas/{id}", method = RequestMethod.DELETE,
	 * headers = "Accept=application/json") public ResponseEntity<Respuesta>
	 * eliminar(@PathVariable int id) {
	 * 
	 * JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);
	 * String sql = "DELETE FROM jornada WHERE jor_codigo=?"; int resultado =
	 * jdbcTemplate.update(sql, id);
	 * 
	 * if (resultado > 0) { Respuesta respuesta = new
	 * Respuesta(Respuesta.EJECUCION_OK); return new
	 * ResponseEntity<Respuesta>(respuesta, HttpStatus.OK); }
	 * 
	 * Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR); return
	 * new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST); }
	 */
}