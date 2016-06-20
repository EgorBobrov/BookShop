package bookshop.service.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epamjuniors.bookshop.bookshop_dao.book.BookDao;
import com.epamjuniors.bookshop.bookshop_model.book.Author;
import com.epamjuniors.bookshop.bookshop_model.book.Book;
import com.epamjuniors.bookshop.bookshop_model.book.Genre;
import com.epamjuniors.bookshop.bookshop_model.user.User;

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
	public Book getBookById(Integer id) {
		return this.bookDao.getBookById(id);
	}

	@Override
	public void delete(Integer id) {
		this.bookDao.delete(id);
	}
	
	@Override
	public List<Book> findBook(String searchInput){
		this.bookDao.setKeyword(searchInput);
		return this.bookDao.doSearch();
	}
	
	@Override
	public List<Book> findBook(Genre genre){
		this.bookDao.setKeyword(null);
		this.bookDao.setGenre(genre);
		return this.bookDao.doSearch();
	}
	
	@Override
	public List<Book> getFoundBooks(){
		return this.bookDao.doSearch();
	}
	
	@Override
	public Author getAuthor(String name){
		return this.bookDao.getAuthor(name);
	}

	@Override
	public void rateBook(Integer id, Integer rating) {
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
	
	@Override
	public List<Book> getSimilarBooks(Book b, User u){
		return this.bookDao.getSimilarBooks(b, u);
	}

	@Override
	public List<Book> getLastCommentedBooks() {
		return this.bookDao.getLastCommentedBooks();
	}
	
	@Override
	public void clearSearchResults(){
		this.bookDao.setGenre(null);
		this.bookDao.setKeyword(null);
	}

}
