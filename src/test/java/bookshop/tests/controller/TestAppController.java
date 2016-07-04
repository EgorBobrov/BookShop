package bookshop.tests.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import bookshop.controller.AppController;
import bookshop.model.book.Author;
import bookshop.model.book.Book;
import bookshop.model.book.Genre;
import bookshop.model.user.User;
import bookshop.model.user.UserProfile;
import bookshop.service.book.BookService;
import bookshop.service.book.CommentService;
import bookshop.service.user.UserProfileService;
import bookshop.service.user.UserService;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


public class TestAppController {

	@Mock
    private BookService mockBookService;
	
	@Mock
	private CommentService mockCommentService;
	
	@Mock
	private UserProfileService mockUserProfileService;
	
	@Mock
	private UserService mockUserService;
	
	@Mock
	private Logger logger = LoggerFactory.getLogger(TestAppController.class);
 
    @InjectMocks
    private AppController appCon = new AppController();
 
    private MockMvc mockMvc;
 
    @Before
    public void setup() {
 
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(appCon)
                                 .setViewResolvers(viewResolver)
                                 .build();
        
     // setting up mocking of authorization to be used in all of the tests
        Authentication authentication = mock(Authentication.class);
        // Mockito.whens() for authorization object if needed
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("sam");
        when(mockUserService.findBySSO("sam")).thenReturn(new User("sam", "sam"));
    }
    
    @Test
    public void testListBooks() throws Exception {
    	
    	    Set<Author> authors1 = new HashSet<Author>();
    	    authors1.add(new Author("Margaret Mitchell"));
    	    Set<Genre> genres1 = new HashSet<Genre>();
    	    genres1.addAll(Arrays.asList(new Genre[] {Genre.ROMANCE, Genre.FICTION}));
    	    
    	    authors1.add(new Author("Margaret Pitchell"));
			Book first = new Book("978-1416548942", "Gone with the Wind", 1472, "The best novel to have ever come out of the South...it is unsurpassed in the whole of American writing.", 
       			  authors1, genres1, 23, 0.1);
            Book second = new Book("978-141654834", "Came Back with the Wind", 1772, "Fascinating and unforgettable! A remarkable book, a spectacular book, a book that will not be forgotten!", 
         			  authors1, genres1, 30, 0.);
            
            when(mockBookService.getFoundBooks()).thenReturn(Arrays.asList(first, second));
            when(mockUserProfileService.findAll()).thenReturn(Arrays.asList(new UserProfile()));
            
            mockMvc.perform(get("/books"))
            		.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("books"))
                    .andExpect(model().attributeExists("book"))
                    .andExpect(model().attributeExists("foundBooks"))
                    .andExpect(model().attributeExists("roles"))
                    .andExpect(model().attributeExists("loggedinuser"))
                    .andExpect(model().attributeExists("genre"))
                    .andExpect(model().attribute("loggedinuser", is("sam")))
                    .andExpect(model().attribute("foundBooks", hasSize(2)));

