package ac.server.admission;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dto.StudentDto;
import ac.server.service.StudentService;

public class AllowConfirmController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		int course_num = Integer.parseInt(request.getParameter("course_list"));
		System.out.println(course_num);
		ArrayList<StudentDto> student;
		try {
			student = StudentService.getInstance().studentCourse(course_num);
			request.setAttribute("student", student);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admission/allowConfirm.jsp";
	}

}