package PostItNote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileCheckers {

	public boolean checkDirExists(String dir) {
		File directory = new File(dir);
		if (!directory.exists()) {
			directory.mkdir();
			return false;
		}
		return true;
	}

	public boolean checkFileExists(String dir, String fileName) throws IOException {
		File file = new File(dir + fileName);
		if (!file.isFile()) {
			// create file
			file.createNewFile();
			return false;
		}
		return true;
	}

	public void writeFile(String dir, String fileName, String value) {

		String path = dir + fileName;
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			bw.write(value);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public String readFile(String dir, String fileName) {
		String path = dir + fileName;
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			String wholeString = "";
			while (line != null) {
				wholeString += line + "\n";
				line = br.readLine();
			}
			br.close();
			if (wholeString == "") {
				return "";
			}
			return wholeString.substring(0, wholeString.length() - 1);

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		return "";
	}

	public File[] getFileList(String dir) {
		File fileDir = new File(dir);
		return fileDir.listFiles();
	}

	public void deleteFile(String path) {
		File file = new File(path);
		file.delete();
	}

}
