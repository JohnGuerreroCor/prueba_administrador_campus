package co.edu.usco.lcms.utility;

public class UtilGenerarPinClave {

	public static String generarPin() {
		String pin = "";

		char[] elementos = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
				'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		for (int i = 0; i < 36; i++) {
			int el = (int) (Math.random() * 36);
			pin += (char) elementos[el];
		}

		return pin;
	}

}
