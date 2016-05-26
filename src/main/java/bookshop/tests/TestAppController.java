package bookshop.tests;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
import bookshop.model.user.UserProfile;
import bookshop.service.book.BookService;
import bookshop.service.book.CommentService;
import bookshop.service.user.UserProfileService;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

public class TestAppController {
	
	@Autowired
	ApplicationContext context;

	@Mock
    private BookService mockBookService;
	
	@Mock
	private CommentService mockCommentService;
	
	@Mock
	private UserProfileService mockUserProfileService;
 
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
            
            when(mockBookService.getAllBooks()).thenReturn(Arrays.asList(first, second));
            when(mockUserProfileService.findAll()).thenReturn(Arrays.asList(new UserProfile()));
            
            Authentication authentication = mock(Authentication.class);
         // Mockito.whens() for authorization object
            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);
            when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("sam");
 
            mockMvc.perform(get("/books"))
            		.andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("books"))
                    .andExpect(model().attributeExists("book"))
                    .andExpect(model().attributeExists("listBooks"))
                    .andExpect(model().attributeExists("foundBooks"))
                    .andExpect(model().attributeExists("roles"))
                    .andExpect(model().attributeExists("loggedinuser"))
                    .andExpect(model().attributeExists("genre"))
                    .andExpect(model().attribute("loggedinuser", is("sam")))
                    .andExpect(model().attribute("listBooks", hasSize(2)));
                   //.andExpect(model().attribute("listBooks", is("[Gone with the Wind, Came Back with the Wind]")));

            verify(mockBookService, times(1)).getAllBooks();
           // verifyNoMoreInteractions(mockBookService);
 
        }
    
	    @Test
	    public void testAddBook() throws Exception {
	    	
	    	Authentication authentication = mock(Authentication.class);
	        SecurityContext securityContext = mock(SecurityContext.class);
	        when(securityContext.getAuthentication()).thenReturn(authentication);
	        SecurityContextHolder.setContext(securityContext);

	        this.mockMvc
	                .perform(post("/books/add").contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                        .param("title", "Brave New World").param("authors", "Aldous Huxley"))
	                .andDo(print())
	                .andExpect(view().name("redirect:/books"))
	                .andExpect(model().attribute("book", hasProperty("title", is("Brave New World"))));
	    }
}
