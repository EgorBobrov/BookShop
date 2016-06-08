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

		<form:form action="${addAction}" commandName="book"
			onSubmit="return ValidateForm();">
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
					<td><form:input path="title" name="title" id="ttl" /> <span
						style="display: none;" class="has-error" id="ttl_validation"></span></td>
				</tr>
				<tr>
				<td><form:label path="cover">
							<spring:message text="Cover" />
						</form:label></td>
					<td><form:input path="cover" value="No-image-available.jpg"
							id="cvr" /> <span style="display: none;" class="has-error"
						id="cvr_validation"></span></td>
				</tr>

				<tr>
					<td><form:label path="isbn">
							<spring:message text="ISBN: " />
						</form:label></td>
					<td><form:input path="isbn" id="isbn" /> <span
						style="display: none;" class="has-error" id="isbn_validation"></span>
					</td>
				</tr>
				<tr>
					<td><form:label path="nbOfPages">
							<spring:message text="Number of pages" />
						</form:label></td>
					<td><form:input path="nbOfPages" id="nbOfPages" /> <span
						style="display: none;" class="has-error" id="nbOfPages_validation"></span>
					</td>
				</tr>
				<tr>
					<td><form:label path="description">
							<spring:message text="Description" />
						</form:label></td>
					<td><form:textarea path="description" rows="5" cols="30"
							id="desc" /><span style="display: none;" class="has-error"
						id="desc_validation"></span></td>

				</tr>
				<tr>
					<td><form:label path="authors">
							<spring:message text="Authors" />
						</form:label></td>
					<td><form:input path="authors" id="authors" /><span
						style="display: none;" class="has-error" id="authors_validation"></span></td>
				</tr>
				<tr>
					<td><form:label path="price">
							<spring:message text="Price" />
						</form:label></td>
					<td><form:input path="price" id="price" /><span
						style="display: none;" class="has-error" id="price_validation"></span></td>
				</tr>
				<tr>
					<td><form:label path="discount">
							<spring:message text="Discount (%)" />
						</form:label></td>
					<td><form:input path="discount" id="discount" /><span
						style="display: none;" class="has-error" id="discount_validation"></span></td>
				</tr>
				<tr>
					<td><form:label path="amountInStock">
							<spring:message text="Amount in Stock" />
						</form:label></td>
					<td><form:input path="amountInStock" id="amountInStock" /><span
						style="display: none;" class="has-error"
						id="amountInStock_validation"></span></td>
				</tr>
				<tr>
					<td><form:select multiple="true" path="genres" name="genres">
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
			placeholder="enter the keyword(s)" tabindex="1"> <input
			type="submit" value="Submit" />
	</form>
	<br>

	<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
		<p style="font-size: 160%;">
			<a href="${pageContext.request.contextPath}/list">User list</a>
		</p>
	</sec:authorize>


	<h3>Books in our shop:</h3>
	<a href="${pageContext.request.contextPath}/books/byrating">Show
		most popular</a>
	<br>
	<a href="${pageContext.request.contextPath}/books/byorder">Show
		most recently added</a>
	<br>
	<c:if test="${!empty user.inventory }">
		<a href="${pageContext.request.contextPath}/books/recommended">Show
			recommended to me</a>
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
					
			<!--  	<sec:authorize
					access="hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')">
					<c:choose>
						<c:when
							test="${!empty book.amountInStock && book.amountInStock > 0}">
							<tr>
								<td><a href="<c:url value='/tobasket/${book.id}' />">Add
										to basket</a></td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr> <td>This book is unavailable at the moment.</td></tr>
						</c:otherwise></c:choose>						
				</sec:authorize>-->
				
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
	<script type="text/javascript">
		function ValidateForm() {
			var desc_input = document.getElementById("desc");
			var title_input = document.getElementById("ttl");
			var cover_input = document.getElementById("cvr");
			var isbn_input = document.getElementById("isbn");
			var nbOfPages_input = document.getElementById("nbOfPages");
			var authors_input = document.getElementById("authors");
			var price_input = document.getElementById("price");
			var discount_input = document.getElementById("discount");
			var amountInStock_input = document.getElementById("amountInStock");
			var valid = true;

			if (desc_input.value == "") {
				var message = document.getElementById("desc_validation");
				message.innerHTML = "Please don't live book description empty";
				message.style.display = "";
				valid = false;
			}
			if (title_input.value == "") {
				var message = document.getElementById("ttl_validation");
				message.innerHTML = "Please provide book title";
				message.style.display = "";
				valid = false;
			}
			if (isbn_input.value == "") {
				var message = document.getElementById("isbn_validation");
				message.innerHTML = "Please provide book ISBN";
				message.style.display = "";
				valid = false;
			}
			// no idea why, but in this case == "" check doesn't work
			if (cover_input.value.length <= 0) {
				var message = document.getElementById("cvr_validation");
				message.innerHTML = "Please provide cover picture";
				message.style.display = "";
				valid = false;
			}
			if (nbOfPages_input.value == "" || isNaN(nbOfPages_input.value)) {
				var message = document.getElementById("nbOfPages_validation");
				message.innerHTML = "Please provide number of pages in numeric format";
				message.style.display = "";
				valid = false;
			}
			if (authors_input.value == "") {
				var message = document.getElementById("authors_validation");
				message.innerHTML = "Please list at least one author";
				message.style.display = "";
				valid = false;
			}
			if (price_input.value == "" || isNaN(price_input.value)) {
				var message = document.getElementById("price_validation");
				message.innerHTML = "Please enter price of the book in numeric format";
				message.style.display = "";
				valid = false;
			}
			if (discount_input.value == "" || isNaN(discount_input.value)) {
				var message = document.getElementById("discount_validation");
				message.innerHTML = "Please enter discount value in numeric format";
				message.style.display = "";
				valid = false;
			}
			if (amountInStock_input.value == ""
					|| isNaN(amountInStock_input.value)) {
				var message = document
						.getElementById("amountInStock_validation");
				message.innerHTML = "Please enter amount of available books in numeric format";
				message.style.display = "";
				valid = false;
			}

			return valid;
		}
	</script>
</body>
</html>
