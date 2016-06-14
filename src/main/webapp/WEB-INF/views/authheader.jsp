<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="authbar">
	<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
	<%@ taglib prefix="sec"
		uri="http://www.springframework.org/security/tags"%>


	<span class="floatRight">
		<sec:authorize
			access="hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')">

			<p>
				Hi, <strong>${loggedinuser}</strong>!
			</p>
			<a href="<c:url value='/order/${loggedinuser}' />">Your basket
				(${user.getBasket().size()})</a>
			<br>
			<c:if test="${loggedinuser != 'anonymousUser'}">
				<a href="<c:url value='/user/${loggedinuser}' />">Your personal
					page</a>
				<br>
			</c:if>
			<a href="<c:url value="/logout" />">Logout</a>
		</sec:authorize>
	<p style="font-size: 120%;">
		<a href="${pageContext.request.contextPath}/login"><c:if test="${loggedinuser eq 'anonymousUser'}">Log in</c:if>
		<c:if test="${loggedinuser != 'anonymousUser'}">See user list</c:if></a>
	</p>
			<p style="font-size: 120%;">
		<a href="${pageContext.request.contextPath}/newuser">Sign up as a
			new user</a>
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