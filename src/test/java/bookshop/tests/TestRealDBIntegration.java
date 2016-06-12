package bookshop.tests;

import static org.junit.Assert.*;  

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;  
import org.springframework.test.context.transaction.TransactionConfiguration;  
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import bookshop.configuration.AppConfig;
import bookshop.dao.book.BookDao;
import bookshop.model.book.Author;
import bookshop.model.book.Book;
import bookshop.model.book.Genre;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration (classes = AppConfig.class, loader = AnnotationConfigWebContextLoader.class)
@TransactionConfiguration(defaultRollback=true, transactionManager="transactionManager")  
@Transactional
public class TestRealDBIntegration { 
	
	@Autowired
	private BookDao bookDao;
	
	@Test  
	public void testBookById() {
		 Book getFromDao = bookDao.getBookById(120);  		   
		 assertNotNull(getFromDao);  
		 assertEquals("Head First Servlets and JSP", getFromDao.getTitle());  
	}  	
	  
	@Test  
	public void persistBook()  
	{   
		 Set<Author> authors1 = new HashSet<Author>();
 	     authors1.add(new Author("Margaret Mitchell"));
 	     Set<Genre> genres1 = new HashSet<Genre>();
 	     genres1.addAll(Arrays.asList(new Genre[] {Genre.ROMANCE, Genre.FICTION}));
 	    
 	     authors1.add(new Author("Margaret Pitchell"));
		 Book first = new Book("978-1416548942", "Gone with the Wind", 1472, "The best novel to have ever come out of the South...it is unsurpassed in the whole of American writing.", 
    			  authors1, genres1, 23, 0.1);
		 Book getFromDao = bookDao.persistBook(first);  
		   
		 assertEquals("Gone with the Wind", getFromDao.getTitle());  
	}  
	 
} 