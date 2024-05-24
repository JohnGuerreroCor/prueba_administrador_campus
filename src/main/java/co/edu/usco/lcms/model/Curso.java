/**
 * 
 */
package co.edu.usco.lcms.model;

import java.sql.Date;

import co.edu.usco.lcms.model.reservaespacios.UaaPersonal;

/**
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 *
 */
public class Curso {

	private int codigo;
	private UaaPersonal uaaPersonal;
	private Sede sede;
	private Asignatura asignatura;
	private Calendario calendario;
	private Usuario usuario;
	private Date fechaInicio;
	private int semanas;
	private String grupo;
	private int cupo;
	private PlanAcademico planAcademico;
	private int cantidad;
	private TipoCurso tipoCurso;
	private EstadoCurso estadoCurso;
	private TipoEscala tipoEscala;
	/**
	 * 
	 */
	public Curso() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param codigo
	 * @param uaaPersonal
	 * @param sede
	 * @param asignatura
	 * @param calendario
	 * @param usuario
	 * @param fechaInicio
	 * @param semanas
	 * @param grupo
	 * @param cupo
	 * @param planAcademico
	 * @param cantidad
	 * @param tipoCurso
	 * @param estadoCurso
	 * @param tipoEscala
	 */
	public Curso(int codigo, UaaPersonal uaaPersonal, Sede sede, Asignatura asignatura, Calendario calendario,
			Usuario usuario, Date fechaInicio, int semanas, String grupo, int cupo, PlanAcademico planAcademico,
			int cantidad, TipoCurso tipoCurso, EstadoCurso estadoCurso, TipoEscala tipoEscala) {
		super();
		this.codigo = codigo;
		this.uaaPersonal = uaaPersonal;
		this.sede = sede;
		this.asignatura = asignatura;
		this.calendario = calendario;
		this.usuario = usuario;
		this.fechaInicio = fechaInicio;
		this.semanas = semanas;
		this.grupo = grupo;
		this.cupo = cupo;
		this.planAcademico = planAcademico;
		this.cantidad = cantidad;
		this.tipoCurso = tipoCurso;
		this.estadoCurso = estadoCurso;
		this.tipoEscala = tipoEscala;
	}
	/**
	 * @return the codigo
	 */
	public int getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the uaaPersonal
	 */
	public UaaPersonal getUaaPersonal() {
		return uaaPersonal;
	}
	/**
	 * @param uaaPersonal the uaaPersonal to set
	 */
	public void setUaaPersonal(UaaPersonal uaaPersonal) {
		this.uaaPersonal = uaaPersonal;
	}
	/**
	 * @return the sede
	 */
	public Sede getSede() {
		return sede;
	}
	/**
	 * @param sede the sede to set
	 */
	public void setSede(Sede sede) {
		this.sede = sede;
	}
	/**
	 * @return the asignatura
	 */
	public Asignatura getAsignatura() {
		return asignatura;
	}
	/**
	 * @param asignatura the asignatura to set
	 */
	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}
	/**
	 * @return the calendario
	 */
	public Calendario getCalendario() {
		return calendario;
	}
	/**
	 * @param calendario the calendario to set
	 */
	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}
	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the semanas
	 */
	public int getSemanas() {
		return semanas;
	}
	/**
	 * @param semanas the semanas to set
	 */
	public void setSemanas(int semanas) {
		this.semanas = semanas;
	}
	/**
	 * @return the grupo
	 */
	public String getGrupo() {
		return grupo;
	}
	/**
	 * @param grupo the grupo to set
	 */
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	/**
	 * @return the cupo
	 */
	public int getCupo() {
		return cupo;
	}
	/**
	 * @param cupo the cupo to set
	 */
	public void setCupo(int cupo) {
		this.cupo = cupo;
	}
	/**
	 * @return the planAcademico
	 */
	public PlanAcademico getPlanAcademico() {
		return planAcademico;
	}
	/**
	 * @param planAcademico the planAcademico to set
	 */
	public void setPlanAcademico(PlanAcademico planAcademico) {
		this.planAcademico = planAcademico;
	}
	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the tipoCurso
	 */
	public TipoCurso getTipoCurso() {
		return tipoCurso;
	}
	/**
	 * @param tipoCurso the tipoCurso to set
	 */
	public void setTipoCurso(TipoCurso tipoCurso) {
		this.tipoCurso = tipoCurso;
	}
	/**
	 * @return the estadoCurso
	 */
	public EstadoCurso getEstadoCurso() {
		return estadoCurso;
	}
	/**
	 * @param estadoCurso the estadoCurso to set
	 */
	public void setEstadoCurso(EstadoCurso estadoCurso) {
		this.estadoCurso = estadoCurso;
	}
	/**
	 * @return the tipoEscala
	 */
	public TipoEscala getTipoEscala() {
		return tipoEscala;
	}
	/**
	 * @param tipoEscala the tipoEscala to set
	 */
	public void setTipoEscala(TipoEscala tipoEscala) {
		this.tipoEscala = tipoEscala;
	}

		
}
