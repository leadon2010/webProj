<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$.ajax({
			url: "<%=request.getContextPath()%>/EmpServlet?action=list",
			dataType:"json",
			success: function(result){
				//console.log(result);
				//console.log(result.datas[0].firstName);
				var $tableTag = $("<table border=1 />");
				for(i in result.datas){
					//console.log(result.datas[i].firstName);
					var $trTag = $("<tr />").append($("<td />").text(result.datas[i].employeeid)
							,$("<td />").text(result.datas[i].firstName)
							,$("<td />").html("<button>Del</button>"));
					$("#show").append($tableTag.append($trTag));
				}
			}
		});
	});
</script>
</head>
<body>
	<div id="show">
	<table>
	<tr>
	<td></td></tr>
	</table>
	</div>
</body>
</html>
