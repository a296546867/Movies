<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/Admin/Header.jsp" %>
<br/><br/>
<form action="${pageContext.request.contextPath}/Admin/ControllerServlet?op=addCategory" method="post">
    	<table border="1" width="438">
    		<tr>
    			<td>分类名称：</td>
    			<td>
    				<input type="text" name="name"/>
    			</td>
    		</tr>
    		<tr>
    			<td>分类描述：</td>
    			<td>
    				<textarea rows="3" cols="38" name="description"></textarea>
    			</td>
    		</tr>
    		<tr>
    			<td colspan="2">
    				<input type="submit" value="保存"/>
    			</td>
    		</tr>
    	</table>
    </form>
  </body>
</html>
