<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		Map<String,Object> map = (Map<String,Object>)request.getAttribute("responseMap");
		if(null != map){
			out.print(map.get("dealResult"));
		}
	%>
</body>
</html>