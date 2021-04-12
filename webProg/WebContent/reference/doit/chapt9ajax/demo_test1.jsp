<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.PrintWriter" %>
<%@page import="employee.EmpDAO" %>
<%@page import="java.util.List" %>
<%@page import="employee.Employee" %>
<%@page import="org.json.simple.JSONObject" %>
<%@page import="org.json.simple.JSONArray" %>
<%
	EmpDAO dao = new EmpDAO();
	List<Employee> list = dao.getEmplsList();
	JSONObject jsonObj = new JSONObject();
	JSONArray jsonAry = new JSONArray();
	for(Employee emp : list){
		jsonObj = new JSONObject();
		jsonObj.put("firstName", emp.getFirstName());
		jsonObj.put("lastName", emp.getLastName());
		jsonObj.put("age",emp.getSalary());
		jsonAry.add(jsonObj);
	}
	JSONObject retObj = new JSONObject();
	retObj.put("datas", jsonAry);
	out.println(retObj.toString());
%>