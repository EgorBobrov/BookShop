<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="user.*"%>
 
<jsp:useBean id="userDao" type="user.UserDao" scope="request" />
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
        <title></title>
    </head>
 
    <body>
    <%String email = request.getParameter("email"); %>
    <%User user = userDao.getUserByEmail(email); %>
    The <%= user.getName() %> page. 
    
    <br>
    
      
    <!--  The ${param.isbn} page --> 
 		 
 		<a href="/BookList/">Go to the home page</a>
 
        <iframe src="http://www.objectdb.com/pw.html?spring-eclipse"
            frameborder="0" scrolling="no" width="100%" height="30"> </iframe>
     </body>
 </html>