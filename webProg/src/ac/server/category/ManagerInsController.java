package ac.server.category;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dao.CategoryDao;
import ac.server.dto.CourseKindDto;
import ac.server.dto.TrainingDto;
import ac.server.service.CategoryService;

/**
 * Servlet implementation class ManagerInsController
 */
//@WebServlet("/ManagerInsController")
public class ManagerInsController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		//
		List<TrainingDto> list = CategoryService.getInstance().TrainingName();
		request.setAttribute("Traininglist", list);
		
		List<CourseKindDto> list1 = CategoryService.getInstance().CourseKindName();
		request.setAttribute("CourseKindlist", list1);
		
		return "/curr/managerIns.jsp";
	}
}
