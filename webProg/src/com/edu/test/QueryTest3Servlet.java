package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/jsp/queryTest3")
public class QueryTest3Servlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print("<html><head><title>Query String Test</title></head>");
		out.print("<body>");
		out.print("<h1>Get Request</h1>");
		String name = req.getParameter("name");
		System.out.println(name);
		out.print("이름: " + name + "<br>");
		out.print("</body></html>");
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print("<html><head><title>Query String Test</title></head>");
		out.print("<body>");
		out.print("<h1>Post Request</h1>");
		req.setCharacterEncoding("UTF-8");
		String name = req.getParameter("name");
		out.print("이름: " + name + "<br>");
		out.print("</body></html>");
		out.close();
	}
}
