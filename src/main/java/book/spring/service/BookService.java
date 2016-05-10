package book.spring.service;

import java.util.List;

import book.spring.model.Book;

public interface BookService {
	public void persistBook(Book book);
	public void updateBook(Book book);
	public List<Book> getAllBooks();
	public Book getBookByIsbn(String isbn);
	public void delete(String isbn);

}
