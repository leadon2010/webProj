<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="employee.EmpDAO"%>
<%@page import="employee.Employee"%>
<%@page import="java.util.List"%>
<%
	String name = request.getParameter("name");
	String city = request.getParameter("city");

	JSONObject jsonObj = new JSONObject();
	JSONArray jsonAry = new JSONArray();
	EmpDAO dao = new EmpDAO();
	List<Employee> list = dao.getEmplsList();

	for (Employee emp : list) {
		jsonObj = new JSONObject();
		jsonObj.put("firstName", emp.getFirstName());
		jsonObj.put("lastName", emp.getLastName());
		jsonObj.put("age", emp.getSalary());
		jsonAry.add(jsonObj);
	}
	JSONObject retObj = new JSONObject();
	retObj.put("datas", jsonAry);
	out.println(retObj.toString());
%>