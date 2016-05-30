package bookshop.dao.book;

import bookshop.model.book.Comment;
import java.util.List;

public interface CommentDao {
	List<Comment> getAll(Long bookId);
	Comment getCommentById(Long id);
	void persistComment(Long bookId, Comment comment);
	void deleteCommentById(Long id);
	void updateComment(Long id);
	void likeComment(Long id);
}
