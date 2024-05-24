/**
 * 
 */
package co.edu.usco.lcms.model;

/**
 * @author ANDRES-GPIE
 *
 */
public class RequisitosAdjuntos {
	private int id;
	
	private int codigo;
	private String nombreCompleto;
	private String nombreEncriptado;
	private String tipo;
	private String ruta;
	private String registro;
	public RequisitosAdjuntos() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RequisitosAdjuntos(int id, int codigo, String nombreCompleto, String nombreEncriptado, String tipo,
			String ruta, String registro) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nombreCompleto = nombreCompleto;
		this.nombreEncriptado = nombreEncriptado;
		this.tipo = tipo;
		this.ruta = ruta;
		this.registro = registro;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getNombreEncriptado() {
		return nombreEncriptado;
	}
	public void setNombreEncriptado(String nombreEncriptado) {
		this.nombreEncriptado = nombreEncriptado;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getRegistro() {
		return registro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}
	
	
	
	
		
}
