/**
 * 
 */
package co.edu.usco.lcms.librerias;

import co.edu.usco.lcms.correo.Correo;

/**
 * @author ANDRES-GPIE
 *
 */
public class TheadEnvioCorreo extends Thread {

	
	Correo correo;
	
	@Override
	public void run() {
		correo.enviarCorreo() ;
	}

	public TheadEnvioCorreo(String destinatario, String asunto, String mensaje, String destinatarioBCC) {
		correo = new Correo(destinatario, asunto, mensaje, destinatarioBCC);		
	}
}
