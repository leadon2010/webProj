package ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/postData")
public class PostServlet extends HttpServlet {

	private static final long serialVersionUID = 4696850546778451709L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.print("<university>");
		out.print("<major>English</major>");
		out.print("</university>");
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println(config.getServletName());
	}

}
