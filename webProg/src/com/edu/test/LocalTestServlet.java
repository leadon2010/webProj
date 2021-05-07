package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/local")
public class LocalTestServlet extends HttpServlet {

	private static final long serialVersionUID = -3052902844619507467L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int number = 0;
		String msg = req.getParameter("msg");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print("<html><head><title>MultiThread Test</title></head>");
		out.print("<body><h2>처리결과</h2>");
		while (number++ < 10) {
			out.print(msg + " : " + number + "<br>");
			out.flush();
			System.out.println(msg + " : " + number);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		out.println("<h2>Done" + msg + " !!</h2>");
		out.println("</body></html>");
		out.close();
	}

}
