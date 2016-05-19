/*
 * This class is responsible for connecting the Book class and the database. 
 * Basically, it executes database queries. 
 */

package bookshop.dao.book;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookshop.model.book.Author;
import bookshop.model.book.Book;
 
//@Component
@Repository
public class BookDaoImpl implements BookDao {
	
	private static final Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);
	private String keyword = null;
	
	@Autowired
    private SessionFactory sessionFactory; 

/* SessionFactory is set in RootConfig.java now
 *    
 * public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
*/
	private Session openSession() {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		return sessionFactory.getCurrentSession();
	}

   // Stores a new book:
	//@Transactional
    public void persistBook(Book book) {
        Session session = openSession();
        session.persist(book);
        logger.info("Book added successfully. Details: " + book);
    }
	//@Transactional
    public void updateBook(Book book) {
        Session session = openSession();
        session.update(book);
        logger.info("Book updated successfully. Details: " + book);
	}
	//@Transactional
    public void delete(Long id) {
        Session session = openSession();
        Book b = (Book) session.load(Book.class, id);
        if(b != null){
            session.delete(b);
        }
        logger.info("Book deleted successfully, book details = " + b);
    }   
 
    // Retrieves all the books:
    @SuppressWarnings("unchecked")
    //@Transactional
    public List<Book> getAllBooks() {
    	Session session = openSession();
    	List<Book> booksList = session.createQuery("from Book").list();
        for(Book b : booksList){
            logger.info("Book List::"+b);
        }
        return booksList;
    }
    
    //search via title/keyword
    @SuppressWarnings("unchecked")
    //@Transactional
	public List<Book> doSearch() {
    	Session session = openSession();
    	if (keyword == null){
    		return session.createQuery("from Book b").list();
    	}
    	System.out.println();
     	return session.createQuery("select distinct b from Book b inner join b.authors a WHERE UPPER(a.lastName) LIKE :keyword OR UPPER(b.title) LIKE :keyword OR UPPER(b.description) LIKE :keyword  LIKE :keyword").setParameter("keyword", "%"+keyword.toUpperCase()+"%").list();
       }
    
    public void setKeyword(String keyword) {
    	this.keyword = keyword;
    }

   // @Transactional
    public Book getBookById(Long id) {
    	Session session = openSession();      
        Book b = (Book) session.load(Book.class, id);
        logger.info("Book loaded successfully, Book details = " + b);
        System.out.println("Book loaded successfully, Book details = " + b);
        return b;
    }
    
    // Stores a new book:
    @Override
    public void persistAuthors(Set <Author> authors) {
        Session session = this.sessionFactory.getCurrentSession();
        authors.stream().forEach(author -> session.persist(author));
    }
}