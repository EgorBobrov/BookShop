package bookshop.dao.book;

import java.util.List;
import java.util.Set;

import bookshop.model.book.Author;
import bookshop.model.book.Book;

public interface BookDao {
	public void persistBook(Book book);
	public void updateBook(Book book);
	public List<Book> getAllBooks();
	public Book getBookById(Long id);
	public void delete(Long id);
	public List<Book> doSearch();
	public void setKeyword(String searchInput);
	
//	public void persistAuthors(Set<Author> authors);
	public Author getAuthor(String name);
	public void updateAuthor(Author author);
}