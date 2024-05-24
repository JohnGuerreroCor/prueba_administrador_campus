/**
 * 
 */
package co.edu.usco.lcms.model.reservaespacios;

import java.sql.Date;
import java.util.List;

import co.edu.usco.lcms.dto.DiasHorasOcupadasDTO;
import co.edu.usco.lcms.model.Curso;
import co.edu.usco.lcms.model.Dia;
import co.edu.usco.lcms.model.Uaa;
import co.edu.usco.lcms.model.Usuario;

/**
 * @author jankarlos
 *
 */
public class EspacioOcupacion {

	private long codigo;
	private String url;
	private Usuario usuario;
	private String descripcion;
	private OcupacionTipo ocupacionTipo;
	private UaaPersonal uaaPersonal;
	private Curso curso;
	private String estado;
	private Horas hora;
	private Horario horario;
	private Date fecha;
	private Uaa uaa;
	private Espacio espacio;
	private Horas horaInicio;
	private Horas horaFin;
	private Dia dia;
	private List<Dia> diaLista;
	private List<DiasHorasOcupadasDTO> diasHorasOcupadasDTO;
	/**
	 * 
	 */
	public EspacioOcupacion() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param codigo
	 * @param url
	 * @param usuario
	 * @param descripcion
	 * @param ocupacionTipo
	 * @param uaaPersonal
	 * @param curso
	 * @param estado
	 * @param hora
	 * @param horario
	 * @param fecha
	 * @param uaa
	 * @param espacio
	 * @param horaInicio
	 * @param horaFin
	 * @param dia
	 * @param diaLista
	 * @param diasHorasOcupadasDTO
	 */
	public EspacioOcupacion(long codigo, String url, Usuario usuario, String descripcion, OcupacionTipo ocupacionTipo,
			UaaPersonal uaaPersonal, Curso curso, String estado, Horas hora, Horario horario, Date fecha, Uaa uaa,
			Espacio espacio, Horas horaInicio, Horas horaFin, Dia dia, List<Dia> diaLista,
			List<DiasHorasOcupadasDTO> diasHorasOcupadasDTO) {
		super();
		this.codigo = codigo;
		this.url = url;
		this.usuario = usuario;
		this.descripcion = descripcion;
		this.ocupacionTipo = ocupacionTipo;
		this.uaaPersonal = uaaPersonal;
		this.curso = curso;
		this.estado = estado;
		this.hora = hora;
		this.horario = horario;
		this.fecha = fecha;
		this.uaa = uaa;
		this.espacio = espacio;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.dia = dia;
		this.diaLista = diaLista;
		this.diasHorasOcupadasDTO = diasHorasOcupadasDTO;
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	 * @return the ocupacionTipo
	 */
	public OcupacionTipo getOcupacionTipo() {
		return ocupacionTipo;
	}
	/**
	 * @param ocupacionTipo the ocupacionTipo to set
	 */
	public void setOcupacionTipo(OcupacionTipo ocupacionTipo) {
		this.ocupacionTipo = ocupacionTipo;
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
	 * @return the hora
	 */
	public Horas getHora() {
		return hora;
	}
	/**
	 * @param hora the hora to set
	 */
	public void setHora(Horas hora) {
		this.hora = hora;
	}
	/**
	 * @return the horario
	 */
	public Horario getHorario() {
		return horario;
	}
	/**
	 * @param horario the horario to set
	 */
	public void setHorario(Horario horario) {
		this.horario = horario;
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
	 * @return the espacio
	 */
	public Espacio getEspacio() {
		return espacio;
	}
	/**
	 * @param espacio the espacio to set
	 */
	public void setEspacio(Espacio espacio) {
		this.espacio = espacio;
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
	 * @return the dia
	 */
	public Dia getDia() {
		return dia;
	}
	/**
	 * @param dia the dia to set
	 */
	public void setDia(Dia dia) {
		this.dia = dia;
	}
	/**
	 * @return the diaLista
	 */
	public List<Dia> getDiaLista() {
		return diaLista;
	}
	/**
	 * @param diaLista the diaLista to set
	 */
	public void setDiaLista(List<Dia> diaLista) {
		this.diaLista = diaLista;
	}
	/**
	 * @return the diasHorasOcupadasDTO
	 */
	public List<DiasHorasOcupadasDTO> getDiasHorasOcupadasDTO() {
		return diasHorasOcupadasDTO;
	}
	/**
	 * @param diasHorasOcupadasDTO the diasHorasOcupadasDTO to set
	 */
	public void setDiasHorasOcupadasDTO(List<DiasHorasOcupadasDTO> diasHorasOcupadasDTO) {
		this.diasHorasOcupadasDTO = diasHorasOcupadasDTO;
	}

	
}
