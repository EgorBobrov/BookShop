<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
 
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <title>Registration Confirmation Page</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
    <div class="generic-container">
        <%@include file="authheader.jsp" %>
         
        <div class="alert alert-success lead">
            <spring:message code="reg.user" /> ${firstname} ${lastname} <spring:message code="reg.successful" />
        </div>
         
        <span class="well floatRight">
            <spring:message code="reg.goto" /> <a href="<c:url value='/books' />"> <spring:message code="reg.main" /></a>
        </span>
    </div>
</body>
 	<%@include file="footer.jsp"%>
 
</html>