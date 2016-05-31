package bookshop.service.user;

import bookshop.model.book.Book;
import bookshop.model.user.User;

import java.util.List; 
 
public interface UserService {
     
    User findById(int id);
     
    User findBySSO(String sso);
     
    void saveUser(User user);
     
    void updateUser(User user);
     
    void deleteUserBySSO(String sso);
 
    List<User> findAllUsers(); 
     
    boolean isUserSSOUnique(Integer id, String sso);

	void addBookToBasket(Long bookId, String ssoId);

	void removeBookFromBasket(Long bookId, String ssoId);

	void commitPurchase(String ssoId);
 
}