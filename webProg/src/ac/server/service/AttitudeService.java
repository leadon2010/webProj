package ac.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ac.server.dao.AttitudeDao;
import ac.server.dbconnection.DbConnection;
import ac.server.dto.AttitudeDto;
import ac.server.dto.LoginDto;

import ac.server.login.LoginDao;

public class AttitudeService {
	private static AttitudeService service = new AttitudeService();
	public AttitudeDao dao = AttitudeDao.getInstance();
	public LoginDao dao1 = LoginDao.getInstance();

	private AttitudeService() {
	}

	public static AttitudeService getInstance() {
		return service;
	}

	// 회원등록
	public void AttitudeInsert(AttitudeDto attit, LoginDto login) throws Exception {
		// 트랜잭션처리
		Connection conn = DbConnection.getConnection();
		try {
			conn.setAutoCommit(false);
			// login 등록
			dao1.LoginInsert(conn, login);
			// member 등록
			dao.AttitudeInsert(conn, attit);

			conn.commit();
		} catch (Exception e) {
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

	public AttitudeDto attitudeSearch(String id) throws Exception {
		Connection conn = DbConnection.getConnection();
		AttitudeDto attit = null;
		try {
			conn.setAutoCommit(false);
			attit = dao.attitudeSearch(conn, id);
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return attit;
	}

	public void AttitudeDelete(String id) throws Exception {
		Connection conn = DbConnection.getConnection();

		try {
			conn.setAutoCommit(false);
			dao.AttitudeDelete(conn, id);
			AttitudeDao.getInstance().AttitudeDelete(conn, id);
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

	public List<Map<String, Object>> attitudeList() {
		Connection conn = null;

		List<Map<String, Object>> list = null;
		try {
			conn = DbConnection.getConnection();
			list = dao.attitudeList(conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return list;
	}

}