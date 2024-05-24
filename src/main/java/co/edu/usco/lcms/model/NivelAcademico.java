package co.edu.usco.lcms.model;

public class NivelAcademico {
	private int codigo;
	private String nombre;
	private int orden;
	private Formalidad formalidad;
	private String snies;


	/**
	 * 
	 */
	public NivelAcademico() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param codigo
	 * @param nombre
	 * @param orden
	 * @param formalidad
	 * @param snies
	 */
	public NivelAcademico(int codigo, String nombre, int orden, Formalidad formalidad, String snies) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.orden = orden;
		this.formalidad = formalidad;
		this.snies = snies;
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
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}


	/**
	 * @param orden the orden to set
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}


	/**
	 * @return the formalidad
	 */
	public Formalidad getFormalidad() {
		return formalidad;
	}


	/**
	 * @param formalidad the formalidad to set
	 */
	public void setFormalidad(Formalidad formalidad) {
		this.formalidad = formalidad;
	}


	/**
	 * @return the snies
	 */
	public String getSnies() {
		return snies;
	}


	/**
	 * @param snies the snies to set
	 */
	public void setSnies(String snies) {
		this.snies = snies;
	}

	
}
