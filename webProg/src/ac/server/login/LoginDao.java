package ac.server.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ac.server.dbconnection.DbConnection;
import ac.server.dto.LoginDto;
import ac.server.dto.StudentDto;

public class LoginDao {

	// 등록
	Connection conn = null;
	PreparedStatement psmt;
	ResultSet rs = null;

	// 싱글톤 : static 필드
	static LoginDao instance;

	public static LoginDao getInstance() {
		if (instance == null)
			instance = new LoginDao();
		return instance;
	}

	public void LoginInsert(Connection conn, LoginDto student) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement("insert into login values(?,?)");
		pstmt.setString(1, student.getId());
		pstmt.setString(2, student.getPw());
		pstmt.executeUpdate();
	}

	public boolean loginCheck(String id, String pw) {
		boolean result = false;
		try {
			conn = DbConnection.getConnection();
			String sql = "select id from login where id=? " + "and pw=? ";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);
			rs = psmt.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}

		return result;
	}

}
