<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	student.list
	<table border='1'>
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Email</th>
			<th>Admission</th>
			<th>Course</th>
		</tr>
		<c:forEach items="${list }" var="ab">
			<tr>
				<td>${ab.getId() }</td>
				<td>${ab.getStudent_name()}</td>
				<td>${ab.getEmail()}</td>
				<td>${ab.getAdmission()}</td>
				<td>${ab.getCourse_code()}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>