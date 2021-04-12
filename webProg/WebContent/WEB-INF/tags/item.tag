<%@ tag language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="mybgcolor" required="true"%>
<%@ attribute name="myborder" required="true"%>
<%@ attribute name="boardList" type="java.util.ArrayList"
	required="true"%>


<table border="${myborder}" bgcolor="${mybgcolor}">
	<c:forEach items="${boardList}" var="board">
		<c:forEach items="${board}" var="map">
			<tr>
				<td>${map.key}</td>
				<td>${map.value}</td>
			</tr>
		</c:forEach>
	</c:forEach>
</table>
