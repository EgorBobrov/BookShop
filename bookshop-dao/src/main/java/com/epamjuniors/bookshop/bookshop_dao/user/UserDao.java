package com.epamjuniors.bookshop.bookshop_dao.user;

import java.util.List;

import com.epamjuniors.bookshop.bookshop_model.user.User;
import com.epamjuniors.bookshop.bookshop_model.user.UserAddress;
 
 
public interface UserDao {
 
    User findById(int id);
    User findBySSO(String sso);
    void save(User user);
    void deleteBySSO(String sso);
     
    List<User> findAllUsers();

	void addBookToBasket(Integer bookId, String ssoId);
	void removeBookFromBasket(Integer bookId, String ssoId);
	void commitPurchase(String ssoId);
	
	List<UserAddress> getExistingAddresses(User user);
	void addAddress (User u, UserAddress ua);
	void deleteAddress (User u, Integer addrId);
	UserAddress findAddressByID(Integer addrId);
 
}