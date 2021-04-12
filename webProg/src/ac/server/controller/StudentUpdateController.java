package ac.server.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.dto.LoginDto;
import ac.server.dto.StudentDto;
import ac.server.service.StudentService;

public class StudentUpdateController implements Controller {
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Parameter 추출
		String id = request.getParameter("id");
		int course_code = Integer.parseInt(request.getParameter("course_code"));
		String student_name = request.getParameter("student_name");
		String email = request.getParameter("email");
		String admission = request.getParameter("admission");
		/* String pw = request.getParameter("pw"); */

		// 유효성 체크
		if (id.isEmpty() || student_name.isEmpty() || email.isEmpty() || admission.isEmpty()) {
			request.setAttribute("error", "모든 항목을 빠짐없이 입력해주시기 바랍니다!");
			// HttpUtil.forward(request, response, "/memberInsert.jsp");
			return "/memberInsert.jsp";
		}

		// VO객체에 데이타 바인딩
		StudentDto st = new StudentDto();
		st.setId(id);
		st.setCourse_code(course_code);
		st.setStudent_name(student_name);
		st.setEmail(email);
		st.setAdmission(admission);

		LoginDto login = new LoginDto();
		login.setId(id);
		// login.setPw(pw);

		// Service 객체의 메서드 호출
		StudentService service = StudentService.getInstance();
		try {
			service.StudentUpdate(st);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Output View 페이지로 이동
		request.setAttribute("id", id);
		// HttpUtil.forward(request, response, "/result/memberInsertOutput.jsp");
		return "redirect:StudentList.do";
	}
}
