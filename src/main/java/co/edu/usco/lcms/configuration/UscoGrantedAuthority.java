package co.edu.usco.lcms.configuration;

import org.springframework.security.core.GrantedAuthority;

import co.edu.usco.lcms.model.Uaa;

public class UscoGrantedAuthority implements GrantedAuthority {

	private final String role;
	private Uaa uaa;

	public UscoGrantedAuthority(String role, Uaa uaa) {
		super();
		this.role = role;
		this.uaa = uaa;
	}

	public String getAuthority() {
		return this.role;
	}

	public Uaa getUaa() {
		return uaa;
	}

	public void setUaa(Uaa uaa) {
		this.uaa = uaa;
	}

	public String getRole() {
		return role;
	}

}
