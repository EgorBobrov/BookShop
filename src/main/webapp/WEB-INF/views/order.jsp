<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>

<title>Order of ${user.ssoId}</title>
</head>
<body>
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>

<%@include file="authheader.jsp"%>

	<c:choose>
		<c:when test="${empty user.basket}">
			<spring:message code="basket.empty" />.
			<a href="${pageContext.request.contextPath}/"><spring:message code="bookshop.tomain"/></a>
		</c:when>
		<c:otherwise>
			<table class="tg">
				<tr>
					<th><spring:message code="basket.book" /></th>
					<th><spring:message code="basket.price" /></th>
					<th></th>
				</tr>
				<c:forEach items="${user.basket}" var="book">
					<tr>
						<td><a
						href="${pageContext.request.contextPath}/book/${book.id}">${book.title}</a></td>
						<td>${book.priceWDiscount}</td>
						<td><a href="<c:url value='/removeFromBasket/${book.id}' />"><button type="button"
						class="btn btn-danger"><spring:message code="basket.remove" /></button></a></td>
					</tr>
				</c:forEach>
			</table>
			
			<a href="<c:url value='/checkout/${loggedinuser}' />"><button type="button"
						class="btn btn-success"><spring:message code="basket.checkout" /></button></a>
			</c:otherwise>
	</c:choose>
	<footer class="container-fluid text-center">
  <p><spring:message
					code="books.title" />, 2016</p>  
</footer>

</body>
</html>