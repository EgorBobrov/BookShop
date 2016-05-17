<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Books Page</title>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
	<h1>Add a Book</h1>

	<c:url var="addAction" value="/books/add"></c:url>

	<form:form action="${addAction}" commandName="book">
		<table>
			<c:if test="${!empty book.title}">
				<tr>
					<td><form:label path="id">
							<spring:message text="ID: " />
						</form:label></td>
					<td><form:input path="id" readonly="true" size="8"
							disabled="true" /> <form:hidden path="id" /></td>
				</tr>
			</c:if>
			<tr>
				<td><form:label path="title">
						<spring:message text="Title" />
					</form:label></td>
				<td><form:input path="title" /></td>
			</tr>
			<tr>
				<td><form:label path="isbn">
						<spring:message text="ISBN: " />
					</form:label></td>
				<td><form:input path="isbn" /></td>
			</tr>
			<tr>
				<td><form:label path="nbOfPages">
						<spring:message text="Number of pages" />
					</form:label></td>
				<td><form:input path="nbOfPages" /></td>
			</tr>
			<tr>
				<td><form:label path="authors">
						<spring:message text="Authors"/>
					</form:label></td>
				<td><form:input path="authors" /></td>
			</tr>
			<tr>
				<td colspan="2"><c:if test="${!empty book.title}">
						<input type="submit" value="<spring:message text="Edit Book"/>" />
					</c:if> <c:if test="${empty book.title}">
						<input type="submit" value="<spring:message text="Add Book"/>" />
					</c:if></td>
			</tr>
		</table>
	</form:form>
	<br>
		
	<c:url var="searchAction" value="/books/search"></c:url>
	<form action="${searchAction}">
	
         <input type="text" name="keyword" class="form-control input-lg" placeholder="enter the keyword(s)" tabindex="1" required="required">
         <input type="submit" value="Submit" />
    </form >
    <br>
	<h3>Book List</h3>
	<c:if test="${!empty foundBooks}">
		<table class="tg">
			<tr>
				<th width="80">Book ID</th>
				<th width="120">Book Title</th>
				<th width="120">Book ISBN</th>
				<th width="120">Number of Pages</th>
				<th width="120">Authors</th>
			</tr>
			<c:forEach items="${foundBooks}" var="book">
				<tr>
					<td>${book.id}</td>
					<td><a href="${pageContext.request.contextPath}/book/${book.id}">${book.title}</a></td>
					<td>${book.isbn}</td>
					<td>${book.nbOfPages}</td>
					<td>${book.authors}</td>
					<td><a href="<c:url value='/edit/${book.id}' />">Edit</a></td>
					<td><a href="<c:url value='/remove/${book.id}' />">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<a href="${pageContext.request.contextPath}/">Home page</a><br/></p>
</body>
</html>