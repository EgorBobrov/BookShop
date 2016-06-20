package bookshop.tests;

import static org.junit.Assert.assertEquals;

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
import org.springframework.test.context.web.WebAppConfiguration;

import bookshop.dao.user.UserDao;
import com.epamjuniors.bookshop.bookshop_model.user.User;
import com.epamjuniors.bookshop.bookshop_model.user.UserAddress;
import com.epamjuniors.bookshop.bookshop_model.user.UserProfile;
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
public class TestUserDaoImpl extends AbstractTransactionalJUnit4SpringContextTests {
	
    	@Autowired
	private UserDao userDao;
	
    	@Test
	public void testFindById() {
    	    User userFromDao = userDao.findById(1);
    	    assertEquals("username is received from DB", userFromDao.getSsoId(), "alaska");
    	    assertEquals("first name is received from DB", userFromDao.getFirstName(), "Alaska");
    	    assertEquals("last name is received from DB", userFromDao.getLastName(), "5000");
    	    assertEquals("email is received from DB", userFromDao.getEmail(), "alaska@whatever.com");
    	}
 
    	@Test
        public void testFindBySSO() {
    	    User userFromDao = userDao.findBySSO("alaska");
    	    assertEquals("first name is received from DB", userFromDao.getFirstName(), "Alaska");
    	    assertEquals("last name is received from DB", userFromDao.getLastName(), "5000");
    	    assertEquals("email is received from DB", userFromDao.getEmail(), "alaska@whatever.com");

    	}
    	
    	@Test
        public void testFindAllUsers() {
    	    List <User> usersFromDao = userDao.findAllUsers();
    	    
    	    assertEquals("2 users received from DB", usersFromDao.size(), 2);
    	    assertEquals("first user is alaska", usersFromDao.get(0).getSsoId(), "alaska");
    	    assertEquals("second user is chichi", usersFromDao.get(1).getSsoId(), "chichi");
     	}
    	
        @Test
    	public void testGetExistingAddresses() {
            User userFromDao = userDao.findById(1);
            List<UserAddress> list = userDao.getExistingAddresses(userFromDao);
            assertEquals("1 saved address received from DB", list.size(), 1);
            assertEquals("street received from DB", list.get(0).getStreet(), "123 Main st.");
            assertEquals("city received from DB", list.get(0).getCity(), "Raleigh");
            assertEquals("region received from DB", list.get(0).getRegion(), "NC");
            assertEquals("country received from DB", list.get(0).getCountry(), "USA");
            assertEquals("name received from DB", list.get(0).getName(), "Alaska 5000");
        }
        
        @Test
        public void testFindAddressByID(){
            UserAddress ua = userDao.findAddressByID(1);
            assertEquals("street received from DB", ua.getStreet(), "123 Main st.");
            assertEquals("city received from DB", ua.getCity(), "Raleigh");
            assertEquals("region received from DB", ua.getRegion(), "NC");
            assertEquals("country received from DB", ua.getCountry(), "USA");
            assertEquals("name received from DB", ua.getName(), "Alaska 5000");
        }
        
        @Test
        public void testDeleteAddress (){
            User userFromDao = userDao.findById(1);
            assertEquals("1 saved address received from DB for Alaska", userDao.getExistingAddresses(userFromDao).size(), 1);
            userDao.deleteAddress(userFromDao, 1);
            assertEquals("after deletion, 0 saved address received from DB", userDao.getExistingAddresses(userFromDao).size(), 0);
        }
        
        @Test
        public void testAddAddress (){
            UserAddress ua = new UserAddress("Sharon Needles", "183 Graham street 11216", "Brooklyn", "NY", "USA");
            User userFromDao = userDao.findById(1);
            List<UserAddress> list = userDao.getExistingAddresses(userFromDao);
            assertEquals("1 saved address received from DB", list.size(), 1);
            userDao.addAddress(userFromDao, ua);
            list = userDao.getExistingAddresses(userFromDao);
            assertEquals("after adding - 2 saved address received from DB", list.size(), 2);
            assertEquals("new street received from DB", list.get(1).getStreet(), "183 Graham street 11216");
            assertEquals("new city received from DB", list.get(1).getCity(), "Brooklyn");
            assertEquals("new region received from DB", list.get(1).getRegion(), "NY");
            assertEquals("new country received from DB", list.get(1).getCountry(), "USA");
            assertEquals("new name received from DB", list.get(1).getName(), "Sharon Needles");
        }
     
        @Test
        public void testSave() {
            User newUser = new User("sharon_needles", "qwerty123");
            newUser.setEmail("sharon@whatever.com");
            newUser.setFirstName("Sharon");
            newUser.setLastName("Needles");
            Set <UserProfile>roles = new HashSet<UserProfile> ();
            roles.add(new UserProfile ());
            newUser.setUserProfiles(roles);
    	    assertEquals("2 users in DB", userDao.findAllUsers().size(), 2);
            userDao.save(newUser);
            assertEquals("after we added a new one, it's 3 users in DB", userDao.findAllUsers().size(), 3);
        }
    
        @Test
        public void testDeleteBySSO() {	    
    	    assertEquals("2 users received from DB", userDao.findAllUsers().size(), 2);
    	    userDao.deleteBySSO("alaska");
    	    assertEquals("after deletion 1 user received from DB", userDao.findAllUsers().size(), 1);
    	    assertEquals("only user chichi is in db", userDao.findAllUsers().get(0).getSsoId(), "chichi");
        }

        @Test
	public void testAddBookToBasket() {
            userDao.addBookToBasket(2, "alaska");
            User userFromDao = userDao.findById(1);
            assertEquals("now it's 2 books in user's basket", userFromDao.getBasket().size(), 2);
        }

        @Test
	public void testRemoveBookFromBasket() {
            userDao.removeBookFromBasket(1, "alaska");
            User userFromDao = userDao.findById(1);
            assertEquals("now it's no books in user's basket", userFromDao.getBasket().size(), 0);

        }
        
        @Test
	public void commitPurchase() {
            assertEquals("first in was 1 book in user's basket", userDao.findById(1).getBasket().size(), 1);
	    assertEquals("user's inventory was empty", userDao.findById(1).getInventory().size(), 0);
            userDao.commitPurchase("alaska");
	    assertEquals("now it's no books in user's basket", userDao.findById(1).getBasket().size(), 0);
	    assertEquals("purchased book went to inventory", userDao.findById(1).getInventory().size(), 1);
	}
	
}