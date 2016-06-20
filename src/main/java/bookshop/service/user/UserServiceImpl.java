package bookshop.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.epamjuniors.bookshop.bookshop_dao.user.UserDao;
import com.epamjuniors.bookshop.bookshop_model.user.User;
import com.epamjuniors.bookshop.bookshop_model.user.UserAddress;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


 
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
	static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao dao;
 
    @Autowired
    private PasswordEncoder passwordEncoder;
     
    public User findById(int id) {
        return dao.findById(id);
    }
 
    public User findBySSO(String sso) {
        User user = dao.findBySSO(sso);
        return user;
    }
 
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }
 
    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends. 
     */
    public void updateUser(User user) {
        User entity = dao.findById(user.getId());
        if(entity!=null){
            entity.setSsoId(user.getSsoId());
            if(!user.getPassword().equals(entity.getPassword())){
                entity.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setEmail(user.getEmail());
            entity.setUserProfiles(user.getUserProfiles());
        }
    }
 
     
    public void deleteUserBySSO(String sso) {
        dao.deleteBySSO(sso);
    }
 
    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }
 
    public boolean isUserSSOUnique(Integer id, String sso) {
        User user = findBySSO(sso);
        return ( user == null || ((id != null) && (user.getId() == id)));
    }

	@Override
	public void addBookToBasket(Integer bookId, String ssoId) {
		dao.addBookToBasket(bookId, ssoId);
		logger.info("Added " + bookId + " to " + ssoId + " inventory.");
	}

	@Override
	public void removeBookFromBasket(Integer bookId, String ssoId) {
		dao.removeBookFromBasket(bookId, ssoId);
	}

	@Override
	public void commitPurchase(String ssoId) {
		dao.commitPurchase(ssoId);
	}
	
	@Override
	public List<UserAddress> getExistingAddresses(User u){
		return dao.getExistingAddresses(u);
	}
	
	@Override
	public void addAddress (User u, UserAddress ua){
		dao.addAddress ( u,  ua);
	}
	
	@Override
	public void deleteAddress (User u, Integer addrId){
		dao.deleteAddress (u, addrId);
	}
     
}