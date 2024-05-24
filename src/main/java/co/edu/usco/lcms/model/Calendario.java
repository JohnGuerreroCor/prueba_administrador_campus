package co.edu.usco.lcms.model;

public class Calendario {
	private int codigo;
	private String nombre;
	private Periodo periodo;

	/**
	 * 
	 */
	public Calendario() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param nombre
	 * @param periodo
	 */
	public Calendario(int codigo, String nombre, Periodo periodo) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.periodo = periodo;
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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the periodo
	 */
	public Periodo getPeriodo() {
		return periodo;
	}

	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	

}
