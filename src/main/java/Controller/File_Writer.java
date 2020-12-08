package Controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class File_Writer {
	
	
	public void writeFileJson(String fileName, String json) {
		File file = new File("C:\\Users\\admin\\Desktop\\LapTrinhMang\\"+fileName);
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write(json);
			fw.close();
			System.out.println("success!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeFileJson(String path, String fileName, String json) {
		File file = new File(path+fileName);
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write(json);
			fw.close();
			System.out.println("success!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void saveImage( String nameImage, String urlString)
	        throws MalformedURLException, IOException {
		
		String filename = "C:\\Users\\admin\\Desktop\\LapTrinhMang\\Anh\\" +nameImage+ ".png";
	    BufferedInputStream in = null;
	    FileOutputStream fout = null;
	    try {
	        in = new BufferedInputStream(new URL(urlString).openStream());
	        fout = new FileOutputStream(filename);
	 
	        final byte data[] = new byte[1024];
	        int count;
	        while ((count = in.read(data, 0, 1024)) != -1) {
	            fout.write(data, 0, count);
	        }
	    } finally {
	        if (in != null) {
	            in.close();
	        }
	        if (fout != null) {
	            fout.close();
	        }
	    }
	}
}
