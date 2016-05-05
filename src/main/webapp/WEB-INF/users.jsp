<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="user.*"%>
 
<jsp:useBean id="userDao" type="user.UserDao" scope="request" />
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
        <title>Users Addition</title>
    </head>
 
    <body>
        <form method="POST" action="users.html">
            Name: <input type="text" name="name" /> 
            <br>
            Email: <input type="text" name="email" />
            <br>
            Password: <input type="password" name="password" />
            <br>
            Register as admin? <input type="checkbox" name="admin"/>
            <br>
            <input type="submit" value="Register" />
        </form>
          <form method="POST" action="dropuser.html">
        	Email: <input type="text" name="email" /> 
            <input type="submit" value="Delete user" />
        </form>
 
        <hr><ol> 
        <% for (User user : userDao.getAllUsers()) { %>
            <a href="user.html?email=<%= user.getEmail() %>"><li> <%= user %> registered; </li></a>
        <% } %>
        </ol><hr>
 		
 		<a href="/BookList/">Go to the home page</a>
 
        <iframe src="http://www.objectdb.com/pw.html?spring-eclipse"
            frameborder="0" scrolling="no" width="100%" height="30"> </iframe>
     </body>
 </html>