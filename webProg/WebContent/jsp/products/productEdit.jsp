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
<title>상품정보수정</title>
<link rel="stylesheet" href="addrbook.css" type="text/css" media="screen" />
<script src="../ckeditor/ckeditor.js"></script>
<script>
window.onload = function() {
	CKEDITOR.replace("productCont", {
		filebrowserUploadUrl : '../ckeditor/fileUpload.jsp',
		customConfig : '../ckeditor/config.js'
	});
}
function return_check() {
	// document.getElementById("contents").value;
	var data = CKEDITOR.instances.contents.getData();
	//console(data);
	if (data == '') {
		alert("input editor..");
		return false;
	}
	return true;
}
function file_open() {
	window.open("upload.jsp", "upload",
			"width=300 height=200 left=300 top=30");
}
</script>
</head>
<body>
	<h3>상품상세정보수정(productEdit.jsp)</h3>
	<h4><a href="productControl.jsp?action=list">상품전체목록</a></h4>
	<form name="frm1" action="productControl.jsp">
		<input type="hidden" name="action" value="update">
		<table style="width:80%">
			<tr>
				<th>상품번호</th>
				<td><input type="text" name="productId" value="${pr.productId}" /></td>
			</tr>
			<tr>
				<th>상품명</th>
				<td><input type="text" name="productName" value="${pr.productName}" /></td>
			</tr>
			<tr>
				<th>상품가격</th>
				<td><input type="text" name="productPrice" value="${pr.productPrice}" /></td>
			</tr>
			<tr>
				<th>상품이미지</th>
				<td><input type="text" name="productImg" value="${pr.productImg}" /></td>
			</tr>
			<tr>
				<th>상품설명</th>
				<td><textarea name="productCont" >${pr.productCont}</textarea></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="수정"></td></tr>
		</table>
	</form>
</body>
</html>