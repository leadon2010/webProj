package ac.server.admission;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dao.CourseDao;
import ac.server.dto.CourseDto;

public class AllowController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		ArrayList<CourseDto> list = CourseDao.getInstance().CourseAllow();
		request.setAttribute("list", list);				
		return "/admission/allow.jsp";
	}

}
