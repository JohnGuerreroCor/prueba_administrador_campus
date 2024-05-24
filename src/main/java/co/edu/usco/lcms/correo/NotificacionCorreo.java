package co.edu.usco.lcms.correo;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NotificacionCorreo {	
	public String emisor_correo = "noreply_videoconferenciacampusvirtual@usco.edu.co";
	public String emisor_clave = "xYz.2016@";
	
	public boolean enviarNotificacionCorreo(String destinatario, String asunto, String mensaje, String destinatarioBCC) {
		boolean correo_enviado = false;

		String exito = "";
		String mensaje_tipo = "16";
		String mensaje_texto = "";

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

			MimeMessage message = new MimeMessage(sessionCorreo);
			message.setFrom(new InternetAddress(emisor_correo));
			message.setHeader("Content-Type", "text/html; charset=UTF-8");
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			if(destinatarioBCC != ""){
				message.addRecipient(Message.RecipientType.BCC, new InternetAddress(destinatarioBCC));
			}
			message.setSubject(asunto);
			message.setContent(mensaje, "text/html");

			Transport t = sessionCorreo.getTransport("smtp");
			t.connect(emisor_correo, emisor_clave);
			t.sendMessage(message, message.getAllRecipients());
			t.close();
			correo_enviado = true;
		} catch (Exception e) {
			correo_enviado = false;
			e.printStackTrace();
		}
		return correo_enviado;
	}
	
}
