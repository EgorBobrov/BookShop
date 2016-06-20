package com.epamjuniors.bookshop.book_model;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.epamjuniors.bookshop.bookshop_model.book.Author;


public class TestAuthor{
	
	@Test	
    public void TestToAuthor()
    {
        String text = "Kris Craus,Sylvère Lotringer";
        	
        Set<Author> set = new HashSet <Author>();
        set.add (new Author ("Kris Craus"));
        set.add (new Author ("Sylvère Lotringer"));
        	
        assertEquals("authors input srting is converted to authors set", Author.toAuthor(text), set);
    }
}
