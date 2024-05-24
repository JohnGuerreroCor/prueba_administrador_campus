package co.edu.usco.lcms.model;

import java.sql.Date;

public class Persona {

	private int id;
	
	private boolean tercero;
	private int codigo;
	private String nombre;
	private String apellido;
	private String identificacion;
	private TipoIdentificacion tipoIdentificacion;
	private String direccionResidencia;
	private String telefono;
	private String email;
	private String emailPreInscripcion;
	
	private String nombreCompleto;
	private Date fechaExpedicion;
	private String direccion;
	private String barrio;
	private String telefonoMovil;
	private Date fechaNacimiento;
	private long identificacionCotizante;
	private String genero;
	
	private LenguaNativa lenguaNativa;
	private EstratoSocioeconomico estrato;
	private Municipio lugarNacimiento;
	private Municipio lugarExpedicion;
	private Eps eps;
	private Eps tipoEps;
	private EstadoCivil estadoCivil;
	private GrupoSanguineo grupoSanguineo;
	private InstitucionEducativa institucionEducativa;	
	private Pais lugarResidenciaPais;
	private Departamento lugarResidenciaDepartamento;
	private Municipio lugarResidencia;
	private EpsTipoAfiliacion tipoAfiliacion;
	public Persona() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Persona(int id, boolean tercero, int codigo, String nombre, String apellido, String identificacion,
			TipoIdentificacion tipoIdentificacion, String direccionResidencia, String telefono, String email,
			String emailPreInscripcion, String nombreCompleto, Date fechaExpedicion, String direccion, String barrio,
			String telefonoMovil, Date fechaNacimiento, long identificacionCotizante, String genero,
			LenguaNativa lenguaNativa, EstratoSocioeconomico estrato, Municipio lugarNacimiento,
			Municipio lugarExpedicion, Eps eps, Eps tipoEps, EstadoCivil estadoCivil, GrupoSanguineo grupoSanguineo,
			InstitucionEducativa institucionEducativa, Pais lugarResidenciaPais,
			Departamento lugarResidenciaDepartamento, Municipio lugarResidencia, EpsTipoAfiliacion tipoAfiliacion) {
		super();
		this.id = id;
		this.tercero = tercero;
		this.codigo = codigo;
		this.nombre = nombre;
		this.apellido = apellido;
		this.identificacion = identificacion;
		this.tipoIdentificacion = tipoIdentificacion;
		this.direccionResidencia = direccionResidencia;
		this.telefono = telefono;
		this.email = email;
		this.emailPreInscripcion = emailPreInscripcion;
		this.nombreCompleto = nombreCompleto;
		this.fechaExpedicion = fechaExpedicion;
		this.direccion = direccion;
		this.barrio = barrio;
		this.telefonoMovil = telefonoMovil;
		this.fechaNacimiento = fechaNacimiento;
		this.identificacionCotizante = identificacionCotizante;
		this.genero = genero;
		this.lenguaNativa = lenguaNativa;
		this.estrato = estrato;
		this.lugarNacimiento = lugarNacimiento;
		this.lugarExpedicion = lugarExpedicion;
		this.eps = eps;
		this.tipoEps = tipoEps;
		this.estadoCivil = estadoCivil;
		this.grupoSanguineo = grupoSanguineo;
		this.institucionEducativa = institucionEducativa;
		this.lugarResidenciaPais = lugarResidenciaPais;
		this.lugarResidenciaDepartamento = lugarResidenciaDepartamento;
		this.lugarResidencia = lugarResidencia;
		this.tipoAfiliacion = tipoAfiliacion;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isTercero() {
		return tercero;
	}
	public void setTercero(boolean tercero) {
		this.tercero = tercero;
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public TipoIdentificacion getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getDireccionResidencia() {
		return direccionResidencia;
	}
	public void setDireccionResidencia(String direccionResidencia) {
		this.direccionResidencia = direccionResidencia;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailPreInscripcion() {
		return emailPreInscripcion;
	}
	public void setEmailPreInscripcion(String emailPreInscripcion) {
		this.emailPreInscripcion = emailPreInscripcion;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public Date getFechaExpedicion() {
		return fechaExpedicion;
	}
	public void setFechaExpedicion(Date fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getBarrio() {
		return barrio;
	}
	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}
	public String getTelefonoMovil() {
		return telefonoMovil;
	}
	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public long getIdentificacionCotizante() {
		return identificacionCotizante;
	}
	public void setIdentificacionCotizante(long identificacionCotizante) {
		this.identificacionCotizante = identificacionCotizante;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public LenguaNativa getLenguaNativa() {
		return lenguaNativa;
	}
	public void setLenguaNativa(LenguaNativa lenguaNativa) {
		this.lenguaNativa = lenguaNativa;
	}
	public EstratoSocioeconomico getEstrato() {
		return estrato;
	}
	public void setEstrato(EstratoSocioeconomico estrato) {
		this.estrato = estrato;
	}
	public Municipio getLugarNacimiento() {
		return lugarNacimiento;
	}
	public void setLugarNacimiento(Municipio lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}
	public Municipio getLugarExpedicion() {
		return lugarExpedicion;
	}
	public void setLugarExpedicion(Municipio lugarExpedicion) {
		this.lugarExpedicion = lugarExpedicion;
	}
	public Eps getEps() {
		return eps;
	}
	public void setEps(Eps eps) {
		this.eps = eps;
	}
	public Eps getTipoEps() {
		return tipoEps;
	}
	public void setTipoEps(Eps tipoEps) {
		this.tipoEps = tipoEps;
	}
	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public GrupoSanguineo getGrupoSanguineo() {
		return grupoSanguineo;
	}
	public void setGrupoSanguineo(GrupoSanguineo grupoSanguineo) {
		this.grupoSanguineo = grupoSanguineo;
	}
	public InstitucionEducativa getInstitucionEducativa() {
		return institucionEducativa;
	}
	public void setInstitucionEducativa(InstitucionEducativa institucionEducativa) {
		this.institucionEducativa = institucionEducativa;
	}
	public Pais getLugarResidenciaPais() {
		return lugarResidenciaPais;
	}
	public void setLugarResidenciaPais(Pais lugarResidenciaPais) {
		this.lugarResidenciaPais = lugarResidenciaPais;
	}
	public Departamento getLugarResidenciaDepartamento() {
		return lugarResidenciaDepartamento;
	}
	public void setLugarResidenciaDepartamento(Departamento lugarResidenciaDepartamento) {
		this.lugarResidenciaDepartamento = lugarResidenciaDepartamento;
	}
	public Municipio getLugarResidencia() {
		return lugarResidencia;
	}
	public void setLugarResidencia(Municipio lugarResidencia) {
		this.lugarResidencia = lugarResidencia;
	}
	public EpsTipoAfiliacion getTipoAfiliacion() {
		return tipoAfiliacion;
	}
	public void setTipoAfiliacion(EpsTipoAfiliacion tipoAfiliacion) {
		this.tipoAfiliacion = tipoAfiliacion;
	}
	
	
}
