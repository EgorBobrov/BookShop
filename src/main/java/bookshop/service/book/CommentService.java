package bookshop.service.book;

import java.util.List;

import bookshop.model.book.Comment;

public interface CommentService {
	List<Comment> getAll(Long bookId);
	Comment getCommentById(Long id);
	void persistComment(Long bookId, Comment comment);
	void deleteCommentById(Long id);
}
