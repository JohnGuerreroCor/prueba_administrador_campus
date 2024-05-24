/**
 * 
 */
package co.edu.usco.lcms.librerias;

/**
 * @author ANDRES-GPIE
 *
 */
public class DividirNombresApellidos {
	public static String[] dividir(String nom) {
		if (nom.indexOf(' ') == -1) {
			return new String[] { nom, "" };
		}
		String nom1 = nom.substring(0, nom.indexOf(' '));
		String nom2 = nom.substring(nom.indexOf(' ') + 1, nom.length());
		return new String[] { nom1, nom2 };
	}
}
