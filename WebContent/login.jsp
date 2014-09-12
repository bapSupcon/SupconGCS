<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户绑定</title>
</head>
<body>
	<form action="binding.action" method="post">
		<input type="hidden" name = "user.openId" value="<%=request.getParameter("openId")%>"/>
		<input type="text" name="user.userName"  />
		<input type="password" name="user.password" />
		<button type="submit" name="submit" >提交</button>   <button type="reset">取消</button>
	</form>
</body>
</html>