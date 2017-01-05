<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://hadesky/functions" prefix="myfn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>欢迎光临</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/CSS/main.css">

</head>

<body>
	<br />
	<br />
	<h1>欢迎光临本小店</h1>
	<br />
	<c:if test="${sessionScope.user==null}">
		<a href="${pageContext.request.contextPath}/User/login.jsp">登录</a>
		<a href="${pageContext.request.contextPath}/User/regist.jsp">免费注册</a>
	</c:if>
	<c:if test="${sessionScope.user!=null}">
    	欢迎您：${sessionScope.user.username} <a
			href="${pageContext.request.contextPath}/User/ClientController?op=logout">注销</a>
	</c:if>
	<hr />
	<br />
	<a href="${pageContext.request.contextPath}/User/ClientController?op=showIndex">首页</a>
	<a href="${pageContext.request.contextPath}/User/ClientController?op=showCustomerOrders">我的订单</a>
	<a href="${pageContext.request.contextPath}/showCart.jsp">购物车</a>
	<br />