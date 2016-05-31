<div class="authbar">
	<span>You are logged in as <strong>${loggedinuser}</strong></span> <span
		class="floatRight"><a
		href="<c:url value='/order/${loggedinuser}' />">${loggedinuser}
			basket </a> <br> <a
		href="<c:url value='/user/${loggedinuser}' />">${loggedinuser}
			personal page</a> <br> <a href="<c:url value="/logout" />">Logout</a></span>
</div>