/*
 * This class is responsible for connecting the Book class and the database. 
 * Basically, it executes database queries. 
 */

package bookshop.dao.book;

import java.util.Arrays;
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
import bookshop.model.book.Comment;
import bookshop.model.book.Genre;
 
//@Component
@Repository
public class BookDaoImpl implements BookDao {
	
	private static final Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);
	private String keyword = null;
	private Genre selectedGenre = null;
	
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
        session.merge(book);
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
        	b.getAuthors().stream().forEach(a -> {
        		a.removeBook(b);
        		//author will be removed automatically by cascade if she's an orphan, otherwise we need to take care of this
        		if (a.getBooks().size()!=0) {
        			b.removeAuthor(a);
        			session.update(b);
        		}
        	});
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
     	if (keyword != null)
     			return session.createQuery("select distinct b from Book b inner join b.authors a WHERE UPPER(a.name) LIKE :keyword OR UPPER(b.title) LIKE :keyword OR UPPER(b.description) LIKE :keyword").setParameter("keyword", "%"+keyword.toUpperCase()+"%").list();
     	if (selectedGenre!=null)	
     		return session.createQuery("select distinct b from Book b inner join b.genres g WHERE g IN (:genres)").setParameterList("genres", Arrays.asList(selectedGenre)).list();
     	return session.createQuery("from Book b").list();
    }
    
    //search via genre
    @SuppressWarnings("unchecked")
    //@Transactional
	public List<Book> doSearch(Genre genre) {
    	Session session = openSession();
     	return session.createQuery("select distinct b from Book b inner join b.genres g WHERE g IN (:genres)").setParameterList("genres", Arrays.asList(genre)).list();
    }
    
    public void setKeyword(String keyword) {
    	this.keyword = keyword;
    }
    
    public void setGenre(Genre g) {
    	this.selectedGenre = g;
    }

   // @Transactional
    public Book getBookById(Long id) {
    	Session session = openSession();      
        Book b = (Book) session.load(Book.class, id);
        logger.info("Book loaded successfully, Book details = " + b);
        System.out.println("Book loaded successfully, Book details = " + b);
        return b;
    }
    
  /*  @Override
    public void persistAuthors(Set <Author> authors) {
        Session session = this.sessionFactory.getCurrentSession();
        authors.stream().forEach(author -> session.persist(author));
    }*/
    
    public Author getAuthor(String name){
    	System.out.println(getClass().getSimpleName());
    	Session session = openSession();
    	Author a = (Author) session.createQuery("select a from Author a where a.name = :name").setParameter("name", name).list().get(0);
    	System.out.println (a.toString());
	    return a;
	}
    
    public void updateAuthor(Author author) {
        Session session = openSession();
        session.update(author);
	}

}