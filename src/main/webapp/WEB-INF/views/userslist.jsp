<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
 <%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 
<html>
 
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Users List</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
 
<body>
    <div class="generic-container">
    <sec:authorize access="hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')">
        <%@include file="authheader.jsp" %>   
        </sec:authorize>
        <div class="panel panel-default">
              <!-- Default panel contents -->
            <div class="panel-heading"><span class="lead"><spring:message code="list.title" /> </span></div>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th><spring:message code="user.fname" /></th>
                        <th><spring:message code="user.lname" /></th>
                        <th>Email</th>
                        <th><spring:message code="user.ssoid" /></th>
                        <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
                            <th width="100"></th>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ADMIN')">
                            <th width="100"></th>
                        </sec:authorize>
                         
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.email}</td>
                        <td><a href="<c:url value='/user/${user.ssoId}' />" >${user.ssoId}</a></td>
                        <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
                            <td><a href="<c:url value='/edit-user-${user.ssoId}' />" class="btn btn-success custom-width"><spring:message code="user.edit" /></a></td>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ADMIN')">
                            <td><a href="<c:url value='/delete-user-${user.ssoId}' />" class="btn btn-danger custom-width"><spring:message code="user.delete" /></a></td>
                        </sec:authorize>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <sec:authorize access="hasRole('ADMIN')">
            <div class="well">
                <a href="<c:url value='/newuser' />"><spring:message code="user.new" /></a>
            </div>
        </sec:authorize>
    </div>
    <p style="font-size:100%;">	<a href="${pageContext.request.contextPath}/"><spring:message code="bookshop.tomain"/></a>
</p>
</body>
</html>