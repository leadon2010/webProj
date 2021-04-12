package ac.server.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.dto.PostDto;
import ac.server.service.postService;

public class PostSelectController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		PostDto postlist = null;
		try {
		postlist = postService.getInstance().PostSelectOne(post_num);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		request.setAttribute("postlist", postlist);
		return "/post/postSelectMain.jsp";
	}

}
