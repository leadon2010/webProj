<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String id = request.getParameter("id");
String name = request.getParameter("name");
String age = request.getParameter("age");

out.print("{\"id\":" + id + ",\"name\":" + name + ",\"age\":" + age + "}");
%>
