<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="loginTest.LoginDAO"%>
<%
	LoginDAO dao = new LoginDAO();
	String name = dao.getName("111");
	out.println(name);
%>