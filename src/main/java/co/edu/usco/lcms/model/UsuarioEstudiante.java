/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author ANDRES-GPIE
 *
 */
public class UsuarioEstudiante {
	private int uid;
	private int up;
	private String us;
	private String uwd;
	private String uwd_seguridad;
	private int isTipo;
	private int isChange;
	private int state;
	private String sys;
	private String correo;
	private String pregunta;
	private String respuesta;
	private String usEncriptado;
	private String fechaInactivacion;
	/**
	 * 
	 */
	public UsuarioEstudiante() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param uid
	 * @param up
	 * @param us
	 * @param uwd
	 * @param uwd_seguridad
	 * @param isTipo
	 * @param isChange
	 * @param state
	 * @param sys
	 * @param correo
	 * @param pregunta
	 * @param respuesta
	 * @param usEncriptado
	 * @param fechaInactivacion
	 */
	public UsuarioEstudiante(int uid, int up, String us, String uwd, String uwd_seguridad, int isTipo, int isChange,
			int state, String sys, String correo, String pregunta, String respuesta, String usEncriptado,
			String fechaInactivacion) {
		super();
		this.uid = uid;
		this.up = up;
		this.us = us;
		this.uwd = uwd;
		this.uwd_seguridad = uwd_seguridad;
		this.isTipo = isTipo;
		this.isChange = isChange;
		this.state = state;
		this.sys = sys;
		this.correo = correo;
		this.pregunta = pregunta;
		this.respuesta = respuesta;
		this.usEncriptado = usEncriptado;
		this.fechaInactivacion = fechaInactivacion;
	}
	/**
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}
	/**
	 * @return the up
	 */
	public int getUp() {
		return up;
	}
	/**
	 * @param up the up to set
	 */
	public void setUp(int up) {
		this.up = up;
	}
	/**
	 * @return the us
	 */
	public String getUs() {
		return us;
	}
	/**
	 * @param us the us to set
	 */
	public void setUs(String us) {
		this.us = us;
	}
	/**
	 * @return the uwd
	 */
	public String getUwd() {
		return uwd;
	}
	/**
	 * @param uwd the uwd to set
	 */
	public void setUwd(String uwd) {
		this.uwd = uwd;
	}
	/**
	 * @return the uwd_seguridad
	 */
	public String getUwd_seguridad() {
		return uwd_seguridad;
	}
	/**
	 * @param uwd_seguridad the uwd_seguridad to set
	 */
	public void setUwd_seguridad(String uwd_seguridad) {
		this.uwd_seguridad = uwd_seguridad;
	}
	/**
	 * @return the isTipo
	 */
	public int getIsTipo() {
		return isTipo;
	}
	/**
	 * @param isTipo the isTipo to set
	 */
	public void setIsTipo(int isTipo) {
		this.isTipo = isTipo;
	}
	/**
	 * @return the isChange
	 */
	public int getIsChange() {
		return isChange;
	}
	/**
	 * @param isChange the isChange to set
	 */
	public void setIsChange(int isChange) {
		this.isChange = isChange;
	}
	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
	/**
	 * @return the sys
	 */
	public String getSys() {
		return sys;
	}
	/**
	 * @param sys the sys to set
	 */
	public void setSys(String sys) {
		this.sys = sys;
	}
	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}
	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	/**
	 * @return the pregunta
	 */
	public String getPregunta() {
		return pregunta;
	}
	/**
	 * @param pregunta the pregunta to set
	 */
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	/**
	 * @return the respuesta
	 */
	public String getRespuesta() {
		return respuesta;
	}
	/**
	 * @param respuesta the respuesta to set
	 */
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	/**
	 * @return the usEncriptado
	 */
	public String getUsEncriptado() {
		return usEncriptado;
	}
	/**
	 * @param usEncriptado the usEncriptado to set
	 */
	public void setUsEncriptado(String usEncriptado) {
		this.usEncriptado = usEncriptado;
	}
	/**
	 * @return the fechaInactivacion
	 */
	public String getFechaInactivacion() {
		return fechaInactivacion;
	}
	/**
	 * @param fechaInactivacion the fechaInactivacion to set
	 */
	public void setFechaInactivacion(String fechaInactivacion) {
		this.fechaInactivacion = fechaInactivacion;
	}
	
	 
}
