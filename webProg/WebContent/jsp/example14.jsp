<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>forward</h3>
	<%
		String p = request.getParameter("p");
		request.setAttribute("p", p);
	%>
	<jsp:forward page="${p }"></jsp:forward>
</body>
</html>