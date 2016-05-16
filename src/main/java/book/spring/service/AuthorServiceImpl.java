package book.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import book.spring.dao.AuthorDao;
import org.springframework.beans.factory.annotation.Qualifier;

import book.spring.model.Author;

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