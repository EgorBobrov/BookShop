<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>The ${book.title} page</title>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>

</head>
<body>
	<table class="tg">
		<tr>
			<th width="80">Book ID</th>
			<th width="120">Book Title</th>
			<th width="120">Book ISBN</th>
			<th width="120">Number of Pages</th>
			<th width="120">Authors</th>
		</tr>
		<tr>
			<td>${book.id}</td>
			<td>${book.title}</td>
			<td>${book.isbn}</td>
			<td>${book.nbOfPages}</td>
			<td>${book.authors}</td>
		</tr>
	</table>
	<br>
	<a href="${pageContext.request.contextPath}/">Go back</a>
</body>
</html>