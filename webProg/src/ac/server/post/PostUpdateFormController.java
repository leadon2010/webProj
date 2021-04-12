package ac.server.post;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dto.PostDto;
import ac.server.service.postService;

public class PostUpdateFormController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		String post_title = request.getParameter("post_title");
		String post_content = request.getParameter("post_content");
		PostDto dto = new PostDto();
		
		
		PrintWriter out = response.getWriter();
		try {
			
			dto = postService.getInstance().PostSelectOne(post_num);
			dto.setPost_title(post_title);
			dto.setContent(post_content);		
			postService.getInstance().PostUpdate(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("post_num", post_num);
		return "redirect:/yedam/MenuList.do";
	}

}