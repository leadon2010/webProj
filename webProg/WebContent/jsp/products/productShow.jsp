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
<link rel="stylesheet" href="addrbook.css" type="text/css" media="screen" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$("#likeit").click(function(){
			$.ajax({
				url:"productControl.jsp?action=likeit&productId=${pr.productId}",
				success:function(result){
					console.log(result);
					var datas = JSON.parse(result);
					console.log(datas);
					$("#likeit").html(datas.likeit);
				}
			});
		});
	});
	function likeit(str){
		//alert(str);
		//window.location.href = "productControl.jsp?action=likeit&productId="+str;
	}
</script>
<title>상품상세조회</title>
</head>
<body>
	<h3>상품상세정보(productShow.jsp)</h3>
	<h4><a href="productControl.jsp?action=list">상품전체목록</a></h4>
	<table>
		<tr>
			<th>상품번호</th>
			<td>${pr.productId}</td>
		</tr>
		<tr>
			<th>상품명</th>
			<td>${pr.productName}</td>
		</tr>
		<tr>
			<th>상품가격</th>
			<td>${pr.productPrice}</td>
		</tr>
		<tr>
			<th>상품이미지</th>
			<td>${pr.productImg} / <a href="javascript:likeit(1)" id="likeit">${pr.likeit}</a></td>
		</tr>
		<tr>
			<th>상품설명</th>
			<td>${pr.productCont}</td>
		</tr>
	</table>
</body>
</html>