package co.edu.usco.lcms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import co.edu.usco.lcms.librerias.TheadEnvioCorreo;
import co.edu.usco.lcms.model.Admision;


@Component
public class AdmisionDaoJDBCTemplateImpl implements AdmisionDao {

	@Autowired
	DataSource dataSource;
	@Override
	public boolean agregarAdmision(final Admision admision) {
		try {
			
			Date fechaActual = new Date();
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
			String cadenaFecha = formato.format(fechaActual);
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			final String sql = "INSERT INTO admision_resultado_lcms (adt_codigo, inp_codigo, adr_metodo, "
					+ "adr_puesto_proceso, adr_estado, adr_fecha, cliente) "
					+ "VALUES (?, ?, ?, ?, ?, convert(datetime,'" + cadenaFecha + "'),?)";
			KeyHolder keyHolder = new GeneratedKeyHolder();
			int resultado = jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					pstm.setInt(1, 1);		
					pstm.setInt(2, admision.getInscripcion().getInscripcionPrograma().getId());
					pstm.setInt(3, 1);
					pstm.setInt(4, 0);
					pstm.setInt(5, 0);
					pstm.setString(6, "modulo admisiones no formal");
					return pstm;
				}
			}, keyHolder);

			if (resultado > 0) {
				
				String destinatario;
				String destinatarioBCC;
				
				if(admision.getInscripcion().getPersona().getEmail() != null){
					System.out.println("Si");
					destinatario = admision.getInscripcion().getPersona().getEmail();
					destinatarioBCC = admision.getInscripcion().getPersona().getEmailPreInscripcion();
				}else{
					destinatario = admision.getInscripcion().getPersona().getEmailPreInscripcion();
					destinatarioBCC = admision.getInscripcion().getPersona().getEmailPreInscripcion();
					System.out.println("No");
				}
				System.out.println(destinatarioBCC);
				
				String footer = "NOTA CONFIDENCIAL:<br>"
						+ "Este mensaje es exclusivamente para el uso de la persona o entidad a quien está dirigido; La información contenida en este e-mail y en todos sus archivos anexos es completamente confidencial, de igual manera las opiniones expresadas son netamente personales, y no necesariamente transmiten el pensamiento de la Universidad Surcolombiana. Si usted no es el destinatario por favor háganoslo saber respondiendo a este correo y por favor destruya cualquier copia del mismo y sus adjuntos, cualquier almacenamiento, distribución, divulgación o copia de este mensaje está estrictamente prohibida y sancionada por la ley."
						+ "Si por error recibe este mensaje, ofrecemos disculpas.<br><br>" + "CONFIDENTIAL NOTE:<br>"
						+ "This message is exclusively for  use of the individual or entity to whom it is forwarded.  The information of this e-mail and all its attachments is completely confidential; likewise, the opinions expressed are purely personal and they do not necessarily convey the thought of  Surcolombiana University.  If you are not the addressee, please let us know it by replying to this e-mail and please delete any copy and its attachments.  Any storage, distribution, dissemination or copy of this information is strictly prohibited and punished  by law.";
				String asunto = "Felicidades Admisión exitosa - Inscripciones Universidad Surcolombiana";
				String mensaje = "Cordial saludo " + admision.getInscripcion().getPersona().getNombre().toUpperCase() + " "
							+ admision.getInscripcion().getPersona().getApellido().toUpperCase().toUpperCase() 							
							+ ",<br><br>Bienvenido a la Universidad Surcolombiana, fue admitido al programa "
							+ admision.getInscripcion().getOferta().getPrograma().getNombreUaa() 
							+ " (" + admision.getInscripcion().getOferta().getPrograma().getNombre() + ")" + "<br><br>" + footer;
				
				new TheadEnvioCorreo(destinatario, asunto, mensaje, destinatarioBCC).start();
				
				admision.setId(keyHolder.getKey().intValue());
				return true;
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public Admision consultarAdmision (int ins_codigo) {
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			String sql = "SELECT adr_codigo "
					+ "FROM admision_resultado_lcms "
					+ "WHERE inp_codigo = " + ins_codigo;
			List<Admision> listaAdmision = jdbcTemplate.query(sql, new RowMapper<Admision>() {

				public Admision mapRow(ResultSet rs, int rowNum) throws SQLException {
					Admision admision = new Admision();
					admision.setId(rs.getInt("adr_codigo"));
					
					return admision;
				}
				
			});
			if (listaAdmision.size() > 0) {
				return listaAdmision.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
