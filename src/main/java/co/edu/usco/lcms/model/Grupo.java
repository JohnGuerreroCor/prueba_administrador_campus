package co.edu.usco.lcms.model;

public class Grupo {
	private int estado;
	private String id;
	private String nombre;
	private int codigo;

	/**
	 * 
	 */
	public Grupo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param estado
	 * @param id
	 * @param nombre
	 * @param codigo
	 */
	public Grupo(int estado, String id, String nombre, int codigo) {
		super();
		this.estado = estado;
		this.id = id;
		this.nombre = nombre;
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
