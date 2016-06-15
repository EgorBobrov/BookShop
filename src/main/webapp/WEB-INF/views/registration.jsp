<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html>

<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<title>User Registration Form</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
	<div class="generic-container">
		<sec:authorize
			access="hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')">

			<%@include file="authheader.jsp"%>
		</sec:authorize>
		<div class="well lead"><spring:message code="reg.title" /></div>
		<form:form method="POST" modelAttribute="user" class="form-horizontal">
			<form:input type="hidden" path="id" id="id" />

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="firstName"><spring:message code="reg.firstname" /></label>
					<div class="col-md-7">
						<form:input type="text" path="firstName" id="firstName"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="firstName" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="lastName"><spring:message code="reg.lastname" /></label>
					<div class="col-md-7">
						<form:input type="text" path="lastName" id="lastName"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="lastName" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="ssoId"><spring:message code="reg.username" /></label>
					<div class="col-md-7">
						<c:choose>
							<c:when test="${edit}">
								<form:input type="text" path="ssoId" id="ssoId"
									class="form-control input-sm" disabled="true" />
							</c:when>
							<c:otherwise>
								<form:input type="text" path="ssoId" id="ssoId"
									class="form-control input-sm" />
								<div class="has-error">
									<form:errors path="ssoId" class="help-inline" />
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="password"><spring:message code="reg.pass" /></label>
					<div class="col-md-7">
						<form:input type="password" path="password" id="password"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="password" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="email">Email</label>
					<div class="col-md-7">
						<form:input type="text" path="email" id="email"
							class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="email" class="help-inline" />
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="userProfiles"><spring:message code="reg.role" /></label>
					<div class="col-md-7">
						<form:select path="userProfiles" multiple="false"
							class="form-control input-sm">
							<!-- for signing up as anonymous - you can only create users with USER privileges -->
							<c:if test="${loggedinuser eq 'anonymousUser'}">
								<form:option value="1" label="USER" />
							</c:if>
							<!-- for creating new account as an existing user - you can only create users with USER and ADMIN privileges -->
							<sec:authorize access="hasRole('USER') or hasRole('ADMIN')">
								<form:option value="1" label="USER" />
							</sec:authorize>
							<!-- for creating new users as DBA - you can create users with any privileges -->
							<sec:authorize access="hasRole('DBA')">
								<form:options items="${roles}" itemValue="id" itemLabel="type" />
							</sec:authorize>

						</form:select>
						<%--                         <form:select path="userProfiles" items="${roles}" multiple="false" itemValue="id" itemLabel="type" class="form-control input-sm" />
 --%>
						<div class="has-error">
							<form:errors path="userProfiles" class="help-inline" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-actions floatRight">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="<spring:message code="reg.update" />"
								class="btn btn-primary btn-sm" /> <spring:message code="reg.or" /> <a
								href="<c:url value='/list' />"><spring:message code="reg.cancel" /></a>
						</c:when>
						<c:otherwise>
							<input type="submit" value="<spring:message code="reg.reg" />"
								class="btn btn-primary btn-sm" /> <spring:message code="reg.or" /> <a
								href="<c:url value='/list' />"><spring:message code="reg.cancel" /></a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</form:form>
	</div>
	<a href="${pageContext.request.contextPath}/"><spring:message
			code="bookshop.tomain" /></a>
</body>
</html>