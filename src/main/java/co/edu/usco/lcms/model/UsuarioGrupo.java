/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author jankarlos
 *
 */
public class UsuarioGrupo {
	private int estado;
	private Uaa uaa;
	private Grupo grupo;
	private Usuario usuario;
	private int codigo;

	/**
	 * 
	 */
	public UsuarioGrupo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param estado
	 * @param uaa
	 * @param grupo
	 * @param usuario
	 * @param codigo
	 */
	public UsuarioGrupo(int estado, Uaa uaa, Grupo grupo, Usuario usuario, int codigo) {
		super();
		this.estado = estado;
		this.uaa = uaa;
		this.grupo = grupo;
		this.usuario = usuario;
		this.codigo = codigo;
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
	 * @return the uaa
	 */
	public Uaa getUaa() {
		return uaa;
	}

	/**
	 * @param uaa
	 *            the uaa to set
	 */
	public void setUaa(Uaa uaa) {
		this.uaa = uaa;
	}

	/**
	 * @return the grupo
	 */
	public Grupo getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo
	 *            the grupo to set
	 */
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

}
