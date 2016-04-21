package user;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class UserController {
 
    @Autowired
    private UserDao userDao;
 
    @RequestMapping(value="/users")
    public ModelAndView userlist(HttpServletRequest request) {
        // Handle a new guest (if any):
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        if (name != null)
        	userDao.persist(new User(name, email));
 
        // Prepare the result view (guest.jsp):
        return new ModelAndView("users.jsp", "userDao", userDao);
    }

}