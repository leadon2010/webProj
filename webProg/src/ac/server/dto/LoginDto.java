package ac.server.dto;

public class LoginDto {
	private String id;
	private String pw;
	
	public LoginDto() {
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}	
}
