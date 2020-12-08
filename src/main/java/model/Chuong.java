package model;

public class Chuong {
	private String tenChuong;
	private String linkChuong;
	private String contentChuong;
	
	public Chuong() {
		
	}

	public Chuong(String tenChuong, String linkChuong, String contentChuong) {
		super();
		this.tenChuong = tenChuong;
		this.linkChuong = linkChuong;
		this.contentChuong = contentChuong;
	}

	public String getTenChuong() {
		return tenChuong;
	}

	public void setTenChuong(String tenChuong) {
		this.tenChuong = tenChuong;
	}

	public String getLinkChuong() {
		return linkChuong;
	}

	public void setLinkChuong(String linkChuong) {
		this.linkChuong = linkChuong;
	}

	public String getContentChuong() {
		return contentChuong;
	}

	public void setContentChuong(String contentChuong) {
		this.contentChuong = contentChuong;
	}

	@Override
	public String toString() {
		return "Chuong [tenChuong=" + tenChuong + ", linkChuong=" + linkChuong + ", contentChuong=" + contentChuong
				+ "]";
	}
	
	
}
