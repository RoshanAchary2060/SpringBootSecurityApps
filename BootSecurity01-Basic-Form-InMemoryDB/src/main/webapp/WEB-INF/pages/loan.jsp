<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" import="java.util.Random"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1 style="color: blue; text-align: center">Loan Approval Page</h1>
	<b>You are approved for loan amount :: <%=new Random().nextInt(100000)%></b>
	<br>
	<br>
	<a href="./">Home</a>
	<br>
	<a href="signout">Signout Directly</a>
</body>
</html>