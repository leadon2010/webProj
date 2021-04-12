package ac.server.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ac.server.dao.CategoryDao;
import ac.server.dao.DateUtil;
import ac.server.dbconnection.DbConnection;
import ac.server.dto.CourseDto;
import ac.server.dto.CourseKindDto;
import ac.server.dto.StudentDto;
import ac.server.dto.TrainingDto;

public class CategoryService {
	//싱글톤
	public CategoryDao dao = CategoryDao.getInstance();
	
	private static CategoryService service = new CategoryService();
	
	public static CategoryService getInstance() {
		return service;
	}
	Connection conn=null;
	PreparedStatement pstmt;
	ResultSet rs = null;
	
	//훈련직종분류출력
		public ArrayList<TrainingDto> TrainingName()  throws SQLException{
			ArrayList<TrainingDto> list= new ArrayList<TrainingDto>();
			try {
				conn = DbConnection.getConnection();				
				TrainingDto member = null;
					pstmt = conn.prepareStatement("select training_num,training_name from training order by 1");
					rs = pstmt.executeQuery();
					while (rs.next()) {
						member = new TrainingDto();
						member.setTraining_num(rs.getInt(1));	
						member.setTraining_name(rs.getString(2));		//훈련직종
						list.add(member);
					} 
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				DbConnection.close(conn);
			}
			return list;
		}	
		
		//교육과정분류출력
		public ArrayList<CourseKindDto> CourseKindName()  throws SQLException{
			ArrayList<CourseKindDto> list = new ArrayList<CourseKindDto>();
			try {
				conn = DbConnection.getConnection();
				CourseKindDto member = null;
				pstmt = conn.prepareStatement("select course_num,course_name from course_Kind order by 1");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					member = new CourseKindDto();
					member.setCourse_num(rs.getInt(1));	
					member.setCourse_name(rs.getString(2));		//훈련직종
					list.add(member);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				DbConnection.close(conn);
			}
			return list;
		}
		
		//훈련분야구분 - 훈련직종분류등록
		public void Traininginsert(TrainingDto vo) {
			try {
				conn = DbConnection.getConnection();
				String sql = " insert into training values(?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,  vo.getTraining_num());
				pstmt.setString(2, vo.getTraining_name());
				pstmt.executeUpdate();	
				conn.commit();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				DbConnection.close(conn);
			}
		}
		
		//훈련분야구분 - 교욱과정분류등록
		public void CourseKindinsert(CourseKindDto beans) {
			try {
				conn = DbConnection.getConnection();
				String sql = " insert into course_kind values(?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,  beans.getCourse_num());
				pstmt.setString(2, beans.getCourse_name());
				pstmt.executeUpdate();	
				conn.commit();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				DbConnection.close(conn);
			}
		}
		
		//훈련분야구분 - 훈련직종분류수정
		public TrainingDto update(TrainingDto beans) {		
			try {
				conn = DbConnection.getConnection();
				String sql = " UPDATE training " + 
						"set training_name=? WHERE training_num = ?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, beans.getTraining_name());
				psmt.setInt(2, beans.getTraining_num());
				psmt.executeUpdate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				DbConnection.close(conn);
			}
			return beans;				
		}		
		
		//훈련분야구분 - 교육직종분류수정
		public CourseKindDto update1(CourseKindDto beans) {		
			try {
				conn = DbConnection.getConnection();
				String sql = " UPDATE course_kind " + 
						"set course_name=? WHERE course_num = ?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, beans.getCourse_name());
				psmt.setInt(2, beans.getCourse_num());
				psmt.executeUpdate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				DbConnection.close(conn);
			}
			return beans;				
		}	
		
		//훈련분야구분 - 훈련직종분류삭제
		public TrainingDto delete(TrainingDto beans) throws Exception {		
				conn = DbConnection.getConnection();
				String sql = "delete from training " + 
						"where training_num=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setInt(1, beans.getTraining_num());
				psmt.executeUpdate();
				conn.commit();
			return beans;				
		}	
		//교육분야구분 - 교육직종분류삭제
		public CourseKindDto delete1(CourseKindDto beans) throws Exception {		
				conn = DbConnection.getConnection();
				String sql = "delete from course_kind " + 
						"where course_num=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setInt(1, beans.getCourse_num());
				psmt.executeUpdate();
				conn.commit();
			return beans;				
		}	
		
