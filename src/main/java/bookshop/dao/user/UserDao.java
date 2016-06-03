package bookshop.dao.user;

import java.util.List;

import bookshop.model.user.User;
 
 
public interface UserDao {
 
    User findById(int id);
     
    User findBySSO(String sso);
     
    void save(User user);
     
    void deleteBySSO(String sso);
     
    List<User> findAllUsers();

	void addBookToBasket(Integer bookId, String ssoId);

	void removeBookFromBasket(Integer bookId, String ssoId);

	void commitPurchase(String ssoId);
 
}