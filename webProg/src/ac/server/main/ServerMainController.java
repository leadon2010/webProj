package ac.server.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dao.MainDao;
import ac.server.dto.BoardDto;
import ac.server.dto.PostDto;
import ac.server.service.postService;

//@WebServlet("serverMain/serverMain.jsp")
public class ServerMainController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		//공지사항 출력
		List<PostDto> list =null;
		try {
			list = postService.getInstance().selectList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("list2", list);		
		return "/serverMain/serverMain.jsp";
		
	
	}
}