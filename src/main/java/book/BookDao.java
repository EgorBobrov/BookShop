package book;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
 
@Component
public class BookDao {
    // Injected database connection:
    @PersistenceContext private EntityManager em;
 
    // Stores a new guest:
    @Transactional
    public void persist(Book book) {
        em.persist(book);
    }
 
    // Retrieves all the guests:
    public List<Book> getAllBooks() {
        TypedQuery<Book> query = em.createQuery(
            "SELECT b FROM Book b ORDER BY b.id", Book.class);
        return query.getResultList();
    }
}