<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
<title>Order of ${user.ssoId}</title>
</head>
<body>

	<c:choose>
		<c:when test="${empty user.basket}">
			The basket is empty.
			<a href="${pageContext.request.contextPath}/">Go back</a>
		</c:when>
		<c:otherwise>
			<table class="tg">
				<tr>
					<th>Book</th>
					<th>Price</th>
					<th></th>
				</tr>
				<c:forEach items="${user.basket}" var="book">
					<tr>
						<td>${book.title}</td>
						<td>${book.priceWDiscount}</td>
						<td><a href="<c:url value='/removeFromBasket/${book.id}' />">Remove
								from basket</a></td>
					</tr>
				</c:forEach>
			</table>
			
			<a href="<c:url value='/checkout/${loggedinuser}' />">Proceed to checkout</a>
			</c:otherwise>
	</c:choose>

</body>
</html>