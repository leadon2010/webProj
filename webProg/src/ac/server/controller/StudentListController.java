package ac.server.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.dto.StudentDto;
import ac.server.service.StudentService;

public class StudentListController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StudentService service = StudentService.getInstance();
		ArrayList<StudentDto> studentList = null;
		try {
			studentList = service.studentList();
			request.setAttribute("list", studentList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 결과페이지 리턴
		return "ac/member/student.jsp";

	}
}
