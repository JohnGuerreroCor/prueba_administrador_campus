/**
 * 
 */
package co.edu.usco.lcms.model;

import java.util.Date;

/**
 * @author ANDRES-GPIE
 *
 */
public class Oferta {
	private int id;
	
	private int codigo;
	private String codigoEncriptado;
	private Date fechaIncripcionInicio;
	private Date fechaIncripcionFin;
	private Date fechaInicio;
	private Date fechaFin;
	private String estado;
	private int admisionAutomatica;
	private int requierePago;
	private int crearUsuario;
	private String imagen;
	private int cupoMaximo; 
	private int valor;
	private int cantidadInscritos;
	private String interna;
	
	private String titulo;
	private String contenido;
	
	private Programa programa;
	private OfertaRequisitos ofertaRequisitos;
	private Calendario calendario;
	
	public Oferta() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 * @param codigo
	 * @param codigoEncriptado
	 * @param fechaIncripcionInicio
	 * @param fechaIncripcionFin
	 * @param fechaInicio
	 * @param fechaFin
	 * @param estado
	 * @param admisionAutomatica
	 * @param requierePago
	 * @param crearUsuario
	 * @param imagen
	 * @param cupoMaximo
	 * @param valor
	 * @param cantidadInscritos
	 * @param interna
	 * @param titulo
	 * @param contenido
	 * @param programa
	 * @param ofertaRequisitos
	 * @param calendario
	 */
	public Oferta(int id, int codigo, String codigoEncriptado, Date fechaIncripcionInicio, Date fechaIncripcionFin,
			Date fechaInicio, Date fechaFin, String estado, int admisionAutomatica, int requierePago, int crearUsuario,
			String imagen, int cupoMaximo, int valor, int cantidadInscritos, String interna, String titulo,
			String contenido, Programa programa, OfertaRequisitos ofertaRequisitos, Calendario calendario) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.codigoEncriptado = codigoEncriptado;
		this.fechaIncripcionInicio = fechaIncripcionInicio;
		this.fechaIncripcionFin = fechaIncripcionFin;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estado = estado;
		this.admisionAutomatica = admisionAutomatica;
		this.requierePago = requierePago;
		this.crearUsuario = crearUsuario;
		this.imagen = imagen;
		this.cupoMaximo = cupoMaximo;
		this.valor = valor;
		this.cantidadInscritos = cantidadInscritos;
		this.interna = interna;
		this.titulo = titulo;
		this.contenido = contenido;
		this.programa = programa;
		this.ofertaRequisitos = ofertaRequisitos;
		this.calendario = calendario;
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
	 * @return the codigoEncriptado
	 */
	public String getCodigoEncriptado() {
		return codigoEncriptado;
	}

	/**
	 * @param codigoEncriptado the codigoEncriptado to set
	 */
	public void setCodigoEncriptado(String codigoEncriptado) {
		this.codigoEncriptado = codigoEncriptado;
	}

	/**
	 * @return the fechaIncripcionInicio
	 */
	public Date getFechaIncripcionInicio() {
		return fechaIncripcionInicio;
	}

	/**
	 * @param fechaIncripcionInicio the fechaIncripcionInicio to set
	 */
	public void setFechaIncripcionInicio(Date fechaIncripcionInicio) {
		this.fechaIncripcionInicio = fechaIncripcionInicio;
	}

	/**
	 * @return the fechaIncripcionFin
	 */
	public Date getFechaIncripcionFin() {
		return fechaIncripcionFin;
	}

	/**
	 * @param fechaIncripcionFin the fechaIncripcionFin to set
	 */
	public void setFechaIncripcionFin(Date fechaIncripcionFin) {
		this.fechaIncripcionFin = fechaIncripcionFin;
	}

	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
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
	 * @return the admisionAutomatica
	 */
	public int getAdmisionAutomatica() {
		return admisionAutomatica;
	}

	/**
	 * @param admisionAutomatica the admisionAutomatica to set
	 */
	public void setAdmisionAutomatica(int admisionAutomatica) {
		this.admisionAutomatica = admisionAutomatica;
	}

	/**
	 * @return the requierePago
	 */
	public int getRequierePago() {
		return requierePago;
	}

	/**
	 * @param requierePago the requierePago to set
	 */
	public void setRequierePago(int requierePago) {
		this.requierePago = requierePago;
	}

	/**
	 * @return the crearUsuario
	 */
	public int getCrearUsuario() {
		return crearUsuario;
	}

	/**
	 * @param crearUsuario the crearUsuario to set
	 */
	public void setCrearUsuario(int crearUsuario) {
		this.crearUsuario = crearUsuario;
	}

	/**
	 * @return the imagen
	 */
	public String getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return the cupoMaximo
	 */
	public int getCupoMaximo() {
		return cupoMaximo;
	}

	/**
	 * @param cupoMaximo the cupoMaximo to set
	 */
	public void setCupoMaximo(int cupoMaximo) {
		this.cupoMaximo = cupoMaximo;
	}

	/**
	 * @return the valor
	 */
	public int getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(int valor) {
		this.valor = valor;
	}

	/**
	 * @return the cantidadInscritos
	 */
	public int getCantidadInscritos() {
		return cantidadInscritos;
	}

	/**
	 * @param cantidadInscritos the cantidadInscritos to set
	 */
	public void setCantidadInscritos(int cantidadInscritos) {
		this.cantidadInscritos = cantidadInscritos;
	}

	/**
	 * @return the interna
	 */
	public String getInterna() {
		return interna;
	}

	/**
	 * @param interna the interna to set
	 */
	public void setInterna(String interna) {
		this.interna = interna;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
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
	 * @return the programa
	 */
	public Programa getPrograma() {
		return programa;
	}

	/**
	 * @param programa the programa to set
	 */
	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	/**
	 * @return the ofertaRequisitos
	 */
	public OfertaRequisitos getOfertaRequisitos() {
		return ofertaRequisitos;
	}

	/**
	 * @param ofertaRequisitos the ofertaRequisitos to set
	 */
	public void setOfertaRequisitos(OfertaRequisitos ofertaRequisitos) {
		this.ofertaRequisitos = ofertaRequisitos;
	}

	/**
	 * @return the calendario
	 */
	public Calendario getCalendario() {
		return calendario;
	}

	/**
	 * @param calendario the calendario to set
	 */
	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}
	
	
}
