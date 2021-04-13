<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="java.util.Map"%>
<%
Map<String, String[]> map = request.getParameterMap();

JSONObject obj = new JSONObject();
JSONArray ary = new JSONArray();

map.forEach((k, v) -> {
	String[] args = request.getParameterValues(k);
	JSONArray inner = new JSONArray();
	for (int i = 0; i < args.length; i++) {
		inner.add(args[i]);
	}
	obj.put(k, inner);
});
ary.add(obj);
out.print(ary.toString());
%>