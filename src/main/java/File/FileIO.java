package File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {
	private File file;

	public void writeFile(String fileName, String data) {
		File file = new File("C:\\Users\\admin\\Desktop\\" + fileName);
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(data);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void writeFile1(String fileNameSource, String data) {
		File file = new File(fileNameSource);
		String end;
		try {
			FileWriter fw = new FileWriter(file);
			end = "<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>\n<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head></head>\r\n" + 
					"<body>" + data+ "</body>\r\n" + 
							"</html>";
			fw.write(end);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void readFile(String fileName) {
		File file = new File("C:\\Users\\admin\\Desktop\\" + fileName);
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			// Bước 3: Đóng luồng
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
