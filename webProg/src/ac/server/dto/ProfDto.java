package ac.server.dto;

public class ProfDto {
	private String id;
	private String prof_name;
	private String email;
	private String phone;
	
	public ProfDto() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getProf_name() {
		return prof_name;
	}

	public void setProf_name(String prof_name) {
		this.prof_name = prof_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
