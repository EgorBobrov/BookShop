package user;

import java.util.List;

import book.spring.model.Book;

public interface UserDao {
    public void persist(User user); 
    public List<User> getAllUsers(); 
	public User getUserByEmail(String email);
	public Boolean isAdmin(String email);
	public void delete(String email);
}
