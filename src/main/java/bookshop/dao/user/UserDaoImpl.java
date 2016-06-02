/*
 * This class is responsible for connecting the User class and the database. 
 * Basically, it executes database queries. 
 */

package bookshop.dao.user;
import java.util.List;
import java.util.logging.Level;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookshop.dao.AbstractDao;
import bookshop.model.book.Book;
import bookshop.model.user.User;
 
 
 
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {
	
	@Autowired
    private SessionFactory sessionFactory; 

	private Session openSession() {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		return sessionFactory.getCurrentSession();
	}

    static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
     
    public User findById(int id) {
        User user = getByKey(id);
        if(user!=null){
            Hibernate.initialize(user.getUserProfiles());
        }
        return user;
    }
 
    public User findBySSO(String sso) {
        logger.info("SSO : {}", sso);
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("ssoId", sso));
        User user = (User)crit.uniqueResult();
        if(user!=null){
            Hibernate.initialize(user.getUserProfiles());
        }
        return user;
    }
 
    @SuppressWarnings("unchecked")
    public List<User> findAllUsers() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<User> users = (List<User>) criteria.list();
         
        // No need to fetch userProfiles since we are not showing them on list page. Let them lazy load. 
        // Uncomment below lines for eagerly fetching of userProfiles if you want.
        /*
        for(User user : users){
            Hibernate.initialize(user.getUserProfiles());
        }*/
        return users;
    }
 
    public void save(User user) {
        persist(user);
    }
 
    public void deleteBySSO(String sso) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("ssoId", sso));
        User user = (User)crit.uniqueResult();
        delete(user);
    }

	@Override
	public void addBookToBasket(Long bookId, String ssoId) {
		// TODO Auto-generated method stub
		Session session = openSession();
		Book addedBook = (Book) session.load(Book.class, bookId);
		User buyer = findBySSO(ssoId);
		System.out.println("!!! amount of books "+addedBook.getAmountInStock());
		buyer.addToBasket(addedBook);
		buyer.getBasket().stream().forEach(System.out::println);
		addedBook.getBuyers().add(buyer);
		addedBook.getBuyers().stream().forEach(System.out::println);
		addedBook.setAmountInStock(addedBook.getAmountInStock() - 1);
		System.out.println("amount of books "+addedBook.getAmountInStock());
		session.update(buyer);
		session.update(addedBook);
		buyer = findBySSO(ssoId);
		buyer.getBasket().stream().forEach(System.out::println);
	}

	@Override
	public void removeBookFromBasket(Long bookId, String ssoId) {
		Session session = openSession();
		Book removedBook = (Book) session.load(Book.class, bookId);
		User buyer = findBySSO(ssoId);
		buyer.getBasket().remove(removedBook);
		removedBook.getBuyers().remove(buyer);
		removedBook.setAmountInStock(removedBook.getAmountInStock() + 1);
		session.update(buyer);
		session.update(removedBook);
	}

	@Override
	public void commitPurchase(String ssoId) {
		Session session = openSession();
		User client = findBySSO(ssoId);
		for(Book book : client.getBasket()) {
			client.getInventory().add(book);
			book.getOwners().add(client);
			book.getBuyers().remove(client);
		}
		client.getBasket().clear();
		
		session.update(client);
	}
 
}