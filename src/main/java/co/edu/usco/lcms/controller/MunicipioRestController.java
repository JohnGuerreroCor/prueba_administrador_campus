/**
 * Clase MunicipioRestController para gestionar el servicio web de listar Municipios
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.lcms.model.Municipio;

/**
 * 
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
@RestController
public class MunicipioRestController {

	@Autowired
	DataSource dataSourceAcademiaInvitado;

	/**
	 * Metodo GET para listar los datos de los municipios
	 * 
	 * @param idDep
	 *            codigo de departamento con el cual se quiere listar los
	 *            municipio
	 * @return retorna una lista de municipios segun el departamento
	 */
	@RequestMapping(value = "/municipio", method = RequestMethod.GET, headers = "Accept=application/json")
	//@Secured({ "ROLE_ADMINISTRADOR_LCMS", "ROLE_ADMINISTRADOR_FACULTAD_LCMS" })
	@PreAuthorize("hasRole(${role.administrador.lcms}) OR hasRole(${role.administrador.facultad.lcms})")
	public List<Municipio> getMunicipios(@RequestParam("idDepartamento") int idDep) {
		List<Municipio> listaDeMunicipio = new ArrayList<Municipio>();
		listaDeMunicipio = crearListaMunicipio(idDep);
		return listaDeMunicipio;
	}

	public List<Municipio> crearListaMunicipio(int idDep) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);

		Object[] obj = new Object[] {};

		String sql = "select mun_codigo, dep_codigo, mun_nombre, SNIES_codigo from municipio";

		if (idDep != 0) {
			sql = sql + " where dep_codigo= ? ";
			obj = new Object[] { idDep };
		}

		sql = sql + " order by mun_nombre";

		List<Municipio> listaMunicipio = jdbcTemplate.query(sql, obj, new RowMapper<Municipio>() {
			public Municipio mapRow(ResultSet rs, int rowNum) throws SQLException {
				Municipio municipio = new Municipio();
				municipio.setCodigo(rs.getInt("mun_codigo"));
				municipio.setDepartamento(rs.getInt("dep_codigo"));
				municipio.setNombre(rs.getString("mun_nombre"));
				municipio.setSnies(rs.getString("SNIES_codigo"));
				return municipio;
			}

		});

		return listaMunicipio;
	}
	/*
	 * @RequestMapping(value = "/municipios/listadoMun", method =
	 * RequestMethod.GET, headers = "Accept=application/json") public
	 * JSONRespuesta getMunicipiosLista(@RequestParam(value = "search[value]",
	 * defaultValue = "") String search,
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
	 * int fin = start + length - 1; JdbcTemplate jdbcTemplate = new
	 * JdbcTemplate(dataSourceAcademiaInvitado);
	 * 
	 * String sql = "select count(*) from municipio "; int count =
	 * jdbcTemplate.queryForObject(sql, Integer.class); int filtrados = count;
	 * 
	 * if (search.length() > 0) { sql = sql + " WHERE mun_nombre like '%" +
	 * search + "%'"; filtrados = jdbcTemplate.queryForObject(sql,
	 * Integer.class); }
	 * 
	 * sql = "SELECT dep_codigo, SNIES_codigo, mun_codigo, mun_nombre"; sql =
	 * sql +
	 * " from (select row_number() over(order by m.mun_nombre ASC) AS RowNumber,"
	 * ; sql = sql + " m.dep_codigo, m.SNIES_codigo, m.mun_codigo, m.mun_nombre"
	 * ; sql = sql + " from municipio m ";
	 * 
	 * if (search.length() > 0) { sql = sql + " WHERE m.mun_nombre like '%" +
	 * search + "%'"; }
	 * 
	 * sql = sql + ") as tabla"; sql = sql + " where tabla.RowNumber between " +
	 * start + " and " + fin;
	 * 
	 * List<Municipio> listaMunicipio = jdbcTemplate.query(sql, new
	 * RowMapper<Municipio>() {
	 * 
	 * public Municipio mapRow(ResultSet rs, int rowNum) throws SQLException {
	 * 
	 * Municipio municipio = new Municipio();
	 * municipio.setCodigo(rs.getInt("mun_codigo"));
	 * municipio.setDepartamento(rs.getInt("dep_codigo"));
	 * municipio.setNombre(rs.getString("mun_nombre"));
	 * municipio.setSnies(rs.getString("SNIES_codigo")); return municipio; }
	 * 
	 * });
	 * 
	 * respuesta.setDraw(draw); respuesta.setRecordsFiltered(filtrados);
	 * respuesta.setRecordsTotal(count); respuesta.setData(listaMunicipio);
	 * 
	 * return respuesta; }
	 * 
	 * @RequestMapping(value = "/municipios/registrarMun", method =
	 * RequestMethod.POST, headers = "Accept=application/json") public
	 * ResponseEntity<Respuesta> adicionar(@RequestBody Municipio mun) {
	 * 
	 * try { JdbcTemplate jdbcTemplate = new
	 * JdbcTemplate(dataSourceAcademiaInvitado);
	 * 
	 * String sql =
	 * "INSERT INTO municipio (mun_codigo, dep_codigo, SNIES_codigo, mun_nombre)"
	 * ; sql = sql + " VALUES (?, ?, ?, ?)"; int resultado =
	 * jdbcTemplate.update(sql, mun.getCodigo(), mun.getDepartamento(),
	 * mun.getSnies(), mun.getNombre());
	 * 
	 * if (resultado > 0) { Respuesta respuesta = new
	 * Respuesta(Respuesta.EJECUCION_OK); return new
	 * ResponseEntity<Respuesta>(respuesta, HttpStatus.OK); } } catch (Exception
	 * e) { System.out.println("Error: " + e.toString()); }
	 * 
	 * Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR); return
	 * new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST); }
	 * 
	 * @RequestMapping(value = "/municipios/editar/{id}", method =
	 * RequestMethod.PUT, headers = "Accept=application/json") public
	 * ResponseEntity<Respuesta> modificar(@PathVariable int id, @RequestBody
	 * Municipio mun) {
	 * 
	 * if (id > 0) { JdbcTemplate jdbcTemplate = new
	 * JdbcTemplate(dataSourceAcademiaInvitado); // update String sql =
	 * "UPDATE municipio SET  dep_codigo = ?, SNIES_codigo = ?, mun_nombre = ?";
	 * sql = sql + " WHERE mun_codigo=?"; int resultado =
	 * jdbcTemplate.update(sql, mun.getDepartamento(), mun.getSnies(),
	 * mun.getNombre(), id);
	 * 
	 * if (resultado > 0) { Respuesta respuesta = new
	 * Respuesta(Respuesta.EJECUCION_OK); return new
	 * ResponseEntity<Respuesta>(respuesta, HttpStatus.OK); } }
	 * 
	 * Respuesta respuesta = new Respuesta(Respuesta.EJECUCION_ERROR); return
	 * new ResponseEntity<Respuesta>(respuesta, HttpStatus.BAD_REQUEST); }
	 * 
	 * @RequestMapping(value = "/municipios/eliminar/{id}", method =
	 * RequestMethod.DELETE, headers = "Accept=application/json") public
	 * ResponseEntity<Respuesta> eliminar(@PathVariable int id) {
	 * 
	 * JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceAcademiaInvitado);
	 * String sql = "DELETE FROM municipio WHERE mun_codigo=?"; int resultado =
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
