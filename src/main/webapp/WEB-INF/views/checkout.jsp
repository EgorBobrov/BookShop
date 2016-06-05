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
<title>Order of ${user.ssoId}: checkout</title>

<c:if test="${!empty existingAddresses}"><br>
<h3>
Is the address you'd like to use displayed below? If so, click the corresponding "Ship to this address" button. </h3>
		<table class="tg">
			<c:forEach items="${existingAddresses}" var="address">
				<tr>
					<td>${address.toString()}</td>
				        <td><input type="submit" value="<spring:message text="Ship to this address"/>" /></td>
						<td><a href="<c:url value='/remove/address/${address.id}' />">Delete address</a></td>
				</tr>
</c:forEach>
</table></c:if>

<c:url var="addAction" value="/addresses/add"></c:url>

	<form:form action="${addAction}" commandName="address">
	<h3>Enter a new shipping address. </h3>
		<table>
			<tr>
				<td><form:label path="name">
						<spring:message text="Name" />
					</form:label></td>
					<td><form:input path="name" /></td>
			</tr>
			<tr>
				<td><form:label path="street">
						<spring:message text="Street/House/Apartment" />
					</form:label></td>
					<td><form:input path="street" /></td>
			</tr><tr>
						<tr>
				<td><form:label path="city">
						<spring:message text="City" />
					</form:label></td>
					<td><form:input path="city" /></td>
			</tr><tr>
						<tr>
				<td><form:label path="region">
						<spring:message text="Region" />
					</form:label></td>
					<td><form:input path="region" /></td>
			</tr><tr>
						<tr>
				<td><form:label path="country">
						<spring:message text="Country" />
					</form:label></td>
					<td><form:input path="country" /></td>
			</tr><tr>
					<td><c:if test="${empty address.street}">
						<input type="submit" value="<spring:message text="Add Adress"/>" />
					</c:if></td>
			</tr>
		</table>
	</form:form>
		
<c:url var="purchaseAction" value="/purchase/${user.ssoId}"></c:url>
<form:form action="${purchaseAction}" commandName="book">
  <h3> Enter your card details  </h3>
  <span class="payment-errors"></span>
   
  <div class="form-row">
    <label>
      <span>Card Number</span>
      <input type="text" size="20">
    </label>
  </div>

  <div class="form-row">
    <label>
      <span>Expiration (MM/YY)</span>
      <input type="text" size="2">
    </label>
    <span> / </span>
    <input type="text" size="2">
  </div>

  <div class="form-row">
    <label>
      <span>CVC</span>
      <input type="text" size="4">
    </label>
  </div>

  <input type="submit" class="submit" value="Submit Payment">
  <a href="${pageContext.request.contextPath}">Go back</a>
</form:form>