package co.edu.usco.lcms.model.reservaespacios;

public class Cargo {

	private int codigo;
	private int nombre;

	/**
	 * 
	 */
	public Cargo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param nombre
	 */
	public Cargo(int codigo, int nombre) {
		super();
		this.codigo = codigo;
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

	/**
	 * @return the nombre
	 */
	public int getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(int nombre) {
		this.nombre = nombre;
	}

}
