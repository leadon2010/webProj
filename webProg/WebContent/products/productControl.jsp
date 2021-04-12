<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<jsp:useBean id="prodDAO" class="product.ProductDAO"></jsp:useBean>
<jsp:useBean id="prod" class="product.Product"></jsp:useBean>
<jsp:setProperty property="*" name="prod" />
<%
	request.setCharacterEncoding("utf-8");
%>
<%
	String action = request.getParameter("action");

	if (action.equals("") || action == null) {
		pageContext.forward("productControl.jsp?action=list");

	} else if (action.equals("insert")) {
		prodDAO.insertProd(prod);
		response.sendRedirect("productControl.jsp?action=list");

	} else if (action.equals("updateForm")) {
		System.out.println("updateForm");
		request.setAttribute("pr", prodDAO.getProdcutOne(prod.getProductId()));
		pageContext.forward("productEdit.jsp");

	} else if (action.equals("update")) {
		System.out.println("update");
		prodDAO.updateProd(prod);
		response.sendRedirect("productControl.jsp?action=list");

	} else if (action.equals("getOne")) {
		System.out.println("getOne");
		request.setAttribute("pr", prodDAO.getProdcutOne(prod.getProductId()));
		pageContext.forward("productShow.jsp");

	} else if (action.equals("list")) {
		System.out.println("list");
		request.setAttribute("datas", prodDAO.getProductList());
		request.getRequestDispatcher("productList.jsp").forward(request, response);

	} else if (action.equals("likeit")) {
		int likeitcnt = prodDAO.addLikeit(prod.getProductId());
		System.out.println("likeit" + likeitcnt);
		out.print("{\"likeit\":" + likeitcnt + "}");

	} else {
		out.print("알 수 없는 action입니다.<br>");
		out.print("<a href='productControl.jsp?action=list'>상품목록보기</a>");

	}
%>