package ac.server.dto;

public class StudentDto {
	private String id;
	private String Student_name;
	private String email;
	private String admission;
	private int course_code;
	
	public StudentDto() {
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudent_name() {
		return Student_name;
	}
	public void setStudent_name(String student_name) {
		Student_name = student_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAdmission() {
		return admission;
	}
	public void setAdmission(String admission) {
		this.admission = admission;
	}
	public int getCourse_code() {
		return course_code;
	}
	public void setCourse_code(int course_code) {
		this.course_code = course_code;
	}
	@Override
	public String toString() {
		return "StudentDto [id=" + id + ", Student_name=" + Student_name + ", email=" + email + ", admission="
				+ admission + ", course_code=" + course_code + "]";
	}
	
	
}
