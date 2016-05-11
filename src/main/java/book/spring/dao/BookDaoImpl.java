/*
 * This class is responsible for connecting the Book class and the database. 
 * Basically, it executes database queries. 
 */

package book.spring.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import book.spring.model.Book;
 
@Component
public class BookDaoImpl implements BookDao {
	
	private static final Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);
	private String keyword = null;
	
	@Autowired
    private SessionFactory sessionFactory; 

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
   // Stores a new book:
    public void persistBook(Book book) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(book);
        logger.info("Book added successfully. Details: " + book);
        System.out.println("Book added successfully. Details: " + book);
    }
    
    public void updateBook(Book book) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(book);
        logger.info("Book updated successfully. Details: " + book);
	}
    
    public void delete(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Book b = (Book) session.load(Book.class, id);
        if(b != null){
            session.delete(b);
        }
        logger.info("Book deleted successfully, book details = " + b);
    }   
 
    // Retrieves all the books:
    @SuppressWarnings("unchecked")
    public List<Book> getAllBooks() {
    	Session session = this.sessionFactory.getCurrentSession();
    	List<Book> booksList = session.createQuery("from Book").list();
        for(Book b : booksList){
            logger.info("Book List::"+b);
        }
        return booksList;
    }
    
    //search via title/keyword
    @SuppressWarnings("unchecked")
	public List<Book> doSearch() {
    	Session session = this.sessionFactory.getCurrentSession();
    	if (keyword == null){
    		return session.createQuery("from Book").list();
    	}
    	logger.info("keyword is: "+keyword);
    	List<Book> bookList = session.createQuery("from Book b WHERE UPPER(b.title) LIKE :keyword OR UPPER(b.description) LIKE :keyword ORDER BY b.title").setParameter("keyword", "%"+keyword.toUpperCase()+"%").list();
    	for(Book b : bookList){
    		logger.info("among found books::"+b);
        }
        return bookList;
    }
    
    public void setKeyword(String keyword) {
    	this.keyword = keyword;
    }

    
    public Book getBookById(Long id) {
    	Session session = this.sessionFactory.getCurrentSession();      
        Book b = (Book) session.load(Book.class, id);
        logger.info("Book loaded successfully, Book details = " + b);
        System.out.println("Book loaded successfully, Book details = " + b);
        return b;
    }
}