/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author jankarlos
 *
 */
public class OfertaRequisito {

	private int codigo;
	private String descripcion;
	private OfertaAcademica ofertaAcademica;
	private OfertaRequisitoTipo ofertaRequisitoTipo;

	/**
	 * 
	 */
	public OfertaRequisito() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param descripcion
	 * @param ofertaAcademica
	 * @param ofertaRequisitoTipo
	 */
	public OfertaRequisito(int codigo, String descripcion, OfertaAcademica ofertaAcademica,
			OfertaRequisitoTipo ofertaRequisitoTipo) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.ofertaAcademica = ofertaAcademica;
		this.ofertaRequisitoTipo = ofertaRequisitoTipo;
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

	/**
	 * @return the ofertaRequisitoTipo
	 */
	public OfertaRequisitoTipo getOfertaRequisitoTipo() {
		return ofertaRequisitoTipo;
	}

	/**
	 * @param ofertaRequisitoTipo the ofertaRequisitoTipo to set
	 */
	public void setOfertaRequisitoTipo(OfertaRequisitoTipo ofertaRequisitoTipo) {
		this.ofertaRequisitoTipo = ofertaRequisitoTipo;
	}

	
}
