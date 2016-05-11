package book.spring.dao;

import java.util.List;

import book.spring.model.Book;

public interface BookDao {
	public void persistBook(Book book);
	public void updateBook(Book book);
	public List<Book> getAllBooks();
	public Book getBookById(Long id);
	public void delete(Long id);
	public List<Book> doSearch();
	public void setKeyword(String searchInput);
}
