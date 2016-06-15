<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- <%@ page session="false"%> --%>
<html>
<head>
<title>Book Shop</title>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
	<%@include file="authheader.jsp"%>

	<h1>
		<spring:message code="books.title" />
	</h1>
	<sec:authorize access="hasRole('DBA')">
		<h1>
			<spring:message code="books.add" />
		</h1>

		<c:url var="addAction" value="/books/add"></c:url>

		<form:form action="${addAction}" commandName="book"
			onSubmit="return ValidateBookAdditionForm();">
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
							<spring:message code="books.booktitle" />
						</form:label></td>
					<td><form:input path="title" name="title" id="ttl" /> <span
						style="display: none;" class="has-error" id="ttl_validation"></span></td>
				</tr>
				<tr>
					<td><form:label path="cover">
							<spring:message code="books.bookcover" />
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
							<spring:message code="books.bookpagenum" />
						</form:label></td>
					<td><form:input path="nbOfPages" id="nbOfPages" /> <span
						style="display: none;" class="has-error" id="nbOfPages_validation"></span>
					</td>
				</tr>
				<tr>
					<td><form:label path="description">
							<spring:message code="books.bookdescr" />
						</form:label></td>
					<td><form:textarea path="description" rows="5" cols="30"
							id="desc" /><span style="display: none;" class="has-error"
						id="desc_validation"></span></td>

				</tr>
				<tr>
					<td><form:label path="authors">
							<spring:message code="books.bookauthors" />
						</form:label></td>
					<td><form:input path="authors" id="authors" /><span
						style="display: none;" class="has-error" id="authors_validation"></span></td>
				</tr>
				<tr>
					<td><form:label path="price">
							<spring:message code="books.bookprice" />
						</form:label></td>
					<td><form:input path="price" id="price" /><span
						style="display: none;" class="has-error" id="price_validation"></span></td>
				</tr>
				<tr>
					<td><form:label path="discount">
							<spring:message code="books.discount" />
						</form:label></td>
					<td><form:input path="discount" id="discount" /><span
						style="display: none;" class="has-error" id="discount_validation"></span></td>
				</tr>
				<tr>
					<td><form:label path="amountInStock">
							<spring:message code="books.amount" />
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
							<input type="submit" value="<spring:message code="books.edit" />" />
						</c:if> <c:if test="${empty book.title}">
							<input type="submit"
								value="<spring:message code="books.addbook" />" />
						</c:if></td>
				</tr>
			</table>
		</form:form>
	</sec:authorize>
	<br>
	<spring:message code="books.search" />:
	<c:url var="searchAction" value="/books/search"></c:url>
	<form action="${searchAction}">

		<input type="text" name="keyword" class="form-control input-lg"
			placeholder="<spring:message code="books.keyword"/>" tabindex="1">
		<input type="submit" value="<spring:message code="books.dosearch"/>" />
	</form>
	<br>


	<h3>
		<spring:message code="books.list" />:
	</h3>
	<a href="${pageContext.request.contextPath}/books/byrating"><spring:message
			code="books.popular" /></a>
	<br>
	<a href="${pageContext.request.contextPath}/books/byorder"><spring:message
			code="books.recentlyadded" /></a>
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
					<th width="80"><spring:message code="books.bookid" /></th>
				</sec:authorize>

				<sec:authorize access="hasRole('USER')">
					<th width="180"><spring:message code="books.bookcover" /></th>
				</sec:authorize>
				<c:if test="${loggedinuser eq 'anonymousUser'}">
					<th width="180"><spring:message code="books.bookcover" /></th>
				</c:if>

				<th width="300"><spring:message code="books.bookinfo" /></th>
			</tr>
			<c:forEach items="${foundBooks}" var="book">
				<tr>
				<tr>
					<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
						<td rowspan="9">${book.id}</td>
					</sec:authorize>
					<sec:authorize access="hasRole('USER')">
						<td rowspan="9"><a
							href="${pageContext.request.contextPath}/book/${book.id}"><img
								src="${pageContext.request.contextPath}/img/books/${book.cover}"
								width="150px" alt="No picture available" /></a></td>
					</sec:authorize>
					<c:if test="${loggedinuser eq 'anonymousUser'}">
						<td rowspan="9"><a
							href="${pageContext.request.contextPath}/book/${book.id}"><img
								src="${pageContext.request.contextPath}/img/books/${book.cover}"
								width="150px" alt="No picture available" /></a></td>

					</c:if>
					<td><spring:message code="books.booktitle" />: <br> <a
						href="${pageContext.request.contextPath}/book/${book.id}">${book.title}</a></td>
				</tr>
				<tr>
					<td><spring:message code="books.bookauthors" />: <br> <c:forEach
							items="${book.authors}" var="author">
							<a
								href="${pageContext.request.contextPath}/author/${author.name}">${author.name}</a>
							<br>
						</c:forEach></td>
				</tr>
				<tr>

					<td><spring:message code="books.bookgenres" />: <br> <c:forEach
							items="${book.genres}" var="genre">
							<a href="${pageContext.request.contextPath}/books/${genre}">${genre.toString()}</a>
							<br>
						</c:forEach></td>
				</tr>
				<sec:authorize access="hasRole('USER')">
					<tr>
						<td><spring:message code="books.bookpagenum" />: <br>
							${book.nbOfPages}</td>
					</tr>
				</sec:authorize>
				<c:if test="${loggedinuser eq 'anonymousUser'}">
					<tr>
						<td><spring:message code="books.bookpagenum" />: <br>
							${book.nbOfPages}</td>
					</tr>
				</c:if>
				<tr>
					<td>ISBN: <br> ${book.isbn}
					</td>
				</tr>
				<sec:authorize access="hasRole('USER')">
					<tr>
						<td><spring:message code="books.bookdescr" />: <br>
							${book.description}</td>
					</tr>
				</sec:authorize>
				<c:if test="${loggedinuser eq 'anonymousUser'}">
					<tr>
						<td><spring:message code="books.bookdescr" />: <br>
							${book.description}</td>
					</tr>
				</c:if>

				<tr>
					<td><spring:message code="books.bookprice" />: <br>
						${book.priceWDiscount}</td>
				</tr>
				<tr>
					<td><spring:message code="books.discount" />: <br> <fmt:formatNumber
							type="percent" maxFractionDigits="0" maxIntegerDigits="2"
							value="${book.discount}" /></td>
				</tr>
				<tr>
					<td><spring:message code="books.bookrating" />: <br> <fmt:formatNumber
							type="number" minFractionDigits="2" maxFractionDigits="2"
							value="${book.resultRating}" /> (${book.votes } <spring:message
							code="books.bookvotes" />)</td>
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
					<sec:authorize access="hasRole('DBA')">
						<td><a href="<c:url value='/edit/${book.id}' />"><spring:message
									code="books.edit" /></a> | <a
							href="<c:url value='/remove/${book.id}' />"><spring:message
									code="books.delete" /></a></td>
					</sec:authorize>
					<sec:authorize access="hasRole('DBA')">
						<tr>
							<td><spring:message code="books.amount" />:
								${book.amountInStock}</td>
						</tr>
					</sec:authorize>
					<sec:authorize access="hasRole('ADMIN')">
						<tr>
							<td><spring:message code="books.comments" />:
								${fn:length(book.comments)}</td>
						</tr>
					</sec:authorize>


				</tr>
			</c:forEach>
		</table>
	</c:if>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
