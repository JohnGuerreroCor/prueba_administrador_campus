/**
 * 
 */
package co.edu.usco.lcms.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ANDRES-GPIE
 *
 */
public class Encriptar {

	private static String convToHex(byte[] data) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	public static String toSha1String(String text) {
		MessageDigest md;
		byte[] sha1hash = new byte[40];
		try {
			md = MessageDigest.getInstance("SHA-1");
			sha1hash = md.digest((text).getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return convToHex(sha1hash);
	}

	public static byte[] toSha1Byte(String text) {
		MessageDigest md;
		byte[] sha1hash = new byte[40];
		try {
			md = MessageDigest.getInstance("SHA-1");
			sha1hash = md.digest((text).getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sha1hash;
	}

	public static String toMD5String(byte[] text) {
		MessageDigest md;
		byte[] sha1hash = new byte[40];
		try {
			md = MessageDigest.getInstance("MD5");
			sha1hash = md.digest(text);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return convToHex(sha1hash);
	}
}
