package ac.server.category;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dao.CategoryDao;
import ac.server.dto.CourseKindDto;
import ac.server.dto.TrainingDto;
import ac.server.service.CategoryService;

public class TraKindselectController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		List<TrainingDto> list = CategoryService.getInstance().TrainingName();
		request.setAttribute("list", list);
		
		List<CourseKindDto> list1 = CategoryService.getInstance().CourseKindName();
		request.setAttribute("list1", list1);
		return "/curr/studyField.jsp";
	}
}