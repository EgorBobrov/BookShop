package bookshop.dao.book;

import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bookshop.model.book.Book;
import bookshop.model.book.Comment;

@Repository
public class CommentDaoImpl implements CommentDao {
	
	private static final Logger logger = LoggerFactory.getLogger(CommentDaoImpl.class);

	@Autowired
    private SessionFactory sessionFactory; 

	private Session openSession() {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void deleteCommentById(Long id) {
        Session session = openSession();
        Comment comment = (Comment) session.load(Comment.class, id);
		if (comment != null) {
			session.delete(comment);
		}
		logger.info("Comment deleted successfully, details: " + comment);
	}

	@Override
	public List<Comment> getAll(Long bookId) {
		Session session = sessionFactory.getCurrentSession();
		Book b = (Book) session.load(Book.class, bookId);
		return new ArrayList<Comment>(b.getComments());
	}

	@Override
	public Comment getCommentById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Comment comment = (Comment) session.get(Comment.class, id);
		return comment;
	}

	@Override
	public void persistComment(Long bookId, Comment comment) {
		  Session session = sessionFactory.getCurrentSession();
		  session.save(comment);
		  Book commentedBook = (Book) session.load(Book.class, bookId);
		  commentedBook.getComments().add(comment);
		  session.save(commentedBook);
	}

}
