/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author jankarlos
 *
 */
public class Escala {

	private TipoEscala tipoEscala;
	private int codigo;
	private String nombre;
	private float notaMinima;
	private float notaMaxima;
	/**
	 * 
	 */
	public Escala() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param tipoEscala
	 * @param codigo
	 * @param nombre
	 * @param notaMinima
	 * @param notaMaxima
	 */
	public Escala(TipoEscala tipoEscala, int codigo, String nombre, float notaMinima, float notaMaxima) {
		super();
		this.tipoEscala = tipoEscala;
		this.codigo = codigo;
		this.nombre = nombre;
		this.notaMinima = notaMinima;
		this.notaMaxima = notaMaxima;
	}
	/**
	 * @return the tipoEscala
	 */
	public TipoEscala getTipoEscala() {
		return tipoEscala;
	}
	/**
	 * @param tipoEscala the tipoEscala to set
	 */
	public void setTipoEscala(TipoEscala tipoEscala) {
		this.tipoEscala = tipoEscala;
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
	 * @return the notaMinima
	 */
	public float getNotaMinima() {
		return notaMinima;
	}
	/**
	 * @param notaMinima the notaMinima to set
	 */
	public void setNotaMinima(float notaMinima) {
		this.notaMinima = notaMinima;
	}
	/**
	 * @return the notaMaxima
	 */
	public float getNotaMaxima() {
		return notaMaxima;
	}
	/**
	 * @param notaMaxima the notaMaxima to set
	 */
	public void setNotaMaxima(float notaMaxima) {
		this.notaMaxima = notaMaxima;
	}
	
	
	
}
