package co.edu.usco.lcms.model;

import java.sql.Date;

public class Periodo {

	private int codigo;
	private int tipoCodigo;
	private PeriodoTipo periodoTipo;
	private String nombre;
	private Date fechaInicio;
	private Date fechaFin;
	private int periodo;
	private String anio;
	/**
	 * 
	 */
	public Periodo() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param codigo
	 * @param tipoCodigo
	 * @param periodoTipo
	 * @param nombre
	 * @param fechaInicio
	 * @param fechaFin
	 * @param periodo
	 * @param anio
	 */
	public Periodo(int codigo, int tipoCodigo, PeriodoTipo periodoTipo, String nombre, Date fechaInicio, Date fechaFin,
			int periodo, String anio) {
		super();
		this.codigo = codigo;
		this.tipoCodigo = tipoCodigo;
		this.periodoTipo = periodoTipo;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.periodo = periodo;
		this.anio = anio;
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
	 * @return the tipoCodigo
	 */
	public int getTipoCodigo() {
		return tipoCodigo;
	}
	/**
	 * @param tipoCodigo the tipoCodigo to set
	 */
	public void setTipoCodigo(int tipoCodigo) {
		this.tipoCodigo = tipoCodigo;
	}
	/**
	 * @return the periodoTipo
	 */
	public PeriodoTipo getPeriodoTipo() {
		return periodoTipo;
	}
	/**
	 * @param periodoTipo the periodoTipo to set
	 */
	public void setPeriodoTipo(PeriodoTipo periodoTipo) {
		this.periodoTipo = periodoTipo;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the periodo
	 */
	public int getPeriodo() {
		return periodo;
	}
	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	/**
	 * @return the anio
	 */
	public String getAnio() {
		return anio;
	}
	/**
	 * @param anio the anio to set
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}
	
	

}
