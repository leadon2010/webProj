package ac.server.dto;

public class BoardDto {
	private int board_num;
	private String board_title;
	private String read_right;
	private String write_right;
	private String delete_right;
	private String secret_right;
	private int menu_num;
	private int rownum;
	
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public BoardDto() {
		
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getRead_right() {
		return read_right;
	}
	public void setRead_right(String read_right) {
		this.read_right = read_right;
	}
	public String getWrite_right() {
		return write_right;
	}
	public void setWrite_right(String write_right) {
		this.write_right = write_right;
	}
	public String getDelete_right() {
		return delete_right;
	}
	public void setDelete_right(String delete_right) {
		this.delete_right = delete_right;
	}
	public String getSecret_right() {
		return secret_right;
	}
	public void setSecret_right(String secret_right) {
		this.secret_right = secret_right;
	}
	public int getMenu_num() {
		return menu_num;
	}
	public void setMenu_num(int menu_num) {
		this.menu_num = menu_num;
	}
	
}
