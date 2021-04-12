package ac.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ac.server.dbconnection.DbConnection;
import ac.server.dto.BoardDto;

public class MainDao {
	// 싱글톤
	private static MainDao dao = new MainDao();

	public static MainDao getInstance() {
		return dao;
	}

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 공지5줄만 출력
	public ArrayList<BoardDto> Notice() throws SQLException {
		try {
			conn = DbConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<BoardDto> list = new ArrayList<BoardDto>();

		BoardDto member = null;
		pstmt = conn.prepareStatement("select b.* from ( select rownum rn , board_title\r\n" + 
				"from board\r\n" + 
				"order by rn desc ) b\r\n" + 
				"where rownum < 6");
		rs = pstmt.executeQuery();
		while (rs.next()) {
			member = new BoardDto();
			member.setRownum(rs.getInt(1));
			member.setBoard_title(rs.getString(2));
			list.add(member);
		}
		return list;
	}
}