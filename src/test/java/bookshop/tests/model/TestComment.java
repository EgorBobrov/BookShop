package bookshop.tests.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import bookshop.model.book.Comment;

public class TestComment {
	
	
	@Test
	public void testGetLastCommentDate() {
			
		List <Comment> list = new ArrayList<Comment>();	
		list.add(new Comment ("Wed, 8 Jun 2016 19:11:09 +0300", "Love it!", "chichi"));
		list.add(new Comment ("Wed, 8 Jun 2016 19:11:06 +0300", "Hate it!", "chichi"));
		list.add(new Comment ("Thu, 9 Jun 2016 19:11:01 +0300", "Whatever!", "chichi"));
		
		Long seconds = (long) 1465488661;
		
		assertEquals("the latest comment date is returned (as toEpochSecond())", Comment.getLastCommentDate(list), seconds);
		
	}
	
	@Test 
	public void testCompareToComments(){
		Comment c1 = new Comment ("Wed, 8 Jun 2016 19:11:09 +0300", "Love it!", "chichi");
		Comment c2 = new Comment ("Wed, 8 Jun 2016 19:11:06 +0300", "Hate it!", "chichi");
		Comment c3 = new Comment ("Thu, 9 Jun 2016 19:11:01 +0300", "Whatever!", "chichi");
		Comment c4 = new Comment ("Thu, 9 Jun 2016 19:11:01 +0300", "Books are nice for kids", "momma dawn");
		
		assertEquals("the comments are equal as they are posted @ the same second", c3.compareTo(c4), 0);
		assertEquals("the one which is posted later is considered bigger", c1.compareTo(c2), 1);
		assertEquals("the one which is posted earlier is considered smaller", c1.compareTo(c3), -1);
	}
}