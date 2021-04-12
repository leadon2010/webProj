<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.PrintWriter" %>
<%
	String name = request.getParameter("name");
	String city = request.getParameter("city");
	
	out.println("name : " + name + ", city : " + city);
%>