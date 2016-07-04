package bookshop.tests.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

import bookshop.model.user.User;

public class TestUser {
	
	@Test	
	public void testEquals() {
		
		User u1 = new User("robby", "123");
		User u2 = new User("bobby", "123");
		User u3 = new User("bobby", "12345");
     
        assertEquals ("users with same handle are equal", u2, u3);
        assertNotSame ("users with diff handle are NOT equal", u1, u2);
	}

}