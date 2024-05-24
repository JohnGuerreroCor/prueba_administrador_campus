/**
 * 
 */
package co.edu.usco.lcms.librerias;

import java.util.Date;

/**
 * @author ANDRES-GPIE
 *
 */
public class DiferenciaFechas {
	public static long diferencia(Date fechaPreInscripcion) {
		final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; // Milisegundos al
															// día
		java.util.Date hoy = new Date(); // Fecha de hoy

		java.sql.Date fecha = (java.sql.Date) fechaPreInscripcion;

		long diferencia = (hoy.getTime() - fecha.getTime()) / MILLSECS_PER_DAY;
		return diferencia;
	}
}
