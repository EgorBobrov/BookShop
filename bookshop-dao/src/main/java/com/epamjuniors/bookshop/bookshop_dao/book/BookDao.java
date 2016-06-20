package com.epamjuniors.bookshop.bookshop_dao.book;

import java.util.List;

import com.epamjuniors.bookshop.bookshop_model.book.Author;
import com.epamjuniors.bookshop.bookshop_model.book.Book;
import com.epamjuniors.bookshop.bookshop_model.book.Genre;
import com.epamjuniors.bookshop.bookshop_model.user.User;

public interface BookDao {
	public Book persistBook(Book book);
	public void updateBook(Book book);
	public List<Book> getAllBooks();
	public List<Book> getBooksSortedByRating();
	public Book getBookById(Integer id);
	public void delete(Integer id);
	public List<Book> doSearch();
	public List<Book> doSearch(Genre genre);
	public void setKeyword(String searchInput);
	public void setGenre(Genre g);
	
	public Author getAuthor(String name);
	public void updateAuthor(Author author);
	
	public List<Book> doSearchByRating();
	public void rateBook(Integer id, Integer rating);
	public List<Book> getSimilarBooks(Book b, User u);
	public List<Book> getLastCommentedBooks();
}