            verify(mockBookService, times(1)).getFoundBooks();
            verifyNoMoreInteractions(mockBookService);
 
        }
    
	    @Test
	    public void testAddBook() throws Exception {
	        this.mockMvc
	                .perform(post("/books/add").contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                        .param("title", "Brave New World").param("authors", "Aldous Huxley"))
	                .andDo(print())
	                .andExpect(view().name("redirect:/books"))
	                .andExpect(model().attribute("book", hasProperty("title", is("Brave New World"))));
	    }
	    
	    @Test
	    public void testDisplayBook() throws Exception {
	    	
	    	Set<Author> authors1 = new HashSet<Author>();
	    	authors1.add(new Author("Margaret Mitchell"));
	    	Set<Genre> genres1 = new HashSet<Genre>();
	    	genres1.addAll(Arrays.asList(new Genre[] {Genre.ROMANCE, Genre.FICTION}));
	    	Book first = new Book("978-1416548942", "Gone with the Wind", 1472, "The best novel to have ever come out of the South...it is unsurpassed in the whole of American writing.", 
	       			  authors1, genres1, 23, 0.1);
	    	
	    	when(mockBookService.getBookById(1)).thenReturn(first);
	    	
	        mockMvc.perform(get("/book/1"))
	                .andDo(print())
	                .andExpect(status().isOk())
	                .andExpect(view().name("book"))
	                .andExpect(model().attributeExists("book"))
                    .andExpect(model().attributeExists("comment"))
                    .andExpect(model().attributeExists("comments"))
                    .andExpect(model().attributeExists("loggedinuser"))
                    .andExpect(model().attributeExists("similarBooks"))
                    .andExpect(model().attribute("loggedinuser", is("sam")))
                    .andExpect(model().attribute("similarBooks", hasSize(0)))
                    .andExpect(model().attribute("book", hasProperty("title", is("Gone with the Wind"))));
	    }
	    
	    @Test
	    public void testDisplayAuthor() throws Exception {
	    	
	    	Author author = new Author("Margaret Mitchell");
	    	author.setBio("Margaret Munnerlyn Mitchell (November 8, 1900 – August 16, 1949) was an American author and journalist.");
	    	
	    	when(mockBookService.getAuthor("Margaret Mitchell")).thenReturn(author);
	    	
	        mockMvc.perform(get("/author/Margaret Mitchell"))
	                .andDo(print())
	                .andExpect(status().isOk())
	                .andExpect(view().name("author"))
	                .andExpect(model().attributeExists("author"))
                    .andExpect(model().attribute("loggedinuser", is("sam")))
                    .andExpect(model().attribute("author", hasProperty("bio",
                    		is("Margaret Munnerlyn Mitchell (November 8, 1900 – August 16, 1949) was an American author and journalist."))));
	    }
	    
	    @Test
	    public void testShowUser() throws Exception {
	    	User user = new User ("scarlett", "qwerty123");
	    	
	    	when(mockUserService.findBySSO("scarlett")).thenReturn(user);
	    	
	    	mockMvc.perform(get("/user/scarlett"))
		    	.andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(view().name("user"))
	            .andExpect(model().attributeExists("user"))
	            .andExpect(model().attribute("loggedinuser", is("sam")))
	            .andExpect(model().attribute("user", hasProperty("ssoId", is("scarlett"))));
	    }
	    
	    @Test
	    public void testAddToBasket() throws Exception {
	    	Set<Author> authors1 = new HashSet<Author>();
    	    authors1.add(new Author("Margaret Mitchell"));
    	    Set<Genre> genres1 = new HashSet<Genre>();
    	    genres1.addAll(Arrays.asList(new Genre[] {Genre.ROMANCE, Genre.FICTION}));
	    	Book first = new Book("978-1416548942", "Gone with the Wind", 1472, "The best novel to have ever come out of the South...it is unsurpassed in the whole of American writing.", 
	       			  authors1, genres1, 23, 0.1);
	    	first.setId(123);
	    	
	    	mockMvc.perform(get("/tobasket/123"))
		    	.andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(view().name("book"))
	            .andExpect(model().attributeExists("comment"))
	            .andExpect(model().attributeExists("comments"))
	            .andExpect(model().attribute("loggedinuser", is("sam")));
	    }
	    
	    @Test
	    public void testUsersList() throws Exception {
	    	User user1 = new User ("scarlett", "qwerty123");
	    	User user2 = new User ("william", "qwerty123");
	    	User user3 = new User ("bonnie", "qwerty12345");
	    	List <User> users = Arrays.asList(new User[] {user1, user2, user3});
	    	
	    	when(mockUserService.findAllUsers()).thenReturn(users);
	    	
	    	mockMvc.perform(get("/list"))
	    		.andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(view().name("userslist"))
	            .andExpect(model().attributeExists("users"))
	            .andExpect(model().attribute("loggedinuser", is("sam")))
	            .andExpect(model().attribute("users", is(users)));
	    }
	    
}
