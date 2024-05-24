/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author jankarlos
 *
 */
public class Uaa {
	private int codigo;
	private String nombre;
	private int dependencia;
	private String nombreCorto;
	private String nombreImpresion;
	private String email;
	private String emailPqr;
	private String pagina;
	private String telefono;
	private String fax;
	private String direccion;
	private String centroCostos;
	private String acronimo;
	private int estado;
	private int ventanilla;
	private String codigoRetencion;
	private String nombreDependencia;
	private int codigoTipoUaaDep;

	private UaaTipo uaaTipo;
	private Municipio municipio;
	private Departamento departamento;
	private Sede sede;

	/**
	 * 
	 */
	public Uaa() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param codigo
	 * @param nombre
	 * @param dependencia
	 * @param nombreCorto
	 * @param nombreImpresion
	 * @param email
	 * @param emailPqr
	 * @param pagina
	 * @param telefono
	 * @param fax
	 * @param direccion
	 * @param centroCostos
	 * @param acronimo
	 * @param estado
	 * @param ventanilla
	 * @param codigoRetencion
	 * @param nombreDependencia
	 * @param codigoTipoUaaDep
	 * @param uaaTipo
	 * @param municipio
	 * @param departamento
	 * @param sede
	 */
	public Uaa(int codigo, String nombre, int dependencia, String nombreCorto, String nombreImpresion, String email,
			String emailPqr, String pagina, String telefono, String fax, String direccion, String centroCostos,
			String acronimo, int estado, int ventanilla, String codigoRetencion, String nombreDependencia,
			int codigoTipoUaaDep, UaaTipo uaaTipo, Municipio municipio, Departamento departamento, Sede sede) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.dependencia = dependencia;
		this.nombreCorto = nombreCorto;
		this.nombreImpresion = nombreImpresion;
		this.email = email;
		this.emailPqr = emailPqr;
		this.pagina = pagina;
		this.telefono = telefono;
		this.fax = fax;
		this.direccion = direccion;
		this.centroCostos = centroCostos;
		this.acronimo = acronimo;
		this.estado = estado;
		this.ventanilla = ventanilla;
		this.codigoRetencion = codigoRetencion;
		this.nombreDependencia = nombreDependencia;
		this.codigoTipoUaaDep = codigoTipoUaaDep;
		this.uaaTipo = uaaTipo;
		this.municipio = municipio;
		this.departamento = departamento;
		this.sede = sede;
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
	 * @return the dependencia
	 */
	public int getDependencia() {
		return dependencia;
	}

	/**
	 * @param dependencia
	 *            the dependencia to set
	 */
	public void setDependencia(int dependencia) {
		this.dependencia = dependencia;
	}

	/**
	 * @return the nombreCorto
	 */
	public String getNombreCorto() {
		return nombreCorto;
	}

	/**
	 * @param nombreCorto
	 *            the nombreCorto to set
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
	 * @param nombreImpresion
	 *            the nombreImpresion to set
	 */
	public void setNombreImpresion(String nombreImpresion) {
		this.nombreImpresion = nombreImpresion;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the emailPqr
	 */
	public String getEmailPqr() {
		return emailPqr;
	}

	/**
	 * @param emailPqr
	 *            the emailPqr to set
	 */
	public void setEmailPqr(String emailPqr) {
		this.emailPqr = emailPqr;
	}

	/**
	 * @return the pagina
	 */
	public String getPagina() {
		return pagina;
	}

	/**
	 * @param pagina
	 *            the pagina to set
	 */
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the centroCostos
	 */
	public String getCentroCostos() {
		return centroCostos;
	}

	/**
	 * @param centroCostos
	 *            the centroCostos to set
	 */
	public void setCentroCostos(String centroCostos) {
		this.centroCostos = centroCostos;
	}

	/**
	 * @return the acronimo
	 */
	public String getAcronimo() {
		return acronimo;
	}

	/**
	 * @param acronimo
	 *            the acronimo to set
	 */
	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}

	/**
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}

	/**
	 * @return the ventanilla
	 */
	public int getVentanilla() {
		return ventanilla;
	}

	/**
	 * @param ventanilla
	 *            the ventanilla to set
	 */
	public void setVentanilla(int ventanilla) {
		this.ventanilla = ventanilla;
	}

	/**
	 * @return the codigoRetencion
	 */
	public String getCodigoRetencion() {
		return codigoRetencion;
	}

	/**
	 * @param codigoRetencion
	 *            the codigoRetencion to set
	 */
	public void setCodigoRetencion(String codigoRetencion) {
		this.codigoRetencion = codigoRetencion;
	}

	/**
	 * @return the nombreDependencia
	 */
	public String getNombreDependencia() {
		return nombreDependencia;
	}

	/**
	 * @param nombreDependencia
	 *            the nombreDependencia to set
	 */
	public void setNombreDependencia(String nombreDependencia) {
		this.nombreDependencia = nombreDependencia;
	}

	/**
	 * @return the codigoTipoUaaDep
	 */
	public int getCodigoTipoUaaDep() {
		return codigoTipoUaaDep;
	}

	/**
	 * @param codigoTipoUaaDep
	 *            the codigoTipoUaaDep to set
	 */
	public void setCodigoTipoUaaDep(int codigoTipoUaaDep) {
		this.codigoTipoUaaDep = codigoTipoUaaDep;
	}

	/**
	 * @return the uaaTipo
	 */
	public UaaTipo getUaaTipo() {
		return uaaTipo;
	}

	/**
	 * @param uaaTipo
	 *            the uaaTipo to set
	 */
	public void setUaaTipo(UaaTipo uaaTipo) {
		this.uaaTipo = uaaTipo;
	}

	/**
	 * @return the municipio
	 */
	public Municipio getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio
	 *            the municipio to set
	 */
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	/**
	 * @return the departamento
	 */
	public Departamento getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento
	 *            the departamento to set
	 */
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the sede
	 */
	public Sede getSede() {
		return sede;
	}

	/**
	 * @param sede
	 *            the sede to set
	 */
	public void setSede(Sede sede) {
		this.sede = sede;
	}

}
