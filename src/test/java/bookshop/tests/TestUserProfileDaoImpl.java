package bookshop.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

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
import org.springframework.test.context.web.WebAppConfiguration;

import com.epamjuniors.bookshop.bookshop_dao.user.UserProfileDao;
import com.epamjuniors.bookshop.bookshop_model.user.UserProfile;
import com.epamjuniors.bookshop.bookshop_model.user.UserProfileType;
import bookshop.tests.configuration.TestHibernateConfiguration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration (classes = { TestHibernateConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
@TransactionConfiguration(defaultRollback=true, transactionManager="transactionManager")  
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DbUnitTestExecutionListener.class})
@DatabaseSetup("/dbxml/books.xml")
public class TestUserProfileDaoImpl extends AbstractTransactionalJUnit4SpringContextTests {
	
    	@Autowired
    	private UserProfileDao userProfileDao;
    	
    	@Test
        public void testFindById() {
    	    assertEquals("user #1 Alaska is of type USER", userProfileDao.findById(1).getType(), UserProfileType.USER.toString());
        }
     
    	@Test
        public void testFindByType() {
            assertNotNull("user type is present", userProfileDao.findByType(UserProfileType.USER.toString())); 
            assertNotNull("admin type is present", userProfileDao.findByType(UserProfileType.ADMIN.toString())); 
            assertNotNull("dba type is present", userProfileDao.findByType(UserProfileType.DBA.toString())); 
        }
         
        @Test
        public void testFindAll(){
            List <UserProfile> list = userProfileDao.findAll();
            assertEquals("all three types were fetched", list.size(), 3);
        }
        	
}