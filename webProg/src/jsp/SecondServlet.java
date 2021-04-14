package jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/second")
public class SecondServlet extends HttpServlet {
	private static final long serialVersionUID = 3263711790232205926L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println("SecondServlet!!");
		System.out.println("goGet()");
		PrintWriter out = resp.getWriter();
		out.print("<html><head><title>Test</title></head>");
		out.print("<body><h1>Have a nice day!!</h1></body>");
		out.print("</html>");
		out.close();

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		super.doPost(req, resp);
		System.out.println("goPost()");
		doGet(req, resp);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("second init()");
	}

//	@Override
//	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
////		super.service(req, resp);
//		System.out.println("HttpServletRequest service");
//		doGet(req, resp);
//	}

//	@Override
//	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
////		super.service(req, res);
//		System.out.println("ServletRequest service");
//	}

}
