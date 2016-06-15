<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="authbar">
	<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
	<%@ taglib prefix="sec"
		uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


	<span class="floatRight"> <sec:authorize
			access="hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')">

			<p>
				<spring:message code="auth.greeting" />, <strong>${loggedinuser}</strong>!
			</p>
			<sec:authorize access="hasRole('USER')">

				<a href="<c:url value='/order/${loggedinuser}' />"><spring:message code="auth.basket" />
					(${user.getBasket().size()})</a>
				<br>
				<a href="<c:url value='/user/${loggedinuser}' />"><spring:message code="auth.page" /></a>
				<br>
			</sec:authorize>

			<a href="<c:url value="/logout" />"><spring:message code="auth.logout" /></a>
		</sec:authorize>
		<p style="font-size: 120%;">
			<a href="${pageContext.request.contextPath}/login"><c:if
					test="${loggedinuser eq 'anonymousUser'}"><spring:message code="auth.login"/></c:if> <c:if
					test="${loggedinuser != 'anonymousUser'}"><spring:message code="auth.userlist"/></c:if></a>
		</p>
		<p style="font-size: 120%;">
			<a href="${pageContext.request.contextPath}/newuser"><spring:message code="auth.newuser"/></a>
		</p>

		<p>
			<spring:message code="books.chooselang" />
			: <br> <a
				href="${pageContext.request.contextPath}/books?language=en">English</a>
			| <a href="${pageContext.request.contextPath}/books?language=ru_RU"><spring:message
					code="books.lang" /></a>
		</p>

	</span>
</div>