package ac.server.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ac.server.controller.Controller;

/**
 * Servlet implementation class LoginoutServ
 */
@WebServlet("/loginoutServ")
public class LoginController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		// String name = request.getParameter("name");

		PrintWriter out = response.getWriter();

		boolean result = LoginDao.getInstance().loginCheck(id, pw);
		if (result == true) {// 로그인이 된 경우

			HttpSession session = request.getSession();

			session.setAttribute("id", id);
			// session.setAttribute("name", name);
			System.out.println(id);
			// System.out.println(name);
			return "redirect:./index.jsp";
		} else {
			out.print("<script>");
			out.print("alert('login id error');");
			out.print("location='./Login.jsp';");
			out.print("</script>");
		}
		return null;
	}

	// 로그인

}
