/**
 * 
 */
package co.edu.usco.lcms.model;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author jankarlos
 *
 */
public class OfertaAcademica {

	private int codigo;
	private String pago;
	private String tipoAdmision;

	private Timestamp fechaInicio;
	private Timestamp fechaFin;
	private Timestamp inscripcionFechaInicio;
	private Timestamp inscripcionFechaFin;
	private int crearUsuario;
	private String imagen;
	private int requiereCupo;
	private int cupoMax;
	private int cantidadInscritos;
	private int cantidadPreInscritos;

	private Programa programa;
	private Calendario calendario;
	private float valor;

	private OfertaAcademicaEstado ofertaAcademicaEstado;

	/**
	 * 
	 */
	public OfertaAcademica() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param pago
	 * @param tipoAdmision
	 * @param fechaInicio
	 * @param fechaFin
	 * @param inscripcionFechaInicio
	 * @param inscripcionFechaFin
	 * @param crearUsuario
	 * @param imagen
	 * @param requiereCupo
	 * @param cupoMax
	 * @param cantidadInscritos
	 * @param cantidadPreInscritos
	 * @param programa
	 * @param calendario
	 * @param valor
	 * @param ofertaAcademicaEstado
	 */
	public OfertaAcademica(int codigo, String pago, String tipoAdmision, Timestamp fechaInicio, Timestamp fechaFin,
			Timestamp inscripcionFechaInicio, Timestamp inscripcionFechaFin, int crearUsuario, String imagen,
			int requiereCupo, int cupoMax, int cantidadInscritos, int cantidadPreInscritos, Programa programa,
			Calendario calendario, float valor, OfertaAcademicaEstado ofertaAcademicaEstado) {
		super();
		this.codigo = codigo;
		this.pago = pago;
		this.tipoAdmision = tipoAdmision;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.inscripcionFechaInicio = inscripcionFechaInicio;
		this.inscripcionFechaFin = inscripcionFechaFin;
		this.crearUsuario = crearUsuario;
		this.imagen = imagen;
		this.requiereCupo = requiereCupo;
		this.cupoMax = cupoMax;
		this.cantidadInscritos = cantidadInscritos;
		this.cantidadPreInscritos = cantidadPreInscritos;
		this.programa = programa;
		this.calendario = calendario;
		this.valor = valor;
		this.ofertaAcademicaEstado = ofertaAcademicaEstado;
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
	 * @return the pago
	 */
	public String getPago() {
		return pago;
	}

	/**
	 * @param pago
	 *            the pago to set
	 */
	public void setPago(String pago) {
		this.pago = pago;
	}

	/**
	 * @return the tipoAdmision
	 */
	public String getTipoAdmision() {
		return tipoAdmision;
	}

	/**
	 * @param tipoAdmision
	 *            the tipoAdmision to set
	 */
	public void setTipoAdmision(String tipoAdmision) {
		this.tipoAdmision = tipoAdmision;
	}

	/**
	 * @return the fechaInicio
	 */
	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio
	 *            the fechaInicio to set
	 */
	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public Timestamp getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin
	 *            the fechaFin to set
	 */
	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the inscripcionFechaInicio
	 */
	public Timestamp getInscripcionFechaInicio() {
		return inscripcionFechaInicio;
	}

	/**
	 * @param inscripcionFechaInicio
	 *            the inscripcionFechaInicio to set
	 */
	public void setInscripcionFechaInicio(Timestamp inscripcionFechaInicio) {
		this.inscripcionFechaInicio = inscripcionFechaInicio;
	}

	/**
	 * @return the inscripcionFechaFin
	 */
	public Timestamp getInscripcionFechaFin() {
		return inscripcionFechaFin;
	}

	/**
	 * @param inscripcionFechaFin
	 *            the inscripcionFechaFin to set
	 */
	public void setInscripcionFechaFin(Timestamp inscripcionFechaFin) {
		this.inscripcionFechaFin = inscripcionFechaFin;
	}

	/**
	 * @return the crearUsuario
	 */
	public int getCrearUsuario() {
		return crearUsuario;
	}

	/**
	 * @param crearUsuario
	 *            the crearUsuario to set
	 */
	public void setCrearUsuario(int crearUsuario) {
		this.crearUsuario = crearUsuario;
	}

	/**
	 * @return the imagen
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * @param imagen
	 *            the imagen to set
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return the requiereCupo
	 */
	public int getRequiereCupo() {
		return requiereCupo;
	}

	/**
	 * @param requiereCupo
	 *            the requiereCupo to set
	 */
	public void setRequiereCupo(int requiereCupo) {
		this.requiereCupo = requiereCupo;
	}

	/**
	 * @return the cupoMax
	 */
	public int getCupoMax() {
		return cupoMax;
	}

	/**
	 * @param cupoMax
	 *            the cupoMax to set
	 */
	public void setCupoMax(int cupoMax) {
		this.cupoMax = cupoMax;
	}

	/**
	 * @return the cantidadInscritos
	 */
	public int getCantidadInscritos() {
		return cantidadInscritos;
	}

	/**
	 * @param cantidadInscritos
	 *            the cantidadInscritos to set
	 */
	public void setCantidadInscritos(int cantidadInscritos) {
		this.cantidadInscritos = cantidadInscritos;
	}

	/**
	 * @return the cantidadPreInscritos
	 */
	public int getCantidadPreInscritos() {
		return cantidadPreInscritos;
	}

	/**
	 * @param cantidadPreInscritos
	 *            the cantidadPreInscritos to set
	 */
	public void setCantidadPreInscritos(int cantidadPreInscritos) {
		this.cantidadPreInscritos = cantidadPreInscritos;
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
	 * @return the calendario
	 */
	public Calendario getCalendario() {
		return calendario;
	}

	/**
	 * @param calendario
	 *            the calendario to set
	 */
	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}

	/**
	 * @return the valor
	 */
	public float getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(float valor) {
		this.valor = valor;
	}

	/**
	 * @return the ofertaAcademicaEstado
	 */
	public OfertaAcademicaEstado getOfertaAcademicaEstado() {
		return ofertaAcademicaEstado;
	}

	/**
	 * @param ofertaAcademicaEstado
	 *            the ofertaAcademicaEstado to set
	 */
	public void setOfertaAcademicaEstado(OfertaAcademicaEstado ofertaAcademicaEstado) {
		this.ofertaAcademicaEstado = ofertaAcademicaEstado;
	}

}
