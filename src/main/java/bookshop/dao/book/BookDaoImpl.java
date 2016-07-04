/*
 * This class is responsible for connecting the Book class and the database. 
 * Basically, it executes database queries. 
 */

package bookshop.dao.book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bookshop.model.book.Author;
import bookshop.model.book.Book;
import bookshop.model.book.Comment;
import bookshop.model.book.Genre;
import bookshop.model.user.User;

@Repository
@Transactional
public class BookDaoImpl implements BookDao {
	
	private static final Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);
	private String keyword = null;
	private Genre selectedGenre = null;
	
    public void setKeyword(String keyword) {
    	this.keyword = keyword;
    }
    
    public void setGenre(Genre g) {
    	this.selectedGenre = g;
    }
	
	@Autowired
    private SessionFactory sessionFactory; 
	
	@Autowired
    private CommentDao commentDao;

	private Session openSession() {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		return sessionFactory.getCurrentSession();
	}

   // Stores a new book:
    public Book persistBook(Book book) {
        Session session = openSession();
        session.merge(book);
        logger.info("Book added successfully. Details: " + book);
        return book;
    }

    public void updateBook(Book book) {
        Session session = openSession();
        session.update(book);
        logger.info("Book updated successfully. Details: " + book);
	}

    public void delete(Integer id) {
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
	public List<Book> doSearch(Genre genre) {
    	Session session = openSession();
     	return session.createQuery("select distinct b from Book b inner join b.genres g WHERE g IN (:genres)").setParameterList("genres", Arrays.asList(genre)).list();
    }
    
    //getting a list of books which were purchased by the same user who purchased the current book
    @SuppressWarnings("unchecked")
    public List<Book> getSimilarBooks(Book b, User u){
    	Session session = openSession();
    	String sql = (u==null) ? String.format("SELECT DISTINCT b.id as rank FROM BOOK b JOIN USER_INVENTORY ui ON b.id = ui.book_id WHERE b.id<>%1$d  AND ui.user_id IN (SELECT DISTINCT us.id FROM APP_USER us JOIN USER_INVENTORY inv ON us.id = inv.user_id WHERE inv.book_id = %1$d) GROUP BY b.id ORDER BY rank DESC, b.title;",
    			b.getId())
    			: String.format("SELECT DISTINCT b.id as rank FROM BOOK b JOIN USER_INVENTORY ui ON b.id = ui.book_id WHERE b.id<>%1$d  AND ui.user_id IN (SELECT DISTINCT us.id FROM APP_USER us JOIN USER_INVENTORY inv ON us.id = inv.user_id WHERE inv.book_id = %1$d  AND us.id<>%2$d ) GROUP BY b.id ORDER BY rank DESC, b.title;",
    			b.getId(), u.getId());
    	Query query = session.createSQLQuery(sql);
    	List<Integer> bookIdList = (List<Integer>) query.list();
    	List<Book> bBooks = new ArrayList<Book>();
    	bookIdList.stream().forEach(bId -> bBooks.add(this.getBookById(bId)));

     	return bBooks;
    }

    public Book getBookById(Integer id) {
    	Session session = openSession();      
        Book b = (Book) session.load(Book.class, id);
        return b;
    }
    
    public Author getAuthor(String name){
    	Session session = openSession();
    	Author a = (Author) session.createQuery("select a from Author a where a.name = :name").setParameter("name", name).list().get(0);
	    return a;
	}
    
    public void updateAuthor(Author author) {
        Session session = openSession();
        session.update(author);
	}

	@Override
	public void rateBook(Integer bookId, Integer rating) {
    	Session session = openSession();  
    	Book b = new Book();
    	try {
    		b = (Book) session.load(Book.class, bookId);
		} catch (Exception e) {
		}
        try {
        	Integer votes = b.getVotes();
        	b.setVotes(votes + 1);
		} catch (Exception e) {
			logger.info("Can't set votes...");
		}
        try {
        	Integer r = b.getRating();
        	b.setRating(r + rating);
		} catch (Exception e) {
			logger.info("Can't update rating...");
		}
        try {
        	b.setResultRating(b.getRating() * 1.0 / b.getVotes());
		} catch (Exception e) {
			logger.info("Can't update rating...");
		}
        session.update(b);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getLastCommentedBooks() {
    	Session session = openSession();
     	if (keyword != null)
     			return session.createQuery("select distinct b from Book b inner join b.authors a WHERE UPPER(a.name) LIKE :keyword OR UPPER(b.title) LIKE :keyword OR UPPER(b.description) LIKE :keyword").setParameter("keyword", "%"+keyword.toUpperCase()+"%").list();
     	if (selectedGenre!=null)	
     		return session.createQuery("select distinct b from Book b inner join b.genres g WHERE g IN (:genres)").setParameterList("genres", Arrays.asList(selectedGenre)).list();
     	List<Book> booksList = session.createQuery("from Book b").list();
     	Collections.sort(booksList, (b1, b2) -> (int) ((Comment.getLastCommentDate(commentDao.getAll(b2.getId())) - Comment.getLastCommentDate(commentDao.getAll(b1.getId())))));
     	return booksList;

	}

}