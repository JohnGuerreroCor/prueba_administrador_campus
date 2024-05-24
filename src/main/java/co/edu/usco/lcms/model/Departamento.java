/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author jankarlos
 *
 */
public class Departamento {
	private int codigo;
	private String nombre;
	private int pais;
	private String dane;

	/**
	 * 
	 */
	public Departamento() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param nombre
	 * @param pais
	 * @param dane
	 */
	public Departamento(int codigo, String nombre, int pais, String dane) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.pais = pais;
		this.dane = dane;
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
	 * @return the pais
	 */
	public int getPais() {
		return pais;
	}

	/**
	 * @param pais
	 *            the pais to set
	 */
	public void setPais(int pais) {
		this.pais = pais;
	}

	/**
	 * @return the dane
	 */
	public String getDane() {
		return dane;
	}

	/**
	 * @param dane
	 *            the dane to set
	 */
	public void setDane(String dane) {
		this.dane = dane;
	}

}
