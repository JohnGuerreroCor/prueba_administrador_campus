/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author jankarlos
 *
 */
public class PlanAcademicoAsignatura {

	private int codigo;
	private int semestre;
	private int creditos;
	private int intensidad;
	private Nucleo nucleo;
	private String activo;
	private float notaMinima;
	private int requisito;// paa_obligatoria
	private int pertenece;
	private int electiva;// chequeo requisito lectiva
	private int programable;
	private int semanasXSemestre;
	private int trabajoIndependiente;
	private int codigoAsignatura;
	private String nombre;
	private Componente componente;
	private Asignatura asignatura;
	private PlanAcademico planAcademico;

	/**
	 * 
	 */
	public PlanAcademicoAsignatura() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param semestre
	 * @param creditos
	 * @param intensidad
	 * @param nucleo
	 * @param activo
	 * @param notaMinima
	 * @param requisito
	 * @param pertenece
	 * @param electiva
	 * @param programable
	 * @param semanasXSemestre
	 * @param trabajoIndependiente
	 * @param codigoAsignatura
	 * @param nombre
	 * @param componente
	 * @param asignatura
	 * @param planAcademico
	 */
	public PlanAcademicoAsignatura(int codigo, int semestre, int creditos, int intensidad, Nucleo nucleo, String activo,
			float notaMinima, int requisito, int pertenece, int electiva, int programable, int semanasXSemestre,
			int trabajoIndependiente, int codigoAsignatura, String nombre, Componente componente, Asignatura asignatura,
			PlanAcademico planAcademico) {
		super();
		this.codigo = codigo;
		this.semestre = semestre;
		this.creditos = creditos;
		this.intensidad = intensidad;
		this.nucleo = nucleo;
		this.activo = activo;
		this.notaMinima = notaMinima;
		this.requisito = requisito;
		this.pertenece = pertenece;
		this.electiva = electiva;
		this.programable = programable;
		this.semanasXSemestre = semanasXSemestre;
		this.trabajoIndependiente = trabajoIndependiente;
		this.codigoAsignatura = codigoAsignatura;
		this.nombre = nombre;
		this.componente = componente;
		this.asignatura = asignatura;
		this.planAcademico = planAcademico;
	}

	/**
	 * @return the codigo
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the semestre
	 */
	public int getSemestre() {
		return semestre;
	}

	/**
	 * @param semestre
	 *            the semestre to set
	 */
	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	/**
	 * @return the creditos
	 */
	public int getCreditos() {
		return creditos;
	}

	/**
	 * @param creditos
	 *            the creditos to set
	 */
	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	/**
	 * @return the intensidad
	 */
	public int getIntensidad() {
		return intensidad;
	}

	/**
	 * @param intensidad
	 *            the intensidad to set
	 */
	public void setIntensidad(int intensidad) {
		this.intensidad = intensidad;
	}

	/**
	 * @return the nucleo
	 */
	public Nucleo getNucleo() {
		return nucleo;
	}

	/**
	 * @param nucleo
	 *            the nucleo to set
	 */
	public void setNucleo(Nucleo nucleo) {
		this.nucleo = nucleo;
	}

	/**
	 * @return the activo
	 */
	public String getActivo() {
		return activo;
	}

	/**
	 * @param activo
	 *            the activo to set
	 */
	public void setActivo(String activo) {
		this.activo = activo;
	}

	/**
	 * @return the notaMinima
	 */
	public float getNotaMinima() {
		return notaMinima;
	}

	/**
	 * @param notaMinima
	 *            the notaMinima to set
	 */
	public void setNotaMinima(float notaMinima) {
		this.notaMinima = notaMinima;
	}

	/**
	 * @return the requisito
	 */
	public int getRequisito() {
		return requisito;
	}

	/**
	 * @param requisito
	 *            the requisito to set
	 */
	public void setRequisito(int requisito) {
		this.requisito = requisito;
	}

	/**
	 * @return the pertenece
	 */
	public int getPertenece() {
		return pertenece;
	}

	/**
	 * @param pertenece
	 *            the pertenece to set
	 */
	public void setPertenece(int pertenece) {
		this.pertenece = pertenece;
	}

	/**
	 * @return the electiva
	 */
	public int getElectiva() {
		return electiva;
	}

	/**
	 * @param electiva
	 *            the electiva to set
	 */
	public void setElectiva(int electiva) {
		this.electiva = electiva;
	}

	/**
	 * @return the programable
	 */
	public int getProgramable() {
		return programable;
	}

	/**
	 * @param programable
	 *            the programable to set
	 */
	public void setProgramable(int programable) {
		this.programable = programable;
	}

	/**
	 * @return the semanasXSemestre
	 */
	public int getSemanasXSemestre() {
		return semanasXSemestre;
	}

	/**
	 * @param semanasXSemestre
	 *            the semanasXSemestre to set
	 */
	public void setSemanasXSemestre(int semanasXSemestre) {
		this.semanasXSemestre = semanasXSemestre;
	}

	/**
	 * @return the trabajoIndependiente
	 */
	public int getTrabajoIndependiente() {
		return trabajoIndependiente;
	}

	/**
	 * @param trabajoIndependiente
	 *            the trabajoIndependiente to set
	 */
	public void setTrabajoIndependiente(int trabajoIndependiente) {
		this.trabajoIndependiente = trabajoIndependiente;
	}

	/**
	 * @return the codigoAsignatura
	 */
	public int getCodigoAsignatura() {
		return codigoAsignatura;
	}

	/**
	 * @param codigoAsignatura
	 *            the codigoAsignatura to set
	 */
	public void setCodigoAsignatura(int codigoAsignatura) {
		this.codigoAsignatura = codigoAsignatura;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the componente
	 */
	public Componente getComponente() {
		return componente;
	}

	/**
	 * @param componente
	 *            the componente to set
	 */
	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	/**
	 * @return the asignatura
	 */
	public Asignatura getAsignatura() {
		return asignatura;
	}

	/**
	 * @param asignatura
	 *            the asignatura to set
	 */
	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	/**
	 * @return the planAcademico
	 */
	public PlanAcademico getPlanAcademico() {
		return planAcademico;
	}

	/**
	 * @param planAcademico
	 *            the planAcademico to set
	 */
	public void setPlanAcademico(PlanAcademico planAcademico) {
		this.planAcademico = planAcademico;
	}

}
