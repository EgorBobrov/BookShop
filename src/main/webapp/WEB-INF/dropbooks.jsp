<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="book.*"%>
 
<jsp:useBean id="bookDao" type="book.BookDao" scope="request" />
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 

<html>
<body>


 Book dropped!
 <a href="books.html">Go to the book addition page</a>
 <br>
 <a href="users.html">Go to the user registration page</a>
 </body>
</html>
