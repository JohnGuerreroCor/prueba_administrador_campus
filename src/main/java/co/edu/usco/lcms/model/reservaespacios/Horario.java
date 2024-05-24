/**
 * 
 */
package co.edu.usco.lcms.model.reservaespacios;

/**
 * @author jankarlos
 *
 */
public class Horario {
	
	private long codigo;
	private String dia;
	private String nombre;
	private Horas hora;
	/**
	 * 
	 */
	public Horario() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param codigo
	 * @param dia
	 * @param nombre
	 * @param hora
	 */
	public Horario(long codigo, String dia, String nombre, Horas hora) {
		super();
		this.codigo = codigo;
		this.dia = dia;
		this.nombre = nombre;
		this.hora = hora;
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
	 * @return the dia
	 */
	public String getDia() {
		return dia;
	}
	/**
	 * @param dia the dia to set
	 */
	public void setDia(String dia) {
		this.dia = dia;
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
	 * @return the hora
	 */
	public Horas getHora() {
		return hora;
	}
	/**
	 * @param hora the hora to set
	 */
	public void setHora(Horas hora) {
		this.hora = hora;
	}
	
	
}