		//Course테이블 등록
		public void Courseinsert(CourseDto beans) {
			try {
				conn = DbConnection.getConnection();
				String sql ="insert into course(course_code,training_list,course_list,course_name,recruit_date,start_date,end_date,fee,class_time,\r\n" + 
							"class_date,regist_num,prof_name) \r\n" + 
							"values((select max(to_number(course_code))+1 from course),?,?,?,?,?,?,?,?,?,?,?)";
				//String sql1 = " insert into course_kind values(?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, beans.getTraining_list());
				pstmt.setInt(2, beans.getCourse_list());
				pstmt.setString(3, beans.getCourse_name());
				pstmt.setDate(4, DateUtil.toSqlDate(beans.getRecruit_date()));
				pstmt.setDate(5, DateUtil.toSqlDate(beans.getStart_date()));
				pstmt.setDate(6, DateUtil.toSqlDate(beans.getEnd_date()));
				pstmt.setInt(7, beans.getFee());
				pstmt.setString(8, beans.getClass_time());
				pstmt.setString(9, beans.getClass_date());
				pstmt.setInt(10, beans.getRegist_num());
				pstmt.setString(11, beans.getProf_name());
				pstmt.executeUpdate();	
				conn.commit();
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				DbConnection.close(conn);
			}
		}
		
		// 훈련분야관리 페이징 할 전체 건수
		public int Searchcount(CourseDto cd) {
			String whereCondition = " where 1 = 1 ";
			if(cd.getTraining_list() != null ) {
				whereCondition += " and training_list = ? ";
			}
			if(cd.getCourse_list() != null ) {
				whereCondition += " and course_list = ? ";
			}
			int cnt = 0;
			try {
				conn = DbConnection.getConnection();
				String sql = 
						"select count(s.course_code) as cnt  " + 
						"from course c left join student s " + 
						"on(s.course_code = c.course_code) " + 
						whereCondition   ;
				pstmt = conn.prepareStatement(sql);
				int pos = 1;
				if(cd.getTraining_list() != null ) {
					pstmt.setInt(pos++, cd.getTraining_list());
				}
				if(cd.getCourse_list() != null ) {
					pstmt.setInt(pos++, cd.getCourse_list());
				}
				rs = pstmt.executeQuery();
				rs.next();
				cnt = rs.getInt(1);
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				DbConnection.close(conn);
			}
			return cnt;
		}
		
		// 훈련분야관리페이징 할 교육과정조회
		public ArrayList<CourseDto> SearchPage(int first, int last,CourseDto cd){
				ArrayList<CourseDto> datas = new ArrayList<CourseDto>();
				CourseDto vo = null;
				String whereCondition = " where 1 = 1 ";
				if(cd.getTraining_list() != null ) {
					whereCondition += " and training_list = ? ";
				}
				if(cd.getCourse_list() != null ) {
					whereCondition += " and course_list = ? ";
				}
				try {
					conn = DbConnection.getConnection();
					String sql =
							"select * from (select a.*,rownum rn  from ( " + 
							"select c.course_code, (select training_name from training where training_num = c.training_list ) as training_list2, " + 
							"(select course_name from course_kind where course_num = c.course_list) as course_list2 , c.course_name, c.recruit_date, c.start_date, " + 
							"c.end_date, c.fee, c.class_time, c.class_date, c.prof_name " + 
							"from course c left join student s " + 
							"on(s.course_code = c.course_code) " + 
							whereCondition + 
							"group by c.course_code, c.training_list, c.course_list, c.course_name, c.recruit_date, c.start_date, c.end_date, c.fee, c.class_time, " + 
							"c.class_date ,c.prof_name " + 
							"order by 5 desc " + 
							") a ) b " + 
							"where rn between ? and ? ";				
					pstmt = conn.prepareStatement(sql);
					int pos = 1;
					if(cd.getTraining_list() != null ) {
						pstmt.setInt(pos++, cd.getTraining_list());
					}
					if(cd.getCourse_list() != null ) {
						pstmt.setInt(pos++, cd.getCourse_list());
					}
					pstmt.setInt(pos++, first);
					pstmt.setInt(pos++, last);
					rs = pstmt.executeQuery();					
					while (rs.next()) {
						vo = new CourseDto();
						vo.setCourse_code(rs.getInt(1));		//과정코드
						vo.setTraining_list2(rs.getString(2));	//훈련직종분류
						vo.setCourse_list2(rs.getString(3));	//교육과정분류
						vo.setCourse_name(rs.getString(4));	//교육과정분류
						vo.setRecruit_date(rs.getDate(5));		//모집기간
						vo.setStart_date(rs.getDate(6));		//교육시작일
						vo.setEnd_date(rs.getDate(7));			//교육종료일
						vo.setFee(rs.getInt(8));				//수강료
						vo.setClass_time(rs.getString(9));		//수업시간
						vo.setClass_date(rs.getString(10));		//수업일수
						vo.setProf_name(rs.getString(11));		//교수명
						datas.add(vo);
					}
					}catch(Exception e) {
						e.printStackTrace();
					}finally {
						DbConnection.close(conn);
					}
				return datas;
			}
		
