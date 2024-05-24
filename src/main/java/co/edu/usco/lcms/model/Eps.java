/**
 * 
 */
package co.edu.usco.lcms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author ANDRES-GPIE
 *
 */
@JsonIgnoreProperties
public class Eps {
	private int codigo;
	private String nombre;
	private EpsTipoAfiliacion tipoEps;
	/**
	 * 
	 */
	public Eps() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param codigo
	 * @param nombre
	 * @param tipoEps
	 */
	public Eps(int codigo, String nombre, EpsTipoAfiliacion tipoEps) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.tipoEps = tipoEps;
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
	 * @return the tipoEps
	 */
	public EpsTipoAfiliacion getTipoEps() {
		return tipoEps;
	}
	/**
	 * @param tipoEps the tipoEps to set
	 */
	public void setTipoEps(EpsTipoAfiliacion tipoEps) {
		this.tipoEps = tipoEps;
	}

	
	
}
