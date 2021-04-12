package ac.server.dto;

public class MenuDto {
	private int menu_num;
	private String menu_title;
	private String url;
	private int seq;
	
	public MenuDto() {
		
	}
	public int getMenu_num() {
		return menu_num;
	}
	public void setMenu_num(int menu_num) {
		this.menu_num = menu_num;
	}
	public String getMenu_title() {
		return menu_title;
	}
	public void setMenu_title(String menu_title) {
		this.menu_title = menu_title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	
}
