package bookshop.tests;

import static org.junit.Assert.*;  

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;  
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;  
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import org.springframework.test.context.web.WebAppConfiguration;

import bookshop.dao.book.BookDao;
import bookshop.model.book.Author;
import bookshop.model.book.Book;
import bookshop.model.book.Genre;
import bookshop.tests.configuration.TestHibernateConfiguration;
import static org.hamcrest.Matchers.*;


//this test set is using in-memory database set in books.xml
//was partly referring to  http://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-integration-testing/ 
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration (classes = { TestHibernateConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
@TransactionConfiguration(defaultRollback=false, transactionManager="transactionManager")  
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DbUnitTestExecutionListener.class})
	//TransactionalTestExecutionListener.class})
//@Transactional
@DatabaseSetup("/dbxml/books.xml")
public class TestBookDaoImpl extends AbstractTransactionalJUnit4SpringContextTests { 
	
	@Autowired
	private BookDao bookDao;

	@Test  
	public void testBookById() {
		 Book getFromDao = bookDao.getBookById(1);  		   
		 assertNotNull("obtaining some kind of result", getFromDao);  
		 assertEquals("getting the actual book details", "The Sound And The Fury", getFromDao.getTitle());  
	}
	
	@Test  
	public void testPersistBook() {
	    Set<Author> authors1 = new HashSet<Author>();
	    authors1.add(new Author("Margaret Mitchell"));
	    Set<Genre> genres1 = new HashSet<Genre>();
	    genres1.addAll(Arrays.asList(new Genre[] {Genre.ROMANCE, Genre.FICTION}));
	    
	    authors1.add(new Author("Margaret Pitchell"));
		Book first = new Book("978-1416548942", "Gone with the Wind", 1472, "The best novel to have ever come out of the South...it is unsurpassed in the whole of American writing.", 
   			  authors1, genres1, 23, 0.1);
		bookDao.persistBook(first);
		assertEquals("the size of table extended", true, (bookDao.getAllBooks().size() == 3));
	}
	
	@Test  
	public void testGetAllBooks() {
		List <Book> bookList = bookDao.getAllBooks();
		assertEquals("the size of table extended", true, (bookList .size() == 2));
		assertEquals("the books inserted earlier are returned", true, (bookList.get(0).getId() +  bookList.get(1).getId() == 3));
	}
	
	@Test  
	public void testUpdateBook() {
		Book getFromDao = bookDao.getBookById(1);
		getFromDao.setPrice(20);
		bookDao.updateBook(getFromDao);
		assertEquals("edited price value persists", true, bookDao.getBookById(1).getPrice().equals(20));
	}
	
	@Test
    public void testGetBooksSortedByRating() {
		List <Book> bookList = bookDao.getBooksSortedByRating();
		assertThat("sorted by rating", bookList.get(1).getRating(), greaterThan(bookList.get(0).getId()));
		//will change rating and see whether the changes are reflected
		bookList.get(1).setRating(2);
		bookDao.updateBook(bookList.get(1));
		assertThat("sorted by rating", bookList.get(0).getRating(), greaterThan(bookList.get(1).getId()));
		
    }

}  