<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <%@ page session="false"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Shop</title>
</head>
<body>
<h1>Welcome</h1>



<a href="${pageContext.request.contextPath}/books">Go to the book addition/deletion page</a>
<br>
<a href="${pageContext.request.contextPath}/login">Login page</a>
<br>
<a href="${pageContext.request.contextPath}/list">User list</a>
	<br>
		
	Search book:
	<c:url var="searchAction" value="/search"></c:url>
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
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>