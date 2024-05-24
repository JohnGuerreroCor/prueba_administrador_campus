/**
 * 
 */
package co.edu.usco.lcms.utility;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jankarlos Diaz Vieda
 *
 */

public class FileUtil {

	public static String paths[];

	public FileUtil() {

	}

	public FileUtil(String[] paths) {
		FileUtil.paths = paths;
	}

	public String SaveFile(MultipartFile file, String name, int posicion) throws IOException {
		File carpeta = new File(paths[posicion]);
		if (!carpeta.exists()) {
			if (carpeta.mkdirs()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}

		byte[] bytes = file.getBytes();
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(paths[posicion] + name)));
		stream.write(bytes);
		stream.close();

		return paths[posicion] + name;
	}

	public static String[] getPath() {
		return paths;
	}

	public static void setPath(String[] paths) {
		FileUtil.paths = paths;
	}
}
