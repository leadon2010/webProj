package ac.server.category;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dao.CategoryDao;
import ac.server.dto.CourseKindDto;
import ac.server.dto.TrainingDto;
import ac.server.service.CategoryService;

public class TrakinddeleteController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		
		response.setContentType("text/html; charset=utf-8");
		String tn = request.getParameter("training_num");
		String cn = request.getParameter("course_num");

		try {
			if(tn !=null ) {
				TrainingDto beans = new TrainingDto();
				beans.setTraining_num(Integer.parseInt(tn));
				CategoryService.getInstance().delete(beans);}
		}catch(Exception e) {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('관련있는 훈련직종분류가 있어서 삭제가 안됩니다.');");
			out.print("history.back();");
			out.print("</script>");
			return null;
		}
		try {
			if( cn != null){
				CourseKindDto beans1 = new CourseKindDto();
				beans1.setCourse_num(Integer.parseInt(cn));
				CategoryService.getInstance().delete1(beans1);
			}
		}catch(Exception e) {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('관련있는 교육직종분류가 있어서 삭제가 안됩니다.');");
			out.print("history.back();");
			out.print("</script>");
			return null;
		}
	
		response.sendRedirect("../yedam/TrakindSelect.do");
		return null;	
	}
}
