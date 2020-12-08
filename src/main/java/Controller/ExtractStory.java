package Controller;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.Chuong;
import model.TheLoaiTruyen;
import model.Truyen;

public class ExtractStory {
	private static Document doc = null;
	private static Elements elements = null;
	

// truyền vào link và thẻ cần lấy nôi dung của trang web lấy ra nội dung
	public Elements getContentFromLink(String url, String tag) {
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {

			e.printStackTrace();
		}
		elements = doc.select(tag);
		return elements;
	}
	
	

	public void writeFileJson(TheLoaiTruyen tlt) {
		File_Writer f = new File_Writer();
		JSONObject obj = new JSONObject();
		obj.put("ten_the_loai_truyen", tlt.getTenTheLoai());
		obj.put("link_the_loai_truyen", tlt.getLinkTheLoai());
		JSONArray arr = new JSONArray();

		ArrayList<Truyen> truyenArrayList = tlt.getTruyen();
		for (Truyen t : truyenArrayList) {
			JSONObject obj1 = new JSONObject();
			obj1.put("ten_truyen", t.getTenTruyen());
			obj1.put("link_truyen", t.getLinkTruyen());
			JSONArray arr1 = new JSONArray();

			ArrayList<Chuong> chuongArrayList = t.getChuong();
			for (Chuong c : chuongArrayList) {
				JSONObject obj2 = new JSONObject();
				obj2.put("ten_chuong", c.getTenChuong());
				obj2.put("link_chuong", c.getLinkChuong());
				//obj2.put("content_chuong", c.getContentChuong());
				arr1.add(obj2);
			}
			obj1.put("Chuong", arr1);

			arr.add(obj1);
		}
		obj.put("the_loai", arr);
		String fileName = tlt.getTenTheLoai() + "__" + tlt.getTruyen().get(0).getTenTruyen() + ".json";
		f.writeFileJson(fileName, obj.toJSONString());
	}
	
