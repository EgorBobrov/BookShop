package com.epamjuniors.bookshop.book_model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.epamjuniors.bookshop.bookshop_model.book.Author;
import com.epamjuniors.bookshop.bookshop_model.book.Book;
import com.epamjuniors.bookshop.bookshop_model.book.Genre;


public class TestBook {
	
	@Test	
	public void testEquals() {
		
		Set<Author> authors1 = new HashSet<Author>();
 	    authors1.add(new Author("Margaret Mitchell"));
 	    Set<Genre> genres1 = new HashSet<Genre>();
 	    genres1.addAll(Arrays.asList(new Genre[] {Genre.ROMANCE, Genre.FICTION}));
 	    
		Book first = new Book("978-1416548942", "Gone with the Wind", 1472, "The best novel to have ever come out of the South...it is unsurpassed in the whole of American writing.", 
    			  authors1, genres1, 23, 0.1);
        Book second = new Book("978-141654834", "Came Back with the Wind", 1772, "Fascinating and unforgettable! A remarkable book, a spectacular book, a book that will not be forgotten!", 
      			  authors1, genres1, 30, 0.);
        Book third = new Book("978-141654834", "Came Back with the Wind", 1772, "Fascinating and unforgettable! A remarkable book, a spectacular book, a book that will not be forgotten!", 
    			  authors1, genres1, 30, 0.);
        
        assertEquals ("books with same isbn and title are equal", second, third);
        assertNotSame ("books with diff isbn and title are not equal", second, first);
        assertNotSame ("books with diff isbn and title are not equal #2", first, third);
	}

}
