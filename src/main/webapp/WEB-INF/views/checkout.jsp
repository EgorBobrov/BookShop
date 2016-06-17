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

<title>Order of ${user.ssoId}: checkout</title>
<%@include file="authheader.jsp"%>
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>

<c:if test="${!empty existingAddresses}">
	<br>
	<h3><spring:message code="checkout.chooseaddress" />.</h3>
	<table class="tg">
		<c:forEach items="${existingAddresses}" var="address">
			<tr>
				<td>${address.toString()}</td>
				<td><input type="submit"
					value="<spring:message code="checkout.shiptoaddress" />" /></td>
				<td><a href="<c:url value='/remove/address/${address.id}' />"><spring:message code="checkout.deleteaddress" /></a></td>
			</tr>
		</c:forEach>
	</table>
</c:if>

<c:url var="addAction" value="/addresses/add"></c:url>

<form:form action="${addAction}" commandName="address" onSubmit="return validateAddress();">
	<h3><spring:message code="checkout.enteraddress" />.</h3>
	<table>
		<tr>
			<td><form:label path="name">
					<spring:message code="checkout.entername" />
				</form:label></td>
			<td><form:input path="name" id="name"/><span
						style="display: none;" class="has-error" id="name_validation"></span></td>
		</tr>
		<tr>
			<td><form:label path="street">
					<spring:message code="checkout.enterstrt" />
				</form:label></td>
			<td><form:input path="street" id="street"/><span
						style="display: none;" class="has-error" id="street_validation"></span></td>
		</tr>
		<tr>
		<tr>
			<td><form:label path="city">
					<spring:message code="checkout.entercity" />
				</form:label></td>
			<td><form:input path="city" id="city"/><span
						style="display: none;" class="has-error" id="city_validation"></span></td>
		</tr>
		<tr>
		<tr>
			<td><form:label path="region">
					<spring:message code="checkout.enterregion" />
				</form:label></td>
			<td><form:input path="region" id="region"/><span
						style="display: none;" class="has-error" id="region_validation"></span></td>
		</tr>
		<tr>
		<tr>
			<td><form:label path="country">
					<spring:message code="checkout.entercountry" />
				</form:label></td>
			<td><form:input path="country" id="country"/><span
						style="display: none;" class="has-error" id="country_validation"></span></td>
		</tr>
		<tr>
			<td><c:if test="${empty address.street}">
					<input type="submit" value="<spring:message code="checkout.addaddress" />" />
				</c:if></td>
		</tr>
	</table>
</form:form>

<c:url var="purchaseAction" value="/purchase/${user.ssoId}"></c:url>
<form:form action="${purchaseAction}" commandName="book"
	onSubmit="return validateCheckout();">
	<h3><spring:message code="checkout.carddetails" /></h3>
	<span class="payment-errors"></span>

	<div class="form-row">
		<label> <span><spring:message code="checkout.cardnum" /></span> <input type="text" size="20"
			id="cardNum"><span style="display: none;" class="has-error"
			id="cardNum_validation"></span>
		</label>
	</div>

	<div class="form-row">
		<label> <span><spring:message code="checkout.expiration" /></span> <input type="text"
			size="2" id="month">
		</label> <span> / </span> <input type="text" size="2" id="year">
		<span style="display: none;" class="has-error"
			id="expDate_validation"></span>
	</div>

	<div class="form-row">
		<label> <span>CVC</span> <input type="text" size="4" id="cvc"><span style="display: none;" class="has-error"
			id="cvc_validation"></span>
		</label>
	</div>

	<input type="submit" class="submit" value="<spring:message code="checkout.submit" />">
</form:form>

	<%@include file="footer.jsp"%>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