	public void saveImage(String filename, String urlString)
	        throws MalformedURLException, IOException {
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

	public ArrayList getListChuong(Elements chuong) {
		String tenChuong;
		String linkChuong;
		String contentChuong;
		String path = "C:\\Users\\admin\\Desktop\\LapTrinhMang\\Content\\";
		String fileName = "";
		File_Writer fw = new File_Writer();
		ArrayList chuongArrayList = new ArrayList<Chuong>();
		for (Element z : chuong) {
			tenChuong = z.html();
			linkChuong = z.attr("href");
			contentChuong = "";
			// lấy nội dung chương ghi file
			Elements contentChuongElements = this.getContentFromLink(linkChuong, "div#chapter div#chapter-content");
			for(Element i: contentChuongElements) {
				String content = i.html().trim().replace("<br>", "");
				fileName = tenChuong.replace("?", "")+".txt";
				fw.writeFileJson(path, fileName, content);
			}
			System.out.println("ten chuong: " + tenChuong + "\t" + "link chuong: " + linkChuong + "\n");
			Chuong c = new Chuong(tenChuong, linkChuong, contentChuong);
			chuongArrayList.add(c);
		}
		return chuongArrayList;
	}

	public ArrayList getListTruyen(Elements truyen, String nameStory) {
		String tenTruyen;
		String linkTruyen;

		ArrayList truyenArrayList = new ArrayList<Truyen>();
		System.out.println("bat dau get list truyen");
		for (Element j : truyen) {
			tenTruyen = j.attr("title").trim();
			linkTruyen = j.attr("href").trim();
			if (tenTruyen.equalsIgnoreCase(nameStory) || (nameStory.equals(""))) {
				System.out.println("Tìm thấy tên truyện tương ứng");
				System.out.println("ten truyen: " + tenTruyen + "\t" + "link truyen: " + linkTruyen + "\n");

				Elements chuong = this.getContentFromLink(linkTruyen, "div#chapters ul.chapters li.vip-0 a");
				Elements pageOrther = this.getContentFromLink(linkTruyen, "div.pagination-control ul.pagination li a");
				Truyen t = new Truyen(tenTruyen, linkTruyen, this.getListChuong(chuong));
				
				for(Element k:pageOrther) {
					String linkPage = k.attr("href").trim();
					String namePage = k.html().trim();
					String titlePage = k.attr("title").trim();
					if( !namePage.equals("1") && !titlePage.equals("Trang sau")) {
						Elements chuongOrther = this.getContentFromLink(linkPage, "div#chapters ul.chapters li.vip-0 a");
						ArrayList<Chuong> chuongArrayList = new ArrayList<Chuong>();
						chuongArrayList = this.getListChuong(chuongOrther);
						for(Chuong c: chuongArrayList) {
							t.getChuong().add(c);
						}
					}
				}		
				truyenArrayList.add(t);

			} else {
				System.out.println("không tìm thấy truyện tương ứng");
			}
		}
		return truyenArrayList;

	}

	public void extractStoryByName(String url, String nameStory, String categoryStory) {
		url = "https://webtruyen.com";
		String tag = "section.card-box div.categories a";
		String theLoaiTruyen;
		String linkTheLoaiTruyen;
		elements = this.getContentFromLink(url, tag);
		for (Element i : elements) {
			theLoaiTruyen = i.html();
			linkTheLoaiTruyen = i.attr("href");

//			kiểm tra tên thể loại truyện
			if (theLoaiTruyen.equalsIgnoreCase(categoryStory) || (categoryStory.equals(""))) {
				//
				System.out.println("tìm kiếm danh mục truyện");

				System.out.println("===========> ten the loai truyen: " + theLoaiTruyen + "\t"
						+ "link the loai truyen: " + linkTheLoaiTruyen + "\n");
				Elements truyen = this.getContentFromLink(linkTheLoaiTruyen, "ul li.story-list a.thumb");
				TheLoaiTruyen tlt = new TheLoaiTruyen(theLoaiTruyen, linkTheLoaiTruyen,
						this.getListTruyen(truyen, nameStory));

				// ghi ra file Json
				this.writeFileJson(tlt);
			}
		}
	}

	public void extractImageStory(String url, String nameStory, String categoryStory) throws MalformedURLException, IOException {
		url = "https://webtruyen.com";
		String tag = "section.card-box div.categories a";
		String theLoaiTruyen;
		String linkTheLoaiTruyen;
		String linkStoryDetail;
		String linkImage;
		String titleImage;
		elements = this.getContentFromLink(url, tag);
		for (Element i : elements) {
			theLoaiTruyen = i.html();
			linkTheLoaiTruyen = i.attr("href");
			if (theLoaiTruyen.equalsIgnoreCase(categoryStory) || (categoryStory.equals(""))) {
				System.out.println("tìm kiếm danh mục truyện");

				System.out.println("===========> ten the loai truyen: " + theLoaiTruyen + "\t"
						+ "link the loai truyen: " + linkTheLoaiTruyen + "\n");
				Elements truyen = this.getContentFromLink(linkTheLoaiTruyen, "ul li.story-list a.thumb");
				for(Element j : truyen) {
					linkStoryDetail = j.attr("href");
					Elements truyen1 = this.getContentFromLink(linkStoryDetail, "div#story-detail div.col1 div.thumb img");
					for(Element z : truyen1) {
						linkImage = z.attr("src");
						titleImage = z.attr("alt");
						String fileName = "C:\\Users\\admin\\Desktop\\LapTrinhMang\\Anh\\"+titleImage.trim()+".jpg";
						this.saveImage(fileName, linkImage);
					}
				}
			}
		}
			
	}
	public void extractImage(String url, String tagImage) throws MalformedURLException, IOException {
		System.out.println("Vào extrect image");
		url = "https://totoshop.vn/do-nam-pc72882.html";
		tagImage = "div.product-image div.product-image__img-wrapper a img";
		elements = this.getContentFromLink(url, tagImage);
		int count = 0;
		for (Element i : elements) {
			count++;
			System.out.println("anh thu = "+count);
			System.out.println(i.toString());
			String linkImage = i.attr("data-src");
			String titleImage = "a"+count;
			String fileName = "C:\\Users\\admin\\Desktop\\LapTrinhMang\\Anh\\"+titleImage.trim()+".jpg";
			
			this.saveImage(fileName, linkImage);
		}
		
	}

}
