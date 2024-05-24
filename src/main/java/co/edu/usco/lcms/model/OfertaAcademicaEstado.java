/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author han
 *
 */
public class OfertaAcademicaEstado {
	
	private int codigo;
	private String nombre;
	
	public OfertaAcademicaEstado(int codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	public OfertaAcademicaEstado() {
		super();
		// TODO Auto-generated constructor stub
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
	
	

}
