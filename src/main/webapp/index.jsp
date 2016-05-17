<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Welcome</title>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
<c:redirect url="/books"/>
</body>
</html>