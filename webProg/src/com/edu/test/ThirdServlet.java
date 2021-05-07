package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/third")
public class ThirdServlet extends HttpServlet {

	private static final long serialVersionUID = -224239225645168999L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print("<!doctype html><html><head><meta charset=UTF-8><title>Third Servlet</title></head>");
		out.print("<body>");
		out.print("<h1>좋은 하루!!!</h1></body></html>");
		out.close();

		System.out.println(resp.getCharacterEncoding() + ", " + resp.getContentType());
	}

}
