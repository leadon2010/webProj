<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>상품록록보기</title>
<link rel="stylesheet" href="addrbook.css" type="text/css" media="screen" />
<script>
	function linkon(str){
		window.location.href = "productControl.jsp?action=getOne&productId=" + str;
	};
	function checkon(str){
		var res = confirm("수정하시겠습니까?");
		if(res)
			window.location.href = "productControl.jsp?action=updateForm&productId=" + str;
		else
			window.location.href = "productControl.jsp?action=list";
	}
</script>	
</head>
<body>
	<h3>상품목록(productList.jsp)</h3>
	<h4>
		<a href="productForm.jsp">상품등록</a>
	</h4>
	<table border="1">
		<tr>
			<th>상품번호/좋아요</th>
			<th>상품명</th>
			<th>상품가격</th>
			<th>상품이미지</th>
		</tr>
		<c:forEach items="${datas}" var="i">
			<tr>
				<td><a href="javascript:checkon(${i.getProductId()})">${i.getProductId()}</a> / ${i.likeit}</td>
				<td><a href="javascript:linkon(${i.getProductId()})">${i.getProductName()}</a></td>
				<td>${i.getProductPrice() }</td>
				<td>${i.productImg }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>