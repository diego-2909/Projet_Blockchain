package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	public static void createFile(File file) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
	}
	
	public static void saveFile(File file, String string) throws IOException {
		createFile(file);
		final FileWriter fw = new FileWriter(file);
		fw.write(string);
		fw.flush();
		fw.close();
	}
	
	public static String loadFile(File file) throws IOException {
		
		if (file.exists()) {
			final BufferedReader reader = new BufferedReader(new FileReader(file));
			final StringBuilder string = new StringBuilder();
			String line;
			
			while((line = reader.readLine()) != null) {
				string.append(line);
			}
			
			reader.close();
			return string.toString();
 		}
		return "";
	}
}
