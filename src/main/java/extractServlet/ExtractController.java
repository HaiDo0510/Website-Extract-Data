package extractServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Controller.ExtractStory;
import File.FileIO;

@WebServlet(name="ExtractController", urlPatterns={"/ExtractController"})
public class ExtractController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Document doc = null;
	private Elements elements = null;
	private ExtractStory ex;
	private FileIO fileIO;
    
    public ExtractController() {
    	ex = new ExtractStory();
    	fileIO = new FileIO();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("link").trim();
		String tag =  request.getParameter("tag").trim();
		String categoryStory = request.getParameter("categoryStory").trim();
		String nameStory = request.getParameter("nameStory").trim();
		String tagImage = request.getParameter("tagImage").trim();
		
		if( tag.equals("")  && tagImage.equals("")  ) {	
			System.out.println("tag == extract Story");
			ex.extractStoryByName(url, nameStory, categoryStory);
			response.sendRedirect("./DataEmpty.jsp");
		}
		if(tag.equals("image")&& tagImage.equals("")) {
			System.out.println("tag = extract imageStory");
			ex.extractImageStory(url, nameStory, categoryStory);
			response.sendRedirect("./DataEmpty.jsp");
		}
		if(!tagImage.equals("")) {
			System.out.println("tag = extract image");
			ex.extractImage(url, tagImage);
			response.sendRedirect("./DataEmpty.jsp");
		}
		if(tag != null && categoryStory == null && nameStory == null && tagImage.equals("")) {
			System.out.println("tag != extract ramdom web");
			doc = Jsoup.connect(url).get();
			elements = doc.select(tag);
			String dataEnd = "";
			for(Element i:elements) {
				String data = i.attr("title");
				dataEnd = dataEnd + data +"<br>";
			}
			
			//fileIO.writeFile("LapTrinhMang.txt", data);
			fileIO.writeFile1("C:\\Users\\admin\\Desktop\\workspace-esclipse\\Extract_Data_On_Web\\src\\main\\webapp\\Data.jsp", dataEnd);
			response.sendRedirect("./Data.jsp");
		} 
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
