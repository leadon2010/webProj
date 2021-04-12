package ac.server.category;

import java.io.IOException;
import java.sql.Date;
import java.util.Locale.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dao.DateUtil;
import ac.server.dto.CourseDto;
import ac.server.dto.LoginDto;
import ac.server.dto.StudentDto;
import ac.server.service.CategoryService;

public class CourseUpdateController implements Controller {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Parameter 추출
		
		int course_code = Integer.parseInt(request.getParameter("course_code"));
		int training_list = Integer.parseInt(request.getParameter("training_list"));
		int course_list = Integer.parseInt(request.getParameter("course_list"));
		String course_name = request.getParameter("course_name");
		java.util.Date recruit_date = DateUtil.StringToDate(request.getParameter("recruit_date"));
		java.util.Date start_date = DateUtil.StringToDate(request.getParameter("start_date"));
		java.util.Date end_date = DateUtil.StringToDate(request.getParameter("end_date"));
		int fee = Integer.parseInt(request.getParameter("fee"));
		String class_time = request.getParameter("class_time");
		String class_date = request.getParameter("class_date");
		String prof_name= request.getParameter("prof_name");
		System.out.println(course_name);
		// 유효성 체크
//		if (course_code.isEmpty() || student_name.isEmpty() || email.isEmpty()||admission.isEmpty()) {
//			request.setAttribute("error", "모든 항목을 빠짐없이 입력해주시기 바랍니다!");
//			//HttpUtil.forward(request, response, "/memberInsert.jsp");
//			return "/memberInsert.jsp";
//		}

		// VO객체에 데이타 바인딩
		CourseDto st = new CourseDto();
		st.setCourse_code(course_code);
		st.setTraining_list(training_list);
		st.setCourse_list(course_list);
		st.setCourse_name(course_name);
		st.setRecruit_date(recruit_date);
		st.setStart_date(start_date);;
		st.setEnd_date(end_date);
		st.setFee(fee);
		st.setClass_time(class_time);
		st.setClass_date(class_date);
		st.setProf_name(prof_name);

		// Service 객체의 메서드 호출
		CategoryService service = CategoryService.getInstance();
		try {
			service.CourseUpdate(st);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:ManagerSearch.do";
	}
}