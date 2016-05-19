<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${author.name}'s page</title>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>

</head>
<body>
	<table class="tg">
		<tr>
			<th width="100">Author Name</th>
			<th width="120">Books</th>
			<th width="120">Biography</th>
		</tr>
		<tr>
			<td>${author.name}</td>
			<td><c:forEach items="${author.books}" var="book">
					<a href="${pageContext.request.contextPath}/book/${book.id}">${book.title}</a><br>
			</c:forEach></td>
			<td>${author.bio}</td>
			<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')"><td><a href="<c:url value='/edit-author/${author.name}' />">[TBD]Edit</a></td></sec:authorize>
		</tr>
	</table>
	<br>
	<a href="${pageContext.request.contextPath}/">Go back</a>
</body>
</html>