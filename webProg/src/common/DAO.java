package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.sql.DataSource;

public class DAO {
	protected Connection conn;
	protected Statement stmt;
	protected PreparedStatement pstmt;
	protected ResultSet rs;

	protected void connect() {
		try {
			// 1. 드라이버로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. DB 연결
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "hr", "hr");

			// connection pool에서 connection을 할당
//			Context initContext = new InitialContext();
//			Context envContext = (Context) initContext.lookup("java:/comp/env");
//			DataSource ds = (DataSource) envContext.lookup("jdbc/oracle_javacafe");
//			conn = ds.getConnection();

			if (conn != null) {
				System.out.println("연결 성공");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void disconnect() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
