package bookshop.dao.book;

import bookshop.model.book.Comment;
import bookshop.model.user.User;

import java.util.List;

public interface CommentDao {
	List<Comment> getAll(Integer bookId);
	Comment getCommentById(Integer  id);
	void persistComment(Integer bookId, Comment comment);
	void deleteCommentById(Integer  id, Integer bookId);
	void updateComment(Integer  id);
	void likeComment(Integer  id, User user);
	void dislikeComment(Integer  id, User user);
}
