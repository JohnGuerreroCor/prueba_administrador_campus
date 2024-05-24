/**
 * 
 */
package co.edu.usco.lcms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author ANDRES-GPIE
 *
 */
@JsonIgnoreProperties
public class InscripcionPrograma {
	private int id;

	public InscripcionPrograma() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InscripcionPrograma(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
