/*
 * This is the Controller. It handles specific requests to some URLs by building specific View using 
 * some logic and the Model class. 
 */


package book;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class BookController {
 
    @Autowired
    private BookDao bookDao;
 
    @RequestMapping(value="/book")
    public ModelAndView booklist(HttpServletRequest request) {
        // Handle a new guest (if any):
        String name = request.getParameter("title");
        String author = request.getParameter("author");
        if (name != null)
        	bookDao.persist(new Book(name, author));
 
        // Prepare the result view (book.jsp):
        return new ModelAndView("book.jsp", "bookDao", bookDao);
    }

}