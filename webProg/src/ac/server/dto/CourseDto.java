package ac.server.dto;

import java.util.Date;

public class CourseDto {
	private int course_code;
	private Integer training_list;
	private Integer course_list;
	private String training_list2;
	private String course_list2;
	private Date recruit_date;
	private Date start_date;
	private Date end_date;
	private int fee;
	private String class_time;
	private String class_date;
	private int accept_num;
	private int regist_num;
	private String prof_name;
	private String qual;
	private String edu_context;
	private String gradu_after;
	private String propo_paper;
	private String image;
	private String course_name;
	//private String searchKey;	
	
	public CourseDto() {
		
	}

	public String getTraining_list2() {
		return training_list2;
	}

	public void setTraining_list2(String training_list2) {
		this.training_list2 = training_list2;
	}

	public String getCourse_list2() {
		return course_list2;
	}

	public void setCourse_list2(String course_list2) {
		this.course_list2 = course_list2;
	}

	public void setTraining_list(Integer training_list) {
		this.training_list = training_list;
	}

	public void setCourse_list(Integer course_list) {
		this.course_list = course_list;
	}

	public int getCourse_code() {
		return course_code;
	}

	public void setCourse_code(int course_code) {
		this.course_code = course_code;
	}

	public Integer getTraining_list() {
		return training_list;
	}

	public void setTraining_list(int training_list) {
		this.training_list = training_list;
	}

	public Integer getCourse_list() {
		return course_list;
	}

	public void setCourse_list(int course_list) {
		this.course_list = course_list;
	}

	public Date getRecruit_date() {
		return recruit_date;
	}

	public void setRecruit_date(Date recruit_date) {
		this.recruit_date = recruit_date;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public String getClass_time() {
		return class_time;
	}

	public void setClass_time(String class_time) {
		this.class_time = class_time;
	}

	public String getClass_date() {
		return class_date;
	}

	public void setClass_date(String class_date) {
		this.class_date = class_date;
	}

	public int getAccept_num() {
		return accept_num;
	}

	public void setAccept_num(int accept_num) {
		this.accept_num = accept_num;
	}

	public int getRegist_num() {
		return regist_num;
	}

	public void setRegist_num(int regist_num) {
		this.regist_num = regist_num;
	}

	public String getProf_name() {
		return prof_name;
	}

	public void setProf_name(String prof_name) {
		this.prof_name = prof_name;
	}

	public String getQual() {
		return qual;
	}

	public void setQual(String qual) {
		this.qual = qual;
	}

	public String getEdu_context() {
		return edu_context;
	}

	public void setEdu_context(String edu_context) {
		this.edu_context = edu_context;
	}

	public String getGradu_after() {
		return gradu_after;
	}

	public void setGradu_after(String gradu_after) {
		this.gradu_after = gradu_after;
	}

	public String getPropo_paper() {
		return propo_paper;
	}

	public void setPropo_paper(String propo_paper) {
		this.propo_paper = propo_paper;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	@Override
	public String toString() {
		return "CourseDto [course_code=" + course_code + ", training_list=" + training_list + ", course_list="
				+ course_list + ", recruit_date=" + recruit_date + ", start_date=" + start_date + ", end_date="
				+ end_date + ", fee=" + fee + ", class_time=" + class_time + ", class_date=" + class_date
				+ ", accept_num=" + accept_num + ", regist_num=" + regist_num + ", prof_name=" + prof_name + ", qual="
				+ qual + ", edu_context=" + edu_context + ", gradu_after=" + gradu_after + ", propo_paper="
				+ propo_paper + ", image=" + image + "]";
	}

	
}
