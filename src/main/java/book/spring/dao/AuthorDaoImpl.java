package book.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import book.spring.model.Author;

@Component("authorDao")
@Transactional
public class AuthorDaoImpl implements AuthorDao{

	@Autowired 
    private SessionFactory sessionFactory; 
	
	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}

/*    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
*/
   // Stores a new book:
    public void persistAuthor(Author author) {
        Session session = openSession();
        session.persist(author);
        System.out.println("author added successfully. Details: " + author.toString());
    }

}
