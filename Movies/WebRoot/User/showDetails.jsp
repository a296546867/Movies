<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/User/Header.jsp"%>
    <br/>
    <img src="${pageContext.request.contextPath}/images${book.path}/${book.photoFileName}"/><br/>
    ${book.name}<br/>
    <br/>
    <a href="${pageContext.request.contextPath}/User/ClientController?op=buyBook&bookId=${book.id}">放入购物车</a>
    <a href="javascript:history.back()">继续购物</a>
  </body>
</html>
