package bookshop.service.book;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookshop.dao.book.BookDao;
import bookshop.model.book.Author;
import bookshop.model.book.Book;
import bookshop.model.book.Genre;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bookDao;
	public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
	
	@Override
	//@Transactional
	public void persistBook(Book book) {
		this.bookDao.persistBook(book);
	}

	@Override
	//@Transactional
	public void updateBook(Book book) {
		this.bookDao.updateBook(book);
	}
	
	@Override
	//@Transactional
	public void updateAuthor(Author author) {
		this.bookDao.updateAuthor(author);
	}

	@Override
	//@Transactional
	public List<Book> getAllBooks() {
		return this.bookDao.getAllBooks();
	}

	@Override
	//@Transactional
	public Book getBookById(Long id) {
		return this.bookDao.getBookById(id);
	}

	@Override
	//@Transactional
	public void delete(Long id) {
		this.bookDao.delete(id);
	}
	
	@Override
	//@Transactional
	public List<Book> findBook(String searchInput){
		this.bookDao.setKeyword(searchInput);
		return this.bookDao.doSearch();
	}
	
	@Override
	//@Transactional
	public List<Book> findBook(Genre genre){
		this.bookDao.setKeyword(null);
		this.bookDao.setGenre(genre);
		return this.bookDao.doSearch();
	}
	
	@Override
	//@Transactional
	public List<Book> getFoundBooks(){
		return this.bookDao.doSearch();
	}
	
	@Override
	//@Transactional
	public void persistAuthors(Set <Author> authors) {
	//	this.bookDao.persistAuthors(authors);
	}
	
	@Override
	public Author getAuthor(String name){
		return this.bookDao.getAuthor(name);
	}

	@Override
	public void rateBook(Long id, Integer rating) {
		this.bookDao.rateBook(id, rating);
	}

	@Override
	public List<Book> getBooksSortedByRating()  {
		return bookDao.getBooksSortedByRating();
	}

	@Override
	public List<Book> getFoundBooksByRating() {
		return bookDao.doSearchByRating();
	}

}
