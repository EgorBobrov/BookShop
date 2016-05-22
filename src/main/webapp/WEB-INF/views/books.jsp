<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- <%@ page session="false"%> --%>
<html>
<head>
<title>Book Shop</title>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
<sec:authorize access="hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')">
<%@include file="authheader.jsp" %>
</sec:authorize>
<p style="font-size:160%;"><a href="${pageContext.request.contextPath}/login">Log in/Change user</a></p>
<p style="font-size:160%;"><a href="${pageContext.request.contextPath}/newuser">Sign up as a new user</a></p>

<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
<p style="font-size:160%;"><a href="${pageContext.request.contextPath}/list">User list</a></p>
</sec:authorize>
  <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
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
				<td><form:label path="description">
						<spring:message text="Description"/>
					</form:label></td>
				<td><form:input path="description" /></td>
			</tr>
			<tr>
				<td><form:label path="authors">
						<spring:message text="Authors"/>
					</form:label></td>
				<td><form:input path="authors" /></td>
			</tr>
			<tr>
				<td><form:label path="price">
						<spring:message text="Price"/>
					</form:label></td>
				<td><form:input path="price" /></td>
			</tr>
			<tr>
				<td><form:label path="discount">
						<spring:message text="Discount (%)"/>
					</form:label></td>
				<td><form:input path="discount" /></td>
			</tr>
			<tr>
				<td><form:label path="amountInStock">
						<spring:message text="Amount in Stock"/>
					</form:label></td>
				<td><form:input path="amountInStock" /></td>
			</tr>
			<tr>
			<td><form:select multiple="true" path="genres">
     			 <form:options items="${genre}"/>
   				</form:select>
			</td>
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
	</sec:authorize>
	<br>
	Search books:
	<c:url var="searchAction" value="/books/search"></c:url>
	<form action="${searchAction}">
	
         <input type="text" name="keyword" class="form-control input-lg" placeholder="enter the keyword(s)" tabindex="1" required="required">
         <input type="submit" value="Submit" />
    </form >
    <br>
    
    <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
<p style="font-size:160%;"><a href="${pageContext.request.contextPath}/list">User list</a></p>
</sec:authorize>
    
    
	<h3>Books in our shop:</h3>
	<c:if test="${!empty foundBooks}">
		<table class="tg">
			<tr>
				<th width="80">Book ID</th>
				<th width="120">Book Title</th>
				<th width="120">Book ISBN</th>
				<th width="120">Number of Pages</th>
				<th width="200">Description</th>
				<th width="120">Authors</th>
				<th width="100">Genres</th>
				<th width="80">Discounted Price</th>
				<th width="80">Discount</th>
			</tr>
			<c:forEach items="${foundBooks}" var="book">
				<tr>
					<td>${book.id}</td>
					<td><a href="${pageContext.request.contextPath}/book/${book.id}">${book.title}</a></td>
					<td>${book.isbn}</td>
					<td>${book.nbOfPages}</td>
					<td>${book.description}</td>
				 	<td>
				 	<c:forEach items="${book.authors}" var="author">
					<a href="${pageContext.request.contextPath}/author/${author.name}">${author.name}</a><br>
					</c:forEach>
					</td>
					<td><c:forEach items="${book.genres}" var="genre">
					<a href="${pageContext.request.contextPath}/books/${genre}">${genre.toString()}</a><br>
					</c:forEach></td>
					<td>${book.priceWDiscount}</td>
					<td><fmt:formatNumber type="percent" maxFractionDigits="0" maxIntegerDigits="2" value="${book.discount}" /></td>
					
					<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')"><td><a href="<c:url value='/edit/${book.id}' />">Edit</a></td></sec:authorize>
					<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')"><td><a href="<c:url value='/remove/${book.id}' />">Delete</a></td></sec:authorize>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>