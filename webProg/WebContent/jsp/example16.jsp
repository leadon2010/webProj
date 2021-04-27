<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String p = request.getParameter("p");
	request.setAttribute("p", p);
%>
<h3>-- include 전 --</h3>
<jsp:include page="${p }"></jsp:include>
<h3>-- include 후 --</h3>
</body>
</html>