		// 페이징 할 전체 건수
		public int count() {
			int cnt = 0;
			try {
				conn = DbConnection.getConnection();
				String sql = "select count(*) from (select a.*,rownum rn  from ( " + 
						"select c.course_code, c.training_list, c.course_list, c.recruit_date, c.start_date, c.end_date, c.fee, c.class_time, c.class_date, count(*) as cnt ,c.regist_num,c.prof_name " + 
						"from course c join student s " + 						
						"on(s.course_code = c.course_code)   " + 
						"group by c.course_code, c.training_list, c.course_list, c.recruit_date, c.start_date, c.end_date, c.fee, c.class_time, " + 
						"c.class_date ,10,c.regist_num,c.prof_name " + 
						"order by 4 desc " + 
						") a )";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				rs.next();
				cnt = rs.getInt(1);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DbConnection.close(conn);
			}
			return cnt;
		}
		
		// 페이징 할 교육과정조회
		public ArrayList<CourseDto> selectPage(int first, int last){
				ArrayList<CourseDto> datas = new ArrayList<CourseDto>();
				try {
					conn = DbConnection.getConnection();
				
					String sql = 
							"select * from (select a.*,rownum rn  from (  \r\n" + 
							"select c.course_code, c.training_list, c.course_list, c.recruit_date, c.start_date, c.end_date, c.fee, c.class_time, c.class_date, count(s.course_code) as cnt ,c.regist_num,c.prof_name\r\n" + 
							"from course c left join student s   \r\n" + 
							"on(s.course_code = c.course_code)   \r\n" + 
							"group by c.course_code, c.training_list, c.course_list, c.recruit_date, c.start_date, c.end_date, c.fee, c.class_time,   \r\n" + 
							"c.class_date ,10,c.regist_num,c.prof_name  \r\n" + 
							"order by 4 desc    \r\n" + 
							") a ) b where rn between ? and ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, first);
					pstmt.setInt(2, last);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						CourseDto member = new CourseDto();
						member.setCourse_code(rs.getInt(1));		//과정코드
						member.setTraining_list(rs.getInt(2));	//훈련직종분류
						member.setCourse_list(rs.getInt(3));		//교육과정분류
						member.setRecruit_date(rs.getDate(4));		//모집기간
						member.setStart_date(rs.getDate(5));		//교육시작일
						member.setEnd_date(rs.getDate(6));			//교육종료일
						member.setFee(rs.getInt(7));				//수강료
						member.setClass_time(rs.getString(8));		//수업시간
						member.setClass_date(rs.getString(9));		//수업일수
						member.setAccept_num(rs.getInt(10));		//승인인원
						member.setProf_name(rs.getString(11));		//교수명
						datas.add(member);
					}
					}catch(Exception e) {
						e.printStackTrace();
					}finally {
						DbConnection.close(conn);
					}
				return datas;
			}
		//과정수정
		public void CourseUpdate(CourseDto st) throws SQLException {
			PreparedStatement pstmt = null;
			try {
				conn = DbConnection.getConnection();
				pstmt = conn.prepareStatement("update course set course_name=?,recruit_date=?,start_date=?,"
						+ "end_date=?,fee=?,class_time=?,class_date=?,prof_name=? where course_code=?");
				pstmt.setString(1, st.getCourse_name());
				pstmt.setDate(2, DateUtil.toSqlDate(st.getRecruit_date()));
				pstmt.setDate(3, DateUtil.toSqlDate(st.getStart_date()));
				pstmt.setDate(4, DateUtil.toSqlDate(st.getEnd_date()));
				pstmt.setInt(5, st.getFee());
				pstmt.setString(6, st.getClass_time());
				pstmt.setString(7, st.getClass_date());
				pstmt.setString(8, st.getProf_name());
				pstmt.setInt(9, st.getCourse_code());
				pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				DbConnection.close(conn);
			}
		}
		//과정수정내용
		public CourseDto CourseSearch(int course_code) throws SQLException {
			CourseDto course = null;
			
			try {
				conn = DbConnection.getConnection();
				pstmt = conn.prepareStatement("select course_code,training_list,course_list,course_name,recruit_date,start_date,end_date,fee,\r\n" + 
						"class_time,class_date, prof_name from course where course_code=?");
				pstmt.setInt(1, course_code);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					course = new CourseDto();
					course.setCourse_code(rs.getInt("course_code"));
					course.setTraining_list(rs.getInt("training_list"));
					course.setCourse_list(rs.getInt("course_list"));
					course.setCourse_name(rs.getString("course_name"));
					course.setRecruit_date(rs.getDate("recruit_date"));
					course.setStart_date(rs.getDate("start_date"));
					course.setEnd_date(rs.getDate("end_date"));
					course.setFee(rs.getInt("fee"));
					course.setClass_time(rs.getString("class_time"));
					course.setClass_date(rs.getString("class_date"));
					course.setProf_name(rs.getString("prof_name"));
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				DbConnection.close(conn);
			}
		return course;
	}
}
