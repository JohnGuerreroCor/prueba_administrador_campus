/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author jankarlos
 *
 */
public class Usuario {
	
	private int estado;
	private String password;
	private String login;
	private Persona persona;
	private int codigo;
	private UsuarioGrupo usuarioGrupo;
	/**
	 * 
	 */
	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param estado
	 * @param password
	 * @param login
	 * @param persona
	 * @param codigo
	 * @param usuarioGrupo
	 */
	public Usuario(int estado, String password, String login, Persona persona, int codigo, UsuarioGrupo usuarioGrupo) {
		super();
		this.estado = estado;
		this.password = password;
		this.login = login;
		this.persona = persona;
		this.codigo = codigo;
		this.usuarioGrupo = usuarioGrupo;
	}
	/**
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
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
	 * @return the usuarioGrupo
	 */
	public UsuarioGrupo getUsuarioGrupo() {
		return usuarioGrupo;
	}
	/**
	 * @param usuarioGrupo the usuarioGrupo to set
	 */
	public void setUsuarioGrupo(UsuarioGrupo usuarioGrupo) {
		this.usuarioGrupo = usuarioGrupo;
	}
	
}
