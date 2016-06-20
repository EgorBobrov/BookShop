package bookshop.service.user;


import java.util.List;

import com.epamjuniors.bookshop.bookshop_model.user.User;
import com.epamjuniors.bookshop.bookshop_model.user.UserAddress; 
 
public interface UserService {
     
    User findById(int id);
     
    User findBySSO(String sso);
     
    void saveUser(User user);
     
    void updateUser(User user);
     
    void deleteUserBySSO(String sso);
 
    List<User> findAllUsers(); 
     
    boolean isUserSSOUnique(Integer id, String sso);

	void addBookToBasket(Integer bookId, String ssoId);

	void removeBookFromBasket(Integer bookId, String ssoId);

	void commitPurchase(String ssoId);

	List<UserAddress> getExistingAddresses(User u);
	
	void addAddress (User u, UserAddress ua);
	void deleteAddress (User u, Integer addrId);
 
}