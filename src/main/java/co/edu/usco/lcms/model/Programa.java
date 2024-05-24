/**
 * 
 */
package co.edu.usco.lcms.model;

import java.util.Date;

/**
 * 
 * @author Jankarlos Diaz Vieda
 *
 */
public class Programa {

	private int codigo;
	private String nombre;
	private String nombreUaa;
	private int codigoUaa;
	private String programa;
	private String registro_icfes;
	private String registro_snies;
	private String titulo_otorgado;
	private Date creacion;
	private String horario;
	private String propio;
	private String calendario;
	private int tipoAdmision;
	private String descripcion;
	private String estado;
	
	private Sede sede;
	private Uaa uaa;
	private NivelAcademico nivelAcademico;
	private Jornada jornada;
	private Resolucion resolucion;
	private Modalidad modalidad;
	private Convenio convenio;
	private Nbc nbc;
	private ProgramaEstado programaEstado;

	public Programa() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param nombre
	 * @param nombreUaa
	 * @param codigoUaa
	 * @param programa
	 * @param registro_icfes
	 * @param registro_snies
	 * @param titulo_otorgado
	 * @param creacion
	 * @param horario
	 * @param propio
	 * @param calendario
	 * @param tipoAdmision
	 * @param descripcion
	 * @param estado
	 * @param sede
	 * @param uaa
	 * @param nivelAcademico
	 * @param jornada
	 * @param resolucion
	 * @param modalidad
	 * @param convenio
	 * @param nbc
	 * @param programaEstado
	 */
	public Programa(int codigo, String nombre, String nombreUaa, int codigoUaa, String programa, String registro_icfes,
			String registro_snies, String titulo_otorgado, Date creacion, String horario, String propio,
			String calendario, int tipoAdmision, String descripcion, String estado, Sede sede, Uaa uaa,
			NivelAcademico nivelAcademico, Jornada jornada, Resolucion resolucion, Modalidad modalidad,
			Convenio convenio, Nbc nbc, ProgramaEstado programaEstado) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.nombreUaa = nombreUaa;
		this.codigoUaa = codigoUaa;
		this.programa = programa;
		this.registro_icfes = registro_icfes;
		this.registro_snies = registro_snies;
		this.titulo_otorgado = titulo_otorgado;
		this.creacion = creacion;
		this.horario = horario;
		this.propio = propio;
		this.calendario = calendario;
		this.tipoAdmision = tipoAdmision;
		this.descripcion = descripcion;
		this.estado = estado;
		this.sede = sede;
		this.uaa = uaa;
		this.nivelAcademico = nivelAcademico;
		this.jornada = jornada;
		this.resolucion = resolucion;
		this.modalidad = modalidad;
		this.convenio = convenio;
		this.nbc = nbc;
		this.programaEstado = programaEstado;
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
	 * @return the nombreUaa
	 */
	public String getNombreUaa() {
		return nombreUaa;
	}

	/**
	 * @param nombreUaa the nombreUaa to set
	 */
	public void setNombreUaa(String nombreUaa) {
		this.nombreUaa = nombreUaa;
	}

	/**
	 * @return the codigoUaa
	 */
	public int getCodigoUaa() {
		return codigoUaa;
	}

	/**
	 * @param codigoUaa the codigoUaa to set
	 */
	public void setCodigoUaa(int codigoUaa) {
		this.codigoUaa = codigoUaa;
	}

	/**
	 * @return the programa
	 */
	public String getPrograma() {
		return programa;
	}

	/**
	 * @param programa the programa to set
	 */
	public void setPrograma(String programa) {
		this.programa = programa;
	}

	/**
	 * @return the registro_icfes
	 */
	public String getRegistro_icfes() {
		return registro_icfes;
	}

	/**
	 * @param registro_icfes the registro_icfes to set
	 */
	public void setRegistro_icfes(String registro_icfes) {
		this.registro_icfes = registro_icfes;
	}

