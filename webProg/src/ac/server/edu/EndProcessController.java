package ac.server.edu;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dao.CourseDao;
import ac.server.dto.CourseDto;
import ac.server.service.courseService;

public class EndProcessController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		List<CourseDto> list = courseService.getInstance().Country("e");
		request.setAttribute("list", list);
		List<CourseDto> list1 = courseService.getInstance().Incumbent("e");
		request.setAttribute("list1", list1);
		List<CourseDto> list2 = courseService.getInstance().unemployed("e");
		request.setAttribute("list2", list2);
		return "/edu/endProcess.jsp";
	}

}