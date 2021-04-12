package ac.server.dto;

public class CourseKindDto {
	private int course_num;
	private String course_name;
	
	public CourseKindDto() {
		
	}
	public int getCourse_num() {
		return course_num;
	}
	public void setCourse_num(int course_num) {
		this.course_num = course_num;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
}
