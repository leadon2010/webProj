package ac.server.dto;

public class ManagermentTraningDto {
	private String course;
	private int course_day;
	private int progress;
	private int attend;
	private String id;
	private int course_code;
	private int month;
	
	public ManagermentTraningDto() {
		
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public int getCourse_day() {
		return course_day;
	}
	public void setCourse_day(int course_day) {
		this.course_day = course_day;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public int getAttend() {
		return attend;
	}
	public void setAttend(int attend) {
		this.attend = attend;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCourse_code() {
		return course_code;
	}
	public void setCourse_code(int course_code) {
		this.course_code = course_code;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
}
