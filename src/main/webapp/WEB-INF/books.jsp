<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="book.*"%>
 
<jsp:useBean id="bookDao" type="book.BookDao" scope="request" />
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
        <title>JPA Book Shop</title>
    </head>
 
    <body>
        <form method="POST" action="books.html">
        	ISBN: <input type="text" name="ISBN" /> 
            <br>
            Title: <input type="text" name="title" /> 
            <br>
            Author(s): <input type="text" name="authors" />
            <br>
            <input type="submit" value="Add the book" />
        </form>
        
         <form method="POST" action="dropbooks.html">
        	ISBN: <input type="text" name="ISBN" /> 
            <input type="submit" value="Delete the book" />
        </form>
        
        <form method="POST" action="books.html">
        	Part of title or description: <input type="text" name="search" /> 
            <input type="submit" value="Search" />
        </form>
 
        <hr><ol> 
        <% for (Book book : bookDao.getAllBooks()) { %>
           <a href="book.html?isbn=<%= book.getIsbn() %>" ><li> <%= book %> added;</li></a> 
        <% } %>
        </ol><hr>
        
        <hr><ol> 
        Search results:
        <% for (Book book : bookDao.doSearch()) { %>
           <a href="book.html?isbn=<%= book.getIsbn() %>" ><li> <%= book %> found;</li></a> 
        <% } %>
        </ol><hr>
 		
 		<a href="/BookList/">Go to the home page</a>
 
        <iframe src="http://www.objectdb.com/pw.html?spring-eclipse"
            frameborder="0" scrolling="no" width="100%" height="30"> </iframe>
     </body>
 </html>