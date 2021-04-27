<%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	서버명:
	<%=application.getServerInfo()%>
	서블릿 버전:
	<%=application.getMajorVersion()%>.<%=application.getMinorVersion()%><br>
	<h3>/edu 리스트</h3>
	<%
	java.util.Set<String> list = application.getResourcePaths("/jsp");
	if (list != null) {
		Object[] obj = list.toArray();
		Arrays.sort(obj);
		for (int i = 0; i < obj.length; i++) {
			out.print(obj[i] + "<br>");
		}
	}
	%>
</body>
</html>