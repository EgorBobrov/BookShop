package bookshop.service.book;

import java.util.List;

import bookshop.model.book.Author;
import bookshop.model.book.Book;
import bookshop.model.book.Genre;
import bookshop.model.user.User;

public interface BookService {
	public void persistBook(Book book);
	public void updateBook(Book book);
	public List<Book> getAllBooks();
	public List<Book> getBooksSortedByRating();
	public Book getBookById(Integer id);
	public void delete(Integer id);
	public List<Book> findBook(String keyword);
	public List<Book> findBook(Genre genre);
	public List<Book> getFoundBooks();
	public Author getAuthor(String name);
	public void updateAuthor(Author author);
	public void rateBook(Integer id, Integer rating);
	public List<Book> getFoundBooksByRating();
	public List<Book> getSimilarBooks(Book b, User u);
	public List<Book> getLastCommentedBooks();
	public void clearSearchResults();

}