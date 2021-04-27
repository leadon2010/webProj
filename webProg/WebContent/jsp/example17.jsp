<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:useBean class="com.edu.beans.HelloBean" id="hello"></jsp:useBean>

	<jsp:getProperty property="name" name="hello" />
	<jsp:getProperty property="number" name="hello" />
	<hr>
	<jsp:setProperty property="*" name="hello" />
	<hr>
	<jsp:getProperty property="name" name="hello" />
	<jsp:getProperty property="number" name="hello" />
</body>
</html>