/*
 * This class is responsible for connecting the Book class and the database. 
 * Basically, it executes database queries. 
 */

package bookshop.dao.book;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookshop.model.book.Author;
import bookshop.model.book.Book;
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
    // TODO: delete - not used
    public List<Book> getAllBooks() {
    	Session session = openSession();
    	List<Book> booksList = session.createQuery("from Book").list();
        for(Book b : booksList){
            logger.info("Book List::"+b);
        }
        return booksList;
    }
    // Retrieves all the books and sorts them by rating
    // TODO: delete - not used
    @SuppressWarnings("unchecked")
    public List<Book> getBooksSortedByRating() {
    	Session session = openSession();
    	List<Book> booksList = session.createQuery("from Book").list();
        for(Book b : booksList){
            logger.info(b.getTitle() + " with rating " + b.getResultRating());
        }
        Collections.sort(booksList, (b1, b2) -> (int) ((b2.getResultRating() - b1.getResultRating()) * 100));
        logger.info("After sorting:");
        for(Book book: booksList) {
        	logger.info(book.getTitle() + " with rating " + book.getResultRating());
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
     	List<Book> booksList = session.createQuery("from Book b").list();
     	Collections.reverse(booksList);
     	return booksList;
    }
    
    @SuppressWarnings("unchecked")
    public List<Book> doSearchByRating() {
    	Session session = openSession();
     	if (keyword != null)
     			return session.createQuery("select distinct b from Book b inner join b.authors a WHERE UPPER(a.name) LIKE :keyword OR UPPER(b.title) LIKE :keyword OR UPPER(b.description) LIKE :keyword").setParameter("keyword", "%"+keyword.toUpperCase()+"%").list();
     	if (selectedGenre!=null)	
     		return session.createQuery("select distinct b from Book b inner join b.genres g WHERE g IN (:genres)").setParameterList("genres", Arrays.asList(selectedGenre)).list();
     	List<Book> booksList = session.createQuery("from Book b").list();
     	Collections.sort(booksList, (b1, b2) -> (int) ((b2.getResultRating() - b1.getResultRating()) * 100));
     	return booksList;
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
        // System.out.println("Book loaded successfully, Book details = " + b);
        return b;
    }
    
  /*  @Override
    public void persistAuthors(Set <Author> authors) {
        Session session = this.sessionFactory.getCurrentSession();
        authors.stream().forEach(author -> session.persist(author));
    }*/
    
    public Author getAuthor(String name){
    	// System.out.println(getClass().getSimpleName());
    	Session session = openSession();
    	Author a = (Author) session.createQuery("select a from Author a where a.name = :name").setParameter("name", name).list().get(0);
    	// System.out.println (a.toString());
	    return a;
	}
    
    public void updateAuthor(Author author) {
        Session session = openSession();
        session.update(author);
	}

	@Override
	public void rateBook(Long bookId, Integer rating) {
		logger.info("Rating book...");
    	Session session = openSession();  
    	Book b = new Book();
    	try {
    		b = (Book) session.load(Book.class, bookId);
    		logger.info("Book loaded!");
		} catch (Exception e) {
			logger.info("Can't load the book...");
		}
        try {
        	Integer votes = b.getVotes();
        	logger.info("Votes before voting: " + votes);
        	b.setVotes(votes + 1);
        	logger.info("Votes after voting: " + b.getVotes());
		} catch (Exception e) {
			logger.info("Can't set votes...");
		}
        try {
        	Integer r = b.getRating();
        	logger.info("Rating before voting: " + r);
        	b.setRating(r + rating);
        	logger.info("Rating after voting: " + b.getRating());
		} catch (Exception e) {
			logger.info("Can't update rating...");
		}
        try {
        	Double rr = b.getResultRating();
        	logger.info("Result Rating before voting: " + rr);
        	b.setResultRating(b.getRating() * 1.0 / b.getVotes());
        	logger.info("Result Rating after voting: " + b.getResultRating());
		} catch (Exception e) {
			logger.info("Can't update rating...");
		}
        session.update(b);
        logger.debug("Book rated!");
	}

}