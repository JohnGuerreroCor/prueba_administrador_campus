/**
 * 
 */
package co.edu.usco.lcms.model.reservaespacios;

/**
 * @author jankarlos
 *
 */
public class Horas {

	private int codigo;
	private String nombre;
	private String hora;
	private String hora24h;
	private boolean ocupado;

	/**
	 * 
	 */
	public Horas() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param nombre
	 * @param hora
	 * @param hora24h
	 * @param ocupado
	 */
	public Horas(int codigo, String nombre, String hora, String hora24h, boolean ocupado) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.hora = hora;
		this.hora24h = hora24h;
		this.ocupado = ocupado;
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
	 * @return the hora
	 */
	public String getHora() {
		return hora;
	}

	/**
	 * @param hora
	 *            the hora to set
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

	/**
	 * @return the hora24h
	 */
	public String getHora24h() {
		return hora24h;
	}

	/**
	 * @param hora24h
	 *            the hora24h to set
	 */
	public void setHora24h(String hora24h) {
		this.hora24h = hora24h;
	}

	/**
	 * @return the ocupado
	 */
	public boolean isOcupado() {
		return ocupado;
	}

	/**
	 * @param ocupado
	 *            the ocupado to set
	 */
	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}

}
