package ac.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ac.server.dto.CourseDto;
import ac.server.dto.StudentDto;

public class StudentDao {

	private static StudentDao dao = new StudentDao();

	private StudentDao() {
	}

	// 싱글톤
	public static StudentDao getInstance() {
		return dao;
	}

	public void StudentInsert(Connection conn, StudentDto student) throws SQLException {
		System.out.println(student);
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement("insert into student(id,course_code,student_name,email) values(?,?,?,?)");
		pstmt.setString(1, student.getId());
		pstmt.setInt(2, student.getCourse_code());
		pstmt.setString(3, student.getStudent_name());
		pstmt.setString(4, student.getEmail());
		pstmt.executeUpdate();
	}

	public ArrayList<StudentDto> StudentSearch(Connection conn, String id) throws SQLException {// id로 단건조회
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<StudentDto> list = new ArrayList<>();
		StudentDto student = null;

		pstmt = conn.prepareStatement("select * from student where id=?");
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			student = new StudentDto();
			student.setId(rs.getString("id"));
			student.setCourse_code(rs.getInt("course_code"));
			student.setStudent_name(rs.getString("student_name"));
			student.setEmail(rs.getString("email"));
			student.setAdmission(rs.getString("admission"));
			list.add(student);
		}
		return list;
	}

	public StudentDto StudentSearch2(Connection conn, StudentDto st) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StudentDto student2 = null;

		pstmt = conn.prepareStatement("select * from student where student_name=?");
		pstmt.setString(1, st.getStudent_name());
		rs = pstmt.executeQuery();
		if (rs.next()) {
			student2 = new StudentDto();
			student2.setId(rs.getString("id"));
			student2.setCourse_code(rs.getInt("course_code"));
			student2.setStudent_name(rs.getString("student_name"));
			student2.setEmail(rs.getString("email"));
			student2.setAdmission(rs.getString("admission"));
		}
		return student2;
	}

	public void StudentUpdate(Connection conn, StudentDto st) throws SQLException {
		PreparedStatement pstmt = null;

		pstmt = conn.prepareStatement("update student set course_code=?,student_name=?,email=?,admission=? where id=?");

		pstmt.setInt(1, st.getCourse_code());
		pstmt.setString(2, st.getStudent_name());
		pstmt.setString(3, st.getEmail());
		pstmt.setString(4, st.getAdmission());
		pstmt.setString(5, st.getId());
		pstmt.executeUpdate();
	}

	public void StudentDelete(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;

		pstmt = conn.prepareStatement("delete from student where id=?");
		pstmt.setString(1, id);
		pstmt.executeUpdate();

	}

	public ArrayList<StudentDto> studentList(Connection conn) throws SQLException {

		ArrayList<StudentDto> list = new ArrayList<StudentDto>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StudentDto student = null;

		pstmt = conn.prepareStatement("select * from student");
		rs = pstmt.executeQuery();
		while (rs.next()) {
			student = new StudentDto();
			student.setId(rs.getString("id"));
			student.setCourse_code(rs.getInt("course_code"));
			student.setStudent_name(rs.getString("Student_name"));
			student.setEmail(rs.getString("email"));
			student.setAdmission(rs.getString("admission"));
			list.add(student);
		}

		return list;
	}

	public ArrayList<StudentDto> studentCourse(Connection conn, int num) throws SQLException {
		ArrayList<StudentDto> list = new ArrayList<StudentDto>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StudentDto student = null;

		pstmt = conn.prepareStatement("select s.id, s.course_code,s.Student_name,s.admission "
				+ "from student s JOIN course c ON s.course_code=c.course_code\r\n " + "where c.course_list = ?");
		pstmt.setInt(1, num);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			student = new StudentDto();
			student.setId(rs.getString("id"));
			student.setCourse_code(rs.getInt("course_code"));
			student.setStudent_name(rs.getString("Student_name"));
			student.setAdmission(rs.getString("admission"));
			list.add(student);
		}

		return list;
	}

	public void AllowUpdate(Connection conn, StudentDto st) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement("update student set admission=? where id=?");
		pstmt.setString(1, st.getAdmission());
		pstmt.setString(2, st.getId());
		pstmt.executeUpdate();
	}

	public CourseDto CourseSearch(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CourseDto student = null;

		pstmt = conn.prepareStatement("select c.course_list "
				+ "from course c JOIN student s on c.course_code=s.course_code " + "where s.id = ?");
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			student = new CourseDto();
			student.setCourse_list(rs.getInt("course_list"));
		}
		return student;
	}

}
