package com.edu.test;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/forthServlet")
public class ForthServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String p1 = req.getParameter("p1");

		req.setAttribute("p1", p1);

//		resp.sendRedirect("FifthServlet");

		RequestDispatcher rd = req.getRequestDispatcher("fifthServlet");
		rd.forward(req, resp);

	}
}
