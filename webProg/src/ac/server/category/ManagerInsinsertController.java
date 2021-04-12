package ac.server.category;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dao.CategoryDao;
import ac.server.dao.DateUtil;
import ac.server.dto.CourseDto;
import ac.server.service.CategoryService;

/**
 * Servlet implementation class ManagerInsinsertController
 */
@WebServlet("/ManagerInsinsertController")
public class ManagerInsinsertController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		CourseDto beans = new CourseDto();
		beans.setTraining_list(Integer.parseInt(request.getParameter("training_list")));
		beans.setCourse_list(Integer.parseInt(request.getParameter("course_list")));
		beans.setCourse_name(request.getParameter("course_name"));
		beans.setRecruit_date(DateUtil.StringToDate(request.getParameter("recruit_date")));
		beans.setStart_date(DateUtil.StringToDate(request.getParameter("start_date")));
		beans.setEnd_date(DateUtil.StringToDate(request.getParameter("end_date")));
		beans.setFee(Integer.parseInt(request.getParameter("fee")));
		beans.setClass_time(request.getParameter("class_time"));
		beans.setClass_date(request.getParameter("class_date"));
		beans.setRegist_num(Integer.parseInt(request.getParameter("regist_num")));
		beans.setProf_name(request.getParameter("prof_name"));
		
		CategoryService.getInstance().Courseinsert(beans);
		response.sendRedirect("./ManagerSearch.do");
		return null;
	}
}
