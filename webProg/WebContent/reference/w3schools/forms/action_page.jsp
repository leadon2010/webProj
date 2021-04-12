<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String fname = request.getParameter("fname");
	out.println("<h1>Submitted Form Data</h1>");
	out.println("<h2>Your input was received as:</h2>");
	out.println("<input type='text' value='fname=" + fname + "'>");
%>