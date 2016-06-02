<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="authbar">
	<span>You are logged in as <strong>${loggedinuser}</strong></span> <span
		class="floatRight">
		<a
		href="<c:url value='/order/${loggedinuser}' />">${loggedinuser}
			basket (${user.getBasket().size()})</a> <br>
		<c:if test="${loggedinuser != 'anonymousUser'}">
<a
		href="<c:url value='/user/${loggedinuser}' />">${loggedinuser}
			personal page</a> <br> </c:if>
			<a href="<c:url value="/logout" />">Logout</a></span>
</div>