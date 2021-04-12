package ac.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import ac.server.dao.BoardDao;
import ac.server.dbconnection.DbConnection;
import ac.server.dto.BoardDto;


public class boardService {
	public BoardDao dao = BoardDao.getInstance();
	
	private static boardService service = new boardService();
	
	public static boardService getInstance() {
		return service;
	}
	
	// 전체 조회
		public ArrayList<BoardDto> selectAll(int num) throws Exception {
			Connection conn = DbConnection.getConnection();
			ArrayList<BoardDto> list = null;
			try {
				list = dao.BoardList(conn, num);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DbConnection.close(conn);
			}

			return list;
		}

		// 전체 조회
				public ArrayList<BoardDto> selectAll() throws Exception {
					Connection conn = DbConnection.getConnection();
					ArrayList<BoardDto> list = null;
					try {
						list = dao.BoardList(conn);
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						DbConnection.close(conn);
					}

					return list;
				}
		// 삽입
		public void BoardInsert(BoardDto dto) throws Exception {
			Connection conn = DbConnection.getConnection();
			try {
				conn.setAutoCommit(false);
				dao.BoardInsert(conn, dto);
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DbConnection.close(conn);
			}
		}

		// 검색
		public BoardDto BoardSearchOne(int id) throws Exception {
			Connection conn = DbConnection.getConnection();
			BoardDto dto = null;
			try {
				dto = dao.BoardSelect(conn, id);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DbConnection.close(conn);
			}
			return null;
		}
		
		public ArrayList<BoardDto> BoardSearch(int id) throws Exception {
			Connection conn = DbConnection.getConnection();
			BoardDto dto = null;
			try {
				dto = dao.BoardSelect(conn, id);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DbConnection.close(conn);
			}
			return null;
		}

		// 수정
		public void BoardUpdate(BoardDto dto) throws Exception {
			Connection conn = DbConnection.getConnection();
			
			try {
				dao.BoardUpdate(conn, dto);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DbConnection.close(conn);
			}
		}
		// 삭제
		public void BoardDelete(int id) throws Exception {
			Connection conn = DbConnection.getConnection();
			try {
				dao.BoardDelete(conn, id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DbConnection.close(conn);
			}
		}
	
}
