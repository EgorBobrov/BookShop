package bookshop.service.book;

import java.util.List;

import bookshop.model.book.Comment;
import bookshop.model.user.User;

public interface CommentService {
	List<Comment> getAll(Integer bookId);
	Comment getCommentById(Long id);
	void persistComment(Integer bookId, Comment comment);
	void updateComment(Long id);
	void likeComment(Long id, User user);
	void dislikeComment(Long id, User user);
	void deleteCommentById(Long id, Integer bookId);
}
