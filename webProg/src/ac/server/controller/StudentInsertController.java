package ac.server.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.dto.LoginDto;
import ac.server.dto.StudentDto;
import ac.server.service.StudentService;

public class StudentInsertController implements Controller {
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Parameter 추출
		String id = request.getParameter("id");
		int course_code = Integer.parseInt(request.getParameter("course_code"));
		String student_name = request.getParameter("student_name");
		String email = request.getParameter("mail");
		String pw = request.getParameter("pw");

		// 유효성 체크
		if (id.isEmpty() || student_name.isEmpty() || email.isEmpty()) {
			request.setAttribute("error", "모든 항목을 빠짐없이 입력해주시기 바랍니다!");
			// HttpUtil.forward(request, response, "/memberInsert.jsp");
			return "/memberInsert.jsp";
		}

		// VO객체에 데이타 바인딩
		StudentDto student = new StudentDto();
		student.setId(id);
		student.setCourse_code(course_code);
		student.setStudent_name(student_name);
		student.setEmail(email);

		LoginDto login = new LoginDto();
		login.setId(id);
		login.setPw(pw);

		// Service 객체의 메서드 호출
		StudentService service = StudentService.getInstance();
		try {
			service.StudentInsert(student, login);
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
