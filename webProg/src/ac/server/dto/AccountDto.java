package ac.server.dto;

import java.util.Date;

public class AccountDto {
	private int num;
	private Date day;
	private String context;
	private int income;
	private int expend;
	private String etc;
	
	public AccountDto() {
		
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public int getIncome() {
		return income;
	}
	public void setIncome(int income) {
		this.income = income;
	}
	public int getExpend() {
		return expend;
	}
	public void setExpend(int expend) {
		this.expend = expend;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
}
