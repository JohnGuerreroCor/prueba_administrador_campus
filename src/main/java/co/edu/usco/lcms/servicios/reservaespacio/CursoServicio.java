package co.edu.usco.lcms.servicios.reservaespacio;

import java.util.List;

import co.edu.usco.lcms.model.Curso;

public interface CursoServicio {
	/**
	 * Listar los cursos
	 * @return Lista de los cursos
	 */
	public List<Curso> listarCurso(int docente, String criterio);
}
