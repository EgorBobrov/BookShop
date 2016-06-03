package bookshop.dao.book;

import java.util.List;

import bookshop.model.book.Author;
import bookshop.model.book.Book;
import bookshop.model.book.Genre;
import bookshop.model.user.User;

public interface BookDao {
	public void persistBook(Book book);
	public void updateBook(Book book);
	public List<Book> getAllBooks();
	public List<Book> getBooksSortedByRating();
	public Book getBookById(Integer id);
	public void delete(Integer id);
	public List<Book> doSearch();
	public List<Book> doSearch(Genre genre);
	public void setKeyword(String searchInput);
	public void setGenre(Genre g);
	
	public Author getAuthor(String name);
	public void updateAuthor(Author author);
	
	public List<Book> doSearchByRating();
	public void rateBook(Integer id, Integer rating);
	public List<Integer> getSimilarBooks(Book b, User u);
}