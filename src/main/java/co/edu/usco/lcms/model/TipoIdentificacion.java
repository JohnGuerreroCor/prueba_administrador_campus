/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author jankarlos
 *
 */
public class TipoIdentificacion {

	private int codigo;
	private String nombre;
	private String nombreCorto;
	public TipoIdentificacion() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TipoIdentificacion(int codigo, String nombre, String nombreCorto) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.nombreCorto = nombreCorto;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombreCorto() {
		return nombreCorto;
	}
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	
}
