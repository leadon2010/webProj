<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>

<body>
	<form action="fileprocess" name="frm" method="post" enctype="multipart/form-data">
		파일업로드<input type="file" id="files" name="files" multiple />
		<input type="submit" value="전송">

	</form>

</body>

</html>