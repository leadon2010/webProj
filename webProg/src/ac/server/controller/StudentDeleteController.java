package ac.server.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import ac.server.service.StudentService;

public class StudentDeleteController implements Controller {

	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Parameter 추출
		String id = request.getParameter("id");

		// Service 객체의 메서드 호출
		StudentService service = StudentService.getInstance();
		try {
			service.StudentDelete(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Output View 페이지로 이동
		// HttpUtil.forward(request, response, "/result/memberDeleteOutput.jsp");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('회원정보 삭제가 완료되었습니다.');");
		out.print("location='StudentList.do'");
		out.print("</script>");
		return null;
	}
}
