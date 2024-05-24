/**
 * 
 */
package co.edu.usco.lcms.model;

import java.sql.Date;

/**
 * @author ANDRES-GPIE
 *
 */
public class Matricula {

	private int id;
	private int codigo;
	private Date fecha;
	private Inscripcion inscripcion;
	private Oferta oferta;
	private Persona persona;
	private Estudiante estudiante;
	private int codigoUsuario;
	private Calendario calendario;

	public Matricula() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Matricula(int id, int codigo, Date fecha, Inscripcion inscripcion, Oferta oferta, Persona persona,
			Estudiante estudiante, int codigoUsuario, Calendario calendario) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.fecha = fecha;
		this.inscripcion = inscripcion;
		this.oferta = oferta;
		this.persona = persona;
		this.estudiante = estudiante;
		this.codigoUsuario = codigoUsuario;
		this.calendario = calendario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Inscripcion getInscripcion() {
		return inscripcion;
	}

	public void setInscripcion(Inscripcion inscripcion) {
		this.inscripcion = inscripcion;
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public int getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(int codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public Calendario getCalendario() {
		return calendario;
	}

	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}

}
