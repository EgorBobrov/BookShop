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
import bookshop.dao.user.UserDao;
import bookshop.model.book.Author;
import bookshop.model.book.Book;
import bookshop.model.book.Genre;
import bookshop.model.user.User;
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
	
	@Autowired
	private UserDao userDao;

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
		assertTrue("edited price value persists", bookDao.getBookById(1).getPrice().equals(20));
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
	
	@Test
	 public void testFindByGenre() {
		List <Book> bookList = bookDao.doSearch(Genre.COMEDY);
		assertTrue("only <as I lay dying> is returned", bookList.get(0).getId().equals(2));	
    }
	
	@Test
	 public void testGetSimilarBooks() {
		Book getFromDao = bookDao.getBookById(1);
		User user = userDao.findById(1);
		List <Book> bookList = bookDao.getSimilarBooks(getFromDao, user);
		Book anotherBook = bookDao.getBookById(2);
		assertEquals("one book is recommended", bookList.size(), 1);
		assertEquals("it's this book that chichi owns with book 1", bookList.get(0), anotherBook);
   }
	
	@Test
	public void testGetAuthor() {
		assertEquals("author's details are retrieved from db", bookDao.getAuthor("William Faulkner").getBio(),
				"William Faulkner was awarded the Nobel Prize for Literature in 1949 and the Pulitzer Prize for The Reivers just before his death in July 1962.");
	}
	
	@Test
	public void testUpdateAuthor() {
		Author toUpdate = bookDao.getAuthor("William Faulkner");
		toUpdate.setPicture("william.png");
		bookDao.updateAuthor(toUpdate);
		assertEquals("not changed details are still valid", bookDao.getAuthor("William Faulkner").getBio(),
				"William Faulkner was awarded the Nobel Prize for Literature in 1949 and the Pulitzer Prize for The Reivers just before his death in July 1962.");
		assertEquals("changed details are changed", bookDao.getAuthor("William Faulkner").getPicture(), "william.png");
	}
	
	@Test
	public void testRateBook() {
		bookDao.rateBook(1, 3);
		assertTrue("sum of all ratings given to this book will change to 8", bookDao.getBookById(1).getRating().equals(8));
	}

}  