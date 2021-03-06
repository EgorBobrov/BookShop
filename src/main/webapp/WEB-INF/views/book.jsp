<%@page import="java.time.ZonedDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="com.epamjuniors.bookshop.bookshop_model.user.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@include file="authheader.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>The ${book.title} page</title>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>

</head>
<body>
<div class="container">
	<table class="tg">
		<tr>
			<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
				<th width="80"><spring:message code="books.bookid" /></th>
			</sec:authorize>
			<th width="180"><spring:message code="books.bookcover" /></th>
			<th width="300"><spring:message code="books.bookinfo" /></th>
		</tr>
		<tr>
			<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
				<td rowspan="10">${book.id}</td>
			</sec:authorize>
			<td rowspan="10"><img
				src="${pageContext.request.contextPath}/img/books/${book.cover}"
				width="150px" class="img-thumbnail" alt="No picture available" /></td>
			<td><spring:message code="books.booktitle" />: <br>
				${book.title}</td>
		</tr>
		<tr>
			<td><spring:message code="books.bookauthors" />: <br> <c:forEach
					items="${book.authors}" var="author">
					<a href="${pageContext.request.contextPath}/author/${author.name}">${author.name}</a>
					<br>
				</c:forEach></td>
		</tr>
		<tr>

			<td><spring:message code="books.bookgenres" />: <br> <c:forEach
					items="${book.genres}" var="genre">
					<a href="${pageContext.request.contextPath}/books/genre/${genre}">${genre.toString()}</a>
					<br>
				</c:forEach></td>
		</tr>
		<tr>
			<td><spring:message code="books.bookpagenum" />: <br>
				${book.nbOfPages}</td>
		</tr>
		<tr>
			<td>ISBN: <br> ${book.isbn}
			</td>
		</tr>
		<tr>
			<td><spring:message code="books.bookdescr" />: <br>
				${book.description} <%-- ${XMLUtil.encodeChars(book.description)} 
			 Does not seem to work with UTF-8
			 Why is it necessary? - EB --%></td>
		</tr>
		<tr>
			<td><spring:message code="books.bookprice" />: <br>
				${book.priceWDiscount}<br> <br> <c:if
					test="${book.amountInStock < 15}">  <spring:message code="books.hurry" /> ${book.amountInStock} <spring:message code="books.leftinstock" />. </c:if>
			</td>
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
		<sec:authorize access="hasRole('USER')">
			<c:choose>
				<c:when
					test="${!empty book.amountInStock && book.amountInStock > 0}">
					<tr>
						<td><a href="<c:url value='/tobasket/${book.id}' />"><spring:message
									code="book.tobasket" /></a></td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td><spring:message code="book.noneleft" /></td>
					</tr>

				</c:otherwise>
			</c:choose>
		</sec:authorize>
		<c:if test="${loggedinuser eq 'anonymousUser'}">
			<tr>
				<td><spring:message code="books.please" /> <a
					href="${pageContext.request.contextPath}/login"><spring:message
							code="book.login" /> </a>
				<spring:message code="books.or" /> <a
					href="${pageContext.request.contextPath}/newuser"><spring:message
							code="book.signup" /></a> <spring:message code="books.topurchase" />.
				</td>
			</tr>

		</c:if>
	</table>
	<br>

	<sec:authorize access="hasRole('USER')">
		<c:url var="rateAction" value="/book/${book.id}/rate"></c:url>

		<form:form action="${rateAction}" commandName="book">
		 <div class="form-group">
			<form:label path="rating">
				<spring:message code="book.rate" />
			</form:label>
			<form:select path="rating" class="form-control">
				<form:option value="5" label="5" />
				<form:option value="4" label="4" />
				<form:option value="3" label="3" />
				<form:option value="2" label="2" />
				<form:option value="1" label="1" />
			</form:select>

			<input type="submit" value="<spring:message code="book.ratebook"/>" />
			</div>
		</form:form>
	</sec:authorize>
	<sec:authorize access="hasRole('USER') or hasRole('ADMIN')">
		<c:url var="postCommentAction" value="/book/${book.id}/postcomment"></c:url>

		<form:form action="${postCommentAction}" commandName="comment"
			onSubmit="return validateComment();">
			<div class="form-group">
			<form:label path="date" value="CURRENT YEAR">
					</form:label>
					<form:hidden path="date"
						value="<%=ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME)%>" class="form-control"/>
				</div>
			<div class="form-group">
			<form:label path="user">
						<%-- <spring:message text="User" /> --%>
					</form:label>
				<form:hidden path="user" value="${loggedinuser}" class="form-control" />
				</div>

			<div class="form-group">
			<form:label path="text">
						<spring:message code="book.entercomment"/>
					</form:label>
					<form:textarea path="text" id="comm" class="form-control" /><span
					style="display: none;" class="has-error" id="comment_validation" ></span>
					</div>
			<input type="submit" value="<spring:message code="book.addcomment"/>" />
		</form:form>
	</sec:authorize>
	<c:if test="${!empty comments}">
		<h3><spring:message code="book.comments"/></h3>
		<table class="tg">
			<tr>
				<td><spring:message code="book.postdate"/></td>
				<td><spring:message code="book.commentauthor"/></td>
				<td><spring:message code="book.text"/></td>
				<td><spring:message code="book.likes"/></td>
				<td><spring:message code="book.flags"/></td>
			</tr>
			<c:forEach items="${comments}" var="comment">
				<tr>
					<td>${comment.date}</td>
					<td>${comment.user}</td>
					<td>${comment.text}</td>
					<td><c:if
							test="${loggedinuser eq 'anonymousUser' || comment.user eq loggedinuser || comment.isItDislikedByMe(loggedinuser.toString()) eq true}">
                    <span class="badge">${comment.likes}</span>
                    </c:if> <sec:authorize
							access="hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')">
							<c:if
								test="${comment.user ne loggedinuser && comment.isItDislikedByMe(loggedinuser.toString()) eq false}">
								<a
									href="${pageContext.request.contextPath}/like/${book.id}/${comment.id}"><span class="badge">${comment.likes}</span></a>
							</c:if>
						</sec:authorize></td>

					<td><c:if
							test="${loggedinuser eq 'anonymousUser' || comment.user eq loggedinuser || comment.isItLikedByMe(loggedinuser.toString())eq true}">
                    ${comment.dislikes}
                    </c:if> <sec:authorize
							access="hasRole('USER') or hasRole('ADMIN')">
							<c:if
								test="${comment.user ne loggedinuser && comment.isItLikedByMe(loggedinuser.toString()) eq false}">
								<a
									href="${pageContext.request.contextPath}/dislike/${book.id}/${comment.id}"><span class="badge">${comment.dislikes}</span></a>
							</c:if>
						</sec:authorize></td>
					<sec:authorize access="hasRole('ADMIN')"><td>
							<a
								href="${pageContext.request.contextPath}/removecomment/${book.id}/${comment.id}"><button type="button"
						class="btn btn-danger">Delete</button></a>
						</td></sec:authorize>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<sec:authorize
		access="hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')">
		<c:if test="${!empty similarBooks}">
			<h2>Customers Who Bought This Item Also Bought</h2>

			<table class="tg">
				<c:forEach items="${similarBooks}" var="book">
					<tr>
						<td rowspan="9"><a
							href="${pageContext.request.contextPath}/book/${book.id}"><img
								src="${pageContext.request.contextPath}/img/books/${book.cover}"
								 class="img-responsive"  alt="No picture available" /></a><div class="bookname"> <a
							href="${pageContext.request.contextPath}/book/${book.id}">${book.title}</a></div></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</sec:authorize>


	</div>
	<%@include file="footer.jsp"%>
	
			<c:choose>
				<c:when
					test="${locale eq 'en'}">
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/script.js"></script>
				</c:when>
				<c:otherwise>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/script_ru.js"></script>
				</c:otherwise>
			</c:choose>

</body>
</html>
