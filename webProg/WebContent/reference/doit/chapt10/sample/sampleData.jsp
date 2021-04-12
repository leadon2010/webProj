<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="employee.EmpDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%
	EmpDAO dao = new EmpDAO();
	List<Map<String, Object>> list = dao.getSampleData();
	JSONArray jsonAry = new JSONArray();
	JSONObject jsonObj = new JSONObject();
	for (Map<String, Object> map : list) {
		JSONArray jsonData = new JSONArray();
		jsonData.add(map.get("fullName"));
		jsonData.add(map.get("position"));
		jsonData.add(map.get("office"));
		jsonData.add(map.get("extn"));
		jsonData.add(map.get("startDate"));
		jsonData.add(map.get("salary"));

		jsonAry.add(jsonData);

	}
	JSONObject json = new JSONObject();
	json.put("data", jsonAry);
	out.println(json.toString());
%>