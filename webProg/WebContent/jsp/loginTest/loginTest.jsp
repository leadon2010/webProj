<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="loginTest.LoginDAO"%>
<%
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	String name = request.getParameter("name");

	LoginDAO dao = new LoginDAO();
	String ret = dao.createRow(id, pwd, name);
	out.println(ret);
%>