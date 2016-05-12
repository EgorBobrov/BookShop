package book.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import book.spring.model.Author;

@Component("authorDao")
public class AuthorDaoImpl implements AuthorDao{

	@Autowired 
    private SessionFactory sessionFactory; 

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
   // Stores a new book:
    public void persistAuthor(Author author) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(author);
        System.out.println("author added successfully. Details: " + author.toString());
    }

}
