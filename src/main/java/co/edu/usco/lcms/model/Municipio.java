/**
 * 
 */
package co.edu.usco.lcms.model;

import java.io.Reader;

/**
 * @author jankarlos
 *
 */
public class Municipio {
	private int codigo;
	private String nombre;
	private String snies;
	private int departamento;

	/**
	 * 
	 */
	public Municipio() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param nombre
	 * @param snies
	 * @param departamento
	 */
	public Municipio(int codigo, String nombre, String snies, int departamento) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.snies = snies;
		this.departamento = departamento;
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
	 * @return the snies
	 */
	public String getSnies() {
		return snies;
	}

	/**
	 * @param snies
	 *            the snies to set
	 */
	public void setSnies(String snies) {
		this.snies = snies;
	}

	/**
	 * @return the departamento
	 */
	public int getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento
	 *            the departamento to set
	 */
	public void setDepartamento(int departamento) {
		this.departamento = departamento;
	}

}
