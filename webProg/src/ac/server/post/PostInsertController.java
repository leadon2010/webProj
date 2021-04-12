package ac.server.post;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dto.PostDto;
import ac.server.service.postService;

public class PostInsertController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
			int post_num = Integer.parseInt(request.getParameter("post_num"));
			int board_num = Integer.parseInt(request.getParameter("board_num"));
			String post_title = request.getParameter("post_title");
			String content = request.getParameter("content");
			String read = request.getParameter("read");
			String secret = request.getParameter("secret");
			
			
			PrintWriter out = response.getWriter();
			
			PostDto postdto = new PostDto();
			postdto.setPost_num(post_num);
			postdto.setBoard_num(board_num);
			postdto.setPost_title(post_title);
			postdto.setContent(content);
			postdto.setAnswer_check(read);
			postdto.setSecret_check(secret);			
			
			postService service = postService.getInstance();
			try {
				service.PostInsert(postdto);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("post_num", post_num);
			return "redirect:/yedam/MenuList.do";
	}

}
