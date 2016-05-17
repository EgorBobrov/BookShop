package bookshop.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookshop.dao.book.AuthorDao;
import bookshop.model.book.Author;

import org.springframework.beans.factory.annotation.Qualifier;

@Service
@Transactional
public class AuthorServiceImpl  implements AuthorService {

	@Autowired @Qualifier("authorDao")
	private AuthorDao authorDao;
	public void setauthorDao(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }
	
	@Override
	//@Transactional
	public void persistAuthor(Author author) {
		this.authorDao.persistAuthor(author);
	}
}