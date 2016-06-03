package bookshop.dao.book;

import bookshop.model.book.Comment;
import bookshop.model.user.User;

import java.util.List;

public interface CommentDao {
	List<Comment> getAll(Integer bookId);
	Comment getCommentById(Long id);
	void persistComment(Integer bookId, Comment comment);
	void deleteCommentById(Long id);
	void updateComment(Long id);
	void likeComment(Long id, User user);
	void dislikeComment(Long id, User user);
}
