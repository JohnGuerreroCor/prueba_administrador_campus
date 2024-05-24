/**
 * 
 */
package co.edu.usco.lcms.utility;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Jankarlos Diaz
 *
 */
public class Correo {
	public String emisor_correo = "noreply_inscripcioncampusvirtual@usco.edu.co";
	public String emisor_clave = "654sdfA._@654sdf";

	public boolean enviarCorreo(String destinatario, String asunto, String mensaje) {
		boolean correo_enviado = false;

		System.out.println("correo 01");

		String exito = "";
		String mensaje_tipo = "16";
		String mensaje_texto = "";

		System.out.println("correo 02: " + emisor_correo + "," + emisor_clave);

		try {
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "smtp.gmail.com");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.user", emisor_correo);
			props.setProperty("mail.smtp.auth", "true");

			Session sessionCorreo = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emisor_correo, emisor_clave);
				}
			});

			System.out.println("correo 03");

			MimeMessage message = new MimeMessage(sessionCorreo);
			message.setFrom(new InternetAddress(emisor_correo));

			System.out.println("correo 04");

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));

			message.setSubject(asunto);

			System.out.println("correo 05");

			message.setContent(mensaje, "text/html");

			Transport t = sessionCorreo.getTransport("smtp");
			t.connect(emisor_correo, emisor_clave);
			System.out.println("correo 06");

			t.sendMessage(message, message.getAllRecipients());

			System.out.println("correo 07");
			t.close();

			System.out.println("correo 08 OKOKOKOOK");

			correo_enviado = true;
		} catch (Exception e) {
			correo_enviado = false;

			System.out.println("correo 10: " + e.toString());
			e.printStackTrace();
		}

		System.out.println("correo FINAL" + correo_enviado);

		return correo_enviado;
	}
}
