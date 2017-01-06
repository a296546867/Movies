<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/User/Header.jsp"%>
    <br/>
    <c:if test="${empty sessionScope.cart.items}">
    	<h2>对不起！您还没有购买任何商品</h2>
    </c:if>
    <c:if test="${!empty sessionScope.cart.items}">
    	<table border="1" width="438">
    		<tr>
    			<th>选择</th>
    			<th>商品名称</th>
    			<th>单价</th>
    			<th>数量</th>
    			<th>小计</th>
    			<th>操作</th>
    		</tr>
    		<c:forEach items="${sessionScope.cart.items}" var="me" varStatus="vs">
    			<tr class="${vs.index%2==0?'odd':'even' }">
	    			<td>
	    				<input type="checkbox" name="bookIds" value="${me.key}"/>
	    			</td>
	    			<td>${me.value.book.name}</td>
	    			<td>${me.value.book.price}</td>
	    			<td>
	    				<input type="text" size="3" id="quantity" name="quantity" value="${me.value.quantity}" onchange="changeNum(this,'${me.key}',${me.value.quantity})"/>
	    			</td>
	    			<td>${me.value.totalPrice}</td>
	    			<td>
	    				<a href="javascript:delOneItem('${me.key}')">删除</a>
	    			</td>
	    		</tr>
    		</c:forEach>
    		<tr>
    			<td colspan="6">
    				共${sessionScope.cart.totalQuantity}件商品,付款金额：${sessionScope.cart.amount}
    				<a href="${pageContext.request.contextPath}/User/ClientController?op=genOrders">去收银台</a>
    			</td>
    		</tr>
    	</table>

    </c:if>
  </body>
</html>
 