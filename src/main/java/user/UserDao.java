package user;

import java.util.List;

import book.Book;

public interface UserDao {
    public void persist(User user); 
    public List<User> getAllUsers(); 
	public Book getUserById(Long id);
	public void delete(User user);
}
