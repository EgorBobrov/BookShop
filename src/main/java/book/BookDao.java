package book;

import java.util.List;

public interface BookDao {
	public void persist(Book book);
	public List<Book> getAllBooks();
	public Book getBookById(Long id);
	public void delete(Book book);
}
