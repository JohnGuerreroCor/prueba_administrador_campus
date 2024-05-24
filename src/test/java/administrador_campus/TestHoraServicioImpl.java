package administrador_campus;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import co.edu.usco.lcms.model.reservaespacios.Horas;
import co.edu.usco.lcms.servicios.reservaespacio.HoraServicio;

public class TestHoraServicioImpl extends JUnitSpringTestBase {

	@Autowired
	HoraServicio horaServicio;

	@Test
	public void testListarHoras() {
		List<Horas> horas = horaServicio.listarHoras();
		assertTrue(horas.size() > 0);
	}

	@Test
	public void testListarHorasDisponibles() {

		// List<Horas> horas = horaServicio.listarHorasDisponibles(2, 1619, 1,
		// 0, 0, "", 0);
		List<Horas> horas = horaServicio.listarHoras();
		assertTrue(horas.size() > 0);
	}

}
