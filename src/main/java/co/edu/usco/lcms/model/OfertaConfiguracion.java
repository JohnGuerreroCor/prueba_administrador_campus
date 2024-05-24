package co.edu.usco.lcms.model;

public class OfertaConfiguracion {

	private int codigo;
	private String tipoUsuario;
	private OfertaAcademica ofertaAcademica;
	private String uaa;
	
	public OfertaConfiguracion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OfertaConfiguracion(int codigo, String tipoUsuario, OfertaAcademica ofertaAcademica, String uaa) {
		super();
		this.codigo = codigo;
		this.tipoUsuario = tipoUsuario;
		this.ofertaAcademica = ofertaAcademica;
		this.uaa = uaa;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public OfertaAcademica getOfertaAcademica() {
		return ofertaAcademica;
	}

	public void setOfertaAcademica(OfertaAcademica ofertaAcademica) {
		this.ofertaAcademica = ofertaAcademica;
	}

	public String getUaa() {
		return uaa;
	}

	public void setUaa(String uaa) {
		this.uaa = uaa;
	}

	
	
}
