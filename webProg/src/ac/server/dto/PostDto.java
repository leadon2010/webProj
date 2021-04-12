package ac.server.dto;

import java.sql.Date;

public class PostDto {
	private int post_num;
	private String content;
	private String writer;
	private Date write_date;
	private String secret_check;
	private int board_num;
	private String answer_check;
	private String post_title;
	private int rownum;
	
	
	
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public String getPost_title() {
		return post_title;
	}
	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}
	public PostDto() {
		
	}
	public int getPost_num() {
		return post_num;
	}
	public void setPost_num(int post_num) {
		this.post_num = post_num;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getWrite_date() {
		return write_date;
	}
	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}
	public String getSecret_check() {
		return secret_check;
	}
	public void setSecret_check(String secret_check) {
		this.secret_check = secret_check;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getAnswer_check() {
		return answer_check;
	}
	public void setAnswer_check(String answer_check) {
		this.answer_check = answer_check;
	}
	
	
}
