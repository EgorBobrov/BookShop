package bookshop.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import bookshop.model.book.Author;
import bookshop.model.book.Book;
import bookshop.model.book.Comment;
import bookshop.model.book.Genre;
import bookshop.model.user.User;
import bookshop.model.user.UserProfile;
import bookshop.service.book.BookService;
import bookshop.service.book.CommentService;
import bookshop.service.book.conversion.AuthorConverter;
import bookshop.service.user.UserProfileService;
import bookshop.service.user.UserService;
 
 
 
@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {
 
	static final Logger logger = LoggerFactory.getLogger(RoleToUserProfileConverter.class);
	
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
   
    @RequestMapping(value="/books", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String listBooks(Model model) {
    	model.addAttribute("book", new Book());
    	model.addAttribute("listBooks", this.bookService.getAllBooks());
    	model.addAttribute("foundBooks", this.bookService.getFoundBooks());
    	model.addAttribute("genre", Genre.values());
    	model.addAttribute("loggedinuser", getPrincipal());
    	return "books";
    }
    
    @RequestMapping(value="/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String displayBook(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("comment", new Comment());
    	model.addAttribute("book", this.bookService.getBookById(id));
    	model.addAttribute("loggedinuser", getPrincipal());
    	model.addAttribute("comments", commentService.getAll(id));
    	return "book";
    }
    
    @RequestMapping(value="/author/{author.name}")
    public String displayAuthor(@PathVariable("author.name") String name, Model model) {
    	model.addAttribute("author", this.bookService.getAuthor(name));
    	model.addAttribute("loggedinuser", getPrincipal());
    	return "author";
    }
    
    /*@InitBinder
    public void initBinder(WebDataBinder binder) {
       binder.registerCustomEditor(Set.class, "authors", new AuthorConverter(this.bookService));
    }*/
   
    //For book addition and update
    @RequestMapping(value= "/books/add", method = RequestMethod.POST)
    public String addBook(@ModelAttribute("book") Book book, @ModelAttribute("authors") Set <Author> auth, 
    		@RequestParam(value = "genres", required = false) Set <Genre> gen){
    	book.setAuthors(AuthorConverter.toAuthor(auth.toString()));
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
    
    @RequestMapping(value="/book/{id}/postcomment")
    public String postComment(@PathVariable("id") Long id, Model model, @ModelAttribute("comment") Comment comment) {
    	model.addAttribute("comment", new Comment());
    	this.commentService.persistComment(id, comment);
    	model.addAttribute("book", this.bookService.getBookById(id));
    	model.addAttribute("loggedinuser", getPrincipal());
    	model.addAttribute("comments", commentService.getAll(id));
    	return "book";
    }
        
    @RequestMapping(value="/book/{id}/rate")
    public String rate(@PathVariable("id") Long id, Model model, @ModelAttribute("rating") Integer rating) {
    	model.addAttribute("rating", rating);
    	bookService.rateBook(id, rating);
    	model.addAttribute("comment", new Comment());
    	model.addAttribute("book", this.bookService.getBookById(id));
    	model.addAttribute("loggedinuser", getPrincipal());
    	model.addAttribute("comments", commentService.getAll(id));
    	return "book";
    }

    @RequestMapping("/remove/{id}")
    public String removeBook(@PathVariable("id") Long id){
        
        this.bookService.delete(id);
        return "redirect:/books";
    }
    @RequestMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long id, Model model){
        model.addAttribute("book", this.bookService.getBookById(id));
        model.addAttribute("genre", Genre.values());
        model.addAttribute("listBooks", this.bookService.getAllBooks());
        model.addAttribute("loggedinuser", getPrincipal());
        return "books";
    }
    
    @RequestMapping(value = "/edit-author/{author.name}")
    public String editAuthor(@ModelAttribute("author") Author author, Model model){
    	this.bookService.updateAuthor(author);
    	model.addAttribute("author", this.bookService.getAuthor(author.getname()));
    	model.addAttribute("loggedinuser", getPrincipal());
    	return "author";
    }
    
    @RequestMapping(value="/books/search")
	public String searchResults(@RequestParam(value = "keyword", required = true) String keyword, Model model) {
    	this.bookService.findBook(keyword);
    	model.addAttribute("loggedinuser", getPrincipal());
	    return "redirect:/books";
	}
    
    @RequestMapping(value="/books/{genre}")
	public String genreFilter(@ModelAttribute("genre") Genre genre, Model model) {
    	System.out.println ("will search for "+genre.toString());
    	this.bookService.findBook(genre);
    	model.addAttribute("loggedinuser", getPrincipal());
	    return "redirect:/books";
	}

    /**
     * This method will list all existing users.
     */
    @RequestMapping(value = { "/list" }, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {
 
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("loggedinuser", getPrincipal());
        return "userslist";
    }
 
    /**
     * This method will provide the medium to add a new user.
     */
    @RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        model.addAttribute("loggedinuser", getPrincipal());
        return "registration";
    }
 
    /**
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the user input
     */
    @RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result,
            ModelMap model) {
 
        if (result.hasErrors()) {
            return "registration";
        }
 
        /*
         * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation 
         * and applying it on field [sso] of Model class [User].
         * 
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         * 
         */
        if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
            FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }
         
        userService.saveUser(user);
 
        model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        //return "success";
        return "registrationsuccess";
    }
 
 
    /**
     * This method will provide the medium to update an existing user.
     */
    @RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.GET)
    public String editUser(@PathVariable String ssoId, ModelMap model) {
        User user = userService.findBySSO(ssoId);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getPrincipal());
        return "registration";
    }
     
    /**
     * This method will be called on form submission, handling POST request for
     * updating user in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
            ModelMap model, @PathVariable String ssoId) {
 
        if (result.hasErrors()) {
            return "registration";
        }
 
        /*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
        if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
            FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }*/
 
 
        userService.updateUser(user);
 
        model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        return "registrationsuccess";
    }
 
     
    /**
     * This method will delete an user by it's SSOID value.
     */
    @RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String ssoId) {
        userService.deleteUserBySSO(ssoId);
        return "redirect:/list";
    }
     
 
    /**
     * This method will provide UserProfile list to views
     */
    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAll();
    }
     
    /**
     * This method handles Access-Denied redirect.
     */
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        return "accessDenied";
    }
 
    /**
     * This method handles login GET requests.
     * If users is already logged-in and tries to goto login page again, will be redirected to list page.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/list";  
        }
    }
 
    /**
     * This method handles logout requests.
     * Toggle the handlers if you are RememberMe functionality is useless in your app.
     */
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            //new SecurityContextLogoutHandler().logout(request, response, auth);
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/";
    }
 
    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    public String getPrincipal(){
    	//return "sam";
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
     
    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }
 
 
}
