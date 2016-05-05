/*
 * This is the Controller. It handles specific requests to some URLs by building specific View using 
 * some logic and the Model class. 
 */


package book;

import java.util.HashSet;
import java.util.List;
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
        String title = request.getParameter("title");
        // taking data from the "author(s)" field
        String authorsInput = request.getParameter("authors");
        // if there are more than 1 author we create an array of them
        
        String searchInput = request.getParameter("search");
        
        if (authorsInput != null) {
	        String[] authorsArray = authorsInput.split(",");
	        // and adding author(s) to the set
	        for (String author : authorsArray) {
	        	authors.add(author);
	        }
        }
        
        if (isbn!= null && title != null){
        	Book entity = new Book(isbn, title, authors);
        	logger.info("received "+entity.toString()+" via user input");
        	bookDao.persist(entity);
        }
        
        if (searchInput != null){
        	bookDao.setKeyword(searchInput);
        	bookDao.doSearch();
        }
        // Prepare the result view (book.jsp):
        return new ModelAndView("books.jsp", "bookDao", bookDao);
    }
    
    @RequestMapping(value="/dropbooks")
    public ModelAndView dropped (HttpServletRequest request) {
        // Handle a new guest (if any):
    	BasicConfigurator.configure();
    	String isbn = request.getParameter("ISBN");
        if (isbn!= null){
        	bookDao.delete(isbn);
        }
        // Prepare the result view (book.jsp):
        return new ModelAndView("books.jsp", "bookDao", bookDao);
    }

    @RequestMapping("/book")
    // @RequestParam binds HTTP request parameters to method arguments in the controller
    // takes the 'id' request parameter from the URL, and maps it to the countryId parameter of the method
    public ModelAndView goToBook(@RequestParam(value="isbn", required=true) String isbn) {
    	return new ModelAndView("book.jsp", "bookDao", bookDao);
    }

}