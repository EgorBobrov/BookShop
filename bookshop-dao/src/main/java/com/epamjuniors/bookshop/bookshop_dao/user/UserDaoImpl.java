/*
 * This class is responsible for connecting the User class and the database. 
 * Basically, it executes database queries. 
 */

package com.epamjuniors.bookshop.bookshop_dao.user;
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

import com.epamjuniors.bookshop.bookshop_dao.AbstractDao;
import com.epamjuniors.bookshop.bookshop_model.book.Book;
import com.epamjuniors.bookshop.bookshop_model.user.User;
import com.epamjuniors.bookshop.bookshop_model.user.UserAddress;
 
 
 
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
    
    @SuppressWarnings("unchecked")
   	public List<UserAddress> getExistingAddresses(User user) {
       	Session session = openSession();
       	List<UserAddress> ua = session.createQuery("select distinct a from UserAddress a inner join a.user WHERE a.user = :user").setParameter("user", user).list();
       	return ua;
    }
    
    public void addAddress (User u, UserAddress ua){
    	Session session = sessionFactory.getCurrentSession();
    	ua.setUserId(u);
		session.save(ua);
		u.addAddress(ua);
		session.update(u);
    }
    
    public void deleteAddress (User u, Integer addrId){
    	Session session = sessionFactory.getCurrentSession();
    	UserAddress ua = findAddressByID(addrId);
		session.delete(ua);
		u.removeAddress(ua);
    }
    
    public UserAddress findAddressByID(Integer addrId){
    	Session session = openSession();
    	UserAddress ua = (UserAddress) session.createQuery("select distinct a from UserAddress a WHERE a.id = :addrId").setParameter("addrId", addrId).uniqueResult();
    	return ua;
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
	public void addBookToBasket(Integer bookId, String ssoId) {
		Session session = openSession();
		Book addedBook = (Book) session.load(Book.class, bookId);
		User buyer = findBySSO(ssoId);
		buyer.addToBasket(addedBook);
		addedBook.getBuyers().add(buyer);
		addedBook.setAmountInStock(addedBook.getAmountInStock() - 1);
		session.update(buyer);
		session.update(addedBook);
		buyer = findBySSO(ssoId);
	}

	@Override
	public void removeBookFromBasket(Integer bookId, String ssoId) {
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