/**
 * 
 */
package co.edu.usco.lcms.utility;

/**
 * @author jankarlos
 *
 */
public class ValidadorParametro {

	public static enum TipoValidador {
		VALIDADOR_PIN, VALIDADOR_USUARIO, VALIDADOR_CLAVE, VALIDADOR_EMAIL, VALIDADOR_TEXTO, VALIDADOR_NUMERO, VALIDADOR_LISTA_NUMEROS, VALIDADOR_LISTA_NUMEROS_PUNTO_Y_COMA
	}

	public static boolean validarParametro(ValidadorParametro.TipoValidador tipo, String parametro, int longitud) {

		boolean valido = false;

		if (parametro == null) {
			parametro = "";
		}

		if (parametro.length() > 0) {
			parametro = parametro.trim();
		}

		if (parametro.length() == 0) {
			return true;
		}

		// Logger log =
		// Logger.getLogger("jacs.usco.matricula.utilidad.ValidadorParametro");
		// // this.getClass().getName());

		String regex = "";

		// String regex =
		// "^(([^<>'\"/;`%-])(?i)(?!ALTER|CREATE|DELETE|DROP|EXEC(UTE){0,1}|INSERT(
		// +INTO){0,1}|MERGE|SELECT|UPDATE|UNION( +ALL){0,1})[\\s\\S])*$";
		// //^[^<>'\"/;`%-]*$";

		String PATRON_PIN = "^(?=[a-zA-Z0-9|_]*$)((?i)(?!ALTER|CREATE|DELETE|DROP|EXEC(UTE){0,1}|INSERT( +INTO){0,1}|MERGE|SELECT|UPDATE| UNION ( +ALL){0,1}|\\^|<|>|'|\\|/|;|%|-| OR | AND |\\||\\&)[\\s\\S]){1,"
				+ longitud + "}$";

		// ^(?=[a-zA-Z0-9-]*$)((?!ALTER|CREATE|DELETE|DROP|EXEC(UTE){0,1}|INSERT(
		// +INTO){0,1}|MERGE|SELECT|UPDATE|UNION(
		// +ALL){0,1}|OR|AND|\\|\\&)[\s\S]){1,20}$";

		String PATRON_USUARIO = "^((?i)(?!ALTER|CREATE|DELETE|DROP|EXEC(UTE){0,1}|INSERT( +INTO){0,1}|MERGE|SELECT|UPDATE| UNION ( +ALL){0,1}|\\^|<|>|'|\\|/|;|%|-| OR | AND |\\||\\&)[\\s\\S]){1,"
				+ longitud + "}$";
		// ^[^<>'\"/;`%-]*$";

		String PATRON_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,"
				+ longitud + "})$";

		String PATRON_CLAVE = "^((?i)(?!ALTER|CREATE|DELETE|DROP|EXEC(UTE){0,1}|INSERT( +INTO){0,1}|MERGE|SELECT|UPDATE| UNION ( +ALL){0,1}|\\^|<|>|'|\\|/|;|%|-| OR | AND |\\||\\&)[\\s\\S]){1,"
				+ longitud + "}$";

		String PATRON_TEXTO = "^((?i)(?!ALTER|CREATE|DELETE|DROP|EXEC(UTE){0,1}|INSERT( +INTO){0,1}|MERGE|SELECT|UPDATE| UNION ( +ALL){0,1}| OR | AND |\\||\\&)[\\s\\S]){1,"
				+ longitud + "}$";

		/// String PATRON_TEXTO =
		/// "^((?i)(?!ALTER|CREATE|DELETE|DROP|EXEC(UTE){0,1}|INSERT(
		/// +INTO){0,1}|MERGE|SELECT|UPDATE|UNION(
		/// +ALL){0,1}|\\^|<|>|'|\\|/|;|%|-| OR | AND |\\||\\&)[\\s\\S]){1,"
		// + longitud + "}$";

		String PATRON_LISTA_NUMEROS = "^(?=[0-9|,]*$)((?i)(?!ALTER|CREATE|DELETE|DROP|EXEC(UTE){0,1}|INSERT( +INTO){0,1}|MERGE|SELECT|UPDATE| UNION ( +ALL){0,1}|\\^|<|>|'|\\|/|;|%|-| OR | AND |\\||\\&)[\\s\\S]){1,"
				+ longitud + "}$";

		String PATRON_LISTA_NUMEROS_PUNTO_Y_COMA = "^(?=[0-9|:|,|;]*$)((?i)(?!ALTER|CREATE|DELETE|DROP|EXEC(UTE){0,1}|INSERT( +INTO){0,1}|MERGE|SELECT|UPDATE| UNION ( +ALL){0,1}|\\^|<|>|'|\\|/|%|-| OR | AND |\\||\\&)[\\s\\S]){1,"
				+ longitud + "}$";

		String PATRON_NUMERO = "^(?=[0-9]*$)((?i)(?!ALTER|CREATE|DELETE|DROP|EXEC(UTE){0,1}|INSERT( +INTO){0,1}|MERGE|SELECT|UPDATE| UNION ( +ALL){0,1}|\\^|<|>|'|\\|/|;|%|-| OR | AND |\\||\\&)[\\s\\S]){1,"
				+ longitud + "}$";

		switch (tipo) {
		case VALIDADOR_PIN:
			regex = PATRON_PIN;
			break;
		case VALIDADOR_USUARIO:
			regex = PATRON_USUARIO;
			break;
		case VALIDADOR_EMAIL:
			regex = PATRON_EMAIL;
			break;
		case VALIDADOR_CLAVE:
			regex = PATRON_CLAVE;
			break;
		case VALIDADOR_TEXTO:
			regex = PATRON_TEXTO;
			break;
		case VALIDADOR_NUMERO:
			regex = PATRON_NUMERO;
			break;
		case VALIDADOR_LISTA_NUMEROS:
			regex = PATRON_LISTA_NUMEROS;
			break;
		case VALIDADOR_LISTA_NUMEROS_PUNTO_Y_COMA:
			regex = PATRON_LISTA_NUMEROS_PUNTO_Y_COMA;
			break;
		}

		if (parametro.matches(regex)) {
			valido = true;
			// log.info(parametro + " matches the regex " + regex);
			System.out.println(parametro + " matches the regex " + regex);
		} else {
			valido = false;
			// log.info(parametro + " does not match the regex " + regex);
			System.out.println(parametro + " does not match the regex " + regex);
		}

		return valido;
	}

}
