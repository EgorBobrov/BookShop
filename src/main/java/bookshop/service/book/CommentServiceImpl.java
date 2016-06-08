package bookshop.service.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookshop.dao.book.CommentDao;
import bookshop.model.book.Comment;
import bookshop.model.user.User;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	@Override
	public List<Comment> getAll(Integer bookId) {
		return commentDao.getAll(bookId);
	}
	@Override
	public Comment getCommentById(Long id) {
		return commentDao.getCommentById(id);
	}
	@Override
	public void persistComment(Integer bookId, Comment comment) {
		commentDao.persistComment(bookId, comment);
	}
	@Override
	public void deleteCommentById(Long id, Integer bookId) {
		commentDao.deleteCommentById(id, bookId);
	}
	
	@Override
	public void updateComment(Long id) {
		commentDao.updateComment(id);
	}
	
	@Override
	public void likeComment(Long id, User user) {
		commentDao.likeComment(id, user);
	}
	
	@Override
	public void dislikeComment(Long id, User user) {
		commentDao.dislikeComment(id, user);
	}

}
