package co.edu.usco.lcms.correo;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Correo {	
	private String emisor_correo = "noreply_inscripcioncampusvirtual@usco.edu.co";
	private String emisor_clave = "654sdfA._@654sdf";
	private String destinatario;
	private String asunto;
	private String mensaje;
	private String destinatarioBCC;
	
	public Correo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Correo(String destinatario, String asunto, String mensaje,
			String destinatarioBCC) {
		super();
		this.destinatario = destinatario;
		this.asunto = asunto;
		this.mensaje = mensaje;
		this.destinatarioBCC = destinatarioBCC;
	}

	public boolean enviarCorreo() {
		return this.enviarCorreo(this.destinatario, this.asunto, this.mensaje, this.destinatarioBCC);
	}
	
	public boolean enviarCorreo(String destinatario, String asunto, String mensaje, String destinatarioBCC) {
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
			if(destinatarioBCC != "" && destinatarioBCC != null){
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

	public String getEmisor_correo() {
		return emisor_correo;
	}

	public void setEmisor_correo(String emisor_correo) {
		this.emisor_correo = emisor_correo;
	}

	public String getEmisor_clave() {
		return emisor_clave;
	}

	public void setEmisor_clave(String emisor_clave) {
		this.emisor_clave = emisor_clave;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getDestinatarioBCC() {
		return destinatarioBCC;
	}

	public void setDestinatarioBCC(String destinatarioBCC) {
		this.destinatarioBCC = destinatarioBCC;
	}
	
	
	
}
