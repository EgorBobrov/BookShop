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
    public ModelAndView guestbook(HttpServletRequest request) {
        // Handle a new guest (if any):
        String name = request.getParameter("name");
        if (name != null)
        	bookDao.persist(new Book(name));
 
        // Prepare the result view (guest.jsp):
        return new ModelAndView("book.jsp", "bookDao", bookDao);
    }
}