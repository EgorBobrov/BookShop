<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
			<th width="120">${pageContext.request.contextPath}</th>
			<th width="120">Books</th>
			<th width="120">Biography</th>
		</tr>
		<tr>
			<td>${author.name}</td>
			<td><img src="${pageContext.request.contextPath}/img/authors/${author.picture}" /></td>
			<td><c:forEach items="${author.books}" var="book">
					<a href="${pageContext.request.contextPath}/book/${book.id}">${book.title}</a><br>
			</c:forEach></td>
			<td>${author.bio}</td>

		</tr>
	</table>
	<br>
	<a href="${pageContext.request.contextPath}/">Go back</a>
	<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
	<h1>Edit an Author</h1>

	<c:url var="editAction" value="/edit-author/${author.name}"></c:url>

	<form:form action="${editAction}" commandName="author">
		<table>
			<tr>
			    <spring:message text="Name" />
				<form:input path="name" class="form-control" type="text" readonly="true"/>
			</tr>
			<tr>
				<td><form:label path="bio">
						<spring:message text="Biography" />
					</form:label></td>
				<td><form:input path="bio" /></td>
			</tr>	<tr>
				<td><form:label path="picture">
					<spring:message text="Picture" />
				</form:label></td>
				<td><form:input path="picture"/></td>
				
			</tr>
		</table>
		<input type="submit" value="<spring:message text="Confirm"/>" />
		
	
        
        
	</form:form>
	</sec:authorize>
</body>
</html>