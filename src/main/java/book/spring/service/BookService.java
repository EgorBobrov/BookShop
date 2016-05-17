package book.spring.service;

import java.util.List;
import java.util.Set;

import book.spring.model.Author;
import book.spring.model.Book;

public interface BookService {
	public void persistBook(Book book);
	public void updateBook(Book book);
	public List<Book> getAllBooks();
	public Book getBookById(Long id);
	public void delete(Long id);
	public List<Book> findBook(String keyword);
	public List<Book> getFoundBooks();
	public void persistAuthors(Set<Author> authors);

}