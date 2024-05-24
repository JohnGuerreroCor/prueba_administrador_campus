/**
 * 
 */
package co.edu.usco.lcms.model;

import java.sql.Date;
import java.util.List;

/**
 * @author jankarlos
 *
 */
public class PlanAcademico {
	private int codigo;
	private String nombre;
	private int creditos;
	private int totalCreditos;
	private int requisitoGrado;
	private int antiguo;
	private String tipoRegistro;
	private int estado;
	private Date fecha;
	private int planActual;
	private int sniesNumPeridos;
	private int id;
	private int totalHoras;
	private String nombreUaa;
	private List<PlanAcademicoAsignatura> asignaturas;
	private Programa programa;
	private Resolucion resolucion;

	/**
	 * 
	 */
	public PlanAcademico() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param nombre
	 * @param creditos
	 * @param totalCreditos
	 * @param requisitoGrado
	 * @param antiguo
	 * @param tipoRegistro
	 * @param estado
	 * @param fecha
	 * @param planActual
	 * @param sniesNumPeridos
	 * @param id
	 * @param totalHoras
	 * @param nombreUaa
	 * @param asignaturas
	 * @param programa
	 * @param resolucion
	 */
	public PlanAcademico(int codigo, String nombre, int creditos, int totalCreditos, int requisitoGrado, int antiguo,
			String tipoRegistro, int estado, Date fecha, int planActual, int sniesNumPeridos, int id, int totalHoras,
			String nombreUaa, List<PlanAcademicoAsignatura> asignaturas, Programa programa, Resolucion resolucion) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.creditos = creditos;
		this.totalCreditos = totalCreditos;
		this.requisitoGrado = requisitoGrado;
		this.antiguo = antiguo;
		this.tipoRegistro = tipoRegistro;
		this.estado = estado;
		this.fecha = fecha;
		this.planActual = planActual;
		this.sniesNumPeridos = sniesNumPeridos;
		this.id = id;
		this.totalHoras = totalHoras;
		this.nombreUaa = nombreUaa;
		this.asignaturas = asignaturas;
		this.programa = programa;
		this.resolucion = resolucion;
	}

	/**
	 * @return the codigo
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the creditos
	 */
	public int getCreditos() {
		return creditos;
	}

	/**
	 * @param creditos
	 *            the creditos to set
	 */
	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	/**
	 * @return the totalCreditos
	 */
	public int getTotalCreditos() {
		return totalCreditos;
	}

	/**
	 * @param totalCreditos
	 *            the totalCreditos to set
	 */
	public void setTotalCreditos(int totalCreditos) {
		this.totalCreditos = totalCreditos;
	}

	/**
	 * @return the requisitoGrado
	 */
	public int getRequisitoGrado() {
		return requisitoGrado;
	}

	/**
	 * @param requisitoGrado
	 *            the requisitoGrado to set
	 */
	public void setRequisitoGrado(int requisitoGrado) {
		this.requisitoGrado = requisitoGrado;
	}

	/**
	 * @return the antiguo
	 */
	public int getAntiguo() {
		return antiguo;
	}

	/**
	 * @param antiguo
	 *            the antiguo to set
	 */
	public void setAntiguo(int antiguo) {
		this.antiguo = antiguo;
	}

	/**
	 * @return the tipoRegistro
	 */
	public String getTipoRegistro() {
		return tipoRegistro;
	}

	/**
	 * @param tipoRegistro
	 *            the tipoRegistro to set
	 */
	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	/**
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the planActual
	 */
	public int getPlanActual() {
		return planActual;
	}

	/**
	 * @param planActual
	 *            the planActual to set
	 */
	public void setPlanActual(int planActual) {
		this.planActual = planActual;
	}

	/**
	 * @return the sniesNumPeridos
	 */
	public int getSniesNumPeridos() {
		return sniesNumPeridos;
	}

	/**
	 * @param sniesNumPeridos
	 *            the sniesNumPeridos to set
	 */
	public void setSniesNumPeridos(int sniesNumPeridos) {
		this.sniesNumPeridos = sniesNumPeridos;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the totalHoras
	 */
	public int getTotalHoras() {
		return totalHoras;
	}

	/**
	 * @param totalHoras
	 *            the totalHoras to set
	 */
	public void setTotalHoras(int totalHoras) {
		this.totalHoras = totalHoras;
	}

	/**
	 * @return the nombreUaa
	 */
	public String getNombreUaa() {
		return nombreUaa;
	}

	/**
	 * @param nombreUaa
	 *            the nombreUaa to set
	 */
	public void setNombreUaa(String nombreUaa) {
		this.nombreUaa = nombreUaa;
	}

	/**
	 * @return the asignaturas
	 */
	public List<PlanAcademicoAsignatura> getAsignaturas() {
		return asignaturas;
	}

	/**
	 * @param asignaturas
	 *            the asignaturas to set
	 */
	public void setAsignaturas(List<PlanAcademicoAsignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}

	/**
	 * @return the programa
	 */
	public Programa getPrograma() {
		return programa;
	}

	/**
	 * @param programa
	 *            the programa to set
	 */
	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	/**
	 * @return the resolucion
	 */
	public Resolucion getResolucion() {
		return resolucion;
	}

	/**
	 * @param resolucion
	 *            the resolucion to set
	 */
	public void setResolucion(Resolucion resolucion) {
		this.resolucion = resolucion;
	}

}
