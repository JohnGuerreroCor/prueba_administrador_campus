/**
 * Clase PaisRestController para gestionar los servicios web de Pais
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

import co.edu.usco.lcms.model.Pais;

/**
 * 
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */
@RestController
public class PaisRestController {

	@Autowired
	DataSource dataSourceAcademiaInvitado;

	/**
	 * Metodo GET para listar los registros de paises
	 * @return retorna la lista de pais
	 */
	@RequestMapping(value = "/paises2", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Pais> getPaises() {
		List<Pais> listOfPaises = new ArrayList<Pais>();
		listOfPaises = crearListaPais();
		return listOfPaises;
	}

	public List<Pais> crearListaPais() {
		// @RolesAllowed("INVITADO")

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);

		String sql = "select pai_codigo, pai_acronimo, pai_nombre from pais";
		sql = sql + " where len(pai_acronimo) > 0 order by pai_nombre";

		List<Pais> listaPais = jdbcTemplate.query(sql, new RowMapper<Pais>() {

			public Pais mapRow(ResultSet rs, int rowNum) throws SQLException {
				Pais pais = new Pais();

				pais.setCodigo(rs.getInt("pai_codigo"));
				pais.setAcronimo(rs.getString("pai_acronimo"));
				pais.setNombre(rs.getString("pai_nombre"));

				return pais;
			}

		});

		return listaPais;
	}

	/*
	 * 
	 * @RequestMapping(value = "/paises", method = RequestMethod.GET, headers =
	 * "Accept=application/json") public JSONRespuesta
	 * getPaises(@RequestParam(value = "search[value]", defaultValue = "")
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
	 * String sql = "select count(*) from pais"; int count =
	 * jdbcTemplate.queryForObject(sql, Integer.class); int filtrados = count;
	 * 
	 * if (search.length() > 0) { sql = sql + " where pai_nombre like '%" +
	 * search + "%'"; filtrados = jdbcTemplate.queryForObject(sql,
	 * Integer.class); }
	 * 
	 * sql = "select pai_codigo, pai_acronimo, pai_nombre"; sql = sql +
	 * " from (select row_number() over(order by pai_nombre ASC) AS RowNumber,";
	 * sql = sql + " pai_codigo, pai_acronimo, pai_nombre"; sql = sql +
	 * " from pais";
	 * 
	 * if (search.length() > 0) { sql = sql + " where pai_nombre like '%" +
	 * search + "%'"; }
	 * 
	 * sql = sql + ") as tabla"; sql = sql + " where tabla.RowNumber between " +
	 * start + " and " + fin;
	 * 
	 * List<Pais> listaPais = jdbcTemplate.query(sql, new RowMapper<Pais>() {
	 * 
	 * public Pais mapRow(ResultSet rs, int rowNum) throws SQLException { Pais
	 * pais = new Pais();
	 * 
	 * pais.setCodigo(rs.getInt("pai_codigo"));
	 * pais.setAcronimo(rs.getString("pai_acronimo"));
	 * pais.setNombre(rs.getString("pai_nombre"));
	 * 
	 * return pais; }
	 * 
	 * });
	 * 
	 * respuesta.setDraw(draw); respuesta.setRecordsFiltered(filtrados);
	 * respuesta.setRecordsTotal(count); respuesta.setData(listaPais);
	 * 
	 * return respuesta; }
	 * 
	 * // ------------ POST - INSERT -----------
	 * 
	 * @RequestMapping(value = "/paises", method = RequestMethod.POST, headers =
	 * "Accept=application/json") public ResponseEntity<Respuesta>
	 * adicionar(@RequestBody Pais pais) {
	 * 
	 * try { JdbcTemplate jdbcTemplate = new
	 * JdbcTemplate(dataSourceAcademiaInvitado);
	 * 
	 * String sql = "INSERT INTO pais (pai_codigo, pai_acronimo, pai_nombre)" +
	 * " VALUES (?, ?, ?)"; int resultado = jdbcTemplate.update(sql,
	 * pais.getCodigo(), pais.getAcronimo(), pais.getNombre());
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
	 * @RequestMapping(value = "/paises/{id}", method = RequestMethod.PUT,
	 * headers = "Accept=application/json") public ResponseEntity<Respuesta>
	 * modificar(@PathVariable int id, @RequestBody Pais pais) {
	 * 
	 * if (id > 0) { JdbcTemplate jdbcTemplate = new
	 * JdbcTemplate(dataSourceAcademiaInvitado); // update String sql =
	 * "UPDATE pais SET pai_acronimo=?, pai_nombre=?" + " WHERE pai_codigo=?";
	 * int resultado = jdbcTemplate.update(sql, pais.getAcronimo(),
	 * pais.getNombre(), id);
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
	 * @RequestMapping(value = "/paises/{id}", method = RequestMethod.DELETE,
	 * headers = "Accept=application/json") public ResponseEntity<Respuesta>
	 * eliminar(@PathVariable int id) {
	 * 
	 * JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);
	 * String sql = "DELETE FROM pais WHERE pai_codigo=?"; int resultado =
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