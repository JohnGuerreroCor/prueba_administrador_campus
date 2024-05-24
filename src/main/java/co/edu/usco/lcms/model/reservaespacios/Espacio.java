/**
 * 
 */
package co.edu.usco.lcms.model.reservaespacios;

import co.edu.usco.lcms.model.Uaa;

/**
 * @author jankarlos
 *
 */
public class Espacio {
	
	private long codigo;
	private String nombre;
	private String nombreCorto;
	private Bloque bloque;
	private int capacidad;
	private int area;
	private EspacioTipo espacioTipo;
	private String compartido;
	private String descripcion;
	private Uaa uaa;
	/**
	 * 
	 */
	public Espacio() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param codigo
	 * @param nombre
	 * @param nombreCorto
	 * @param bloque
	 * @param capacidad
	 * @param area
	 * @param espacioTipo
	 * @param compartido
	 * @param descripcion
	 * @param uaa
	 */
	public Espacio(long codigo, String nombre, String nombreCorto, Bloque bloque, int capacidad, int area,
			EspacioTipo espacioTipo, String compartido, String descripcion, Uaa uaa) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.nombreCorto = nombreCorto;
		this.bloque = bloque;
		this.capacidad = capacidad;
		this.area = area;
		this.espacioTipo = espacioTipo;
		this.compartido = compartido;
		this.descripcion = descripcion;
		this.uaa = uaa;
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
	/**
	 * @return the nombreCorto
	 */
	public String getNombreCorto() {
		return nombreCorto;
	}
	/**
	 * @param nombreCorto the nombreCorto to set
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	/**
	 * @return the bloque
	 */
	public Bloque getBloque() {
		return bloque;
	}
	/**
	 * @param bloque the bloque to set
	 */
	public void setBloque(Bloque bloque) {
		this.bloque = bloque;
	}
	/**
	 * @return the capacidad
	 */
	public int getCapacidad() {
		return capacidad;
	}
	/**
	 * @param capacidad the capacidad to set
	 */
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	/**
	 * @return the area
	 */
	public int getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(int area) {
		this.area = area;
	}
	/**
	 * @return the espacioTipo
	 */
	public EspacioTipo getEspacioTipo() {
		return espacioTipo;
	}
	/**
	 * @param espacioTipo the espacioTipo to set
	 */
	public void setEspacioTipo(EspacioTipo espacioTipo) {
		this.espacioTipo = espacioTipo;
	}
	/**
	 * @return the compartido
	 */
	public String getCompartido() {
		return compartido;
	}
	/**
	 * @param compartido the compartido to set
	 */
	public void setCompartido(String compartido) {
		this.compartido = compartido;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the uaa
	 */
	public Uaa getUaa() {
		return uaa;
	}
	/**
	 * @param uaa the uaa to set
	 */
	public void setUaa(Uaa uaa) {
		this.uaa = uaa;
	}
	
	
	
}
