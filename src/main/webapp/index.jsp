<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome page</title>
</head>
<body>
<h1>Welcome</h1>
<p>This is the Home page. It's available for all users.<br/>

<a href="${pageContext.request.contextPath}/sec/moderation.html">Moderation page</a><br/>
<a href="${pageContext.request.contextPath}/admin/first.html">First Admin page</a><br/>
<a href="${pageContext.request.contextPath}/admin/second.html">Second Admin page</a><br/>

<a href="${pageContext.request.contextPath}/books.html">Go to book addition/deletion page</a>
</p>
</body>
</html>