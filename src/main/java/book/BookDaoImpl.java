/*
 * This class is responsible for connecting the Book class and the database. 
 * Basically, it executes database queries. 
 */

package book;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
 
@Component
public class BookDaoImpl implements BookDao {
	
	private String keyword = null;
    // Injected database connection:
    @PersistenceContext private EntityManager em;
 
    // Stores a new book:
    @Transactional
    public void persist(Book book) {
        em.persist(book);
    }
    
    @Transactional
    public void delete(String isbn) {
    	//em.getTransaction().begin();
    	Book book = em.find(Book.class, isbn);
    	em.remove(book);
    	//em.getTransaction().commit();
    }
 
    // Retrieves all the books:
    public List<Book> getAllBooks() {
        TypedQuery<Book> query = em.createQuery(
            "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.authors ORDER BY b.isbn", Book.class);
        return query.getResultList();
    }
    
    public List<Book> doSearch() {
    	if (keyword == null){
    		//the intention here is to return an empty query always when there's no keyword typed in
    		return  em.createQuery(
    	            "SELECT DISTINCT b FROM Book b WHERE b.title = null", Book.class).getResultList();
    	}
        TypedQuery<Book> typedQuery = em.createNamedQuery(Book.SEARCH, Book.class);
        typedQuery.setParameter("keyword", "%" + keyword.toUpperCase() + "%");
        return typedQuery.getResultList();
    }
    
    public void setKeyword(String keyword) {
    	this.keyword = keyword;
    }

    
    public Book getBookByIsbn(String isbn) {
    	// TODO: fill in the functionality
    	return em.find(Book.class, isbn);
    }
}