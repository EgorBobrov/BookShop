/*
 * This is the Controller. It handles specific requests to some URLs by building specific View using 
 * some logic and the Model class. 
 */


package book.spring;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import book.spring.model.Author;
import book.spring.model.Book;
import book.spring.service.BookService;
import book.spring.service.conversion.AuthorConverter;


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
    
   // @Autowired
    //@Qualifier(value="authorConverter")
    //private AuthorConverter authorConverter; 
   
    @RequestMapping(value="/books", method = RequestMethod.GET)
    public String listBooks(Model model) {
    	model.addAttribute("book", new Book());
    	model.addAttribute("listBooks", this.bookService.getAllBooks());
    	model.addAttribute("foundBooks", this.bookService.getFoundBooks());
    	return "books";
    }
    
    @RequestMapping(value="/book/{id}")
    public String displayBook(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("book", this.bookService.getBookById(id));
    	return "book";
    }
/*
    @InitBinder
    public void initBinder(WebDataBinder binder) {
       // binder.registerCustomEditor(Author.class, this.authorConverter);
        binder.registerCustomEditor(Set.class, "authors", new AuthorConverter(this.bookService));
    }*/

	//For book addition and update
    @RequestMapping(value= "/books/add", method = RequestMethod.POST)
    public String addBook(@ModelAttribute("book") Book book, @ModelAttribute("authors") Set <Author> auth){
    	book.setAuthors(AuthorConverter.toAuthor(auth.toString()));
        if(book.getId() == null){
            //new book, add it
        	
            this.bookService.persistBook(book);
        }
        else {
            //existing book, call update
            this.bookService.updateBook(book);
        }
         
        return "redirect:/books";
    }
    
    @RequestMapping("/remove/{id}")
    public String removeBook(@PathVariable("id") Long id){
         
        this.bookService.delete(id);
        return "redirect:/books";
    }
    @RequestMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long id, Model model){
        model.addAttribute("book", this.bookService.getBookById(id));
        model.addAttribute("listBooks", this.bookService.getAllBooks());
        return "books";
    }
    
    @RequestMapping(value="/books/search")
	public String searchResults(@RequestParam(value = "keyword", required = true) String keyword, Model model) {
    	this.bookService.findBook(keyword);
	    return "redirect:/books";
	}
    
    
}