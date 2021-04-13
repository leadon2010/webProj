<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%
Map<String, String[]> map = request.getParameterMap();
String result = "[{";
Set<Map.Entry<String, String[]>> set = map.entrySet();
Iterator<Map.Entry<String, String[]>> iter = set.iterator();
while (iter.hasNext()) {
	Map.Entry<String, String[]> ent = iter.next();
	result += "\"" + ent.getKey() + "\":[";
	for (int i = 0; i < ent.getValue().length; i++) {
		result += "\"" + ent.getValue()[i] + "\"" + (i == ent.getValue().length - 1 ? "" : ",");
	}
	result += "]";
	if (iter.hasNext()) {
		result += ",";
	}
}
result += "}]";
out.print(result);
%>