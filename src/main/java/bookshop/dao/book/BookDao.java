package bookshop.dao.book;

import java.util.List;

import bookshop.model.book.Author;
import bookshop.model.book.Book;
import bookshop.model.book.Genre;

public interface BookDao {
	public void persistBook(Book book);
	public void updateBook(Book book);
	public List<Book> getAllBooks();
	public List<Book> getBooksSortedByRating();
	public Book getBookById(Long id);
	public void delete(Long id);
	public List<Book> doSearch();
	public List<Book> doSearch(Genre genre);
	public void setKeyword(String searchInput);
	public void setGenre(Genre g);
	
//	public void persistAuthors(Set<Author> authors);
	public Author getAuthor(String name);
	public void updateAuthor(Author author);
	
/*	public Set<Comment> getCommentsByBookId(Book book);
	public void addComment(Comment comment);
*/	
	public List<Book> doSearchByRating();
	public void rateBook(Long id, Integer rating);
}