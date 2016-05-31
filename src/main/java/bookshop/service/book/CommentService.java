package bookshop.service.book;

import java.util.List;

import bookshop.model.book.Comment;
import bookshop.model.user.User;

public interface CommentService {
	List<Comment> getAll(Long bookId);
	Comment getCommentById(Long id);
	void persistComment(Long bookId, Comment comment);
	void deleteCommentById(Long id);
	void updateComment(Long id);
	void likeComment(Long id, User user);
	void dislikeComment(Long id, User user);
}
