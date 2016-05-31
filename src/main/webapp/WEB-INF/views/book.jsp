<%@page import="java.time.ZonedDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
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
				<td rowspan="9">${book.id}</td>
			</sec:authorize>
			<td rowspan="9"><img
				src="${pageContext.request.contextPath}/img/books/${book.cover}"
				width="150px" alt="No picture available" /></td>
			<td>Title: <br> ${book.title}
			</td>
		</tr>
		<tr>
			<td>Authors: <br> <c:forEach items="${book.authors}"
					var="author">
					<a href="${pageContext.request.contextPath}/author/${author.name}">${author.name}</a>
					<br>
				</c:forEach></td>
		</tr>
		<tr>

			<td>Genre(s): <br> <c:forEach items="${book.genres}"
					var="genre">
					<a href="${pageContext.request.contextPath}/books/${genre}">${genre.toString()}</a>
					<br>
				</c:forEach></td>
		</tr>
		<tr>
			<td>Number of pages: <br> ${book.nbOfPages}
			</td>
		</tr>
		<tr>
			<td>ISBN: <br> ${book.isbn}
			</td>
		</tr>
		<tr>
			<td>Description: <br> ${book.description}
			</td>
		</tr>
		<tr>
			<td>Price: <br> ${book.priceWDiscount}
			</td>
		</tr>
		<tr>
			<td>Discount: <br> <fmt:formatNumber type="percent"
					maxFractionDigits="0" maxIntegerDigits="2" value="${book.discount}" /></td>
		</tr>
		<tr>
			<td>Rating: <br>
	<fmt:formatNumber type="number" minFractionDigits="2"
		maxFractionDigits="2" value="${book.resultRating}" />
	based on ${book.votes } votes</td>
		</tr>
	</table>
	<br>

	<sec:authorize
		access="hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')">
		<c:url var="rateAction" value="/book/${book.id}/rate"></c:url>

		<form:form action="${rateAction}" commandName="book">
			<form:label path="rating">
				<spring:message text="Rate this book: " />
			</form:label>
			<form:select path="rating">
				<form:option value="1" label="Bad" />
				<form:option value="2" label="Not so good" />
				<form:option value="3" label="Fine" />
				<form:option value="4" label="Good" />
				<form:option value="5" label="Great" />
			</form:select>

			<input type="submit" value="<spring:message text="Rate Book"/>" />
		</form:form>
	</sec:authorize>
	
	<br>
	<sec:authorize
		access="hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')">
		<c:url var="postCommentAction" value="/book/${book.id}/postcomment"></c:url>

		<form:form action="${postCommentAction}" commandName="comment">
			<tr>
				<td><form:label path="date" value="CURRENT YEAR">
						<%-- <spring:message text="Date" /> --%>
					</form:label></td>
				<td><form:hidden path="date"
						value="<%= ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME) %>" />
				</td>
			</tr>
			<tr>
				<td><form:label path="user">
						<%-- <spring:message text="User" /> --%>
					</form:label></td>
				<td><form:hidden path="user" value="${loggedinuser}" /></td>
			</tr>

			<tr>
				<td><form:label path="text">
						<spring:message text="Enter your comment: " />
					</form:label></td>
				<td><form:textarea path="text" /></td>
			</tr>
			<input type="submit" value="<spring:message text="Add Comment"/>" />
		</form:form>
	</sec:authorize>
	<c:if test="${!empty comments}">
		<h3>Comments</h3>
		<table>
			<tr>
				<td>Post date</td>
				<td>Author</td>
				<td>Text</td>
				<td>Likes</td>
				<td>Flags</td>
			</tr>
			<c:forEach items="${comments}" var="comment">
				<tr>
					<td>${comment.date}</td>
					<td>${comment.user}</td>
					<td>${comment.text}</td>
					<td><c:if test="${loggedinuser eq 'anonymousUser' || comment.user eq loggedinuser || comment.isItDislikedByMe(loggedinuser.toString()) eq true}">
                    ${comment.likes}
                    </c:if>
					<sec:authorize access="hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')">
					<c:if test="${comment.user ne loggedinuser && comment.isItDislikedByMe(loggedinuser.toString()) eq false}">
					<a href="${pageContext.request.contextPath}/like/${book.id}/${comment.id}">${comment.likes}</a>
					</c:if></sec:authorize></td>
					
					<td><c:if test="${loggedinuser eq 'anonymousUser' || comment.user eq loggedinuser || comment.isItLikedByMe(loggedinuser.toString())eq true}">
                    ${comment.dislikes}
                    </c:if>
					<sec:authorize access="hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')">
					<c:if test="${comment.user ne loggedinuser && comment.isItLikedByMe(loggedinuser.toString()) eq false}">
					<a href="${pageContext.request.contextPath}/dislike/${book.id}/${comment.id}">${comment.dislikes}</a>
					</c:if></sec:authorize></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<a href="${pageContext.request.contextPath}/">Go back</a>
</body>
</html>
