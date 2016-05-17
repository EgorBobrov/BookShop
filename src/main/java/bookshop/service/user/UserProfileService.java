package bookshop.service.user;
import java.util.List;
import bookshop.model.user.UserProfile;

public interface UserProfileService {
	
    UserProfile findById(int id);
    
    UserProfile findByType(String type);
     
    List<UserProfile> findAll();
}
