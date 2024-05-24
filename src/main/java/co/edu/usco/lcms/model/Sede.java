/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author jankarlos
 *
 */
public class Sede {
	private int codigo;
	private String nombre;
	private int codigoMunicipio;
	private String nombreMunicipio;
	private int codigoDepartamento;
	private String nombreDepartamento;
	private String estado;

	/**
	 * 
	 */
	public Sede() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param nombre
	 * @param codigoMunicipio
	 * @param nombreMunicipio
	 * @param codigoDepartamento
	 * @param nombreDepartamento
	 * @param estado
	 */
	public Sede(int codigo, String nombre, int codigoMunicipio, String nombreMunicipio, int codigoDepartamento,
			String nombreDepartamento, String estado) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.codigoMunicipio = codigoMunicipio;
		this.nombreMunicipio = nombreMunicipio;
		this.codigoDepartamento = codigoDepartamento;
		this.nombreDepartamento = nombreDepartamento;
		this.estado = estado;
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
	 * @return the codigoMunicipio
	 */
	public int getCodigoMunicipio() {
		return codigoMunicipio;
	}

	/**
	 * @param codigoMunicipio
	 *            the codigoMunicipio to set
	 */
	public void setCodigoMunicipio(int codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

	/**
	 * @return the nombreMunicipio
	 */
	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	/**
	 * @param nombreMunicipio
	 *            the nombreMunicipio to set
	 */
	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}

	/**
	 * @return the codigoDepartamento
	 */
	public int getCodigoDepartamento() {
		return codigoDepartamento;
	}

	/**
	 * @param codigoDepartamento
	 *            the codigoDepartamento to set
	 */
	public void setCodigoDepartamento(int codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}

	/**
	 * @return the nombreDepartamento
	 */
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	/**
	 * @param nombreDepartamento
	 *            the nombreDepartamento to set
	 */
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

}
