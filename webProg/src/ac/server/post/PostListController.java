package ac.server.post;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.dto.PostDto;
import ac.server.service.postService;

public class PostListController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		ArrayList<PostDto> postlist = null;
		try {
		postlist = postService.getInstance().selectAll(board_num);
		} catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("postlist", postlist);
		return "/board/board.jsp";
	}

}
