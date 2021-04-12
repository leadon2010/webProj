package ac.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import ac.server.dao.ProfDao;
import ac.server.dbconnection.DbConnection;
import ac.server.dto.LoginDto;
import ac.server.dto.ProfDto;
import ac.server.login.LoginDao;


public class ProfService {
	private static ProfService service = new ProfService();
	public ProfDao dao =ProfDao.getInstance();
	public LoginDao dao1 =LoginDao.getInstance();
	
	private ProfService() {
	}

	public static ProfService getInstance() {
		return service;
	}

	// 회원등록
	public void ProfInsert(ProfDto prof,LoginDto login) throws Exception {
		//트랜잭션처리
		Connection conn= DbConnection.getConnection();
		try {
			conn.setAutoCommit(false);
			//login 등록
			dao1.LoginInsert(conn, login);
			//member 등록
			dao.ProfInsert(conn,prof);
			
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			DbConnection.close(conn);
		}
	}

	public ArrayList<ProfDto> ProfSearch(String id) {
		Connection conn = null;
		ArrayList<ProfDto> prof = null ;
		try {
			conn = DbConnection.getConnection();
			conn.setAutoCommit(false);
			prof = dao.ProfSearch(conn, id);
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DbConnection.close(conn);
		}
		return prof;
	}

	public void ProfUpdate(ProfDto prof) throws Exception {
		//트랜잭션 처리
		Connection conn = DbConnection.getConnection();
		try {
			conn.setAutoCommit(false);
			dao.ProfUpdate(conn, prof);	
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
	}

	public void ProfDelete(String id) throws Exception {
		Connection conn=DbConnection.getConnection();
		
		try {
			conn.setAutoCommit(false);
			dao.ProfDelete(conn, id);
			ProfDao.getInstance().ProfDelete(conn, id);
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
	}

	public ArrayList<ProfDto> ProfList() throws Exception {
		Connection conn = DbConnection.getConnection();
		ArrayList<ProfDto> list = null;
		try {
			list = dao.profList(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return list;
	}

}
