/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author ANDRES-GPIE
 *
 */
public class PlanEstudiante {
	private int id;
	
	private PlanAcademico planAcademico;
	private Inscripcion inscripcion;
	/**
	 * 
	 */
	public PlanEstudiante() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param id
	 * @param planAcademico
	 * @param inscripcion
	 */
	public PlanEstudiante(int id, PlanAcademico planAcademico, Inscripcion inscripcion) {
		super();
		this.id = id;
		this.planAcademico = planAcademico;
		this.inscripcion = inscripcion;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the planAcademico
	 */
	public PlanAcademico getPlanAcademico() {
		return planAcademico;
	}
	/**
	 * @param planAcademico the planAcademico to set
	 */
	public void setPlanAcademico(PlanAcademico planAcademico) {
		this.planAcademico = planAcademico;
	}
	/**
	 * @return the inscripcion
	 */
	public Inscripcion getInscripcion() {
		return inscripcion;
	}
	/**
	 * @param inscripcion the inscripcion to set
	 */
	public void setInscripcion(Inscripcion inscripcion) {
		this.inscripcion = inscripcion;
	}

	
	
}