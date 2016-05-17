package bookshop.service.book;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookshop.dao.book.BookDao;
import bookshop.model.book.Book;

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
	public List<Book> getFoundBooks(){
		return this.bookDao.doSearch();
	}

}