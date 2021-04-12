<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>상품등록</title>
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
	<h3>상품등록화면(productForm.jsp)</h3>
	<h4>
		<a href="productControl.jsp?action=list">상품목록보기</a>
	</h4>
	<form name="form1" method="post" action="productControl.jsp">
		<input type="hidden" name="action" value="insert">
		<table border="1" style="width: 800px">
			<tr>
				<th>상품명</th>
				<td><input type="text" name="productName" /></td>
			</tr>
			<tr>
				<th>상품가격</th>
				<td><input type="text" name="productPrice" /></td>
			</tr>
			<tr>
				<th>상품이미지</th>
				<td><input type="text" name="productImg" />
					<input type="button" value="파일첨부" onclick="file_open()"></td>
			</tr>
			<tr>
				<th>상품설명</th>
				<td><textarea name="productCont"> </textarea></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="저장" />
					<input type="reset" value="취소" /></td>
		</table>
	</form>
</body>
</html>