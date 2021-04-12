package ac.server.category;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dto.CourseKindDto;
import ac.server.service.CategoryService;

public class CoursekindinsertController implements Controller{
	private static final long serialVersionUID = 1L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		CourseKindDto beans = new CourseKindDto();
		beans.setCourse_num(Integer.parseInt(request.getParameter("course_num")));
		beans.setCourse_name(request.getParameter("course_name"));
		CategoryService.getInstance().CourseKindinsert(beans);
		response.sendRedirect("../yedam/TrakindSelect.do");
		return null;
	}
}
