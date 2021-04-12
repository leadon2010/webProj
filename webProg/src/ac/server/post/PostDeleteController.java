package ac.server.post;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ac.server.controller.Controller;
import ac.server.service.postService;

public class PostDeleteController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		int post_num = Integer.parseInt(request.getParameter("post_num"));
		
		postService service = postService.getInstance();
			try {
				service.PostDelete(post_num);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		response.setContentType("text/html; charset=utf-8");
		PrintWriter  out = response.getWriter();
		out.print("<script>alert('게시글 삭제가 완료되었습니다! ' );");
		out.print("</script>");
		return "redirect:/yedam/MenuList.do";
	}

}