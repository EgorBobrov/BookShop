package bookshop.dao.book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epamjuniors.bookshop.bookshop_model.book.Book;
import com.epamjuniors.bookshop.bookshop_model.book.Comment;
import com.epamjuniors.bookshop.bookshop_model.user.User;

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
	public void deleteCommentById(Integer  id, Integer bookId) {
        Session session = openSession();
        Comment comment = (Comment) session.load(Comment.class, id);
		if (comment != null) {
	    	Book commentedBook = (Book) session.load(Book.class, bookId);
			commentedBook.removeComment(comment);
	      	session.save(commentedBook);
			session.delete(comment);
		}

		logger.info("Comment deleted successfully, details: " + comment);
	}

	//@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getAll(Integer bookId) {
		Session session = sessionFactory.getCurrentSession();
		Book b = (Book) session.load(Book.class, bookId);
		List<Comment> comments = new ArrayList<Comment>(b.getComments());
		Collections.sort(comments);
		return comments;
	}

	@Override
	public Comment getCommentById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Comment comment = (Comment) session.get(Comment.class, id);
		return comment;
	}

	@Override
	public void persistComment(Integer bookId, Comment comment) {
		  Session session = sessionFactory.getCurrentSession();
		  session.save(comment);
		  Book commentedBook = (Book) session.load(Book.class, bookId);
		  commentedBook.getComments().add(comment);
		  session.save(commentedBook);
	}
	
	@Override
	public void updateComment(Integer  id){
		Session session = sessionFactory.getCurrentSession();
		Comment comment = (Comment) session.get(Comment.class, id);
		session.merge(comment);
	}
	
	@Override
	public void likeComment(Integer  id, User user){
		Session session = sessionFactory.getCurrentSession();
		Comment comment = (Comment) session.get(Comment.class, id);
		//in case the comment was already liked, the user can choose to remove the "like"
		if (!comment.isItLikedByMe(user.getSsoId())){
			comment.addLiker(user);
			comment.like();
		} else {
			comment.removeLiker(user);
			comment.unlike();
		}
		session.merge(comment);
	}
	
	@Override
	public void dislikeComment(Integer id, User user){
		Session session = sessionFactory.getCurrentSession();
		Comment comment = (Comment) session.get(Comment.class, id);
		//in case the comment was already disliked, the user can choose to remove the "dislike"
		if (!comment.isItDislikedByMe(user.getSsoId())){
			comment.addDisliker(user);
			comment.dislike();
		} else {
			comment.removeDisliker(user);
			comment.undislike();
		}
		session.merge(comment);
	}

}
