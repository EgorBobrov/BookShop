/*
 * This class is responsible for connecting the User class and the database. 
 * Basically, it executes database queries. 
 */

package user.spring.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import book.spring.model.Book;
import user.spring.model.User;
 

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public User getUser(String login) {
		List<User> userList = new ArrayList<User>();
		Query query = openSession().createQuery("from User u where u.login = :login");
		query.setParameter("login", login);
		userList = query.list();
		if (userList.size() > 0) {
			System.out.println("Query query = openSession().createQuery(\"from User u where u.login = :login\");");
			return userList.get(0);
		}
		else {
			System.out.println("(userList.size() == 0)");
			return null;
		}
	}

}