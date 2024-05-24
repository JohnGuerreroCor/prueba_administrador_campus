/**
 * 
 */
package co.edu.usco.lcms.librerias;

import co.edu.usco.lcms.correo.NotificacionCorreo;

/**
 * @author ANDRES-GPIE
 *
 */
public class TheadNotificacionCorreo extends Thread {

	@Override
	public void run() {
		System.out.println("Enviando Correo!");
	}

	public TheadNotificacionCorreo(String destinatario, String asunto, String mensaje, String destinatarioBCC) {
		new NotificacionCorreo().enviarNotificacionCorreo(destinatario, asunto, mensaje, destinatarioBCC);
	}
}
