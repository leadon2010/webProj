package ac.server.dto;

import java.util.Date;

public class AttitudeDto {
	private String name;
	private	String day;
	private String id;
	
	public AttitudeDto() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
