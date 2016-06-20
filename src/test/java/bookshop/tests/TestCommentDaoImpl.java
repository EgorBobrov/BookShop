package bookshop.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import bookshop.dao.book.CommentDao;
import bookshop.dao.user.UserDao;
import com.epamjuniors.bookshop.bookshop_model.book.Comment;
import com.epamjuniors.bookshop.bookshop_model.user.User;
import bookshop.tests.configuration.TestHibernateConfiguration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration (classes = { TestHibernateConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
@TransactionConfiguration(defaultRollback=true, transactionManager="transactionManager")  
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DbUnitTestExecutionListener.class})
@DatabaseSetup("/dbxml/books.xml")
public class TestCommentDaoImpl extends AbstractTransactionalJUnit4SpringContextTests {
	
	//@Autowired
	//private BookDao bookDao;
	
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private UserDao userDao;
	
	
	@Test 
	public void testGetAll(){
		assertTrue("at first, no comments for book #2", commentDao.getAll(2).size() == 0);
		Comment newComment = new Comment ("Wed, 8 Jun 2016 19:11:01 +0300", "Love it!", "chichi");
		commentDao.persistComment(2, newComment);
		assertTrue("now there is one comment", commentDao.getAll(2).size() == 1);
	}
	
	@Test
	public void testDeleteCommentById(){
		commentDao.deleteCommentById(1, 1);
		assertTrue("after one of the comments for book 1 was deleted, only one is in db", commentDao.getAll(1).size() == 1);
	}
	
	@Test 
	public void testGetCommentById(){
		Comment comm = commentDao.getCommentById(2);
		assertEquals("date is stored", comm.getDate(), "Wed, 8 Jun 2016 12:11:01 +0300");
		assertEquals("content is stored", comm.getText(), "But then it was kind of meh");
		assertEquals("user who left the comment is stored", comm.getUser(), "alaska");
	}
 
	@Test 
	public void persistComment() {
		assertTrue("at first, no comments for book #2", commentDao.getAll(2).size() == 0);
		Comment newComment = new Comment ("Wed, 8 Jun 2016 19:11:01 +0300", "Love it!", "chichi");
		commentDao.persistComment(2, newComment);
		List<Comment> commList = commentDao.getAll(2);
		assertTrue("now there is one comment", commList.size() == 1);
		assertEquals("the text is what we saved", commList.get(0).getText(), "Love it!");
	}
	
	@Test 
	public void testUpdateComment(){
		Comment comm = commentDao.getCommentById(2);
		comm.setText("And then I decided it's my favorite book.");
		commentDao.updateComment(2);
		assertEquals("text is changed", commentDao.getCommentById(2).getText(), "And then I decided it's my favorite book.");
	}
	
	@Test 
	public void testLikeComment(){
		User chichi = userDao.findBySSO("chichi");
		commentDao.likeComment(1, chichi);
		Comment comm = commentDao.getCommentById(1);
		assertEquals("amount of likes = 1", comm.getLikes(), 1);
		commentDao.likeComment(1, chichi);
		comm = commentDao.getCommentById(1);
		assertEquals("comment can be unliked", comm.getLikes(), 0);
	}
	
	@Test 
	public void testDislikeComment(){
		User chichi = userDao.findBySSO("chichi");
		commentDao.dislikeComment(1, chichi);
		Comment comm = commentDao.getCommentById(1);
		assertEquals("amount of dislikes = 1", comm.getDislikes(), 1);	
	}

}
