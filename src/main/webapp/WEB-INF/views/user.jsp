<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${user.ssoId}personalpage</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>

</head>
<body>
	<table class="table table-hover">
		<thead>
			<tr>
				<th><spring:message code="user.fname" /></th>
				<th><spring:message code="user.lname" /></th>
				<th><spring:message code="user.email" /></th>
				<th><spring:message code="user.ssoid" /></th>
				<th><spring:message code="user.basket" /></th>
				<th><spring:message code="user.inventory" /></th>
				<%-- 				<c:if test="${loggedinuser eq user.ssoId}">
					<th width="100"></th>
				</c:if>
				<c:if test="${loggedinuser eq user.ssoId}">
					<th width="100"></th>
				</c:if>
 --%>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${user.firstName}</td>
				<td>${user.lastName}</td>
				<td>${user.email}</td>
				<td>${user.ssoId}</td>
				<td><c:forEach items="${user.basket}" var="book">
						<a href="${pageContext.request.contextPath}/book/${book.id}">${book.title}</a>
						<br>
					</c:forEach></td>
				<td><c:forEach items="${user.inventory}" var="book">
						<a href="${pageContext.request.contextPath}/book/${book.id}">${book.title}</a>
						<br>
					</c:forEach></td>

				<%-- 				<c:if test="${loggedinuser eq user.ssoId}">
					<td><a href="<c:url value='/edit-user-${user.ssoId}' />"
						class="btn btn-success custom-width">edit</a></td>
				</c:if>
				<c:if test="${loggedinuser eq user.ssoId}">
					<td><a href="<c:url value='/delete-user-${user.ssoId}' />"
						class="btn btn-danger custom-width">delete</a></td>
				</c:if>
 --%>
			</tr>
		</tbody>
	</table>
	<a href="${pageContext.request.contextPath}/"><spring:message code="bookshop.tomain"/></a>
</body>
</html>