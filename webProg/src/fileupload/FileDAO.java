package fileupload;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.DAO;
import common.DbCon;

public class FileDAO extends DAO {

	// 파일업로드
	public int uploadFile(String author, String title, String file) {
//		Connection conn = DbCon.connect();
		connect();
		int r = 0;
		String sql = "insert into fileboard values((select nvl(max(num)+1,1) from fileboard), ?, ?, ?, to_char(sysdate,'YYYY-MM-DD'))";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, author);
			pstmt.setString(2, title);
			pstmt.setString(3, file);
			r = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return r;
	}

	public FileVO getInsertKeyVal(String author, String title, String fileName) {
		connect();
		String selectKey = "select nvl(max(num)+1,1) from fileboard";
		String insertSql = "insert into fileboard values(?, ?, ?, ?, to_char(sysdate,'YYYY-MM-DD'))";
		String selectSql = "select * from fileboard where num = ?";
		int key = 0;
		FileVO vo = new FileVO();
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(selectKey);
			if (rs.next()) {
				key = rs.getInt(1);
			}
			pstmt = conn.prepareStatement(insertSql);
			pstmt.setInt(1, key);
			pstmt.setString(2, author);
			pstmt.setString(3, title);
			pstmt.setString(4, fileName);

			int r = pstmt.executeUpdate();
			System.out.println(r + "건 입력. \n" + "key: " + key);

			pstmt = conn.prepareStatement(selectSql);
			pstmt.setInt(1, key);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo.setNum(rs.getInt("num"));
				vo.setAuthor(rs.getString("author"));
				vo.setTitle(rs.getString("title"));
				vo.setDay(rs.getString("day"));
				vo.setFile(rs.getString("filename"));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			disconnect();

		}

		return vo;
	}

	public ArrayList<FileVO> selectAll() {
		connect();
		ArrayList<FileVO> list = new ArrayList<>();
		FileVO vo;
		String sql = "select num, author, title, filename, day from fileboard order by num";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				vo = new FileVO(rs.getInt("num"), rs.getString("author"), rs.getString("title"),
						rs.getString("filename"), rs.getString("day"));
				list.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	public FileVO getSearch(int num) {
		connect();
		String sql = "select * from fileboard where num = ?";
		FileVO vo = new FileVO();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo.setAuthor(rs.getString("author"));
				vo.setDay(rs.getString("day"));
				vo.setFile(rs.getString("fileName"));
				vo.setNum(rs.getInt("num"));
				vo.setTitle(rs.getString("title"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return vo;
	}

}
