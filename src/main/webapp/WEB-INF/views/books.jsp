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

</head>
<body>
    
    <div class="jumbotron">
  <div class="container text-center">
    <h1><spring:message
					code="books.title" /></h1>      
  </div>
</div>
    
    
	<%@include file="authheader.jsp"%>
	<sec:authorize access="hasRole('DBA')">
		<h1>
			<spring:message code="books.add" />
		</h1>

		<c:url var="addAction" value="/books/add"></c:url>

		<form:form action="${addAction}" commandName="book"
			onSubmit="return ValidateBookAdditionForm();">
				<c:if test="${!empty book.title}">
				 <div class="form-group">
						<form:label path="id">
							<spring:message text="ID: " />
							</form:label>
						<form:input path="id" readonly="true" size="8"
								disabled="true" class="form-control" /> <form:hidden path="id" />
					</div>
								
				</c:if>
 				<div class="form-group">
					<form:label path="title">
							<spring:message code="books.booktitle" />
						</form:label>
					<form:input path="title" name="title" id="ttl" class="form-control"/> <span
						style="display: none;" class="has-error" id="ttl_validation"></span>
				</div>
				
				 <div class="form-group">
					<form:label path="cover">
							<spring:message code="books.bookcover" />
						</form:label>
					<form:input path="cover" value="No-image-available.jpg"
							id="cvr" class="form-control" /> <span style="display: none;" class="has-error"
						id="cvr_validation"></span>
				
				</div>
				
				 <div class="form-group">
					<form:label path="isbn">
							<spring:message text="ISBN: " />
						</form:label>
					<form:input path="isbn" id="isbn" class="form-control"/> <span
						style="display: none;" class="has-error" id="isbn_validation"></span>
					</div>
				 <div class="form-group">
					<form:label path="nbOfPages">
							<spring:message code="books.bookpagenum" />
						</form:label>
					<form:input path="nbOfPages" id="nbOfPages" class="form-control"/> <span
						style="display: none;" class="has-error" id="nbOfPages_validation"></span>
					</div>

				<div class="form-group">
				<form:label path="description">
							<spring:message code="books.bookdescr" />
						</form:label>
					<form:textarea path="description" rows="5" cols="30"
							id="desc" class="form-control" /><span style="display: none;" class="has-error"
						id="desc_validation"></span>
				</div>
				
				
				<div class="form-group">
				<form:label path="authors">
							<spring:message code="books.bookauthors" />
						</form:label>
						<form:input path="authors" id="authors" class="form-control" /><span
						style="display: none;" class="has-error" id="authors_validation"></span>
				</div>
				<div class="form-group">
				<form:label path="price">
							<spring:message code="books.bookprice" />
						</form:label>
						<form:input path="price" id="price" class="form-control" /><span
						style="display: none;" class="has-error" id="price_validation"></span>
				</div>
								
				 <div class="form-group"><form:label path="discount">
							<spring:message code="books.discount" />
						</form:label><form:input path="discount" id="discount" class="form-control" /><span
						style="display: none;" class="has-error" id="discount_validation"></span></div>
				 <div class="form-group">
				 <form:label path="amountInStock">
							<spring:message code="books.amount" />
						</form:label><form:input path="amountInStock" id="amountInStock" class="form-control" /><span
						style="display: none;" class="has-error"
						id="amountInStock_validation"></span></div>
				 
				 <div class="form-group"><form:select multiple="true" path="genres" name="genres">
							<form:options items="${genre}" class="form-control" />
						</form:select></div>
				<div class="form-group">
				 <c:if test="${!empty book.title}">
							<input type="submit" value="<spring:message code="books.edit" />" />
						</c:if> 
						<c:if test="${empty book.title}">
							<input type="submit"
								value="<spring:message code="books.addbook" />" />
						</c:if>
				</div>
		</form:form>
	</sec:authorize>
	<br>
	<div class="form-group">
	<spring:message code="books.search" />:
	<c:url var="searchAction" value="/books/search"></c:url>
	
	<form action="${searchAction}">

		<input type="text" name="keyword" class="form-control input-lg"
			placeholder="<spring:message code="books.keyword"/>" tabindex="1">
		<input type="submit" class="btn btn-primary" value="<spring:message code="books.dosearch"/>" />
		<a href="${pageContext.request.contextPath}/books/search/clear"><button type="button" class="btn btn-warning btn-sm"><spring:message
			code="books.clearsearch" /></button></a>
	<br>
	</form>
	</div>
	<h3>
		<spring:message code="books.list" />:
	</h3>
	 <div class="btn-group btn-group-justified">
	<a href="${pageContext.request.contextPath}/books/byrating" class="btn btn-primary"><spring:message
			code="books.popular" /></a>
	<a href="${pageContext.request.contextPath}/books/byorder" class="btn btn-primary"><spring:message
			code="books.recentlyadded" /></a>
	<sec:authorize access="hasRole('ADMIN')">
		<a href="${pageContext.request.contextPath}/books/lastcommented" class="btn btn-primary"><spring:message
			code="books.lastcommented" /></a>
	</sec:authorize>
		<sec:authorize access="hasRole('USER')">
	<c:if test="${!empty user.inventory }">
		<a href="${pageContext.request.contextPath}/books/recommended" class="btn btn-primary"><spring:message
			code="books.recommended" /></a>
	</c:if>
	</sec:authorize>
	
	</div>
	


	<c:if test="${!empty foundBooks}">
	<div class = "table-responsive">
		<table class="table table-striped">
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
						<td rowspan="3">${book.id}</td>
					</sec:authorize>
					<sec:authorize access="hasRole('USER')">
						<td rowspan="3"><a
							href="${pageContext.request.contextPath}/book/${book.id}"><img
								src="${pageContext.request.contextPath}/img/books/${book.cover}"
								width="150px" alt="No picture available" /></a></td>
					</sec:authorize>
					<c:if test="${loggedinuser eq 'anonymousUser'}">
						<td rowspan="3"><a
							href="${pageContext.request.contextPath}/book/${book.id}" ><img
								src="${pageContext.request.contextPath}/img/books/${book.cover}"
								width="150px" class="img-thumbnail" alt="No picture available" /></a></td>

					</c:if>
					<td><spring:message code="books.booktitle" />: <br> <a
						href="${pageContext.request.contextPath}/book/${book.id}">${book.title}</a></td>
				</tr>
				<tr>
				<td><spring:message code="books.bookprice" />: <br>
						${book.priceWDiscount}</td>
				</tr>
				<tr>
					<td><spring:message code="books.bookrating" />: <br> <fmt:formatNumber
							type="number" minFractionDigits="2" maxFractionDigits="2"
							value="${book.resultRating}" /> (${book.votes } <spring:message
							code="books.bookvotes" />)</td>
				</tr>
				<tr>
					<sec:authorize access="hasRole('DBA')">
						<td><a href="<c:url value='/edit/${book.id}' />"><button type="button"
						class="btn btn-warning"><spring:message
									code="books.edit" /></button></a> | <a
							href="<c:url value='/remove/${book.id}' />"><button type="button"
						class="btn btn-danger"><spring:message
									code="books.delete" /></button></a></td>
					</sec:authorize>
					<sec:authorize access="hasRole('DBA')">
						<tr>
							<td><spring:message code="books.amount" />:
								<span class="badge">${book.amountInStock}</span></td>
						</tr>
					</sec:authorize>
					<sec:authorize access="hasRole('ADMIN')">
						<tr>
							<td><spring:message code="books.comments" />:
								<span class="badge">${fn:length(book.comments)}</span></td>
						</tr>
					</sec:authorize>
				</tr>
				
			</c:forEach>
		</table>
		</div>
	</c:if>
	
	<%@include file="footer.jsp"%>
	
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
