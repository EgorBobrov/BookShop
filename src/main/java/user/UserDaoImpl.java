/*
 * This class is responsible for connecting the User class and the database. 
 * Basically, it executes database queries. 
 */

package user;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import book.Book;
 
@Component
public class UserDaoImpl implements UserDao {
    // Injected database connection:
    @PersistenceContext private EntityManager em;
 
    // Stores a new user:
    @Transactional
    public void persist(User user) {
        em.persist(user);
    }
 
    // Retrieves all the registered users:
    public List<User> getAllUsers() {
        TypedQuery<User> query = em.createQuery(
            "SELECT u FROM User u ORDER BY u.id", User.class);
        return query.getResultList();
    }

	@Override
	public Book getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		
	}
}