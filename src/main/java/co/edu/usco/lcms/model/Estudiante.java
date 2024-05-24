/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author ANDRES-GPIE
 *
 */
public class Estudiante {
	private int id;

	private String codigo;
	private Persona persona;
	private Admision admision;
	private Inscripcion inscripcion;
	private Programa programa;
	private UsuarioEstudiante usuarioestudiante;

	/**
	 * 
	 */
	public Estudiante() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param codigo
	 * @param persona
	 * @param admision
	 * @param inscripcion
	 * @param programa
	 * @param usuarioestudiante
	 */
	public Estudiante(int id, String codigo, Persona persona, Admision admision, Inscripcion inscripcion,
			Programa programa, UsuarioEstudiante usuarioestudiante) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.persona = persona;
		this.admision = admision;
		this.inscripcion = inscripcion;
		this.programa = programa;
		this.usuarioestudiante = usuarioestudiante;
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
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(String codigo) {
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
	 * @return the admision
	 */
	public Admision getAdmision() {
		return admision;
	}

	/**
	 * @param admision
	 *            the admision to set
	 */
	public void setAdmision(Admision admision) {
		this.admision = admision;
	}

	/**
	 * @return the inscripcion
	 */
	public Inscripcion getInscripcion() {
		return inscripcion;
	}

	/**
	 * @param inscripcion
	 *            the inscripcion to set
	 */
	public void setInscripcion(Inscripcion inscripcion) {
		this.inscripcion = inscripcion;
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
	 * @return the usuarioestudiante
	 */
	public UsuarioEstudiante getUsuarioestudiante() {
		return usuarioestudiante;
	}

	/**
	 * @param usuarioestudiante
	 *            the usuarioestudiante to set
	 */
	public void setUsuarioestudiante(UsuarioEstudiante usuarioestudiante) {
		this.usuarioestudiante = usuarioestudiante;
	}

}
