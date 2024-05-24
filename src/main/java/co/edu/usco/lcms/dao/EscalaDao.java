package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.Escala;

/**
 * 
 * @author Jankarlos Diaz Vieda
 * @version 1.0
 */

public interface EscalaDao {
	
	/**
	 * Lista escala
	 * @return lista escala objeto escala
	 */
	List<Escala> listaEscala(int codigoTipo);
}
