<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="employee.EmpDAO"%>
<%
	String fullName = request.getParameter("full_name");
	String position = request.getParameter("position");
	String office = request.getParameter("office");
	String extn = request.getParameter("extn");
	String startDate = request.getParameter("start_date");
	String salary = request.getParameter("salary");

	EmpDAO dao = new EmpDAO();
	dao.insertSample(fullName, position, office, extn, startDate, salary);
%>