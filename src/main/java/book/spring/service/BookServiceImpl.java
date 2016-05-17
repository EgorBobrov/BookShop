package book.spring.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import book.spring.dao.BookDao;
import book.spring.model.Author;
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
	public Book getBookById(Long id) {
		return this.bookDao.getBookById(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		this.bookDao.delete(id);
	}
	
	@Override
	@Transactional
	public List<Book> findBook(String searchInput){
		this.bookDao.setKeyword(searchInput);
		return this.bookDao.doSearch();
	}
	
	@Override
	@Transactional
	public List<Book> getFoundBooks(){
		return this.bookDao.doSearch();
	}
	
	@Override
	@Transactional
	public void persistAuthors(Set <Author> authors) {
		this.bookDao.persistAuthors(authors);
	}

}
