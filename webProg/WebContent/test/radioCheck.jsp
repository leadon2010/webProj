<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script>
		function checkR() {
			//var ch = document.querySelector('input[name="gender"]:checked').value;
			var ch = $("input[type='radio'][name='gender']:checked").val();
			console.log(ch);
			location.href = "radioSam.jsp?name=" + ch;

		}

	</script>
</head>

<body>
	<form>
		<input type="radio" name="gender" value="male" +'0' checked> Male<br>
		<input type="radio" name="gender" value="female" +'1'> Female<br>
		<input type="radio" name="gender" value="other" +'2'> Other<br>
		<br> <input type="button" onclick="checkR()" value="check"></input>
	</form>
</body>

</html>