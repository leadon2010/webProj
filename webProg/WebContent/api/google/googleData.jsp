<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="employee.EmpDAO"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="net.sf.json.JSONArray"%>
<%
	request.setCharacterEncoding("utf-8");
	EmpDAO dao = new EmpDAO();
	List<Map<String, Object>> list = dao.getEmpPerDept();

	//out.print(JSONArray.fromObject(list).toString());
	//for (Map<String, Object> map : list) {
	out.print("title" + list.iterator().toString());
	//}
%>