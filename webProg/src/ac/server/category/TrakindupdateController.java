package ac.server.category;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dao.CategoryDao;
import ac.server.dto.CourseKindDto;
import ac.server.dto.TrainingDto;
import ac.server.service.CategoryService;
import net.sf.json.JSONObject;


//@WebServlet("/TrakindupdateController")
public class TrakindupdateController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String kind = request.getParameter("kind");
		if(kind.equals("1")) {
			TrainingDto ti = new TrainingDto();
			ti.setTraining_num(Integer.parseInt(request.getParameter("num")));
			ti.setTraining_name(request.getParameter("name"));
			TrainingDto list = CategoryService.getInstance().update(ti);
			return "ajax:"+JSONObject.fromObject(list).toString();
			
		} else if(kind.equals("2")) {
			CourseKindDto ti = new CourseKindDto();
			ti.setCourse_num(Integer.parseInt(request.getParameter("num")));
			ti.setCourse_name(request.getParameter("name"));
			CourseKindDto list = CategoryService.getInstance().update1(ti);
			return "ajax:"+JSONObject.fromObject(list).toString();
		}
		return null;
	}
}
