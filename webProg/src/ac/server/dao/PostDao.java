package ac.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ac.server.dbconnection.DbConnection;
import ac.server.dto.BoardDto;
import ac.server.dto.PostDto;

public class PostDao {

	private static PostDao Postdao = new PostDao();

	public static PostDao getInstance() {
		return Postdao;
	}

	public void PostInsert(Connection conn, PostDto Postdto) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(
				"insert into Post(post_num, content, writer, write_date, secret_check, board_num, answer_check, post_title) values(?,?,?,?,?,?,?,?)");
		pstmt.setInt(1, Postdto.getPost_num());
		pstmt.setString(2, Postdto.getContent());
		pstmt.setString(3, Postdto.getWriter());
		pstmt.setDate(4, Postdto.getWrite_date());
		pstmt.setString(5, Postdto.getSecret_check());
		pstmt.setInt(6, Postdto.getBoard_num());
		pstmt.setString(7, Postdto.getAnswer_check());
		pstmt.setString(8, Postdto.getPost_title());
		pstmt.executeUpdate();
	}

	public PostDto PostSelect(Connection conn, int id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PostDto postdto = null;

		pstmt = conn.prepareStatement(
				"select content, writer, write_date, secret_check, board_num, answer_check, post_title from post where post_num = ?");
		pstmt.setInt(1, id);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			postdto = new PostDto();
			postdto.setContent(rs.getString(1));
			postdto.setWriter(rs.getString(2));
			postdto.setWrite_date(rs.getDate(3));
			postdto.setSecret_check(rs.getString(4));
			postdto.setBoard_num(rs.getInt(5));
			postdto.setAnswer_check(rs.getString(6));
			postdto.setPost_title(rs.getString(7));
			postdto.setPost_num(id);
		}
		return postdto;
	}

	public boolean PostUpdate(Connection conn, PostDto Postdto) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(
				"update post set content=?, writer=?, write_date=?, secret_check=?, board_num=?, answer_check=?, post_title=? where post_num=?");
		pstmt.setString(1, Postdto.getContent());
		pstmt.setString(2, Postdto.getWriter());
		pstmt.setDate(3, Postdto.getWrite_date());
		pstmt.setString(4, Postdto.getSecret_check());
		pstmt.setInt(5, Postdto.getBoard_num());
		pstmt.setString(6, Postdto.getAnswer_check());
		pstmt.setString(7, Postdto.getPost_title());
		pstmt.setInt(8, Postdto.getPost_num());
		pstmt.executeUpdate();
		DbConnection.close(conn);
		return true;
	}

	public void PostDelete(Connection conn, int id) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement("delete from post where post_num=?");
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
	}

	public ArrayList<PostDto> PostList(Connection conn, int num) throws SQLException {

		ArrayList<PostDto> list = new ArrayList<PostDto>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		PostDto postdto = null;

		pstmt = conn.prepareStatement(
				"select content, writer, write_date, secret_check, board_num, answer_check, post_title,post_num from post where board_num=? order by post_num desc");
		pstmt.setInt(1, num);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			postdto = new PostDto();
			postdto.setContent(rs.getString(1));
			postdto.setWriter(rs.getString(2));
			postdto.setWrite_date(rs.getDate(3));
			postdto.setSecret_check(rs.getString(4));
			postdto.setBoard_num(rs.getInt(5));
			postdto.setAnswer_check(rs.getString(6));
			postdto.setPost_title(rs.getString(7));
			postdto.setPost_num(rs.getInt(8));

			list.add(postdto);
		}
		return list;
	}

	public ArrayList<PostDto> PostListMain(Connection conn) throws SQLException {

		ArrayList<PostDto> list = new ArrayList<PostDto>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		PostDto postdto = null;

		pstmt = conn.prepareStatement(
				"select b.* from ( select rownum rn , post_title, post_num "+ "from post  where board_num=1 "+"order by rn desc ) b "
						+ "where rownum < 6");
		rs = pstmt.executeQuery();
		while (rs.next()) {
			postdto = new PostDto();
			postdto.setRownum(rs.getInt(1));
			postdto.setPost_title(rs.getString(2));
			postdto.setPost_num(rs.getInt(3));
			list.add(postdto);
		}
		return list;
	}
}
