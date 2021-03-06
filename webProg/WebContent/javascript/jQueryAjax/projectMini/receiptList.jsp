<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="ajax.projectMini.Receipt"%>
<%@page import="ajax.projectMini.MemberDAO"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<title>주문정보 리스트</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<style>
		* {
			box-sizing: border-box;
		}

		body {
			font-family: Arial, Helvetica, sans-serif;
		}

		/* Style the header */
		header {
			background-color: #666;
			padding: 30px;
			text-align: center;
			font-size: 35px;
			color: white;
		}

		/* Create two columns/boxes that floats next to each other */
		nav {
			float: left;
			width: 25%;
			height: 500px;
			/* only for demonstration, should be removed */
			background: #ccc;
			padding: 20px;
		}

		/* Style the list inside the menu */
		nav ul {
			list-style-type: none;
			padding: 0;
		}

		article {
			float: left;
			padding: 20px;
			width: 60%;
			background-color: #f1f1f1;
			height: auto;
			/* only for demonstration, should be removed */
		}

		/* Clear floats after the columns */
		section:after {
			content: "";
			display: table;
			clear: both;
		}

		/* Style the footer */
		footer {
			background-color: #777;
			padding: 10px;
			text-align: center;
			color: white;
		}

		/* Responsive layout - makes the two columns/boxes stack on top of each other instead of next to each other, on small screens */
		@media (max-width : 600px) {

			nav,
			article {
				width: 100%;
				height: auto;
			}
		}

		.center {
			display: "block";
			margin-left: auto;
			margin-right: auto;
			width: 20%;
		}
	</style>
	<script>
	function receiptTxn(receiptNo){
		window.location.href = "http://localhost:80/javaScript/MiniControl?action=receiptTxn&receiptNo="+receiptNo;
		console.log("txn");
	}
	</script>
</head>

<body>

	<header>
		<h2>입고 관리 화면</h2>
	</header>

	<section>
		<nav>
			<ul>
				<li><a href="receipt.jsp">입고관리화면</a></li>
				<li><a href="issue.jsp">출고관리화면</a></li>
				<li><a href="onhand.jsp">재고조회화면</a></li>
			</ul>
		</nav>

		<article>
			<h1>Receipt List</h1>
			<%
		MemberDAO dao = new MemberDAO();
		List<Receipt> list = dao.getReceiptInfoList();
		out.println("<table border=1>");
		out.println(
				"<tr><th>ReceiptNo</th><th>ReceiptItem</th><th>ReceiptQty</th><th>ReceiptPrice</th><th>ReceiptAmt</th><th>ReceiptSub</th><th>Vendor</th><th width=150>ReceiptTxn</th></tr>");
		for (Receipt rt : list) {
			out.println("<tr><td>");
			out.println(rt.getReceiptNo() + "</td><td>");
			out.println(rt.getReceiptItem() + "</td><td>");
			out.println(rt.getReceiptQty() + "</td><td>");
			out.println(rt.getReceiptPrice() + "</td><td>");
			out.println(rt.getReceiptAmount() + "</td><td>");
			out.println(rt.getReceiptSub() + "</td><td>");
			out.println(rt.getReceiptVendor() + "</td><td align='center'>");
			out.println("<button onclick=\"receiptTxn(\'"+rt.getReceiptNo()+"\')\">입고</button>" + "</td></tr>");
		}
		out.println("</table>");
	%>
		</article>
	</section>

	<footer>
		<p>Footer</p>
	</footer>

</body>

</html>