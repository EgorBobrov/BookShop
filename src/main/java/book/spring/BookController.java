/*
 * This is the Controller. It handles specific requests to some URLs by building specific View using 
 * some logic and the Model class. 
 */


package book.spring;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import book.spring.dao.BookDao;
import book.spring.model.Book;
import book.spring.service.BookService;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

@Controller
public class BookController {
 
    @Autowired(required=true)
    // in a situation when you create more than one bean of the same type and want to wire only one of them with a property
    // you can use @Qualifier annotation along with @Autowired to remove the confusion 
    // by specifying which exact bean will be wired
    @Qualifier(value="bookService")
    private BookService bookService;
    
    //@Autowired(required=true)
    //@Qualifier(value="bookService")
    public void setBookService(BookService bs){
        this.bookService = bs;
    }
   
    @RequestMapping(value="/book", method = RequestMethod.GET)
    public String listBooks(Model model) {
    	model.addAttribute("book", new Book());
    	model.addAttribute("listBooks", this.bookService.getAllBooks());
    	return "book";
    }
    //For book addition and update
    @RequestMapping(value= "/book/add", method = RequestMethod.POST)
    public String addBook(@ModelAttribute("book") Book book){
         
        if(book.getId() == null){
            //new book, add it
            this.bookService.persistBook(book);
        }
        else {
            //existing book, call update
            this.bookService.updateBook(book);
        }
         
        return "redirect:/book";
    }
    
    @RequestMapping("/remove/{id}")
    public String removeBook(@PathVariable("id") Long id){
         
        this.bookService.delete(id);
        return "redirect:/book";
    }
    @RequestMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long id, Model model){
        model.addAttribute("book", this.bookService.getBookById(id));
        model.addAttribute("listBooks", this.bookService.getAllBooks());
        return "book";
    }
    
}