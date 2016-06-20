package bookshop.service.book;

import java.util.List;

import com.epamjuniors.bookshop.bookshop_model.book.Comment;
import com.epamjuniors.bookshop.bookshop_model.user.User;

public interface CommentService {
	List<Comment> getAll(Integer bookId);
	Comment getCommentById(Integer  id);
	void persistComment(Integer bookId, Comment comment);
	void updateComment(Integer  id);
	void likeComment(Integer  id, User user);
	void dislikeComment(Integer  id, User user);
	void deleteCommentById(Integer  id, Integer bookId);
}
