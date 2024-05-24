package co.edu.usco.lcms.dto;

import java.util.List;

import co.edu.usco.lcms.model.OfertaAcademica;

public class OfertaConfiguracionDTO {

	private int codigo;
	private List<String> usuarios;
	private List<String> uaa;
	private OfertaAcademica ofertaAcademica;

	public OfertaConfiguracionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OfertaConfiguracionDTO(int codigo, List<String> usuarios, List<String> uaa,
			OfertaAcademica ofertaAcademica) {
		super();
		this.codigo = codigo;
		this.usuarios = usuarios;
		this.uaa = uaa;
		this.ofertaAcademica = ofertaAcademica;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public List<String> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<String> usuarios) {
		this.usuarios = usuarios;
	}

	public List<String> getUaa() {
		return uaa;
	}

	public void setUaa(List<String> uaa) {
		this.uaa = uaa;
	}

	public OfertaAcademica getOfertaAcademica() {
		return ofertaAcademica;
	}

	public void setOfertaAcademica(OfertaAcademica ofertaAcademica) {
		this.ofertaAcademica = ofertaAcademica;
	}

}
