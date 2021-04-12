<%@ tag language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<select>
	<option>선택</option>
	<c:forEach items="${applicationScope.booklist}" var="list">
		<option value="${list.ab_id}">${list.ab_name}
	</c:forEach>
</select>