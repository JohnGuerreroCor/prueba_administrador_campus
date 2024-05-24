/**
 * 
 */
package co.edu.usco.lcms.model;

import java.util.List;

import co.edu.usco.lcms.model.reservaespacios.Horas;

/**
 * @author jankarlos
 *
 */
public class Dia {

	private int codigo;
	private String nombre;
	private int posicion;
	private List<Horas> listaHoras;
	
	

	/**
	 * 
	 */
	public Dia() {
		super();
		// TODO Auto-generated constructor stub
	}



	/**
	 * @param codigo
	 * @param nombre
	 * @param posicion
	 * @param listaHoras
	 */
	public Dia(int codigo, String nombre, int posicion, List<Horas> listaHoras) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.posicion = posicion;
		this.listaHoras = listaHoras;
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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}



	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	/**
	 * @return the posicion
	 */
	public int getPosicion() {
		return posicion;
	}



	/**
	 * @param posicion the posicion to set
	 */
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}



	/**
	 * @return the listaHoras
	 */
	public List<Horas> getListaHoras() {
		return listaHoras;
	}



	/**
	 * @param listaHoras the listaHoras to set
	 */
	public void setListaHoras(List<Horas> listaHoras) {
		this.listaHoras = listaHoras;
	}

	
}
