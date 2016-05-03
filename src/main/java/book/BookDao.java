package book;

import java.util.List;

public interface BookDao {
	public void persist(Book book);
	public List<Book> getAllBooks();
	public Book getBookByIsbn(String isbn);
	public void delete(String isbn);
}
