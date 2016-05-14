<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>The ${book.title} page</title>
<style type="text/css">
.tg {
	border-collapse: collapse;
	border-spacing: 0;
	border-color: #ccc;
}

.tg td {
	font-family: Arial, sans-serif;
	font-size: 14px;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: #ccc;
	color: #333;
	background-color: #fff;
}

.tg th {
	font-family: Arial, sans-serif;
	font-size: 14px;
	font-weight: normal;
	padding: 10px 5px;
	border-style: solid;
	border-width: 1px;
	overflow: hidden;
	word-break: normal;
	border-color: #ccc;
	color: #333;
	background-color: #f0f0f0;
}

.tg .tg-4eph {
	background-color: #f9f9f9
}
</style>

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
	<a href="${pageContext.request.contextPath}/books.html">Go back</a>
</body>
</html>