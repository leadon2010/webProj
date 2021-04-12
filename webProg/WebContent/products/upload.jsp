<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head><title>upload.jsp</title>
</head>
<body>
	<form method="post" enctype="multipart/form-data" action="../common/BoardFileUpload">
		<input type="file" name="attach1"/>
		<input type="submit" value="파일업로드"/>
	</form>
</body>
</html>