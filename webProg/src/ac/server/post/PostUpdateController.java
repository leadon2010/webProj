package ac.server.post;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dto.PostDto;
import ac.server.service.postService;

public class PostUpdateController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		PostDto postlist = null;
		try {
		postlist = postService.getInstance().PostSelectOne(post_num);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		request.setAttribute("postlist", postlist);
		return "/post/postModify.jsp";
	}
}
