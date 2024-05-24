/**
 * 
 */
package co.edu.usco.lcms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author ANDRES-GPIE
 *
 */
@JsonIgnoreProperties
public class Inscripcion {
	private int id;
	private int codigo;

	private Persona persona;
	private Oferta oferta;
	private Estudiante estudiante;
	private InscripcionPrograma inscripcionPrograma;
	private int codigoUsuario;

	public Inscripcion() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param codigo
	 * @param persona
	 * @param oferta
	 * @param estudiante
	 * @param inscripcionPrograma
	 * @param codigoUsuario
	 */
	public Inscripcion(int id, int codigo, Persona persona, Oferta oferta, Estudiante estudiante,
			InscripcionPrograma inscripcionPrograma, int codigoUsuario) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.persona = persona;
		this.oferta = oferta;
		this.estudiante = estudiante;
		this.inscripcionPrograma = inscripcionPrograma;
		this.codigoUsuario = codigoUsuario;
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
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * @param persona
	 *            the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * @return the oferta
	 */
	public Oferta getOferta() {
		return oferta;
	}

	/**
	 * @param oferta
	 *            the oferta to set
	 */
	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	/**
	 * @return the estudiante
	 */
	public Estudiante getEstudiante() {
		return estudiante;
	}

	/**
	 * @param estudiante
	 *            the estudiante to set
	 */
	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	/**
	 * @return the inscripcionPrograma
	 */
	public InscripcionPrograma getInscripcionPrograma() {
		return inscripcionPrograma;
	}

	/**
	 * @param inscripcionPrograma
	 *            the inscripcionPrograma to set
	 */
	public void setInscripcionPrograma(InscripcionPrograma inscripcionPrograma) {
		this.inscripcionPrograma = inscripcionPrograma;
	}

	/**
	 * @return the codigoUsuario
	 */
	public int getCodigoUsuario() {
		return codigoUsuario;
	}

	/**
	 * @param codigoUsuario
	 *            the codigoUsuario to set
	 */
	public void setCodigoUsuario(int codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

}
