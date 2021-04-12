package ac.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ac.server.dto.BoardDto;
import ac.server.service.boardService;

public class BoardDao {

	private static BoardDao Boarddao = new BoardDao();

	public static BoardDao getInstance() {
		return Boarddao;
	}
	//게시판삽입
		public void BoardInsert(Connection conn, BoardDto Boarddto) throws SQLException {
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement("insert into Board(board_num, board_title, read_right, write_right, delete_right, secret_right) values(?,?,?,?,?,?,?)");
			pstmt.setInt(1, Boarddto.getBoard_num());
			pstmt.setString(2, Boarddto.getBoard_title());
			pstmt.setString(3, Boarddto.getRead_right());
			pstmt.setString(4, Boarddto.getWrite_right());
			pstmt.setString(5, Boarddto.getSecret_right());
			pstmt.executeUpdate();
		}
		//게시판검색
		public BoardDto BoardSelect(Connection conn, int id) throws SQLException {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			BoardDto Boarddto = null;

			pstmt = conn.prepareStatement("select board_title, read_right, write_right, delete_right, secret_right from Board where board_num = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Boarddto = new BoardDto();
				Boarddto.setBoard_num(rs.getInt(1));
			}
			pstmt.executeUpdate();
			return Boarddto;
		}
		//게시판수정
		public void BoardUpdate(Connection conn, BoardDto Boarddto) throws SQLException {
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement("update Board set board_title, read_right, write_right, delete_right, secret_right where board_num=?");
			pstmt.setString(1, Boarddto.getBoard_title());
			pstmt.setString(2, Boarddto.getRead_right());
			pstmt.setString(3, Boarddto.getWrite_right());
			pstmt.setString(4, Boarddto.getSecret_right());
			pstmt.setInt(5, Boarddto.getBoard_num());
			pstmt.executeUpdate();
		}
		

		//게시판삭제
		public void BoardDelete(Connection conn, int id) throws SQLException {
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement("delete from board where board_num=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}
		
		//게시판 전체 조회
			public ArrayList<BoardDto> BoardList(Connection conn, int num) throws SQLException{

				ArrayList<BoardDto> list = new ArrayList<BoardDto>();

				PreparedStatement pstmt = null;
				ResultSet rs = null;

				BoardDto Board = null;

					pstmt = conn.prepareStatement("select board_title, board_num, read_right, write_right, delete_right, secret_right, menu_num from board where menu_num=? order by board_num");
					pstmt.setInt(1, num);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						Board = new BoardDto();
						Board.setBoard_title(rs.getString(1));
						Board.setBoard_num(rs.getInt(2));
						Board.setRead_right(rs.getString(3));
						Board.setWrite_right(rs.getString(4));
						Board.setDelete_right(rs.getString(5));
						Board.setSecret_right(rs.getString(6));
						Board.setMenu_num(rs.getInt(7));
						list.add(Board);
					}
				return list;
			}
			
			public ArrayList<BoardDto> BoardList(Connection conn) throws SQLException{

				ArrayList<BoardDto> list = new ArrayList<BoardDto>();

				PreparedStatement pstmt = null;
				ResultSet rs = null;

				BoardDto Board = null;

					pstmt = conn.prepareStatement("select board_title, board_num, read_right, write_right, delete_right, secret_right, menu_num from board order by board_num");
					rs = pstmt.executeQuery();
					while (rs.next()) {
						Board = new BoardDto();
						Board.setBoard_title(rs.getString(1));
						Board.setBoard_num(rs.getInt(2));
						Board.setRead_right(rs.getString(3));
						Board.setWrite_right(rs.getString(4));
						Board.setDelete_right(rs.getString(5));
						Board.setSecret_right(rs.getString(6));
						Board.setMenu_num(rs.getInt(7));
						list.add(Board);
					}
				return list;
			}
}
