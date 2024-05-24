package co.edu.usco.lcms.dao;

import java.text.ParseException;
import java.util.List;

import co.edu.usco.lcms.model.JSONRespuesta;
import co.edu.usco.lcms.model.Resolucion;

public interface ResolucionDao {
	
	/**
	 * Agrega una nueva Resolucion
	 * @param Resolucion Datos Resolucion a registrar
	 */
	public boolean agregarResolucion(Resolucion resolucion);
	
	/**
	 * Agrega una modificar Resolucion
	 * @param Resolucion Datos Resolucion a modificar
	 * @throws ParseException 
	 */
	public boolean modificarResolucion(int id, Resolucion resolucion) throws ParseException;
	
	/**
	 * Agrega una eliminar Resolucion
	 * @param Resolucion Datos Resolucion a eliminar
	 */
	public boolean eliminarResolucion(int id);
	
	/**
	 * Metodo para consultar una resolución
	 * @param codigo valor de la resolución a consultar
	 * @return objeto resolucion
	 */
	public Resolucion buscarResolucion(int codigo);
	
	/**
	 * Listar las Resoluciones
	 * @return Lista de las Resoluciones
	 */
	public List<Resolucion> listarResolucion(int codigo);
	
	public JSONRespuesta listarTablaResolucion(String search, int start, int length, int draw, int posicion, String direccion);
}
