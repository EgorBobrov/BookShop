package bookshop.service.book;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookshop.dao.book.CommentDao;
import bookshop.model.book.Comment;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	@Override
	public List<Comment> getAll(Long bookId) {
		return commentDao.getAll(bookId);
	}
	@Override
	public Comment getCommentById(Long id) {
		return commentDao.getCommentById(id);
	}
	@Override
	public void persistComment(Long bookId, Comment comment) {
		commentDao.persistComment(bookId, comment);
	}
	@Override
	public void deleteCommentById(Long id) {
		commentDao.deleteCommentById(id);
	}
	
	@Override
	public void updateComment(Long id) {
		commentDao.updateComment(id);
	}
	
	@Override
	public void likeComment(Long id) {
		commentDao.likeComment(id);
	}

}
