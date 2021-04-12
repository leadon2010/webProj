package ac.server.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ac.server.dbconnection.DbConnection;
import ac.server.dto.CourseDto;
import ac.server.dto.CourseKindDto;

public class courseService {
	// 싱글톤
	private static courseService dao = new courseService();

	public static courseService getInstance() {
		return dao;
	}

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;

	// 국가기간전략산업직종 조회
	public ArrayList<CourseDto> Country(String ing) throws SQLException {
		ArrayList<CourseDto> list = new ArrayList<CourseDto>();
		try {
			conn = DbConnection.getConnection();
			CourseDto member = null;
			String aaff = "";
			if (ing.equals("r")) {
				aaff = " and to_char( recruit_date, 'yyyymmdd' ) > to_char( sysdate, 'yyyymmdd')";
			} else if (ing.equals("p")) {
				aaff = " and to_char( start_date, 'yyyymmdd' ) < to_char( sysdate, 'yyyymmdd') and "
						+ "to_char( end_date, 'yyyymmdd' ) > to_char( sysdate, 'yyyymmdd')";
			} else if (ing.equals("e")) {
				aaff = " and to_char( start_date, 'yyyymmdd' ) < to_char( sysdate, 'yyyymmdd') and "
						+ "to_char( end_date, 'yyyymmdd' ) < to_char( sysdate, 'yyyymmdd')";
			}
			pstmt = conn.prepareStatement("select * from (select a.*,rownum rn  from ( "
					+ "select c.course_code, (select training_name from training where training_num = c.training_list ) as training_list2, "
					+ "(select course_name from course_kind where course_num = c.course_list) as course_list2 , c.recruit_date, c.start_date, "
					+ "c.end_date, c.fee, c.class_time, c.class_date, count(s.course_code) as cnt ,c.regist_num,c.prof_name "
					+ "from course c left join student s " + "on(s.course_code = c.course_code) "
					+ " where c.training_list between 100 and 199 " + aaff
					+ "group by c.course_code, c.training_list, c.course_list, c.recruit_date, c.start_date, c.end_date, c.fee, c.class_time, "
					+ "c.class_date ,10,c.regist_num,c.prof_name " + "order by 4 desc " + ") a ) b ");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member = new CourseDto();
				member.setCourse_code(rs.getInt(1)); // 과정코드
				member.setTraining_list2(rs.getString(2)); // 훈련직종분류
				member.setCourse_list2(rs.getString(3)); // 교육과정분류
				member.setRecruit_date(rs.getDate(4)); // 모집기간
				member.setStart_date(rs.getDate(5)); // 교육시작일
				member.setEnd_date(rs.getDate(6)); // 교육종료일
				member.setFee(rs.getInt(7)); // 수강료
				member.setClass_time(rs.getString(8)); // 수업시간
				member.setClass_date(rs.getString(9)); // 수업일수
				member.setAccept_num(rs.getInt(10)); // 승인인원
				member.setRegist_num(rs.getInt(11)); // 접수인원
				member.setProf_name(rs.getString(12)); // 교수명
				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return list;
	}

	// 재직자 조회
	public ArrayList<CourseDto> Incumbent(String ing) throws SQLException {
		ArrayList<CourseDto> list1 = new ArrayList<CourseDto>();
		try {
			conn = DbConnection.getConnection();
			CourseDto member = null;
			String aaff = "";
			if (ing.equals("r")) {
				aaff = " and to_char( recruit_date, 'yyyymmdd' ) > to_char( sysdate, 'yyyymmdd')";
			} else if (ing.equals("p")) {
				aaff = " and to_char( start_date, 'yyyymmdd' ) < to_char( sysdate, 'yyyymmdd') and "
						+ "to_char( end_date, 'yyyymmdd' ) > to_char( sysdate, 'yyyymmdd')";
			} else if (ing.equals("e")) {
				aaff = " and to_char( start_date, 'yyyymmdd' ) < to_char( sysdate, 'yyyymmdd') and "
						+ "to_char( end_date, 'yyyymmdd' ) < to_char( sysdate, 'yyyymmdd')";
			}
			pstmt = conn.prepareStatement(
					"select c.course_code, (select training_name from training where training_num = c.training_list ) as training_list2, (select course_name from course_kind where course_num = c.course_list) as course_list2 , c.recruit_date, c.start_date, c.end_date, c.fee, c.class_time, c.class_date, count(*) as cnt ,c.regist_num,c.prof_name "
							+ " from course c join student s on(s.course_code = c.course_code) "
							+ " where c.training_list between 200 and 299 " + aaff
							+ " group by c.course_code, c.training_list, c.course_list, c.recruit_date, c.start_date, c.end_date, c.fee, c.class_time, c.class_date ,10,c.regist_num,c.prof_name");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member = new CourseDto();
				member.setCourse_code(rs.getInt(1)); // 과정코드
				member.setTraining_list2(rs.getString(2)); // 훈련직종분류
				member.setCourse_list2(rs.getString(3)); // 교육과정분류
				member.setRecruit_date(rs.getDate(4)); // 모집기간
				member.setStart_date(rs.getDate(5)); // 교육시작일
				member.setEnd_date(rs.getDate(6)); // 교육종료일
				member.setFee(rs.getInt(7)); // 수강료
				member.setClass_time(rs.getString(8)); // 수업시간
				member.setClass_date(rs.getString(9)); // 수업일수
				member.setAccept_num(rs.getInt(10)); // 승인인원
				member.setRegist_num(rs.getInt(11)); // 접수인원
				member.setProf_name(rs.getString(12)); // 교수명
				list1.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return list1;
	}

	// 실업자 조회
	public ArrayList<CourseDto> unemployed(String ing) throws SQLException {
		ArrayList<CourseDto> list2 = new ArrayList<CourseDto>();
		try {
			conn = DbConnection.getConnection();
			CourseDto member = null;
			String aaff = "";
			if (ing.equals("r")) {
				aaff = " and to_char( recruit_date, 'yyyymmdd' ) > to_char( sysdate, 'yyyymmdd')";
			} else if (ing.equals("p")) {
				aaff = " and to_char( start_date, 'yyyymmdd' ) < to_char( sysdate, 'yyyymmdd') and "
						+ "to_char( end_date, 'yyyymmdd' ) > to_char( sysdate, 'yyyymmdd')";
			} else if (ing.equals("e")) {
				aaff = " and to_char( start_date, 'yyyymmdd' ) < to_char( sysdate, 'yyyymmdd') and "
						+ "to_char( end_date, 'yyyymmdd' ) < to_char( sysdate, 'yyyymmdd')";
			}
			pstmt = conn.prepareStatement(
					"select c.course_code, (select training_name from training where training_num = c.training_list ) as training_list2, (select course_name from course_kind where course_num = c.course_list) as course_list2 , c.recruit_date, c.start_date, c.end_date, c.fee, c.class_time, c.class_date, count(*) as cnt ,c.regist_num,c.prof_name "
							+ " from course c join student s on(s.course_code = c.course_code) "
							+ " where c.training_list between 300 and 399 " + aaff
							+ " group by c.course_code, c.training_list, c.course_list, c.recruit_date, c.start_date, c.end_date, c.fee, c.class_time, c.class_date ,10,c.regist_num,c.prof_name");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member = new CourseDto();
				member.setCourse_code(rs.getInt(1)); // 과정코드
				member.setTraining_list2(rs.getString(2)); // 훈련직종분류
				member.setCourse_list2(rs.getString(3)); // 교육과정분류
				member.setRecruit_date(rs.getDate(4)); // 모집기간
				member.setStart_date(rs.getDate(5)); // 교육시작일
				member.setEnd_date(rs.getDate(6)); // 교육종료일
				member.setFee(rs.getInt(7)); // 수강료
				member.setClass_time(rs.getString(8)); // 수업시간
				member.setClass_date(rs.getString(9)); // 수업일수
				member.setAccept_num(rs.getInt(10)); // 승인인원
				member.setRegist_num(rs.getInt(11)); // 접수인원
				member.setProf_name(rs.getString(12)); // 교수명
				list2.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return list2;
	}

	// 교육과정분류수정
	public CourseKindDto update1(CourseKindDto beans) {
		try {
			conn = DbConnection.getConnection();
			String sql = " UPDATE training " + "set training_name=? WHERE training_num = ?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, beans.getCourse_name());
			psmt.setInt(2, beans.getCourse_num());
			psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return beans;
	}

	// 교육과정 전체조회
	public ArrayList<CourseDto> CourseSelectAll() throws SQLException {
		ArrayList<CourseDto> list2 = new ArrayList<CourseDto>();
		try {
			conn = DbConnection.getConnection();
			CourseDto member = null;
			pstmt = conn.prepareStatement(
					"select c.course_code, c.training_list, c.course_list, c.recruit_date, c.start_date, c.end_date, c.fee, c.class_time, c.class_date, count(*) as cnt ,c.regist_num,c.prof_name\r\n"
							+ "from course c join student s \r\n" + "on(s.course_code = c.course_code)  \r\n"
							+ "group by c.course_code, c.training_list, c.course_list, c.recruit_date, c.start_date, c.end_date, c.fee, c.class_time, c.class_date ,10,c.regist_num,c.prof_name\r\n"
							+ "order by 4");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member = new CourseDto();
				member.setCourse_code(rs.getInt(1)); // 과정코드
				member.setTraining_list(rs.getInt(2)); // 훈련직종분류
				member.setCourse_list(rs.getInt(3)); // 교육과정분류
				member.setRecruit_date(rs.getDate(4)); // 모집기간
				member.setStart_date(rs.getDate(5)); // 교육시작일
				member.setEnd_date(rs.getDate(6)); // 교육종료일
				member.setFee(rs.getInt(7)); // 수강료
				member.setClass_time(rs.getString(8)); // 수업시간
				member.setClass_date(rs.getString(9)); // 수업일수
				member.setAccept_num(rs.getInt(10)); // 승인인원
				member.setRegist_num(rs.getInt(11)); // 접수인원
				member.setProf_name(rs.getString(12)); // 교수명
				list2.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return list2;
	}

	public ArrayList<CourseDto> CourseSelect() throws SQLException {
		ArrayList<CourseDto> list = new ArrayList<CourseDto>();
		try {
			conn = DbConnection.getConnection();
			CourseDto member = null;
			pstmt = conn.prepareStatement(
					"select k.course_name, t.training_name, c.start_date, c.end_date, count(c.course_list) "
							+ "from course c JOIN course_kind k on c.course_list=k.course_num JOIN training t on t.training_num=c.training_list "
							+ "group by k.course_name, c.start_date, t.training_name, c.end_date "
							+ "order by k.course_name");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				member = new CourseDto();
				member.setCourse_list2(rs.getString(1)); // 교육과정분류
				member.setTraining_list2(rs.getString(2));
				member.setStart_date(rs.getDate(3)); // 교육시작일
				member.setEnd_date(rs.getDate(4)); // 교육종료일
				member.setCourse_list(5);
				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.close(conn);
		}
		return list;
	}
}