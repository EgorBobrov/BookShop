package bookshop.service.book;

import java.util.List;
import java.util.Set;

import bookshop.model.book.Author;
import bookshop.model.book.Book;

public interface BookService {
	public void persistBook(Book book);
	public void updateBook(Book book);
	public List<Book> getAllBooks();
	public Book getBookById(Long id);
	public void delete(Long id);
	public List<Book> findBook(String keyword);
	public List<Book> getFoundBooks();
	public void persistAuthors(Set<Author> authors);
	public Author getAuthor(String name);
}