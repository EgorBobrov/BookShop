<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<link href="<c:url value='/static/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css"></link> 

<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>

<nav class="navbar navbar-inverse">

	<div class="container-fluid">
		<ul class="nav navbar-nav">
			<li><a href="${pageContext.request.contextPath}/"><spring:message
						code="bookshop.tomain" /></a></li>
			<sec:authorize
				access="hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')">

				<li><a href="<c:url value='/user/${loggedinuser}' />"><spring:message code="auth.greeting" />, <strong>${loggedinuser}</strong>!</a></li>

				<sec:authorize access="hasRole('USER')">
					<li><a href="<c:url value='/order/${loggedinuser}' />"> <spring:message
								code="auth.basket" /> <span class="badge">${user.getBasket().size()}
						</span></a></li>

				</sec:authorize>
				<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
					<li><a href="${pageContext.request.contextPath}/list"><spring:message code="auth.userlist" /></a></li>
				</sec:authorize>
			</sec:authorize>
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#"><spring:message
						code="books.chooselang" /> <span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a
						href="${pageContext.request.contextPath}/books?language=en">English</a>
					</li>
					<li><a
						href="${pageContext.request.contextPath}/books?language=ru_RU"><spring:message
								code="books.lang" /></a></li>
				</ul></li>
		</ul>

		<ul class="nav navbar-nav navbar-right">
			<li><a href="${pageContext.request.contextPath}/newuser"><span
					class="glyphicon glyphicon-user"></span> <spring:message
						code="auth.newuser" /></a></li>

			<c:if test="${loggedinuser eq 'anonymousUser'}">
				<li><a href="${pageContext.request.contextPath}/login"><span
						class="glyphicon glyphicon-log-in"></span> <spring:message
							code="auth.login" /></a></li>
			</c:if>
			<c:if test="${loggedinuser != 'anonymousUser'}">
				<li><a href="<c:url value="/logout" />"><span
						class="glyphicon glyphicon-log-in"></span> <spring:message
							code="auth.logout" /></a></li>
			</c:if>

		</ul>

	</div>
</nav>

