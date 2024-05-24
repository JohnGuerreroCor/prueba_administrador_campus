/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author ANDRES-GPIE
 *
 */
public class OfertaRequisitos {
	private int codigo;
	private String descripcion;
	private String nombreTipo;
	/**
	 * 
	 */
	public OfertaRequisitos() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param codigo
	 * @param descripcion
	 * @param nombreTipo
	 */
	public OfertaRequisitos(int codigo, String descripcion, String nombreTipo) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.nombreTipo = nombreTipo;
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
	 * @return the nombreTipo
	 */
	public String getNombreTipo() {
		return nombreTipo;
	}
	/**
	 * @param nombreTipo the nombreTipo to set
	 */
	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
	}
	
	
}
