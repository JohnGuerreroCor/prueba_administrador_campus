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
public class PreInscripcion {
	private int id;
	private int codigo;
	private int emailVerificado;
	
	private Persona persona;
	private Oferta oferta;
	/**
	 * 
	 */
	public PreInscripcion() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param id
	 * @param codigo
	 * @param emailVerificado
	 * @param persona
	 * @param oferta
	 */
	public PreInscripcion(int id, int codigo, int emailVerificado, Persona persona, Oferta oferta) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.emailVerificado = emailVerificado;
		this.persona = persona;
		this.oferta = oferta;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
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
	 * @param codigo the codigo to set
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the emailVerificado
	 */
	public int getEmailVerificado() {
		return emailVerificado;
	}
	/**
	 * @param emailVerificado the emailVerificado to set
	 */
	public void setEmailVerificado(int emailVerificado) {
		this.emailVerificado = emailVerificado;
	}
	/**
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}
	/**
	 * @param persona the persona to set
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
	 * @param oferta the oferta to set
	 */
	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}
	

	
}
