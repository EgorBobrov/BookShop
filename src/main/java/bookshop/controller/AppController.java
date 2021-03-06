package bookshop.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.http.HttpStatus;

import bookshop.converter.RoleToUserProfileConverter;
import com.epamjuniors.bookshop.bookshop_model.book.Author;
import com.epamjuniors.bookshop.bookshop_model.book.Book;
import com.epamjuniors.bookshop.bookshop_model.book.Comment;
import com.epamjuniors.bookshop.bookshop_model.book.Genre;
import com.epamjuniors.bookshop.bookshop_model.user.User;
import com.epamjuniors.bookshop.bookshop_model.user.UserAddress;
import com.epamjuniors.bookshop.bookshop_model.user.UserProfile;
import bookshop.service.book.BookService;
import bookshop.service.book.CommentService;
import bookshop.service.user.UserProfileService;
import bookshop.service.user.UserService;
 
 
 
@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {
 
	//had to make the logger non final& static because of http://stackoverflow.com/questions/30703149/mock-private-static-final-field-using-mockito-or-jmockit
	Logger logger = LoggerFactory.getLogger(RoleToUserProfileConverter.class);
	
    @Autowired
    UserService userService;
     
    @Autowired
    UserProfileService userProfileService;
     
    @Autowired
    MessageSource messageSource;
 
    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
     
    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private CommentService commentService;
    
    public AppController(){}
    
    public void setBookService(BookService bs){
        this.bookService = bs;
    }
    
    public void setLogger(Logger logger){
    	this.logger = logger;
    }
   /*
    * Method returns a basic front page view 
    */
    @RequestMapping(value="/books", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String listBooks(Model model, Locale locale) {
    	model.addAttribute("book", new Book());
    	model.addAttribute("foundBooks", this.bookService.getFoundBooks());
    	model.addAttribute("genre", Genre.values());
    	model.addAttribute("loggedinuser", getPrincipal());
        User user = userService.findBySSO(getPrincipal());
        model.addAttribute("user", user);
        model.addAttribute("locale", locale);
        logger.info("The client locale is {}.", locale);
    	return "books";
    }
    
    /*
     * Method returns a basic front page view with book sorted in selected order
     */
    @RequestMapping(value="/books/{order}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String listBooksByOrder(@PathVariable("order") String order, Model model) {
    	model.addAttribute("book", new Book());
    	User user = userService.findBySSO(getPrincipal());
    	if (order.equals("byorder")) {
    		model.addAttribute("foundBooks", this.bookService.getFoundBooks());
    	}
    	else if (order.equals("byrating")){
    		model.addAttribute("foundBooks", this.bookService.getFoundBooksByRating());
		}
    	else if (order.equals("lastcommented")) {
			model.addAttribute("foundBooks", this.bookService.getLastCommentedBooks());
		}
    	//for recommended books we're checking the books that users who bought the same books as current user,
    	//and displaying the books they bought as well.
    	else if (order.equals("recommended")) {
    		Long start = System.currentTimeMillis();
    		Set<Book> recommendedBooks = new HashSet<>();
    		List<Book> userBooks = user.getInventory();
    		
    		// TODO: saved for future testing on bigger library and user number
/*    		userBooks.stream().forEach((book) -> {
    			List<Book> books = this.bookService.getSimilarBooks(book, user);
    			for (Book bb : books) {
    				recommendedBooks.add(bb);
    			}
    		});
*/  
    		for (Book b : userBooks) {
    			List<Book> books = this.bookService.getSimilarBooks(b, user);
    			for (Book bb : books) {
    				if (!userBooks.contains(bb) && !recommendedBooks.contains(bb)){
    					recommendedBooks.add(bb);
    				}
    			}
    		}
    		model.addAttribute("foundBooks", recommendedBooks);
    		Long finish = System.currentTimeMillis();
    		logger.info("Time passed: " + (finish - start));
    	}
    	model.addAttribute("genre", Genre.values());
    	model.addAttribute("loggedinuser", getPrincipal());
        
        model.addAttribute("user", user);
    	return "books";
    }
    
    /*
     * This method shows a specific book page
     */
    @RequestMapping(value="/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String displayBook(@PathVariable("id") Integer id, Model model, Locale locale) {
    	model.addAttribute("comment", new Comment());
    	model.addAttribute("book", this.bookService.getBookById(id));
    	model.addAttribute("loggedinuser", getPrincipal());
    	model.addAttribute("comments", commentService.getAll(id));
    	User user = userService.findBySSO(getPrincipal());
    	model.addAttribute("user", user);
    	//lower panel - getting "similar" books (someone bought them as a bundle)
    	Book b = this.bookService.getBookById(id);
    	List<Book> bBooks = this.bookService.getSimilarBooks(b, user);
    	model.addAttribute("similarBooks", bBooks);
    	model.addAttribute("locale", locale);
    	return "book";
    }
    
    /*
     * This method shows a specific author page
     */
    @RequestMapping(value="/author/{author.name}")
    public String displayAuthor(@PathVariable("author.name") String name, Model model) {
    	model.addAttribute("author", this.bookService.getAuthor(name));
    	model.addAttribute("loggedinuser", getPrincipal());
    	User user = userService.findBySSO(getPrincipal());
    	model.addAttribute("user", user);
    	return "author";
    }
   
    //For book addition and update
    @RequestMapping(value= "/books/add", method = RequestMethod.POST)
    public String addBook(@ModelAttribute("book") Book book, @ModelAttribute("authors") Set <Author> auth, 
    		@RequestParam(value = "genres", required = false) Set <Genre> gen){
    	book.setAuthors(Author.toAuthor(auth.toString()));
    	book.setGenres(gen);
    	
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
    
    /*
     * Below methods allow to post, remove, like and dislike comments 
     */
    @RequestMapping(value="/book/{id}/postcomment")
    public String postComment(@PathVariable("id") Integer id, Model model, @ModelAttribute("comment") Comment comment) {
    	this.commentService.persistComment(id, comment);
    	model.addAttribute("book", this.bookService.getBookById(id));
    	model.addAttribute("loggedinuser", getPrincipal());
    	model.addAttribute("comments", commentService.getAll(id));
    	model.addAttribute("comment", new Comment());
    	return "book";
    } 

    @RequestMapping(value="/removecomment/{book.id}/{comment.id}")
    public String removeComment(Model model, @PathVariable("book.id") Integer bookId,  @PathVariable("comment.id") Integer commentId) {
        	this.commentService.deleteCommentById(commentId,  bookId);
        	model.addAttribute("comment",  new Comment());
        	model.addAttribute("book", this.bookService.getBookById(bookId));
        	model.addAttribute("loggedinuser", getPrincipal());
            model.addAttribute("comments", commentService.getAll(bookId));
            return "book";
    } 
    
    //"liking" someone's comment
    @RequestMapping("/like/{book.id}/{comment.id}")
    public String likingComment(Model model, @PathVariable("book.id") Integer bookId,  @PathVariable("comment.id") Integer commentId) {
    	User user = userService.findBySSO(this.getPrincipal());
    	this.commentService.likeComment(commentId, user);
    	model.addAttribute("comment",  new Comment());
    	model.addAttribute("book", this.bookService.getBookById(bookId));
    	model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("comments", commentService.getAll(bookId));
    	return "book";
    }
    
    //"disliking"/flagging someone's comment
    @RequestMapping("/dislike/{book.id}/{comment.id}")
    public String dislikingComment(Model model, @PathVariable("book.id") Integer bookId,  @PathVariable("comment.id") Integer commentId) {
    	User user = userService.findBySSO(this.getPrincipal());
    	this.commentService.dislikeComment(commentId, user);
    	model.addAttribute("comment",  new Comment());
    	model.addAttribute("book", this.bookService.getBookById(bookId));
    	model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("comments", commentService.getAll(bookId));
    	return "book";
    }

    /*
     * This method allows to rate a book 
     */
    @RequestMapping(value="/book/{id}/rate")
    public String rate(@PathVariable("id") Integer id, Model model, @ModelAttribute("rating") Integer rating) {
    	model.addAttribute("rating", rating);
    	bookService.rateBook(id, rating);
    	model.addAttribute("comment", new Comment());
    	model.addAttribute("book", this.bookService.getBookById(id));
    	model.addAttribute("loggedinuser", getPrincipal());
    	model.addAttribute("comments", commentService.getAll(id));
    	return "book";
    }
    
    /*
     * This method allows to remove a book 
     */
    @RequestMapping("/remove/{id}")
    public String removeBook(@PathVariable("id") Integer id){
        
        this.bookService.delete(id);
        return "redirect:/books";
    }
    
    /*
     * This method allows to edit a book
     */
    @RequestMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Integer id, Model model){
        model.addAttribute("book", this.bookService.getBookById(id));
        model.addAttribute("genre", Genre.values());
        model.addAttribute("listBooks", this.bookService.getAllBooks());
        model.addAttribute("loggedinuser", getPrincipal());
        User user = userService.findBySSO(getPrincipal());
        model.addAttribute("user", user);
        return "books";
    }
    
    /*
     * This method allows to edit an author
     */
    @RequestMapping(value = "/edit-author/{author.name}")
    public String editAuthor(@ModelAttribute("author") Author author, Model model){
    	this.bookService.updateAuthor(author);
    	model.addAttribute("author", this.bookService.getAuthor(author.getname()));
    	model.addAttribute("loggedinuser", getPrincipal());
    	return "author";
    }
    
    /*
     * This method displays the search results as a main page view
     */
    @RequestMapping(value="/books/search")
	public String searchResults(@RequestParam(value = "keyword", required = true) String keyword, Model model) {
    	this.bookService.findBook(keyword);
    	model.addAttribute("loggedinuser", getPrincipal());
	    return "redirect:/books";
	}
    
    /*
     * This method displays the search results (only books with specific genre) as a main page view
     */
    @RequestMapping(value="/books/genre/{genre}")
	public String genreFilter(@ModelAttribute("genre") Genre genre, Model model) {
    	model.addAttribute("loggedinuser", getPrincipal());
    	model.addAttribute("book", new Book());
    	model.addAttribute("foundBooks", this.bookService.findBook(genre));
    	this.bookService.findBook(genre).stream().forEach(System.out::println);
    	model.addAttribute("genre", Genre.values());
    	model.addAttribute("loggedinuser", getPrincipal());
        User user = userService.findBySSO(getPrincipal());
        model.addAttribute("user", user);
	    return "books";
	}
    /*
     * This method clears the search results and returns main page view
     */
    @RequestMapping(value="/books/search/clear", method = RequestMethod.GET)
    public String clearSearchResults(){
    	this.bookService.clearSearchResults();
    	return "redirect:/books";
    }
    
    /*
     * This method will list all existing users.
     */
    @RequestMapping(value = { "/list" }, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {
 
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("loggedinuser", getPrincipal());
        return "userslist";
    }
    /*
     * Method for creating a user page
     */
    @RequestMapping(value = { "/user/{ssoId}" }, method = RequestMethod.GET)
    public String showUser(@PathVariable String ssoId, ModelMap model) {
        User user = userService.findBySSO(ssoId);
        model.addAttribute("user", user);
        model.addAttribute("loggedinuser", getPrincipal());
        return "user";
    }
    
    @RequestMapping(value = { "/order/{ssoId}" }, method = RequestMethod.GET)
    public String showOrder(@PathVariable String ssoId, ModelMap model) {
        User user = userService.findBySSO(ssoId);
        model.addAttribute("user", user);
        model.addAttribute("loggedinuser", getPrincipal());
        return "order";
    }


    /*
     * This method is used to add a new user.
     */
    @RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        model.addAttribute("loggedinuser", getPrincipal());
        return "registration";
    }
 
    /*
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the user input
     */
    @RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result,
            ModelMap model) {
 
        if (result.hasErrors()) {
            return "registration";
        }
        
        if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
            FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }
        userService.saveUser(user);
        model.addAttribute("firstname", user.getFirstName());
        model.addAttribute("lastname", user.getLastName());
        model.addAttribute("loggedinuser", getPrincipal());
        return "registrationsuccess";
    }
 
 
    /*
     * This method is used to update an existing user.
     */
    @RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.GET)
    public String editUser(@PathVariable String ssoId, ModelMap model) {
        User user = userService.findBySSO(ssoId);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getPrincipal());
        return "registration";
    }
     
    /*
     * This method will be called on form submission, handling POST request for
     * updating user in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
            ModelMap model, @PathVariable String ssoId) {
 
        if (result.hasErrors()) {
            return "registration";
        }
 
        userService.updateUser(user);
 
        model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        return "registrationsuccess";
    }
 
     
    /*
     * This method deletes user by it's SSOID value.
     */
    @RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String ssoId) {
        userService.deleteUserBySSO(ssoId);
        return "redirect:/list";
    }
     
 
    /*
     * This method will provide list of UserProfiles to views
     */
    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAll();
    }
     
    /*
     * This method handles Access-Denied redirect.
     */
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        return "accessDenied";
    }
 
    /*
     * This method handles login GET requests.
     * If user is already logged-in and tries to goto login page again, will be redirected to books page.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/books";  
        }
    }
 
    /*
     * This method handles logout requests
     */
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
        	logger.info("Entering logout");
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            logger.info("Logged out with persistentTokenBasedRememberMeServices");
            SecurityContextHolder.getContext().setAuthentication(null);
            logger.info("Logged out with setAuthentication(null);");
        }
        return "redirect:/";
    }
 
    /*
     * This method returns the principal (username) of logged-in user.
     */
    public String getPrincipal(){
    	//return user ssoId;
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
     
    /*
     * This method returns true if user is already authenticated, else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }
    
    /*
     * Methods related to checkout process
     */
    @RequestMapping(value = { "/checkout/{ssoId}" }, method = RequestMethod.GET)
    public String showCheckout(@PathVariable String ssoId, ModelMap model, Locale locale) {
        User user = userService.findBySSO(ssoId);
        model.addAttribute("user", user);
        model.addAttribute("loggedinuser", getPrincipal());
        List<UserAddress> addresses = this.userService.getExistingAddresses(user);
        model.addAttribute("locale", locale);
        model.addAttribute("existingAddresses", addresses);
        return "checkout";
    }
    
    @ModelAttribute("address")
    public String addressHandling(Model model, @ModelAttribute("address") UserAddress address) {
    	model.addAttribute("address", address);
    	return "address";
    }

    @RequestMapping("/remove/address/{id}")
    public String removeAddress(Model model, @PathVariable("id") Integer addrId){
    	User user = userService.findBySSO(getPrincipal());
    	this.userService.deleteAddress(user, addrId);
    	model.addAttribute("address", new UserAddress());
        model.addAttribute("user", user);
        List<UserAddress> addresses = this.userService.getExistingAddresses(user);
        model.addAttribute("existingAddresses", addresses);
        return "checkout";
    }
    
    @RequestMapping(value= "/addresses/add", method = RequestMethod.POST)
    public String addAdress(Model model, @ModelAttribute("address") UserAddress address){
    	User user = userService.findBySSO(getPrincipal());
    	this.userService.addAddress(user, address);
    	model.addAttribute("address", new UserAddress());
        model.addAttribute("user", user);
        List<UserAddress> addresses = this.userService.getExistingAddresses(user);
        model.addAttribute("existingAddresses", addresses);
        return "checkout";
    }
    
    @RequestMapping("/tobasket/{book.id}")
    public String addToBasket(Model model, @PathVariable("book.id") Integer bookId) {
    	this.userService.addBookToBasket(bookId, this.getPrincipal());
    	model.addAttribute("comment",  new Comment());
    	model.addAttribute("book", this.bookService.getBookById(bookId));
    	model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("comments", commentService.getAll(bookId));
        User user = userService.findBySSO(this.getPrincipal());
        model.addAttribute("user", user);
    	return "book";
    }
    
    @RequestMapping("/removeFromBasket/{book.id}")
    public String removeFromBasket(Model model, @PathVariable("book.id") Integer bookId) {
    	this.userService.removeBookFromBasket(bookId, this.getPrincipal());
        User user = userService.findBySSO(this.getPrincipal());
        model.addAttribute("user", user);
        model.addAttribute("loggedinuser", getPrincipal());
    	return "order";
    }
    
    
    @RequestMapping("/purchase/{user.ssoId}")
    public String purchase(Model model, @PathVariable("user.ssoId") String ssoId) {
        User user = userService.findBySSO(ssoId);
        this.userService.commitPurchase(ssoId);
        model.addAttribute("user", user);
        model.addAttribute("loggedinuser", getPrincipal());
        return "redirect:/user/" + ssoId;
    }

}