	/**
	 * @return the registro_snies
	 */
	public String getRegistro_snies() {
		return registro_snies;
	}

	/**
	 * @param registro_snies the registro_snies to set
	 */
	public void setRegistro_snies(String registro_snies) {
		this.registro_snies = registro_snies;
	}

	/**
	 * @return the titulo_otorgado
	 */
	public String getTitulo_otorgado() {
		return titulo_otorgado;
	}

	/**
	 * @param titulo_otorgado the titulo_otorgado to set
	 */
	public void setTitulo_otorgado(String titulo_otorgado) {
		this.titulo_otorgado = titulo_otorgado;
	}

	/**
	 * @return the creacion
	 */
	public Date getCreacion() {
		return creacion;
	}

	/**
	 * @param creacion the creacion to set
	 */
	public void setCreacion(Date creacion) {
		this.creacion = creacion;
	}

	/**
	 * @return the horario
	 */
	public String getHorario() {
		return horario;
	}

	/**
	 * @param horario the horario to set
	 */
	public void setHorario(String horario) {
		this.horario = horario;
	}

	/**
	 * @return the propio
	 */
	public String getPropio() {
		return propio;
	}

	/**
	 * @param propio the propio to set
	 */
	public void setPropio(String propio) {
		this.propio = propio;
	}

	/**
	 * @return the calendario
	 */
	public String getCalendario() {
		return calendario;
	}

	/**
	 * @param calendario the calendario to set
	 */
	public void setCalendario(String calendario) {
		this.calendario = calendario;
	}

	/**
	 * @return the tipoAdmision
	 */
	public int getTipoAdmision() {
		return tipoAdmision;
	}

	/**
	 * @param tipoAdmision the tipoAdmision to set
	 */
	public void setTipoAdmision(int tipoAdmision) {
		this.tipoAdmision = tipoAdmision;
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
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the sede
	 */
	public Sede getSede() {
		return sede;
	}

	/**
	 * @param sede the sede to set
	 */
	public void setSede(Sede sede) {
		this.sede = sede;
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

	/**
	 * @return the nivelAcademico
	 */
	public NivelAcademico getNivelAcademico() {
		return nivelAcademico;
	}

	/**
	 * @param nivelAcademico the nivelAcademico to set
	 */
	public void setNivelAcademico(NivelAcademico nivelAcademico) {
		this.nivelAcademico = nivelAcademico;
	}

	/**
	 * @return the jornada
	 */
	public Jornada getJornada() {
		return jornada;
	}

	/**
	 * @param jornada the jornada to set
	 */
	public void setJornada(Jornada jornada) {
		this.jornada = jornada;
	}

	/**
	 * @return the resolucion
	 */
	public Resolucion getResolucion() {
		return resolucion;
	}

	/**
	 * @param resolucion the resolucion to set
	 */
	public void setResolucion(Resolucion resolucion) {
		this.resolucion = resolucion;
	}

	/**
	 * @return the modalidad
	 */
	public Modalidad getModalidad() {
		return modalidad;
	}

	/**
	 * @param modalidad the modalidad to set
	 */
	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}

	/**
	 * @return the convenio
	 */
	public Convenio getConvenio() {
		return convenio;
	}

	/**
	 * @param convenio the convenio to set
	 */
	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}

	/**
	 * @return the nbc
	 */
	public Nbc getNbc() {
		return nbc;
	}

	/**
	 * @param nbc the nbc to set
	 */
	public void setNbc(Nbc nbc) {
		this.nbc = nbc;
	}

	/**
	 * @return the programaEstado
	 */
	public ProgramaEstado getProgramaEstado() {
		return programaEstado;
	}

	/**
	 * @param programaEstado the programaEstado to set
	 */
	public void setProgramaEstado(ProgramaEstado programaEstado) {
		this.programaEstado = programaEstado;
	}

	

}
