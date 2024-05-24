
package co.edu.usco.lcms.model.reservaespacios;

import java.util.Date;

import co.edu.usco.lcms.model.Curso;

/**
 * @author jankarlos
 *
 */
public class Solicitud {

	private long codigo;
	private String title;
	private String start;
	private String end;
	private Date fecha;
	private Horas horaInicio;
	private Horas horaFin;
	private UaaPersonal uaaPersonal;
	private Curso curso;
	private String tema;
	private String tipoAcceso;
	private String descripcion;
	private String estado;
	private int cantidad;
	private EspacioOcupacion espacioOcupacion;
	private String textColor;
	private String color;
	private long codigoVideoconferencia;
	private String mensaje;
	/**
	 * 
	 */
	public Solicitud() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param codigo
	 * @param title
	 * @param start
	 * @param end
	 * @param fecha
	 * @param horaInicio
	 * @param horaFin
	 * @param uaaPersonal
	 * @param curso
	 * @param tema
	 * @param tipoAcceso
	 * @param descripcion
	 * @param estado
	 * @param cantidad
	 * @param espacioOcupacion
	 * @param textColor
	 * @param color
	 * @param codigoVideoconferencia
	 * @param mensaje
	 */
	public Solicitud(long codigo, String title, String start, String end, Date fecha, Horas horaInicio, Horas horaFin,
			UaaPersonal uaaPersonal, Curso curso, String tema, String tipoAcceso, String descripcion, String estado,
			int cantidad, EspacioOcupacion espacioOcupacion, String textColor, String color,
			long codigoVideoconferencia, String mensaje) {
		super();
		this.codigo = codigo;
		this.title = title;
		this.start = start;
		this.end = end;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.uaaPersonal = uaaPersonal;
		this.curso = curso;
		this.tema = tema;
		this.tipoAcceso = tipoAcceso;
		this.descripcion = descripcion;
		this.estado = estado;
		this.cantidad = cantidad;
		this.espacioOcupacion = espacioOcupacion;
		this.textColor = textColor;
		this.color = color;
		this.codigoVideoconferencia = codigoVideoconferencia;
		this.mensaje = mensaje;
	}
	/**
	 * @return the codigo
	 */
	public long getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}
	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the horaInicio
	 */
	public Horas getHoraInicio() {
		return horaInicio;
	}
	/**
	 * @param horaInicio the horaInicio to set
	 */
	public void setHoraInicio(Horas horaInicio) {
		this.horaInicio = horaInicio;
	}
	/**
	 * @return the horaFin
	 */
	public Horas getHoraFin() {
		return horaFin;
	}
	/**
	 * @param horaFin the horaFin to set
	 */
	public void setHoraFin(Horas horaFin) {
		this.horaFin = horaFin;
	}
	/**
	 * @return the uaaPersonal
	 */
	public UaaPersonal getUaaPersonal() {
		return uaaPersonal;
	}
	/**
	 * @param uaaPersonal the uaaPersonal to set
	 */
	public void setUaaPersonal(UaaPersonal uaaPersonal) {
		this.uaaPersonal = uaaPersonal;
	}
	/**
	 * @return the curso
	 */
	public Curso getCurso() {
		return curso;
	}
	/**
	 * @param curso the curso to set
	 */
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	/**
	 * @return the tema
	 */
	public String getTema() {
		return tema;
	}
	/**
	 * @param tema the tema to set
	 */
	public void setTema(String tema) {
		this.tema = tema;
	}
	/**
	 * @return the tipoAcceso
	 */
	public String getTipoAcceso() {
		return tipoAcceso;
	}
	/**
	 * @param tipoAcceso the tipoAcceso to set
	 */
	public void setTipoAcceso(String tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
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
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the espacioOcupacion
	 */
	public EspacioOcupacion getEspacioOcupacion() {
		return espacioOcupacion;
	}
	/**
	 * @param espacioOcupacion the espacioOcupacion to set
	 */
	public void setEspacioOcupacion(EspacioOcupacion espacioOcupacion) {
		this.espacioOcupacion = espacioOcupacion;
	}
	/**
	 * @return the textColor
	 */
	public String getTextColor() {
		return textColor;
	}
	/**
	 * @param textColor the textColor to set
	 */
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the codigoVideoconferencia
	 */
	public long getCodigoVideoconferencia() {
		return codigoVideoconferencia;
	}
	/**
	 * @param codigoVideoconferencia the codigoVideoconferencia to set
	 */
	public void setCodigoVideoconferencia(long codigoVideoconferencia) {
		this.codigoVideoconferencia = codigoVideoconferencia;
	}
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
