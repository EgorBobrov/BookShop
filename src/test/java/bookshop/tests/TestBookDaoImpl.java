package bookshop.tests;

import static org.junit.Assert.*;  

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;  
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;  
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import bookshop.dao.book.BookDao;
import bookshop.model.book.Book;
import bookshop.tests.configuration.TestHibernateConfiguration;


//this test set is using in-memory database set in books.xml
//was partly referring to  http://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-integration-testing/ 
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration (classes = { TestHibernateConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
@TransactionConfiguration(defaultRollback=true, transactionManager="transactionManager")  
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DbUnitTestExecutionListener.class,
	TransactionalTestExecutionListener.class})
@Transactional
@DatabaseSetup("/dbxml/books.xml")
public class TestBookDaoImpl  { 
	
	@Autowired
	private BookDao bookDao;

	@Test  
	public void testBookById() {
		 Book getFromDao = bookDao.getBookById(1);  		   
		 assertNotNull(getFromDao);  
		 assertEquals("HATEEEEEEEEEEEE", getFromDao.getTitle());  
	}  	 
}  