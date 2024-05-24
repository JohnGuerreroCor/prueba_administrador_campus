package co.edu.usco.lcms.dao;

import java.util.List;

import co.edu.usco.lcms.model.EstudioAnterior;

public interface EstudioAnteriorDao {
	
	public List<EstudioAnterior> consultarEstudioAnterior(int persona, int tercero);
}
