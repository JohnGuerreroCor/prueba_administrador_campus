/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author jankarlos
 *
 */
public class OfertaInformacion {

	private int codigo;
	private int orden;
	private int estado;
	private String titulo;
	private String contenido;
	private OfertaAcademica ofertaAcademica;
	
	
	/**
	 * 
	 */
	public OfertaInformacion() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param codigo
	 * @param orden
	 * @param estado
	 * @param titlo
	 * @param contenido
	 * @param ofertaAcademica
	 */
	public OfertaInformacion(int codigo, int orden, int estado, String titulo, String contenido,
			OfertaAcademica ofertaAcademica) {
		super();
		this.codigo = codigo;
		this.orden = orden;
		this.estado = estado;
		this.titulo = titulo;
		this.contenido = contenido;
		this.ofertaAcademica = ofertaAcademica;
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
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}


	/**
	 * @param estado the estado to set
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}


	/**
	 * @return the titlo
	 */
	public String getTitulo() {
		return titulo;
	}


	/**
	 * @param titlo the titlo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	/**
	 * @return the contenido
	 */
	public String getContenido() {
		return contenido;
	}


	/**
	 * @param contenido the contenido to set
	 */
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}


	/**
	 * @return the ofertaAcademica
	 */
	public OfertaAcademica getOfertaAcademica() {
		return ofertaAcademica;
	}


	/**
	 * @param ofertaAcademica the ofertaAcademica to set
	 */
	public void setOfertaAcademica(OfertaAcademica ofertaAcademica) {
		this.ofertaAcademica = ofertaAcademica;
	}
	
}
