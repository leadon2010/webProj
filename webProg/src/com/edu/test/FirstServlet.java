package com.edu.test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/first")
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = -2893737361570313739L;

	@Override
	public void init(ServletConfig config) {
		System.out.println("init() 실행됨");
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) {
		System.out.println("service() 실행됨");
	}
}
