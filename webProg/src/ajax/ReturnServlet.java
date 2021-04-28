package ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/javascript/return.jsp")
public class ReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReturnServlet() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("get request");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("post request");
		String val = request.getParameter("username");
		response.getWriter().print("{\"user\":\"get\"}");
	}

}
