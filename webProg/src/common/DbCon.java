package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbCon {

	static Connection conn = null;
	static PreparedStatement pstmt = null;

	/*
	 * Oracle 연결정보
	 */
	static String jdbc_driver = "oracle.jdbc.driver.OracleDriver";
	static String jdbc_url = "jdbc:oracle:thin:@localhost:1521:xe";
	static String user = "hr";
	static String passwd = "hr";

	/* MySQL 연결정보 */
	// String jdbc_driver = "com.mysql.jdbc.Driver";
	// String jdbc_url = "jdbc:mysql://127.0.0.1:3306/jspdb";

	// DB연결 메서드
	public static Connection connect() {
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url, user, passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void disconnect() {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
