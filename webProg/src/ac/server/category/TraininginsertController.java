package ac.server.category;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dao.CategoryDao;
import ac.server.dto.TrainingDto;
import ac.server.service.CategoryService;

public class TraininginsertController implements Controller{
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		TrainingDto beans = new TrainingDto();
		beans.setTraining_num(Integer.parseInt(request.getParameter("training_num")));
		beans.setTraining_name(request.getParameter("training_name"));
		CategoryService.getInstance().Traininginsert(beans);
		response.sendRedirect("../yedam/TrakindSelect.do");
		return null;
	}
}