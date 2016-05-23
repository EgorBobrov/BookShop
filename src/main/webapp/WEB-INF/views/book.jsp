<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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
			<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')"><th width="80">Book ID</th></sec:authorize>
			<th width="120">Book Title</th>
			<th width="200">Cover</th>
			<th width="120">Book ISBN</th>
			<th width="120">Number of Pages</th>
			<th width="200">Description</th>
			<th width="120">Authors</th>
			<th width="100">Genres</th>
			<th width="120">Price</th>
			<th width="120">Discount</th>
		</tr>
		<tr>
			<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')"><td>${book.id}</td></sec:authorize>
			<td>${book.title}</td>
			<td><img src="${pageContext.request.contextPath}/img/books/${book.cover}" /></td>
			<td>${book.isbn}</td>
			<td>${book.nbOfPages}</td>
			<td>${book.description}</td>
			<td><c:forEach items="${book.authors}" var="author">
					<a href="${pageContext.request.contextPath}/author/${author.name}">${author.name}</a><br>
			</c:forEach></td>
			<td><c:forEach items="${book.genres}" var="genre">
					<a href="${pageContext.request.contextPath}/books/${genre}">${genre.toString()}</a><br>
			</c:forEach></td>
			<td>${book.priceWDiscount}</td>
			<td><fmt:formatNumber type="percent" maxFractionDigits="0" maxIntegerDigits="2" value="${book.discount}" /></td>
		</tr>
	</table>
	<br>
	<a href="${pageContext.request.contextPath}/">Go back</a>
</body>
</html>
