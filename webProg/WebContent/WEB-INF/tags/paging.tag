<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="paging" type="common.Paging"%>
<%@ attribute name="jsfunc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<div class="pagination">
	<div class="center">
		<c:if test="${paging.startPage>1}">
			<span><a href="javascript:${jsfunc}(${paging.startPage-1})">이전</a></span>
		</c:if>
		<c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="i">
			<c:if test="${i != paging.page}">
				<span><a href="javascript:${jsfunc}(${i})">${i}</a></span>
			</c:if>
			<c:if test="${i == paging.page}">
				<%-- <li class="active">${i} --%>
				<span>${i}</span>
			</c:if>
		</c:forEach>
		<c:if test="${paging.endPage<paging.totalPageCount}">
			<span><a href="javascript:${jsfunc}(${paging.endPage+1})">다음</a></span>
		</c:if>
	</div>
</div>