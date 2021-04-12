package ac.server.category;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dto.CourseDto;
import ac.server.dto.StudentDto;
import ac.server.service.CategoryService;
import ac.server.service.StudentService;

public class CourseUpdateformController implements Controller {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Parameter 추출
		int course_code = Integer.parseInt(request.getParameter("course_code"));
		CategoryService service= CategoryService.getInstance();
		CourseDto st;
		try {
			st = service.CourseSearch(course_code);
			request.setAttribute("st", st);	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		//forward될 페이지명을 리턴		
		return "curr/course_update.jsp";
	}
}