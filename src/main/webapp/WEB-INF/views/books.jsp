<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- <%@ page session="false"%> --%>
<html>
<head>
<title>Book Shop</title>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
	<sec:authorize
		access="hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')">
		<%@include file="authheader.jsp"%>
	</sec:authorize>
	<p style="font-size: 160%;">
		<a href="${pageContext.request.contextPath}/login">Log in/Change
			user</a>
	</p>
	<p style="font-size: 160%;">
		<a href="${pageContext.request.contextPath}/newuser">Sign up as a
			new user</a>
	</p>

	<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
		<p style="font-size: 160%;">
			<a href="${pageContext.request.contextPath}/list">User list</a>
		</p>
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
					<td><form:label path="cover">
							<spring:message text="Cover" />
						</form:label></td>
					<td><form:input path="cover" value="No-image-available.jpg" /></td>
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
							<spring:message text="Description" />
						</form:label></td>
					<td><form:textarea path="description" rows="5" cols="30" /></td>
				</tr>
				<tr>
					<td><form:label path="authors">
							<spring:message text="Authors" />
						</form:label></td>
					<td><form:input path="authors" /></td>
				</tr>
				<tr>
					<td><form:label path="price">
							<spring:message text="Price" />
						</form:label></td>
					<td><form:input path="price" /></td>
				</tr>
				<tr>
					<td><form:label path="discount">
							<spring:message text="Discount (%)" />
						</form:label></td>
					<td><form:input path="discount" /></td>
				</tr>
				<tr>
					<td><form:label path="amountInStock">
							<spring:message text="Amount in Stock" />
						</form:label></td>
					<td><form:input path="amountInStock" /></td>
				</tr>
				<tr>
					<td><form:select multiple="true" path="genres">
							<form:options items="${genre}" />
						</form:select></td>
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
	<br> Search books:
	<c:url var="searchAction" value="/books/search"></c:url>
	<form action="${searchAction}">

		<input type="text" name="keyword" class="form-control input-lg"
			placeholder="enter the keyword(s)" tabindex="1">
		<input type="submit" value="Submit" />
	</form>
	<br>

	<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
		<p style="font-size: 160%;">
			<a href="${pageContext.request.contextPath}/list">User list</a>
		</p>
	</sec:authorize>


	<h3>Books in our shop:</h3>
	<a href="${pageContext.request.contextPath}/books/byrating">Show most popular</a>
	<br>
	<a href="${pageContext.request.contextPath}/books/byorder">Show most recently added</a>
	<br>
	<c:if test="${!empty user.inventory }">
		<a href="${pageContext.request.contextPath}/books/recommended">Show recommended to me</a>
	</c:if>
	<br>


	<c:if test="${!empty foundBooks}">
		<table class="tg">
			<tr>
				<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
					<th width="80">Book ID</th>
				</sec:authorize>
				<th width="180">Cover</th>
				<th width="300">Book info</th>
			</tr>
			<c:forEach items="${foundBooks}" var="book">
				<tr>
					<%-- 					<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')"><td>${book.id}</td></sec:authorize>
					<td><a href="${pageContext.request.contextPath}/book/${book.id}">${book.title}</a></td>
					<td><img src="${pageContext.request.contextPath}/resources/img/books/${book.cover}" width="150px" /></td>
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
 --%>
				<tr>
					<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
						<td rowspan="9">${book.id}</td>
					</sec:authorize>
					<td rowspan="9"><a
						href="${pageContext.request.contextPath}/book/${book.id}"><img
							src="${pageContext.request.contextPath}/img/books/${book.cover}"
							width="150px" alt="No picture available" /></a></td>
					<td>Title: <br> <a
						href="${pageContext.request.contextPath}/book/${book.id}">${book.title}</a></td>
				</tr>
				<tr>
					<td>Authors: <br> <c:forEach items="${book.authors}"
							var="author">
							<a
								href="${pageContext.request.contextPath}/author/${author.name}">${author.name}</a>
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
							maxFractionDigits="0" maxIntegerDigits="2"
							value="${book.discount}" /></td>
				</tr>
				<tr>
					<td>Rating: <br> <fmt:formatNumber type="number"
							minFractionDigits="2" maxFractionDigits="2"
							value="${book.resultRating}" /> based on ${book.votes } votes
					</td>
				</tr>
				<%-- 				<c:if test="${!empty book.amountInStock}">
					<tr>
						<td><a href="<c:url value='/tobasket/${book.id}/${user.ssoId}' />">Add
								to basket</a></td>
					</tr>
				</c:if>
				<c:if test="${empty book.amountInStock}">
					<tr>
						<td>This book is unavailable at the moment.</td>
					</tr>
				</c:if>
 --%>
				<tr>
					<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
						<td><a href="<c:url value='/edit/${book.id}' />">Edit</a></td>
					</sec:authorize>
					<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
						<td><a href="<c:url value='/remove/${book.id}' />">Delete</a></td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>
