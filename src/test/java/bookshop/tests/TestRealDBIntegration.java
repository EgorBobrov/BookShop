package bookshop.tests;

import static org.junit.Assert.*;  

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
import bookshop.model.book.Book;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration (classes = AppConfig.class, loader = AnnotationConfigWebContextLoader.class)
@TransactionConfiguration(defaultRollback=true, transactionManager="transactionManager")  
@Transactional
//this test can be used if you want to check whether it's all alright with production db integration
//use TestBookDaoImpl if you need detailed check on data processing (it uses test db)
public class TestRealDBIntegration { 
	
	@Autowired
	private BookDao bookDao;
	
	@Test  
	public void testBookById() {
		 Book getFromDao = bookDao.getBookById(120);  		   
		 assertNotNull(getFromDao);  
		 assertEquals("Head First Servlets and JSP", getFromDao.getTitle());  
	}  	
	 
} 