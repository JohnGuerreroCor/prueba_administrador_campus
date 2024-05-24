/**
 * 
 */
package co.edu.usco.lcms.model.reservaespacios;

/**
 * @author jankarlos
 *
 */
public class EspacioTipo {
	
	private long codigo;
	private String nombre;
	/**
	 * 
	 */
	public EspacioTipo() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param codigo
	 * @param nombre
	 */
	public EspacioTipo(long codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}
	/**
	 * @return the codigo
	 */
	public long getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(long codigo) {
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
	
	
}
