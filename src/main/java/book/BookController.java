/*
 * This is the Controller. It handles specific requests to some URLs by building specific View using 
 * some logic and the Model class. 
 */


package book;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

@Controller
public class BookController {
 
    @Autowired
    private BookDao bookDao;
    
    //a log4j logger
    static final Logger logger = Logger.getLogger(BookController.class);
 
    @RequestMapping(value="/books")
    public ModelAndView booklist(HttpServletRequest request) {
        // Handle a new guest (if any):
    	BasicConfigurator.configure();
    	Set<String> authors = new HashSet<>();
    	String isbn = request.getParameter("ISBN");
        String name = request.getParameter("title");
        authors.add(request.getParameter("author1"));
        authors.add(request.getParameter("author2"));

        if (isbn!= null && name != null){
        	Book entity = new Book(isbn, name, authors);
        	logger.info("received "+entity.toString()+" via user input");
        	bookDao.persist(entity);
        }
        // Prepare the result view (book.jsp):
        return new ModelAndView("books.jsp", "bookDao", bookDao);
    }
    @RequestMapping("/book")
    // @RequestParam binds HTTP request parameters to method arguments in the controller
    // takes the 'id' request parameter from the URL, and maps it to the countryId parameter of the method
    public Book getBook(@RequestParam(value="isbn", required=true) String isbn) {
    	return bookDao.getBookByIsbn(isbn);
    }

}