package ac.server.category;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dao.CategoryDao;
import ac.server.dto.CourseDto;
import ac.server.dto.CourseKindDto;
import ac.server.dto.TrainingDto;
import ac.server.paging.Paging;
import ac.server.service.CategoryService;

/**
 * Servlet implementation class ManagerSearchController
 */
@WebServlet("/ManagerSearchController")
public class ManagerSearchController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Paging paging = new Paging();
		ArrayList<CourseDto> list = null;
		ArrayList<TrainingDto> list3;
		ArrayList<CourseKindDto> list4;
		
		int tt=0;
		int ckk=0;
		response.setContentType("text/html; charset=utf-8");
		String t = request.getParameter("training");
		String ck = request.getParameter("coursekind");
		String sp = request.getParameter("p");
		
		CourseDto dto = new CourseDto();
		if(t !=null ) 
		{ 
			tt = Integer.parseInt(t);	
			dto.setTraining_list(tt);	
		}	
		if(ck != null) 
		{
			ckk = Integer.parseInt(ck);		
			dto.setCourse_list(ckk);		
		}
	
		int p = 1;	
		if(sp != null && ! sp.isEmpty()){
			p = Integer.parseInt(sp);
		}
		
		
		paging.setPage(p); //현재 페이지
		int cnt = CategoryService.getInstance().Searchcount(dto);
		paging.setPageSize(3); //페이지버튼 갯수
		paging.setPageUnit(5); //페이지 갯수
		paging.setTotalRecord(cnt); //전체 레코드 건수
		
		int first = paging.getFirst();
		int last = paging.getLast();
		list = CategoryService.getInstance().SearchPage(first, last, dto);

			try {
				list3 = CategoryService.getInstance().TrainingName();
				list4 = CategoryService.getInstance().CourseKindName();
				request.setAttribute("paging", paging);
				request.setAttribute("list", list);
				request.setAttribute("list3", list3);
				request.setAttribute("list4", list4);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return "/curr/managerCurr.jsp";
	}
}