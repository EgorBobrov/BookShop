package book.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import book.spring.dao.BookDao;
import book.spring.model.Book;

public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bookDao;
	public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
	
	@Override
	@Transactional
	public void persistBook(Book book) {
		this.bookDao.persistBook(book);
		
	}

	@Override
	@Transactional
	public void updateBook(Book book) {
		this.bookDao.updateBook(book);
	}

	@Override
	@Transactional
	public List<Book> getAllBooks() {
		return this.bookDao.getAllBooks();
	}

	@Override
	@Transactional
	public Book getBookByIsbn(String isbn) {
		return this.bookDao.getBookByIsbn(isbn);
	}

	@Override
	@Transactional
	public void delete(String isbn) {
		this.bookDao.delete(isbn);
	}

}
