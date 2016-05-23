<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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
			<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
				<th width="80">Book ID</th>
			</sec:authorize>
			<th width="180">Cover</th>
			<th width="300">Book info</th>
		</tr>
		<tr>
			<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
				<td rowspan="8">${book.id}</td>
			</sec:authorize>
			<td rowspan="8"><img
				src="${pageContext.request.contextPath}/img/books/${book.cover}"
				width="150px" alt="No picture available" /></td>
			<td>Title: <br> ${book.title}</td>
		</tr>
		<tr>
			<td>Authors: <br> <c:forEach items="${book.authors}" var="author">
					<a href="${pageContext.request.contextPath}/author/${author.name}">${author.name}</a>
					<br>
				</c:forEach></td>
		</tr>
		<tr>
		
			<td>Genre(s): <br>
			<c:forEach items="${book.genres}" var="genre">
					<a href="${pageContext.request.contextPath}/books/${genre}">${genre.toString()}</a>
					<br>
				</c:forEach></td>
		</tr>
		<tr>
			<td>Number of pages: <br> ${book.nbOfPages}</td>
		</tr>
		<tr>
			<td>ISBN: <br> ${book.isbn}</td>
		</tr>
		<tr>
			<td>Description: <br> ${book.description}</td>
		</tr>
		<tr>
			<td>Price: <br> ${book.priceWDiscount}</td>
		</tr>
		<tr>
			<td>Discount: <br> <fmt:formatNumber type="percent"
					maxFractionDigits="0" maxIntegerDigits="2" value="${book.discount}" /></td>
		</tr>
	</table>
	<br>
	<a href="${pageContext.request.contextPath}/">Go back</a>
</body>
</html>
