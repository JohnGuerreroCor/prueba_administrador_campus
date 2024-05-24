/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author jankarlos
 *
 */
public class Asignatura {
	private int codigo;
	private String nombre;
	private String nombreCorto;
	private String nombreImpresion;
	private int publicar;
	private int creditos;
	private int creditosTeoricos;
	private int creditosPractico;
	private int trabajoPresencial;
	private int trabajoIndependiente;
	private int estado;
	private int semanasXSemestre;
	private String extraMuro;
	private String pendienteNbc;
	
	private Componente componente;
	private Nucleo nucleo;
	private Nbc nbc;
	private Caracter caracter;
	private Uaa uaa;
	private UaaTipo uaaTipo;
	/**
	 * 
	 */
	public Asignatura() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param codigo
	 * @param nombre
	 * @param nombreCorto
	 * @param nombreImpresion
	 * @param publicar
	 * @param creditos
	 * @param creditosTeoricos
	 * @param creditosPractico
	 * @param trabajoPresencial
	 * @param trabajoIndependiente
	 * @param estado
	 * @param semanasXSemestre
	 * @param extraMuro
	 * @param pendienteNbc
	 * @param componente
	 * @param nucleo
	 * @param nbc
	 * @param caracter
	 * @param uaa
	 * @param uaaTipo
	 */
	public Asignatura(int codigo, String nombre, String nombreCorto, String nombreImpresion, int publicar, int creditos,
			int creditosTeoricos, int creditosPractico, int trabajoPresencial, int trabajoIndependiente, int estado,
			int semanasXSemestre, String extraMuro, String pendienteNbc, Componente componente, Nucleo nucleo, Nbc nbc,
			Caracter caracter, Uaa uaa, UaaTipo uaaTipo) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.nombreCorto = nombreCorto;
		this.nombreImpresion = nombreImpresion;
		this.publicar = publicar;
		this.creditos = creditos;
		this.creditosTeoricos = creditosTeoricos;
		this.creditosPractico = creditosPractico;
		this.trabajoPresencial = trabajoPresencial;
		this.trabajoIndependiente = trabajoIndependiente;
		this.estado = estado;
		this.semanasXSemestre = semanasXSemestre;
		this.extraMuro = extraMuro;
		this.pendienteNbc = pendienteNbc;
		this.componente = componente;
		this.nucleo = nucleo;
		this.nbc = nbc;
		this.caracter = caracter;
		this.uaa = uaa;
		this.uaaTipo = uaaTipo;
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
	 * @return the nombreCorto
	 */
	public String getNombreCorto() {
		return nombreCorto;
	}
	/**
	 * @param nombreCorto the nombreCorto to set
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	/**
	 * @return the nombreImpresion
	 */
	public String getNombreImpresion() {
		return nombreImpresion;
	}
	/**
	 * @param nombreImpresion the nombreImpresion to set
	 */
	public void setNombreImpresion(String nombreImpresion) {
		this.nombreImpresion = nombreImpresion;
	}
	/**
	 * @return the publicar
	 */
	public int getPublicar() {
		return publicar;
	}
	/**
	 * @param publicar the publicar to set
	 */
	public void setPublicar(int publicar) {
		this.publicar = publicar;
	}
	/**
	 * @return the creditos
	 */
	public int getCreditos() {
		return creditos;
	}
	/**
	 * @param creditos the creditos to set
	 */
	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}
	/**
	 * @return the creditosTeoricos
	 */
	public int getCreditosTeoricos() {
		return creditosTeoricos;
	}
	/**
	 * @param creditosTeoricos the creditosTeoricos to set
	 */
	public void setCreditosTeoricos(int creditosTeoricos) {
		this.creditosTeoricos = creditosTeoricos;
	}
	/**
	 * @return the creditosPractico
	 */
	public int getCreditosPractico() {
		return creditosPractico;
	}
	/**
	 * @param creditosPractico the creditosPractico to set
	 */
	public void setCreditosPractico(int creditosPractico) {
		this.creditosPractico = creditosPractico;
	}
	/**
	 * @return the trabajoPresencial
	 */
	public int getTrabajoPresencial() {
		return trabajoPresencial;
	}
	/**
	 * @param trabajoPresencial the trabajoPresencial to set
	 */
	public void setTrabajoPresencial(int trabajoPresencial) {
		this.trabajoPresencial = trabajoPresencial;
	}
	/**
	 * @return the trabajoIndependiente
	 */
	public int getTrabajoIndependiente() {
		return trabajoIndependiente;
	}
	/**
	 * @param trabajoIndependiente the trabajoIndependiente to set
	 */
	public void setTrabajoIndependiente(int trabajoIndependiente) {
		this.trabajoIndependiente = trabajoIndependiente;
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
	 * @return the semanasXSemestre
	 */
	public int getSemanasXSemestre() {
		return semanasXSemestre;
	}
	/**
	 * @param semanasXSemestre the semanasXSemestre to set
	 */
	public void setSemanasXSemestre(int semanasXSemestre) {
		this.semanasXSemestre = semanasXSemestre;
	}
	/**
	 * @return the extraMuro
	 */
	public String getExtraMuro() {
		return extraMuro;
	}
	/**
	 * @param extraMuro the extraMuro to set
	 */
	public void setExtraMuro(String extraMuro) {
		this.extraMuro = extraMuro;
	}
	/**
	 * @return the pendienteNbc
	 */
	public String getPendienteNbc() {
		return pendienteNbc;
	}
	/**
	 * @param pendienteNbc the pendienteNbc to set
	 */
	public void setPendienteNbc(String pendienteNbc) {
		this.pendienteNbc = pendienteNbc;
	}
	/**
	 * @return the componente
	 */
	public Componente getComponente() {
		return componente;
	}
	/**
	 * @param componente the componente to set
	 */
	public void setComponente(Componente componente) {
		this.componente = componente;
	}
	/**
	 * @return the nucleo
	 */
	public Nucleo getNucleo() {
		return nucleo;
	}
	/**
	 * @param nucleo the nucleo to set
	 */
	public void setNucleo(Nucleo nucleo) {
		this.nucleo = nucleo;
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
	 * @return the caracter
	 */
	public Caracter getCaracter() {
		return caracter;
	}
	/**
	 * @param caracter the caracter to set
	 */
	public void setCaracter(Caracter caracter) {
		this.caracter = caracter;
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
	 * @return the uaaTipo
	 */
	public UaaTipo getUaaTipo() {
		return uaaTipo;
	}
	/**
	 * @param uaaTipo the uaaTipo to set
	 */
	public void setUaaTipo(UaaTipo uaaTipo) {
		this.uaaTipo = uaaTipo;
	}
	
	
	
	
}
