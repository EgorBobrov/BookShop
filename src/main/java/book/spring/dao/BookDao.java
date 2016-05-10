package book.spring.dao;

import java.util.List;

import book.spring.model.Book;

public interface BookDao {
	public void persistBook(Book book);
	public void updateBook(Book book);
	public List<Book> getAllBooks();
	public Book getBookByIsbn(String isbn);
	public void delete(String isbn);
	//public List<Book> doSearch();
	//public void setKeyword(String searchInput);
}
