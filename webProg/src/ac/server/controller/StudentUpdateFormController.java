package ac.server.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.dto.LoginDto;
import ac.server.dto.StudentDto;
import ac.server.service.StudentService;

public class StudentUpdateFormController implements Controller {
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Parameter 추출
		String id = request.getParameter("id");
		// 서비스(db처리)
		StudentService service = StudentService.getInstance();
		ArrayList<StudentDto> st = service.StudentSearch(id);
		// DeptBeans beans = DeptService.getInstance().getDept(department_id);
		// 결과를 저장
		request.setAttribute("st", st.get(0));
		// forward될 페이지명을 리턴
		return "member/student_update.jsp";
	}
}
