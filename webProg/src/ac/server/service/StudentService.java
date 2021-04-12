package ac.server.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import ac.server.dao.StudentDao;
import ac.server.dbconnection.DbConnection;
import ac.server.dto.CourseDto;
import ac.server.dto.LoginDto;
import ac.server.dto.StudentDto;
import ac.server.login.LoginDao;

public class StudentService {

	private static StudentService service = new StudentService();
	public StudentDao dao = StudentDao.getInstance();
	public LoginDao dao1 = LoginDao.getInstance();

	private StudentService() {
	}

	public static StudentService getInstance() {
		return service;
	}

	// 회원등록
	public void StudentInsert(StudentDto student, LoginDto login) throws Exception {
		// 트랜잭션처리
		Connection conn = DbConnection.getConnection();
		try {
			conn.setAutoCommit(false);
			// login 등록
			dao1.LoginInsert(conn, login);
			// member 등록
			dao.StudentInsert(conn, student);

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

	public ArrayList<StudentDto> StudentSearch(String id) {
		Connection conn = null;
		ArrayList<StudentDto> student = null;
		try {
			conn = DbConnection.getConnection();
			conn.setAutoCommit(false);
			student = dao.StudentSearch(conn, id);
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
		} finally {
			DbConnection.close(conn);
		}
		return student;
	}

	public StudentDto StudentSearch2(StudentDto st) {
		Connection conn = null;
		StudentDto student = null;
		try {
			conn = DbConnection.getConnection();
			conn.setAutoCommit(false);
			student = dao.StudentSearch2(conn, st);
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
		} finally {
			DbConnection.close(conn);
		}
		return student;
	}

	public void StudentUpdate(StudentDto st) throws Exception {
		// 트랜잭션 처리
		Connection conn = DbConnection.getConnection();
		try {
			conn.setAutoCommit(false);
			dao.StudentUpdate(conn, st);
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

	public void StudentDelete(String id) throws Exception {
		Connection conn = DbConnection.getConnection();

		try {
			conn.setAutoCommit(false);
			dao.StudentDelete(conn, id);
			StudentDao.getInstance().StudentDelete(conn, id);
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

	public ArrayList<StudentDto> studentList() throws Exception {
		Connection conn = DbConnection.getConnection();
		ArrayList<StudentDto> list = null;
		try {
			list = dao.studentList(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return list;
	}

	public ArrayList<StudentDto> studentCourse(int num) throws Exception {
		Connection conn = DbConnection.getConnection();
		ArrayList<StudentDto> list = null;
		try {
			list = dao.studentCourse(conn, num);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return list;
	}

	public StudentDto AllowUpdate(StudentDto st) throws Exception {
		// 트랜잭션 처리
		Connection conn = DbConnection.getConnection();
		try {
			conn.setAutoCommit(false);
			dao.AllowUpdate(conn, st);
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
		return st;
	}

	public CourseDto StudentCourseSearch(String st) throws Exception {
		// 트랜잭션 처리
		Connection conn = DbConnection.getConnection();
		CourseDto coursedto = new CourseDto();
		try {
			coursedto = dao.CourseSearch(conn, st);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return coursedto;
	}
}
