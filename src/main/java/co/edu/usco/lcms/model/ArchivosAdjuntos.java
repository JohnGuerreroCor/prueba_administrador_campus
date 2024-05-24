package co.edu.usco.lcms.model;

public class ArchivosAdjuntos {
	private int id;
	private String nombreCompleto;
	private String nombreEncriptado;
	private String tipo;
	private String ruta;
	private int registro;
	public ArchivosAdjuntos() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ArchivosAdjuntos(int id, String nombreCompleto, String nombreEncriptado, String tipo, String ruta,
			int registro) {
		super();
		this.id = id;
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
	public int getRegistro() {
		return registro;
	}
	public void setRegistro(int registro) {
		this.registro = registro;
	}
	
	
}
