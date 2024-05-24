/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author jankarlos
 *
 */
public class EstudioAnterior {

	private int codigo;
	private NivelAcademico nivelAcademico;
	private String titulo;
	private String institucion;
	private String anio;
	private int persona;
	private int tercero;

	public EstudioAnterior() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EstudioAnterior(int codigo, NivelAcademico nivelAcademico, String titulo, String institucion, String anio,
			int persona, int tercero) {
		super();
		this.codigo = codigo;
		this.nivelAcademico = nivelAcademico;
		this.titulo = titulo;
		this.institucion = institucion;
		this.anio = anio;
		this.persona = persona;
		this.tercero = tercero;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public NivelAcademico getNivelAcademico() {
		return nivelAcademico;
	}

	public void setNivelAcademico(NivelAcademico nivelAcademico) {
		this.nivelAcademico = nivelAcademico;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public int getPersona() {
		return persona;
	}

	public void setPersona(int persona) {
		this.persona = persona;
	}

	public int getTercero() {
		return tercero;
	}

	public void setTercero(int tercero) {
		this.tercero = tercero;
	}

}
