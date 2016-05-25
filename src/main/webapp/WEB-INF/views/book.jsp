<%@page import="bookshop.model.user.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
	
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
	
	<c:url var="postCommentAction" value="/book/${book.id}/postcomment"></c:url>

	<form:form action="${postCommentAction}" commandName="comment">
				<tr>
				<td><form:label path="date" value="CURRENT YEAR" >
						<spring:message text="Date" />
					</form:label></td>
				<td><form:input path="date" value="CURRENT YEAR"/></td>
			</tr>
			<tr>
				<td><form:label path="user">
						<spring:message text="User" />
					</form:label></td>
				<td><form:input path="user" value="CURRENT USER" /></td>
			</tr>
			
			<tr>
				<td><form:label path="text">
						<spring:message text="Text: " />
					</form:label></td>
				<td><form:textarea path="text" /></td>
			</tr>
	<input type="submit" value="<spring:message text="Add Comment"/>" />
	</form:form>
	
	<c:if test="${!empty comments}">
		<table>
			<tr>
				<td>Post date</td>
				<td>Author</td>
				<td>Text</td>
			</tr>
			<c:forEach items="${comments}" var="comment">
			<tr>
				<td>${comment.date}</td>
				<td>${comment.user}</td>
				<td>${comment.text}</td>
			</tr>
			</c:forEach>
		</table>
	</c:if>
	<a href="${pageContext.request.contextPath}/">Go back</a>
</body>
</html>
